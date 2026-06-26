#!/usr/bin/env node
'use strict';

const { spawn, execSync } = require('child_process');
const path = require('path');
const fs = require('fs');

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
    Object.assign({}, opts, { stdio: opts.stdio || ['ignore', 'inherit', 'inherit'], shell: !!isWin })
  );
}

function cmdExists(cmd) {
  try {
    execSync(isWin ? `where ${cmd} 2>nul` : `which ${cmd} 2>/dev/null`, { stdio: 'ignore' });
    return true;
  } catch (e) {
    return false;
  }
}

function getJavaVersion() {
  try {
    const out = execSync('java -version 2>&1', { encoding: 'utf8' });
    const m = out.match(/(?:version\s+)"?(\d+)/);
    return m ? parseInt(m[1], 10) : 0;
  } catch (e) {
    return 0;
  }
}

function getNodeVersion() {
  try {
    const out = execSync('node -v', { encoding: 'utf8' });
    const m = out.match(/v?(\d+)/);
    return m ? parseInt(m[1], 10) : 0;
  } catch (e) {
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
  } catch (e) {
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

  if (pm === 'winget') {
    if (installPkg(pm, 'Apache.Maven')) {
      if (checkMaven()) return true;
    }
  } else if (pm === 'choco') {
    if (installPkg(pm, 'maven')) {
      if (checkMaven()) return true;
    }
  } else if (pm === 'brew') {
    if (installPkg(pm, 'maven')) {
      if (checkMaven()) return true;
    }
  } else if (pm === 'apt-get') {
    if (installPkg(pm, 'maven')) {
      if (checkMaven()) return true;
    }
  } else if (pm === 'dnf' || pm === 'yum') {
    if (installPkg(pm, 'maven')) {
      if (checkMaven()) return true;
    }
  } else if (pm === 'apk') {
    if (installPkg(pm, 'maven')) {
      if (checkMaven()) return true;
    }
  }

  log('SETUP', 'Trying direct download from Apache mirror...');
  const mvVer = '3.9.9';
  const baseUrl = 'https://dlcdn.apache.org/maven/maven-3/' + mvVer + '/binaries';
  const toolsDir = path.join(ROOT, '.tools');

  try {
    if (!fs.existsSync(toolsDir)) fs.mkdirSync(toolsDir, { recursive: true });

    if (isWin) {
      const zipFile = 'apache-maven-' + mvVer + '-bin.zip';
      const extractDir = path.join(toolsDir, 'apache-maven-' + mvVer);
      if (!fs.existsSync(extractDir)) {
        execSync('curl -fsSL "' + baseUrl + '/' + zipFile + '" -o "' + path.join(toolsDir, zipFile) + '" 2>&1', { stdio: 'inherit', timeout: 120000 });
        execSync('powershell -Command "Expand-Archive -Path \'' + path.join(toolsDir, zipFile) + '\' -DestinationPath \'' + toolsDir + '\' -Force" 2>&1', { stdio: 'inherit', timeout: 60000 });
      }
      const mvnBin = path.join(extractDir, 'bin', 'mvn.cmd');
      if (fs.existsSync(mvnBin)) {
        log('SETUP', 'Maven extracted to ' + extractDir);
        log('SETUP', 'Add it to PATH or re-run this script from a new terminal.');
        log('SETUP', '  [System] setx /M PATH "%PATH%;' + path.join(extractDir, 'bin') + '"');
        log('SETUP', '  [User]   setx PATH "%PATH%;' + path.join(extractDir, 'bin') + '"');
        return true;
      }
    } else {
      const tgzFile = 'apache-maven-' + mvVer + '-bin.tar.gz';
      const extractDir = path.join(toolsDir, 'apache-maven-' + mvVer);
      if (!fs.existsSync(extractDir)) {
        execSync('curl -fsSL "' + baseUrl + '/' + tgzFile + '" -o "' + path.join(toolsDir, tgzFile) + '" 2>&1', { stdio: 'inherit', timeout: 120000 });
        execSync('tar -xzf "' + path.join(toolsDir, tgzFile) + '" -C "' + toolsDir + '" 2>&1', { stdio: 'inherit', timeout: 60000 });
      }
      const mvnBin = path.join(extractDir, 'bin', 'mvn');
      if (fs.existsSync(mvnBin)) {
        fs.chmodSync(mvnBin, '755');
        log('SETUP', 'Maven extracted to ' + extractDir);
        log('SETUP', 'Add it to PATH or symlink: sudo ln -sf ' + mvnBin + ' /usr/local/bin/mvn');
        return true;
      }
    }
  } catch (e) {
    log('SETUP', 'Direct download failed: ' + e.message);
  }

  log('SETUP', 'Checking common Maven installation locations...');
  try {
    if (isWin) {
      const dirOut = execSync('dir /s /b C:\\mvn.cmd C:\\tools\\apache-maven-*\\bin\\mvn.cmd C:\\ProgramData\\chocolatey\\lib\\maven\\*\\bin\\mvn.cmd 2>nul', { encoding: 'utf8', timeout: 10000 });
      const lines = dirOut.trim().split(/\r?\n/).filter(Boolean);
      if (lines.length > 0) { log('SETUP', 'Maven found at ' + lines[0]); return true; }
    } else {
      for (const dir of ['/usr/local/apache-maven-*/bin/mvn', '/opt/apache-maven-*/bin/mvn', '/usr/share/maven/bin/mvn', '/usr/local/bin/mvn']) {
        try {
          const out = execSync('ls ' + dir + ' 2>/dev/null', { encoding: 'utf8', timeout: 5000 });
          const match = out.trim().split(/\n/)[0];
          if (match) { log('SETUP', 'Maven found at ' + match); return true; }
        } catch {}
      }
    }
  } catch {}

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
    } catch (e) { return false; }
  }
  if (pm === 'dnf' || pm === 'yum') return installPkg(pm, 'nodejs');
  if (pm === 'apk') return installPkg(pm, 'nodejs');
  log('SETUP', 'Download Node.js 20+ from https://nodejs.org/');
  return false;
}

