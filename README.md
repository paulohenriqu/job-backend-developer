#  Intelipost: Teste prático para Backend Developer
**Paulo Henrique de Siqueira**
paulohenriqu@hotmail.com
https://www.linkedin.com/in/paulohenriquesiqueira/

# Solução
Considerando o desafio apresentado pensei em dividir os serviços de autenticação e perfil de usuário em microsserviços.
Também implementei o método de autenticação utilizando JWT.  A autenticação via tokens é stateless, sendo assim é possível escalar os servidores sem preocupação com perca de sessão.
Com a utilização de microsserviços e JWT seria possível utilizar um load balancer, sempre que houver um pico de requisições os servidores podem ser escalados, possivelmente resolvendo um dos problemas de lentidão pelo fato de o servidor não conseguir processar uma quantidade extremamente grande de requisições http simultâneamente. Caso também haja gargalo na conexão com o banco de dados é possível utilizar um cluster em conjunto com o microsserviço de autenticação.
Foi desenvolvido um microsserviço chamado gateway, que serve como autenticação e gateway para os outros microsserviços, realizando também o redirecionamento utilizando Spring Cloud (Eureka e Zuul).
O segundo microsserviço gerencia os perfis cadastrados no sistema.
Também considerando a grande quantidade de usuários cadastrados no banco foi criado um índice na coluna username para agilizar a query de autenticação e um índice na coluna user_id da tabela profile para que os perfis sejam carregados de forma mais rápida também. 

# Tecnologias utilizadas
As seguintes tecnologias foram utilizadas nesta solução:
* Java 8
* Spring Boot
* Maven
* PostgreSQL
* Docker
# Dependências
Para rodar esse projeto as seguintes dependências precisam estar instaladas no computador:
* Git (https://git-scm.com/book/pt-br/v1/Primeiros-passos-Instalando-Git)
* JDK 8 (https://docs.oracle.com/javase/8/docs/technotes/guides/install/install_overview.html)
* Maven (https://maven.apache.org/install.html)
* Docker (https://docs.docker.com/install/)
# Executando o projeto
* Clone este repositório
* Construa o projeto do gateway utilizando o Manven:

   ```bash
 cd auth-service
 mvn clean install -DskipTests
```

* Construa o projeto do profile-service com o Maven:

   ```bash
 cd ..
 cd profile-service
 mvn clean install -DskipTests
```
* Volte para a pasta raíz do repositório e inicie os containers do docker

   ```bash
 cd ..
 docker-compose up
```
* O container do gateway aguarda 20 segundos para que o PostgreSQL termine de iniciar.
* A API de autenticação está no endpoint **localhost:8080/auth/login**. A aplicação é iniciada com um usuário de testes:
Username: admin 
Password: passowrd
Então para gerar o token é necessário fazer a requisição POST passando os dados do usuário no corpo da requisição:

   

     curl -X POST \
      http://localhost:8080/auth/login \
      -H 'cache-control: no-cache' \
      -H 'content-type: application/json' \ 
      -d '{
    	"username":"admin",
    	"password":"password"
    }'

O token JWT será retornado no corpo da reposta.

[![auth.png](https://i.postimg.cc/Fzzyq8DY/auth.png)](https://postimg.cc/1VxVVCxS)

* Para pegar mais informações sobre a conta do usuário logado o endpoint à ser utilizado é o **localhost:8080/auth/account-info**, passando no header da requisição GET o token na key authorization:

        curl -X GET \
      http://localhost:8080/auth/account-info \
      -H 'authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGVzIjpbIlJPTEVfVVNFUiIsIlJPTEVfQURNSU4iXSwiaWF0IjoxNTUzNTM2MTkwLCJleHAiOjE1NTM1Mzk3OTB9.DY8PaP2X9Q3SakSF4IjAXg98kXK20NoJjyYweD3Xb2M' \
      -H 'cache-control: no-cache' 


[![account-info.png](https://i.postimg.cc/MHL785Fd/account-info.png)](https://postimg.cc/tnhZ33yx)

* Para buscar os perfis no serviço profile o endpoint à ser utilizado é **localhost:8080/api/profile/v1/profile**. 

 

    curl -X GET \
      http://localhost:8080/api/profile/v1/profile \
      -H 'authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGVzIjpbIlJPTEVfVVNFUiIsIlJPTEVfQURNSU4iXSwiaWF0IjoxNTUzNTM2MTkwLCJleHAiOjE1NTM1Mzk3OTB9.DY8PaP2X9Q3SakSF4IjAXg98kXK20NoJjyYweD3Xb2M' \
      -H 'cache-control: no-cache' 

* Para buscar um perfil específico o endpoint é **localhost:8080/api/profile/v1/profile/:id**

    curl -X GET \
      http://localhost:8080/api/profile/v1/profile/1 \
      -H 'authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGVzIjpbIlJPTEVfVVNFUiIsIlJPTEVfQURNSU4iXSwiaWF0IjoxNTUzNTM2MTkwLCJleHAiOjE1NTM1Mzk3OTB9.DY8PaP2X9Q3SakSF4IjAXg98kXK20NoJjyYweD3Xb2M' \
      -H 'cache-control: no-cache'
* Para buscar um perfil pelo id de um usuário o endpoint é **localhost:8080/api/profile/v1/profile/user/:id**

    curl -X GET \
      http://localhost:8080/api/profile/v1/profile/user/1 \
      -H 'authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGVzIjpbIlJPTEVfVVNFUiIsIlJPTEVfQURNSU4iXSwiaWF0IjoxNTUzNTM2MTkwLCJleHAiOjE1NTM1Mzk3OTB9.DY8PaP2X9Q3SakSF4IjAXg98kXK20NoJjyYweD3Xb2M' \
      -H 'cache-control: no-cache'
