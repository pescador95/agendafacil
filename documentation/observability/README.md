# Tecnologias de Observabilidade utilizadas

Este projeto utiliza uma stack completa de observabilidade para garantir a monitoração eficaz de métricas, logs e traces. As tecnologias utilizadas são:

### 1. **Prometheus**
   - O **Prometheus** é uma ferramenta de monitoramento e alerta de sistemas. Ele coleta e armazena métricas em uma base de dados time-series.
   - No projeto, o Prometheus é configurado para coletar métricas de desempenho do backend, frontend e containers, permitindo acompanhar a saúde e o desempenho da aplicação.
   - [Documentação oficial do Prometheus](https://prometheus.io/docs/)

### 2. **Grafana**
   - O **Grafana** é utilizado para visualização de métricas e logs. Ele conecta-se ao Prometheus para exibir dashboards que ajudam na análise de desempenho e na observação do comportamento da aplicação ao longo do tempo.
   - O Grafana também é usado para criar alertas e fornecer insights detalhados sobre os dados coletados.
   - [Documentação oficial do Grafana](https://grafana.com/docs/)

### 3. **Loki**
   - O **Loki** é um sistema de agregação de logs que facilita a consulta e o armazenamento de logs de aplicações. Ele é utilizado em conjunto com o Grafana para fornecer uma visualização centralizada de logs e ajudar na detecção e resolução de problemas.
   - [Documentação oficial do Loki](https://grafana.com/docs/loki/latest/)

### 4. **Tempo**
   - O **Tempo** é uma ferramenta de rastreamento distribuído, utilizada para coletar traces de requisições e interações entre diferentes serviços da aplicação. Isso ajuda a entender o fluxo das requisições e a identificar gargalos de desempenho.
   - Ele é integrado ao OpenTelemetry para coletar, armazenar e visualizar os traces gerados.
   - [Documentação oficial do Tempo](https://grafana.com/docs/tempo/latest/)

### 5. **OpenTelemetry Collector**
   - O **OpenTelemetry Collector** é utilizado para coletar e exportar métricas, logs e traces de forma padronizada. Ele é configurado para coletar dados de diversas fontes e enviá-los para o Prometheus (métricas), Loki (logs) e Tempo (traces), garantindo a observabilidade completa da aplicação.
   - [Documentação oficial do OpenTelemetry Collector](https://opentelemetry.io/docs/collector/)

[voltar](/documentation/techDocs/README.md)