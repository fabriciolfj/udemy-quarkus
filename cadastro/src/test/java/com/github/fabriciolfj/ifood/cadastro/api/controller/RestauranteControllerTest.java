package com.github.fabriciolfj.ifood.cadastro.api.controller;

import com.github.database.rider.cdi.api.DBRider;
import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.configuration.Orthography;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.fabriciolfj.ifood.cadastro.CadastroTestLifecycleManager;
import com.github.fabriciolfj.ifood.cadastro.api.dto.request.AdicionarRestauranteDTO;
import com.github.fabriciolfj.ifood.cadastro.domain.entity.Restaurante;
import com.github.fabriciolfj.ifood.cadastro.util.TokenUtils;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.Header;
import org.approvaltests.Approvals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@DBRider
@DBUnit(caseInsensitiveStrategy = Orthography.LOWERCASE)
@QuarkusTest
@QuarkusTestResource(CadastroTestLifecycleManager.class)
public class RestauranteControllerTest {

    private String token;

    @BeforeEach
    public void setup() throws Exception {
        token = TokenUtils.generateTokenString("/JWTProprietarioClaims.json", null);
    }

    @Test
    @DataSet("restaurantes-cenario-1.yml")
    public void testBuscarRestaurantes() {
        final String resultado = RestAssured.given()
                .when()
                .header(new Header("Authorization", "Bearer " + token))
                .get("/restaurantes")
                .then()
                .statusCode(200)
               .extract().asString();

        Approvals.verifyJson(resultado);
    }

    @Test
    public void testAdicionarRestaurante() {
        var restaurante = new AdicionarRestauranteDTO();
        restaurante.nome = "Restaurante 3";
        restaurante.proprietario = "Fabricio";
        restaurante.cnpj = "68.562.575/0001-31";

        RestAssured.given()
                .contentType("application/json")
                .header(new Header("Authorization", "Bearer " + token))   
                .body(restaurante)
                .post("/restaurantes")
                .then()
                .statusCode(201);
    }

    /*
    @Test
    @DataSet("restaurantes-cenario-1.yml")
    public void testAlterarRestaurante() {
        AtualizarRestauranteDTO dto = new AtualizarRestauranteDTO();
        dto.nomeFantasia = "novoNome";
        Long parameterValue = 123L;
        given()
                .with().pathParam("id", parameterValue)
                .body(dto)
                .when().put("/restaurantes/{id}")
                .then()
                .statusCode(Status.NO_CONTENT.getStatusCode())
                .extract().asString();

        Restaurante findById = Restaurante.findById(parameterValue);

        //poderia testar todos os outros atribudos
        Assert.assertEquals(dto.nomeFantasia, findById.nome);
    }*/

}
