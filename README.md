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
   
   Retorne para o diretório raiz e execute:
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
   Se conecte ao banco usando as credenciais:
   ```
   user: admin@example.com
   senha: admin
   ```
   ![image](https://github.com/user-attachments/assets/76be2ccf-7a7e-4b2e-a74d-5db0476cf217)
   
   Se conecte ao banco usando os seguintes dados (botão direito em "Servers", "Register" e depois em "Server...":
   ```
   Name: salarios
   host name/address: endereco ip da máquina
   Maintenance: postgres
   Username: admin
   senha: admin
   Ativar Save Password
   ```
   ![image](https://github.com/user-attachments/assets/5d2dea0f-2626-448a-aa1d-d50434977965)
   ![image](https://github.com/user-attachments/assets/cde8365b-68d3-4047-902b-2ce07cf63acb)
   
   Feito isso, clicar em save.
   
   Após isso, atualizar a página de "listagem de pessoas" para que o hibernate crie as tabelas no DB.
   Terminado isso, retornar para o pgAdmin 4 e fazer a inserção dos dados no DB da seguinte forma:

   - Seguir sequencia de inserção: cargo, vencimentos, cargo_vencimentos e pessoa;
   - Clicar com o botão direito sobre uma tabela e selecionar "Import/Export Data...":
     ![image](https://github.com/user-attachments/assets/91b33034-7935-46c4-a8a7-1ed36b6c8f94)
   
   - Clicar no ícone de folder, existente no final da linha de Filename:
     ![image](https://github.com/user-attachments/assets/4006fb30-0c06-4852-a815-d6e257c7b2a5)

   - Clicar nos três pontinhos na parte superior direita e depois em upload:
     ![image](https://github.com/user-attachments/assets/00fc8dac-1455-48aa-ac48-9aee9bfaa294)

   - Clicar em "Drops file..." e selecionar e salvar os arquivos CSVs que estão no projeto (ir para o diretório correto: ~/mvnsalarios/src/main/docs/avaliacao/):
    ![image](https://github.com/user-attachments/assets/40c12e17-77cd-47e6-b516-6ed7a747a63b)
    ![image](https://github.com/user-attachments/assets/d03acc50-e766-4fdc-80ce-10fbd85afbb2)
    ![image](https://github.com/user-attachments/assets/61a6ee4a-2770-4c75-bf42-a8d751747c1a)

   - Feita a inserção dos dados, fechar o dialog, voltar para Import/Export Data, selecionar o arquivo CSV referente a tabela, clicar na tab "Options", selecionar o checkbox "header" e depois clicar no botão "OK" do dialog Import/Export data:
     ![image](https://github.com/user-attachments/assets/ed7f6535-f43f-4553-9281-85c41a897df4)
     ![image](https://github.com/user-attachments/assets/597c9a32-6156-4cc5-acff-74553484008e)

   - Realizar essa operação (com seus referidos arquivos CSVs) para todas as tabelas, seguindo a sequência citada anteriormente.
   - Se conecte ao banco:
     
   ![image](https://github.com/user-attachments/assets/6b01a781-f869-41a5-ba76-b2de508890eb)
   - Preencha os campos e selecione "Connect & Open Query Tool":
     
   ![image](https://github.com/user-attachments/assets/adbdc5f5-b35e-426b-8217-444a4e8f29d4)

   -    execute a query abaixo:
   ```
   SELECT setval('pessoa_seq', COALESCE((SELECT MAX(id) FROM pessoa), 1));
   SELECT last_value FROM pessoa_seq;
   ```
   - o resultado exibido deve ser:
     
   ![image](https://github.com/user-attachments/assets/5330e705-0747-448c-aa4c-2c8ea0f6ef1d)

   - Realizadas essas operações no pgAdmin 4, o banco de dados estará alimentado. Só voltar para o sistema e dar refresh na página xhtml que os dados serão renderizados.


## Observações  
- O arquivo do relatório `relatorioSalarios.jrxml` deve estar localizado em:  
  ```
  src/main/resources/relatorios/
  ```
- Antes de gerar um relatório, execute o cálculo de salários clicando no botão **"Calcular Salários"**.  
- Para atualizar os dados na interface, utilize o botão **"Atualizar Página"** disponível no sistema.  

