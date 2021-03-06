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

#### Activemq
- Existem alguns tipos de envios, como:
  - point-to-point menssaging (anycast): enviar mensagem para a fila associada (não possui mais de 1 fila)
  - point-to-point address (anycast): vai mandar a mensagem para uma das filas associadas.
  - topic (multicast): vai mandar a mensagem para todas as filas associadas.
  
#### Log centralizado
- Use a dependência logging gelf.

```
    <dependency>
      <groupId>io.quarkus</groupId>
      <artifactId>quarkus-logging-gelf</artifactId>
    </dependency>
```

#### Health
- Para verificar a saúde da sua aplicação:
```
mvn quarkus:add-extension -Dextensions="io.quarkus:quarkus-smallrye-health"
```
- Endoint: /health ou pelo ui http://localhost:8080/health-ui/

#### Debezium
- Pega eventos no banco de dados e os transforma em streams, para postar em uma mensageria, por exemplo: kafka
- Em resumo, qualquer alteração em qualquer linha na base, vira um record para o kafka.

#### Command cli
- Indicado para aplicações que não precisam de uma interface http, por exemplo: consumer e producer de streams.

#### Picocli
- Uma lib que facilida o uso de commandos via console, um exemplo abaixo executando o comando ola, passando o parâmetro joao.
```
java -jar target/command-mode-1.0.0-SNAPSHOT-runner.jar ola -n joao
```

#### gRPC
- É uma implementação do google, onde RPC siginifica remote procedure call, se assemelha ao lookup do ejb, mas podemos chamar métodos que estão em outra jvm,  ou máquina e etc.

#### Graphql
- Realizar consultas no meu endpoint rest.

#### Colocando em um ambiente kubernetes
- mvn package -Dquarkus.container-image.build=true , gerar a imagem da aplicação.
- kubectl apply -f ./target/kubernetes/kubernetes.yml 
