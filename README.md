# mvnsalarios

## Orientações do Projeto

### Descrição  
Este projeto é uma aplicação Jakarta Faces 4.0 com a utilização do Primefaces 14 para criação da interface web do usuário.  
Ele permite o gerenciamento de pessoas e cargos, além do cálculo de salários, incluindo a geração de relatórios em PDF com JasperReports.  

### Funcionalidades Implementadas  
- **CRUD de Pessoa e Cargo** – Operações de criação, leitura, atualização e exclusão de registros.  
- **Cálculo de Salários** – Cálculo de salários de forma síncrona e assíncrona.  
- **Geração de Relatórios em PDF** – Relatórios de salários gerados via JasperReports.  
- **Atualização Dinâmica de Dados** – Atualização da lista de pessoas na interface.  
- **Configuração REST** – Implementada através da classe `JakartaRestConfiguration`.  
- **Configuração do JSF** – Definida via `FacesActivatorConfiguration`.  

## Instalação e Execução  

### Pré-requisitos  
- Java 21  
- Maven  
- Docker  
- Servidor de Aplicação Jakarta EE (ex: WildFly, Payara, TomEE)  

### Passos para execução  
1. **Clone o repositório e navegue até o diretório do projeto**  
   ```
   git clone https://github.com/felipesilva2102/mvnsalarios.git
   cd mvnsalarios/
   ```

2. **Compile e construa o projeto**  
   ```
   mvn clean install
   ```

3. **Inicie o banco de dados PostgreSQL e o PgAdmin 4 via Docker**  
   ```
   cd src/main/docs/postgres/
   sudo docker-compose up -d
   ```
   Isso baixará as imagens do PostgreSQL e do PgAdmin 4 e iniciará os containers.  

4. **Execute a aplicação com Maven:**
   
   Retorne para o arquivo raiz e execute:
   ```
   mvn clean package wildfly:dev  
   ```

5. **Acesse a aplicação via navegador**  
   ```
   http://localhost:8080/mvnsalarios
   ```

6. **Acesse o PgAdmin 4 e carregue os dados iniciais**
   ```
   http://localhost:5050/browser/
   ```
   Se conecte ao banco e execute a query abaixo:
   ```
   SELECT setval('pessoa_seq', COALESCE((SELECT MAX(id) FROM pessoa), 1));
   SELECT last_value FROM pessoa_seq;
   ```
   - Utilize os arquivos CSV disponíveis no projeto (`src/main/docs/avaliacao/*.csv`) para inserir os registros no banco de dados.  

## Observações  
- O arquivo do relatório `relatorioSalarios.jrxml` deve estar localizado em:  
  ```
  src/main/resources/relatorios/
  ```
- Antes de gerar um relatório, execute o cálculo de salários clicando no botão **"Calcular Salários"**.  
- Para atualizar os dados na interface, utilize o botão **"Atualizar Página"** disponível no sistema.  

