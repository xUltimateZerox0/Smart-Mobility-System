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
    execSync(isWin ? `where ${cmd}` : `which ${cmd}`, { stdio: 'ignore' });
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
  if (isWin) return cmdExists('choco') ? 'choco' : null;
  if (isMac) return cmdExists('brew') ? 'brew' : null;
  for (const pm of ['apt-get', 'dnf', 'yum', 'apk']) {
    if (cmdExists(pm)) return pm;
  }
  return null;
}

function installPkg(pm, pkg) {
  log('SETUP', `Installing \`${pkg}\` via ${pm}...`);
  try {
    let pre = '';
    let post = `install -y ${pkg}`;
    if (pm === 'apt-get') {
      pre = 'apt-get update -y && ';
    } else if (pm === 'apk') {
      pre = 'apk update && ';
      post = `add ${pkg}`;
    } else if (pm === 'brew') {
      post = `install ${pkg}`;
    } else if (pm === 'choco') {
      post = `install -y ${pkg}`;
    } else if (pm === 'dnf' || pm === 'yum') {
      post = `install -y ${pkg}`;
    }

    const needsSudo = !isWin && !isMac && process.getuid && process.getuid() !== 0;
    const prefix = needsSudo && cmdExists('sudo') ? 'sudo ' : '';
    execSync(`${prefix}${pm} ${pre}${post} 2>&1`, { stdio: 'inherit', timeout: 180000 });
    return true;
  } catch {
    return false;
  }
}

function installJava() {
  log('SETUP', 'Java 21+ required. Attempting installation...');
  if (isWin) {
    log('SETUP', 'Download JDK 21 from https://adoptium.net/temurin/releases/?version=21');
    log('SETUP', 'After install, set JAVA_HOME and add java to PATH, then re-run this script.');
    return false;
  }
  if (isMac) {
    if (installPkg('brew', 'openjdk@21')) {
      log('SETUP', 'Run: sudo ln -sfn /usr/local/opt/openjdk@21/libexec/openjdk.jdk /Library/Java/JavaVirtualMachines/openjdk-21.jdk');
      return true;
    }
    return false;
  }
  const pm = getPackageManager();
  if (!pm) return false;
  const pkgs = {
    'apt-get': 'openjdk-21-jdk',
    'dnf': 'java-21-openjdk-devel',
    'yum': 'java-21-openjdk-devel',
    'apk': 'openjdk21',
  };
  return installPkg(pm, pkgs[pm]);
}

function installMaven() {
  log('SETUP', 'Maven required. Attempting installation...');
  const pm = getPackageManager();
  if (!pm) {
    log('SETUP', 'No package manager found. Download Maven from https://maven.apache.org/download.cgi');
    return false;
  }
  return installPkg(pm, 'maven');
}

function installNode() {
  log('SETUP', 'Node.js 20+ required. Attempting installation...');
  if (isWin) {
    log('SETUP', 'Download Node.js 20+ from https://nodejs.org/, install, and re-run this script.');
    return false;
  }
  if (isMac) {
    return installPkg('brew', 'node');
  }
  // Linux: prefer NodeSource for up-to-date Node.js
  if (cmdExists('apt-get')) {
    log('SETUP', 'Using NodeSource setup for Node.js 20...');
    try {
      const needsSudo = process.getuid && process.getuid() !== 0 && cmdExists('sudo');
      const s = needsSudo ? 'sudo ' : '';
      execSync(`${s}apt-get update -y && ${s}apt-get install -y ca-certificates curl gnupg 2>&1`, { stdio: 'inherit', timeout: 60000 });
      execSync(`curl -fsSL https://deb.nodesource.com/setup_20.x | ${s}bash - 2>&1`, { stdio: 'inherit', timeout: 60000 });
      execSync(`${s}apt-get install -y nodejs 2>&1`, { stdio: 'inherit', timeout: 120000 });
      return true;
    } catch {
      return false;
    }
  }
  const pm = getPackageManager();
  if (pm) {
    const pkgs = { 'dnf': 'nodejs', 'yum': 'nodejs', 'apk': 'nodejs' };
    if (installPkg(pm, pkgs[pm])) return true;
  }
  log('SETUP', 'Could not install Node.js. Install manually from https://nodejs.org/');
  return false;
}

function checkAll() {
  log('SETUP', 'Verifying system dependencies...');

  let pass = true;
  let changed = false;

  // --- Java 21+ ---
  const jVer = getJavaVersion();
  if (jVer >= 21) {
    log('SETUP', `[OK] Java ${jVer}`);
  } else if (jVer > 0) {
    log('SETUP', `[WARN] Java ${jVer} found, version 21+ required.`);
    if (installJava()) { changed = true; pass = getJavaVersion() >= 21; }
    else { pass = false; }
  } else {
    log('SETUP', '[MISS] Java not found.');
    if (installJava()) { changed = true; pass = getJavaVersion() >= 21; }
    else { pass = false; }
  }

  // --- Maven ---
  if (checkMaven()) {
    log('SETUP', '[OK] Maven');
  } else {
    log('SETUP', '[MISS] Maven not found.');
    if (installMaven()) { changed = true; pass = checkMaven() && pass; }
    else { pass = false; }
  }

  // --- Node.js 20+ ---
  const nVer = getNodeVersion();
  if (nVer >= 20) {
    log('SETUP', `[OK] Node.js ${nVer}`);
  } else if (nVer > 0) {
    log('SETUP', `[WARN] Node.js ${nVer} found, version 20+ required.`);
    if (installNode()) { changed = true; pass = getNodeVersion() >= 20 && pass; }
    else { pass = false; }
  } else {
    log('SETUP', '[MISS] Node.js not found.');
    if (installNode()) { changed = true; pass = getNodeVersion() >= 20 && pass; }
    else { pass = false; }
  }

  // --- npm ---
  if (checkNpm()) {
    log('SETUP', '[OK] npm');
  } else {
    log('SETUP', '[MISS] npm not found (bundled with Node.js).');
    pass = false;
  }

  if (!pass) {
    log('ERROR', 'Some dependencies could not be resolved.');
    log('ERROR', 'Install missing tools manually (see INSTALL.md) and re-run.');
    process.exit(1);
  }

  if (changed) {
    log('SETUP', 'Dependencies installed. Proceeding to launch.');
  }
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
