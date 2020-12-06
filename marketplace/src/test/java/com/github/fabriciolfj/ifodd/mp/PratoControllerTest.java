package com.github.fabriciolfj.ifodd.mp;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class PratoControllerTest {

    @Test
    public void testFindAll() {
        String body = given()
                .when()
                .get("/pratos")
                .then()
                .statusCode(200)
                .extract()
                .asString();

        System.out.println(body);
    }
}
