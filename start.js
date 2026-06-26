#!/usr/bin/env node
'use strict';

const { spawn, execSync } = require('child_process');
const path = require('path');

const ROOT = __dirname;
const FRONTEND_DIR = path.join(ROOT, 'frontend');
const isWin = process.platform === 'win32';
const isMac = process.platform === 'darwin';

let backendProc = null;
let frontendProc = null;

function log(tag, msg) {
  const ts = new Date().toLocaleTimeString();
  console.log(`[${ts}] [${tag}] ${msg}`);
}

function run(cmd, args, opts = {}) {
  return spawn(
    isWin ? 'cmd.exe' : cmd,
    isWin ? ['/c', cmd, ...args] : args,
    { ...opts, stdio: opts.stdio || ['ignore', 'inherit', 'inherit'], shell: !!isWin }
  );
}

function cmdExists(cmd) {
  try {
    execSync(isWin ? `where ${cmd} 2>nul` : `which ${cmd} 2>/dev/null`, { stdio: 'ignore' });
    return true;
  } catch {
    return false;
  }
}

function getJavaVersion() {
  try {
    const out = execSync('java -version 2>&1', { encoding: 'utf8' });
    const m = out.match(/(?:version\s+)"?(\d+)/);
    return m ? parseInt(m[1], 10) : 0;
  } catch {
    return 0;
  }
}

function getNodeVersion() {
  try {
    const out = execSync('node -v', { encoding: 'utf8' });
    const m = out.match(/v?(\d+)/);
    return m ? parseInt(m[1], 10) : 0;
  } catch {
    return 0;
  }
}

function checkMaven() { return cmdExists('mvn'); }
function checkNpm()   { return cmdExists('npm'); }

function getPackageManager() {
  if (isWin) {
    if (cmdExists('winget')) return 'winget';
    if (cmdExists('choco'))  return 'choco';
    return null;
  }
  if (isMac) return cmdExists('brew') ? 'brew' : null;
  for (const pm of ['apt-get', 'dnf', 'yum', 'apk']) {
    if (cmdExists(pm)) return pm;
  }
  return null;
}

function installPkg(pm, pkg) {
  log('SETUP', `Installing via ${pm}...`);
  try {
    let cmd;
    if (pm === 'winget') {
      cmd = `winget install --id ${pkg} --accept-package-agreements --accept-source-agreements`;
    } else if (pm === 'choco') {
      cmd = `choco install -y ${pkg}`;
    } else if (pm === 'brew') {
      cmd = `brew install ${pkg}`;
    } else if (pm === 'apk') {
      cmd = `apk update && apk add ${pkg}`;
    } else if (pm === 'apt-get') {
      cmd = `apt-get update -y && apt-get install -y ${pkg}`;
    } else {
      cmd = `${pm} install -y ${pkg}`;
    }

    const needsElevation =
      !isWin && !isMac && process.getuid && process.getuid() !== 0 && cmdExists('sudo');
    const prefix = needsElevation ? 'sudo ' : '';
    execSync(`${prefix}${cmd} 2>&1`, { stdio: 'inherit', timeout: 180000 });
    return true;
  } catch {
    return false;
  }
}

function installJava() {
  log('SETUP', 'Java 21+ required. Attempting installation...');
  const pm = getPackageManager();
  if (pm === 'winget') return installPkg(pm, 'EclipseAdoptium.Temurin.21.JDK');
  if (pm === 'choco')  return installPkg(pm, 'temurin21');
  if (pm === 'brew') {
    if (installPkg(pm, 'openjdk@21')) {
      log('SETUP', 'Run: sudo ln -sfn /usr/local/opt/openjdk@21/libexec/openjdk.jdk /Library/Java/JavaVirtualMachines/openjdk-21.jdk');
      return true;
    }
    return false;
  }
  if (pm === 'apt-get') return installPkg(pm, 'openjdk-21-jdk');
  if (pm === 'dnf' || pm === 'yum') return installPkg(pm, 'java-21-openjdk-devel');
  if (pm === 'apk') return installPkg(pm, 'openjdk21');
  log('SETUP', 'No supported package manager found.');
  log('SETUP', 'Download JDK 21 from https://adoptium.net/temurin/releases/?version=21');
  return false;
}

function installMaven() {
  log('SETUP', 'Maven required. Attempting installation...');
  const pm = getPackageManager();
  if (pm === 'winget') return installPkg(pm, 'Apache.Maven');
  if (pm === 'choco')  return installPkg(pm, 'maven');
  if (pm === 'brew')   return installPkg(pm, 'maven');
  if (pm)              return installPkg(pm, 'maven');
  log('SETUP', 'Download Maven from https://maven.apache.org/download.cgi');
  return false;
}

function installNode() {
  log('SETUP', 'Node.js 20+ required. Attempting installation...');
  const pm = getPackageManager();
  if (pm === 'winget') return installPkg(pm, 'OpenJS.NodeJS.20');
  if (pm === 'choco')  return installPkg(pm, 'nodejs-lts');
  if (pm === 'brew')   return installPkg(pm, 'node');
  if (pm === 'apt-get') {
    log('SETUP', 'Using NodeSource setup for Node.js 20...');
    try {
      const s = (process.getuid && process.getuid() !== 0 && cmdExists('sudo')) ? 'sudo ' : '';
      execSync(`${s}apt-get update -y && ${s}apt-get install -y ca-certificates curl gnupg 2>&1`, { stdio: 'inherit', timeout: 60000 });
      execSync(`curl -fsSL https://deb.nodesource.com/setup_20.x | ${s}bash - 2>&1`, { stdio: 'inherit', timeout: 60000 });
      execSync(`${s}apt-get install -y nodejs 2>&1`, { stdio: 'inherit', timeout: 120000 });
      return true;
    } catch { return false; }
  }
  if (pm === 'dnf' || pm === 'yum') return installPkg(pm, 'nodejs');
  if (pm === 'apk') return installPkg(pm, 'nodejs');
  log('SETUP', 'Download Node.js 20+ from https://nodejs.org/');
  return false;
}

function checkAll() {
  log('SETUP', 'Verifying system dependencies...');

  let pass = true;
  let changed = false;

  const jVer = getJavaVersion();
  if (jVer >= 21) {
    log('SETUP', `[OK] Java ${jVer}`);
  } else if (jVer > 0) {
    log('SETUP', `[WARN] Java ${jVer} found, need 21+`);
    if (installJava()) { changed = true; pass = getJavaVersion() >= 21; }
    else { pass = false; }
  } else {
    log('SETUP', '[MISS] Java not found');
    if (installJava()) { changed = true; pass = getJavaVersion() >= 21; }
    else { pass = false; }
  }

  if (checkMaven()) {
    log('SETUP', '[OK] Maven');
  } else {
    log('SETUP', '[MISS] Maven not found');
    if (installMaven()) { changed = true; pass = checkMaven() && pass; }
    else { pass = false; }
  }

  const nVer = getNodeVersion();
  if (nVer >= 20) {
    log('SETUP', `[OK] Node.js ${nVer}`);
  } else if (nVer > 0) {
    log('SETUP', `[WARN] Node.js ${nVer} found, need 20+`);
    if (installNode()) { changed = true; pass = getNodeVersion() >= 20 && pass; }
    else { pass = false; }
  } else {
    log('SETUP', '[MISS] Node.js not found');
    if (installNode()) { changed = true; pass = getNodeVersion() >= 20 && pass; }
    else { pass = false; }
  }

  if (checkNpm()) {
    log('SETUP', '[OK] npm');
  } else {
    log('SETUP', '[MISS] npm not found (bundled with Node.js)');
    pass = false;
  }

  if (!pass) {
    log('ERROR', 'Some dependencies could not be resolved automatically.');
    log('ERROR', 'Install missing tools manually (see INSTALL.md) and re-run.');
    process.exit(1);
  }

  if (changed) log('SETUP', 'All dependencies satisfied.');
}

function runBackend() {
  log('BACKEND', 'Compiling project...');

  const compile = run('mvn', ['compile', '-q']);
  compile.on('close', (code) => {
    if (code !== 0) {
      log('BACKEND', `Compilation failed (exit ${code}).`);
      return;
    }
    log('BACKEND', 'Starting backend (dev profile)...');
    backendProc = run('mvn', ['spring-boot:run', '-Dspring-boot.run.profiles=dev']);
    backendProc.on('error', (err) => {
      log('BACKEND', `Failed to start: ${err.message}`);
    });
    backendProc.on('exit', () => { backendProc = null; });
  });
}

function runFrontend() {
  log('FRONTEND', 'Installing frontend dependencies...');

  const installProc = run('npm', ['install', '--silent'], { cwd: FRONTEND_DIR });
  installProc.on('close', (code) => {
    if (code !== 0) {
      log('FRONTEND', `npm install failed (exit ${code}).`);
      return;
    }
    log('FRONTEND', 'Starting frontend dev server...');
    frontendProc = run('npm', ['run', 'dev'], { cwd: FRONTEND_DIR });
    frontendProc.on('error', (err) => {
      log('FRONTEND', `Failed to start: ${err.message}`);
    });
    frontendProc.on('exit', () => { frontendProc = null; });
  });
}

function printInfo() {
  console.log('');
  console.log('  ===============================================');
  console.log('    Smart Mobility System - Unified Launcher');
  console.log('  ===============================================');
  console.log('');
  log('INFO', 'Backend  -> http://localhost:8080/api/v1');
  log('INFO', 'Frontend -> http://localhost:5173');
  log('INFO', 'Swagger  -> http://localhost:8080/api/v1/swagger-ui.html');
  log('INFO', 'Press Ctrl+C to stop all services.');
  console.log('');
  log('INFO', 'Test credentials (dev profile):');
  log('INFO', '  User:   test@smartmobility.com / password');
  log('INFO', '  Tech:   operatore.tecnico@smartmobility.com / password');
  log('INFO', '  Social: operatore.sc@smartmobility.com / password');
  log('INFO', '  PA:     pa@smartmobility.com / password');
}

function shutdown() {
  log('SHUTDOWN', 'Stopping all services...');
  if (backendProc)  backendProc.kill(isWin ? 'SIGINT' : 'SIGTERM');
  if (frontendProc) frontendProc.kill(isWin ? 'SIGINT' : 'SIGTERM');
  setTimeout(() => process.exit(0), 2000);
}

process.on('SIGINT', shutdown);
process.on('SIGTERM', shutdown);

printInfo();
checkAll();
runBackend();
runFrontend();
