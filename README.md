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
