# API DE COMPRAS

A API de Compras desenvolvida com SPRING é uma solução eficiente e robusta para gerenciar transações de compra de produtos. 
Ela oferece endpoints para adicionar produtos, clientes, visualizar informações de compra, realizar o fechamento da compra, etc. 
A API é construída utilizando o framework SPRING, o que proporciona uma arquitetura escalável e flexível, facilitando a integração com outros
sistemas e serviços. 

# Tecnologias utilizadas
## Back end
- Java
- Spring Boot
- JPA / Hibernate
- Maven

- ## Banco de dados
-H2 

Para rodar o projeto localmente faça o download das seguintes ferramentas:

- [STS](https://spring.io.xy2401.com/tools3/sts/all/)
- [PostgreSQL](https://www.postgresql.org/download/)
- [Postman](https://www.postman.com/downloads/)
- [Git](https://git-scm.com/downloads)

Depois de ter instalado as ferramentas, crie uma pasta e de um nome a ela. Então abra o seu terminal nessa pasta. e digite o seguinte comando:

```bash
git clone git@github.com:Wilson-Pedro/COMPRA-API.git
```

Após isso abra o projeto no STS ou qualquer IDE que suporte o SPRING.

-> Com o STS vá em 'Compra' e clique com o potão direito.

-> Vá em 'Run As'.

-> E clique em 'Spring Boot App'

![START-PROJECT](https://github.com/Wilson-Pedro/images/blob/main/compra/start-projet-compra.png)

# POST
```
http://localhost:8080/clientes
```
![POST](https://github.com/Wilson-Pedro/images/blob/main/compra/endpoints/POST.PNG)

# GET ALL
```
http://localhost:8080/clientes
```
![GET ALL](https://github.com/Wilson-Pedro/images/blob/main/compra/endpoints/GET-ALL.PNG)

# GET ONE
```
http://localhost:8080/clientes/2
```
![GET ONE](https://github.com/Wilson-Pedro/images/blob/main/compra/endpoints/GET-ONE.PNG)

# PUT
```
http://localhost:8080/clientes/6
```
![PUT](https://github.com/Wilson-Pedro/images/blob/main/compra/endpoints/PUT.PNG)

# DELETE
```
http://localhost:8080/clientes/7
```
![DELETE](https://github.com/Wilson-Pedro/images/blob/main/compra/endpoints/DELETE.PNG)

## Outros EndPoints:
# Produto
```
http://localhost:8080/produtos
```
```
http://localhost:8080/produtos/page
```
```
http://localhost:8080/produtos/1
```
```
http://localhost:8080/produtos/1/produtos
```
```
http://localhost:8080/produtos/1
```
```
http://localhost:8080/produtos/1
```

# Compra
```
http://localhost:8080/compras
```
```
http://localhost:8080/compras/page
```
```
http://localhost:8080/compras/1
```
```
http://localhost:8080/compras
```
```
http://localhost:8080/compras/1
```
```
http://localhost:8080/clientes/1/finalizacao
```
```
http://localhost:8080/clientes/1/continuar
```
```
http://localhost:8080/compras/2
```

## Autor

- [@Wilson Pedro](https://github.com/Wilson-Pedro)
