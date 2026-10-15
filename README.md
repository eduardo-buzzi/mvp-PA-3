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
