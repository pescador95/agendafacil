# Tecnologias utilizadas 

O Agenda Fácil utiliza uma arquitetura moderna e escalável, baseada em microserviços e práticas de multitenancy, permitindo que cada cliente tenha sua própria instância isolada, com dados e configurações independentes.

## Documentação das tecnologias usadas

Aqui você pode conferir um pouco mais sobre a [documentação das tecnologias usadas](/documentation/techDocs/README.md).


## Especificações

 As tecnologias e ferramentas utilizadas incluem:

- **Frontend**: Desenvolvido com **Vue.js** e **Vite**, proporcionando uma experiência de usuário rápida e fluida.

- **Backend**: **Quarkus**, um framework **Java** de alto desempenho, utilizado para garantir escalabilidade e alta performance nas operações de backend. Utilizado **arquitetura monolítica**, facilitando a gestão e manutenção da aplicação em sua estrutura inicial. No entanto, o ecossistema conta com outros serviços complementares que interagem com o backend para atender a diferentes necessidades do sistema.

- **Arquitetura Multitenancy**: Através de subdomínios, foi escolhido e implementado **Arquitetura multitenant**, com cada cliente isolado em seu próprio esquema de banco de dados, garantindo segurança e personalização sem interferências entre clientes.

- **Chatbots**: Integração com Telegram e WhatsApp para atendimento ao cliente, utilizando APIs **Node.js** para automação de processos como agendamento e gerenciamento de notificações.

- **Agendamento** Dinâmico: Algoritmos personalizados para **geração dinâmica de horários** de acordo com a disponibilidade de cada profissional, com configurações específicas por cliente.

- **Notificações** Automatizadas: Sistema de envio de **notificações adaptáveis ao fuso horário de cada empresa**, garantindo que os usuários recebam lembretes e atualizações de forma eficiente e no momento certo.

- **Banco de Dados**: Utilização de bancos de dados relacionais **Postgres**, com separação de dados por cliente, para garantir performance e segurança, e do **Redis** para auxiliar no controle de sessões de autenticação, de acordo com cada tipo de Contrato do cliente.

- **Liquibase**: Utilização do **Liquibase** para **versionamento das migrações do banco de dados**, garantindo um controle eficiente e consistente sobre as mudanças no esquema de dados ao longo do tempo.

- **Ingress e Nginx**: O ecossistema da plataforma inclui o uso de **Ingress** e **Nginx** para o roteamento eficiente entre os serviços, garantindo uma comunicação segura e escalável entre o backend, os chatbots e outros componentes do sistema.

- **Kind**: O **Kind** (Kubernetes in Docker) é utilizado para **orquestrar os containers e serviços** no ambiente de desenvolvimento e testes, permitindo a criação de clusters Kubernetes dentro de containers Docker, facilitando a implantação e o gerenciamento dos serviços de forma isolada e eficiente, de forma eficiente.

- **Automatização de Ambiente**: O projeto conta com scripts de automatização que **simplificam a criação e a configuração** do ambiente utilizando o Kind, permitindo subir o ambiente de maneira rápida e sem complicações.

- **GitHub Actions**: O fluxo de trabalho inclui **workflows** e **pipelines** de build automatizados no GitHub Actions, garantindo que o processo de build, testes e deploy seja **eficiente** e repetível.

- **Testes Automatizados com JUnit**: A aplicação utiliza **JUnit** para testar todo o fluxo, desde a entrada da requisição na camada controller até a interação com o banco de dados. Os testes garantem que a lógica de negócios, a persistência e os endpoints da API funcionem corretamente. Além disso, **asseguram que as operações sejam idempotentes**, ou seja, chamadas repetidas não alterem o estado da aplicação inesperadamente.

- **Observabilidade**: O projeto utiliza ferramentas de observabilidade como **Prometheus, Grafana, Loki e Tempo**, para monitoramento, visualização de logs e rastreamento distribuído. Essas ferramentas permitem acompanhar o desempenho da aplicação, identificar problemas e otimizar a operação.

- **Logs** e Identificação de Tenant: Nos **logs** gerados, é incluída a identificação do **tenant** em que ocorreu o erro ou evento, facilitando a análise e resolução de problemas específicos de cada cliente.

Essas tecnologias e decisões arquiteturais foram escolhidas para oferecer uma plataforma robusta, flexível e capaz de atender a diferentes necessidades de clientes de forma personalizada e eficiente.

[voltar](../../README.md)