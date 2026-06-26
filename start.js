#!/usr/bin/env node
'use strict';

const { spawn } = require('child_process');
const path = require('path');

const ROOT = __dirname;
const FRONTEND_DIR = path.join(ROOT, 'frontend');
const isWin = process.platform === 'win32';

let backendProc = null;
let frontendProc = null;

function log(tag, msg) {
  const ts = new Date().toLocaleTimeString();
  console.log(`[${ts}] [${tag}] ${msg}`);
}

function runBackend() {
  log('BACKEND', 'Compiling project...');
  log('BACKEND', 'First launch compiles Maven project (30-60s).');

  const compile = spawn(
    isWin ? 'cmd.exe' : 'mvn',
    isWin ? ['/c', 'mvn', 'compile', '-q'] : ['compile', '-q'],
    { cwd: ROOT, stdio: ['ignore', 'inherit', 'inherit'], shell: isWin }
  );

  compile.on('close', (code) => {
    if (code !== 0) {
      log('BACKEND', `Compilation failed (exit ${code}).`);
      return;
    }
    log('BACKEND', 'Starting backend (dev profile)...');
    backendProc = spawn(
      isWin ? 'cmd.exe' : 'mvn',
      isWin ? ['/c', 'mvn', 'spring-boot:run', '-Dspring-boot.run.profiles=dev']
            : ['spring-boot:run', '-Dspring-boot.run.profiles=dev'],
      { cwd: ROOT, stdio: ['ignore', 'inherit', 'inherit'], shell: isWin }
    );
    backendProc.on('error', (err) => {
      log('BACKEND', `Failed to start: ${err.message}. Verify Maven is installed and JAVA_HOME points to JDK 21+.`);
    });
    backendProc.on('exit', () => { backendProc = null; });
  });
}

function runFrontend() {
  log('FRONTEND', 'Installing frontend dependencies...');

  const installProc = spawn(
    isWin ? 'cmd.exe' : 'npm',
    isWin ? ['/c', 'npm', 'install', '--silent'] : ['install', '--silent'],
    { cwd: FRONTEND_DIR, stdio: ['ignore', 'inherit', 'inherit'], shell: isWin }
  );

  installProc.on('close', (code) => {
    if (code !== 0) {
      log('FRONTEND', `npm install failed (exit ${code}). Check Node.js version (20+ required).`);
      return;
    }
    log('FRONTEND', 'Starting frontend dev server...');

    frontendProc = spawn(
      isWin ? 'cmd.exe' : 'npm',
      isWin ? ['/c', 'npm', 'run', 'dev'] : ['run', 'dev'],
      { cwd: FRONTEND_DIR, stdio: ['ignore', 'inherit', 'inherit'], shell: isWin }
    );
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
runBackend();
runFrontend();
