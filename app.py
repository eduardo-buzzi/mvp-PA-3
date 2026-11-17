"""Inicializador da aplicação Java/Spring Boot da Schulz (Python 3.10+)."""
from pathlib import Path
import shutil
import subprocess
import sys
import time
import urllib.request
import webbrowser

ROOT = Path(__file__).resolve().parent
URL = 'http://localhost:8080/'


def main():
    if not shutil.which('java'):
        sys.exit('ERRO: Java 17+ nao encontrado no PATH. Instale o JDK 17 ou superior.')
    java_version = subprocess.run(['java', '-version'], capture_output=True, text=True)
    print(java_version.stderr.splitlines()[0] if java_version.stderr else 'Java encontrado.')

    mvn = shutil.which('mvn.cmd') or shutil.which('mvn')
    if not mvn:
        sys.exit('ERRO: Maven nao encontrado no PATH. Instale o Maven e tente novamente.')
    (ROOT / 'data').mkdir(exist_ok=True)
    print('Iniciando a aplicacao Java/Spring Boot via Maven...')
    proc = subprocess.Popen([mvn, 'spring-boot:run'], cwd=ROOT)
    try:
        for _ in range(120):
            if proc.poll() is not None:
                sys.exit('ERRO: a aplicacao encerrou antes de iniciar. Confira as mensagens do Maven.')
            try:
                with urllib.request.urlopen(URL, timeout=1) as response:
                    if response.status < 500:
                        print('Aplicacao disponivel em', URL)
                        webbrowser.open(URL)
                        break
            except Exception:
                pass
            time.sleep(1)
        else:
            print('A aplicacao nao respondeu em localhost:8080. Confira o terminal e tente acessar manualmente.')
        print('Mantenha esta janela aberta. Use Ctrl+C para encerrar.')
        proc.wait()
    except KeyboardInterrupt:
        print('\nEncerrando inicializador...')
        proc.terminate()
        try:
            proc.wait(timeout=8)
        except subprocess.TimeoutExpired:
            proc.kill()

if __name__ == '__main__':
    main()
