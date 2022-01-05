# Sobre este projeto

Este projeto tem como finalidade possibilitar importação de arquivos CNAB 
de forma a contabilizar as transações, e portanto sendo também possível 
consultar os resultados agrupados por Loja e Tipo de transação.

Ele possui a camada Client (Frontend) e também uma API com 3 endpoints:

1-  POST /file/upload

    Body: Byte stream do conteudo do arquivo CNAB a ser importado

    Respostas:
        HTTP200 - Importação realizada com sucesso
        HTTP400 - Arquivo com conteúdo vazio

2-  GET /loja

    Query Params para paginação (opcionais):
        - pageSize (ex:  ?pageSize=10)  quantidade de registros a serem retornados
        - fromId (ex:  ?pageSize=10&fromId=23)  a partir de qual loja Id deverá ser retornado.

    Resposta:
        HTTP200 - Conteúdo da resposta será JSON no seguinte formato:
        [{"id": 1,"loja": "BAR DO JOÃO","saldo": -612.00}...]

3-  GET /transacao/soma-por-operacao-por-loja/{idLoja}

    Resposta:
        HTTP200 - Conteúdo da resposta será JSON no seguinte formato:
        [{"tipo": "DEBITO","total": 304.00},...]


# Construir e executar o projeto

### Execute os comandos abaixo a partir do diretório raíz do projeto 'desafio-dev'
1. `mvn clean compile install` este comando deverá ser executado em ambos projetos/pastas (client e api). Ele irá fazer o clean, build, rodar os testes e então construir o artefato final.

Ex: 

`cd client`[ENTER] 

`mvn clean compile install`[ENTER] 

`cd ../api`[ENTER] 

`mvn clean compile install`[ENTER] 

`cd ..`[ENTER]


2. `docker-compose up -d` este comando irá iniciar 1 container para o banco de dados PostgreSQL, criar uma nova imagem usando DockerFile da API e também da camada CLIENT, e iniciar a aplicação (api) na porta 8180 e o frontend na porta 8080.

Para testar a API, basta acessar: `{hostnameAqui}:8081/api/loja`. 

Para testar o CLIENT, basta acessar: `{hostnameAqui}:8080`. 

O 'Hostname' vai depender de sua instalação do Docker. Geralmente será `localhost` ou então (Windows) `192.168.99.100`  padrão do Docker

Falando em hostname, tem um arquivo chamado `application.properties` dentro da pasta `resources` do projeto API, ele contem a url para conexão com banco de dados. Se necessário, por favor alterar o hostname conforme seu setup local. O mesmo se aplica para a camada CLIENT, mesmo arquivo properties porém para configurar endereço da API.

Outra propriedade que vale mencionar é a `spring.jpa.hibernate.ddl-auto`. Atualmente está configurado como `create`, o que significa que toda vez que reiniciar o serviço o Banco de dados será criado. Para produção, este valor deverá ser alterado para `update`.