function installNpm() {
  log('SETUP', 'npm not found. Attempting installation...');
  const pm = getPackageManager();

  if (pm === 'winget') {
    if (installPkg(pm, 'OpenJS.NodeJS.20')) {
      if (checkNpm()) return true;
    }
  }
  if (pm === 'choco') {
    if (installPkg(pm, 'npm')) {
      if (checkNpm()) return true;
    }
  }
  if (pm === 'brew') {
    if (installPkg(pm, 'npm')) {
      if (checkNpm()) return true;
    }
  }
  if (pm === 'apt-get') {
    if (installPkg(pm, 'npm')) {
      if (checkNpm()) return true;
    }
  }
  if (pm === 'dnf' || pm === 'yum') {
    if (installPkg(pm, 'npm')) {
      if (checkNpm()) return true;
    }
  }
  if (pm === 'apk') {
    if (installPkg(pm, 'npm')) {
      if (checkNpm()) return true;
    }
  }

  log('SETUP', 'Trying official npm installer as fallback...');
  try {
    const s = !isWin && !isMac && process.getuid && process.getuid() !== 0 && cmdExists('sudo') ? 'sudo ' : '';
    execSync(`${s}curl -L https://www.npmjs.org/install.sh 2>&1`, { stdio: 'inherit', timeout: 60000 });
    if (checkNpm()) return true;
  } catch {}

  log('SETUP', 'Checking common npm locations bundled with Node.js...');
  const candidates = [];
  if (isWin) {
    candidates.push(
      path.join(process.execPath, '..', 'node_modules', 'npm', 'bin', 'npm.cmd'),
      path.join(process.execPath, '..', '..', 'node_modules', 'npm', 'bin', 'npm.cmd'),
      path.join(process.execPath, '..', '..', '..', 'node_modules', 'npm', 'bin', 'npm.cmd')
    );
  } else {
    candidates.push(
      path.join(process.execPath, '..', '..', 'lib', 'node_modules', 'npm', 'bin', 'npm-cli.js'),
      path.join(process.execPath, '..', 'lib', 'node_modules', 'npm', 'bin', 'npm-cli.js'),
      '/usr/local/lib/node_modules/npm/bin/npm-cli.js',
      '/usr/lib/node_modules/npm/bin/npm-cli.js'
    );
  }
  for (const loc of candidates) {
    try {
      execSync(`"${loc}" --version 2>&1`, { stdio: 'ignore' });
      log('SETUP', `npm found at ${loc}`);
      return true;
    } catch {}
  }

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
    log('SETUP', '[MISS] npm not found');
    if (installNpm()) { changed = true; pass = checkNpm() && pass; }
    else { pass = false; }
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
