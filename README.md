# MVP - Automação da Emissão de Certificados de Calibração

Projeto acadêmico da AV03 - Projeto Aplicado III.

## Tecnologias
- Java 17
- Spring Boot 3.5.5
- Spring MVC + Thymeleaf
- Spring Data JPA
- H2 (banco relacional para o MVP local)
- Apache POI (leitura de XLSX)
- OpenPDF (geração do PDF)

## Como executar
1. Instale Java 17 e Maven.
2. Abra o terminal na pasta do projeto.
3. Execute `mvn spring-boot:run`.
4. Acesse `http://localhost:8080`.

## Planilha de entrada
A primeira aba deve conter os cabeçalhos:
`parametro`, `nominal`, `medida1`, `medida2`, `lea`.

Podem existir também `toleranciaSuperior` e `toleranciaInferior`.

Exemplo:
| parametro | nominal | toleranciaSuperior | toleranciaInferior | medida1 | medida2 | lea |
| P01 | 10.000 | 10.010 | 9.990 | 10.003 | 10.005 | 0.010 |

## Regras implementadas
- Arquivo precisa ser XLSX.
- Colunas obrigatórias precisam existir.
- Medidas obrigatórias precisam ser numéricas.
- Média = (medida1 + medida2) / 2.
- Erro = média - nominal.
- Erro absoluto = |erro|.
- Ponto é DENTRO_DA_TOLERANCIA quando erro absoluto <= LEA.
- Certificado é APROVADO quando todos os pontos estão dentro da tolerância.
- Certificado é REPROVADO quando pelo menos um ponto está fora.
- Padrão vencido bloqueia o processamento.
- O PDF é gerado após o processamento.

## Evidências para a AV03
Tirar prints de:
1. Tela inicial/upload.
2. Planilha usada.
3. Resultado com pontos processados.
4. Caso APROVADO.
5. Caso REPROVADO.
6. PDF gerado.
7. Código no GitHub.
