# MVP de Automação de Certificados de Calibração

Aplicação web desenvolvida para otimizar a emissão de certificados de calibração na Schulz S.A., substituindo fluxos manuais por processamento automatizado de dados.

## O Problema e a Solução

No contexto industrial, o tratamento de leituras metrológicas e a validação de limites de erro costumam ser processos manuais e suscetíveis a gargalos. Esta aplicação automatiza a ingestão de planilhas operacionais, calcula estatísticas e desvios e emite o parecer final de conformidade integrado à geração de certificados em PDF.

## Funcionalidades

- **Ingestão de dados:** Importação de planilhas Excel com as medições do instrumento e os valores de referência do padrão.
- **Cálculos metrológicos:** 
  - Cálculo automático da média das leituras por ponto.
  - Apuração do erro sistemático (diferença entre média e referência).
  - Validação contra o Limite de Erro Admissível (LEA).
  - Atribuição automática do parecer final (Aprovado ou Reprovado).
- **Emissão de PDF:** Geração instantânea do documento de calibração contendo os resultados consolidados.

## Tecnologias

- **Backend:** Java, Spring Boot, Apache POI (manipulação de planilhas)
- **Frontend:** HTML, Tailwind CSS, JavaScript
- **Relatórios:** Geração de PDF integrada
- **Controle de Versão:** Git e GitHub

## Como Executar

Certifique-se de ter o Java JDK 17+ e o Maven instalados na sua máquina.

1. Clone o repositório:
   ```bash
   git clone [https://github.com/eduardo-buzzi/mvp-PA-3.git](https://github.com/eduardo-buzzi/mvp-PA-3.git)
   cd mvp-PA-3

## Inicialização automatizada para AV 05 (Windows)

Requisitos: Python 3.10+, Java JDK 17+ e Maven disponíveis no PATH, além de conexão com a internet para baixar dependências Maven na primeira execução.

1. Extraia o projeto para uma pasta no Windows.
2. Dê dois cliques em `iniciar_aplicacao_schulz.bat`.
3. O script verifica Python, cria/ativa `.venv`, instala `requirements.txt` e executa `app.py`.
4. O `app.py` verifica Java e Maven, inicia o Spring Boot e abre `http://localhost:8080/` quando o servidor responder.
5. Selecione `planilha_teste.xlsx` na tela inicial, preencha os dados, processe a calibração e gere o PDF na página de resultados.

**Observação:** o backend permanece em Java; `app.py` é um inicializador, não uma API em Python. O requisito literal da AV 05 referente a `app.py` como backend deve ser validado com o professor.
