@echo off
setlocal
cd /d "%~dp0"
title MVP Schulz - Inicializacao

echo ===========================================
echo       MVP DE CALIBRACAO - SCHULZ
echo ===========================================

where py >nul 2>nul
if not errorlevel 1 (
    py -3 -c "import sys; sys.exit(0 if sys.version_info >= (3,10) else 1)" >nul 2>nul
    if not errorlevel 1 (
        set "PYTHON_CMD=py -3"
        goto :python_ok
    )
)
where python >nul 2>nul
if not errorlevel 1 (
    python -c "import sys; sys.exit(0 if sys.version_info >= (3,10) else 1)" >nul 2>nul
    if not errorlevel 1 (
        set "PYTHON_CMD=python"
        goto :python_ok
    )
)
echo ERRO: Python 3.10 ou superior nao encontrado no PATH.
pause
exit /b 1

:python_ok
echo Python 3.10+ encontrado.
if not exist ".venv\Scripts\python.exe" (
    echo Criando ambiente virtual...
    %PYTHON_CMD% -m venv ".venv"
    if errorlevel 1 goto :erro
)
call ".venv\Scripts\activate.bat"
if errorlevel 1 goto :erro

echo Instalando dependencias Python...
python -m pip install -r requirements.txt
if errorlevel 1 goto :erro

echo Inicializando aplicacao...
python app.py
if errorlevel 1 goto :erro
exit /b 0

:erro
echo ERRO: nao foi possivel concluir a inicializacao.
pause
exit /b 1
