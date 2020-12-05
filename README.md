# Udemy quarkus

#### Configurando o keycloak.
- Criar realm
- Criar client
- Configurar login
- Alterar senha
- criar role no realm
- vincular role ao cliente
- entrar no client -> mappers ->add build in, realms roles, mapear para groups.
- http://localhost:8080/auth/realms/ifood para pegar a chave pública

#### Possíveis eros
- Caso recebe o erro keycloak Auth Error Error: Bad Request, error: invalid_grant, description: Account is not fully set up, ao solicitar o token, atualiza o password do user.


#### Gerando imagens do prometheus e grafana:
- docker build -f Dockerfile.prometheus -t prometheus-ifood .

#### Traces
- Para monitorar nossa aplicação, precisamos do jaeger e as depêndencias abaixo:
```
    <dependency>
      <groupId>io.quarkus</groupId>
      <artifactId>quarkus-smallrye-opentracing</artifactId>
    </dependency>
    <dependency>
      <groupId>io.opentracing.contrib</groupId>
      <artifactId>opentracing-jdbc</artifactId>
    </dependency>
```

#### Metricas
- Para nossa api expor dados, que serão consumidos pelo prometheus por exemplo, precisamos da dependência abaixo:
```
 mvn quarkus:add-extension -Dextensions="metrics"
 ```

#### Flyway
- Adicionando o flyway ao projeto
```
mvn quarkus:add-extension -Dextensions="flyway"

```

#### Multiny
- Biblioteca reactive, onde usamos o uni e mult.
- Dependencias:

```
mvn quarkus:add-extension -Dextensions="resteasy-mutiny,jdbc-postgres, flyway, pg-client"
```
