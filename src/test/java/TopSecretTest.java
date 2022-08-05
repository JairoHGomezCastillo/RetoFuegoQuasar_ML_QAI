import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.equalToIgnoringCase;

public class TopSecretTest {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "https://quasar-qa-challenge.prodeng-playground.mercadolibre.com";
        RestAssured.basePath = "/topsecret";
        RestAssured.requestSpecification = given().contentType(ContentType.JSON)
                .filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }

    @Test
    public void determinaPosicionPost() {

        String response = given()
                .when()
                .body("{\n" +
                        "\"satellites\": [\n" +
                        "    {\n" +
                        "    \"name\": \"kenobi\",\n" +
                        "    \"distance\": 100.0,\n" +
                        "    \"message\": [\"este\", \"\", \"\", \"mensaje\", \"\"]\n" +
                        "    },\n" +
                        "    {\n" +
                        "    \"name\": \"skywalker\",\n" +
                        "    \"distance\": 115.5,\n" +
                        "    \"message\": [\"\", \"es\", \"\", \"\", \"secreto\"]\n" +
                        "    },\n" +
                        "    {\n" +
                        "    \"name\": \"sato\",\n" +
                        "    \"distance\": 142.7,\n" +
                        "    \"message\": [\"este\", \"\", \"un\", \"\", \"\"]\n" +
                        "    }\n" +
                        "    ]\n" +
                        "}")
                .post()
                .then()
                .statusCode(200)
                .body("position.x", equalTo(301.39246F))
                .body("position.y", equalTo(1731.018F))
                .extract().jsonPath().getString("message");

        assertThat(response, equalToIgnoringCase("este es un mensaje secreto"));



    }

    @Test
    public void determinaPosicionSinNombrePostFail() {

        given()
                .when()
                .body("{\n" +
                        "\"satellites\": [\n" +
                        "    {\n" +
                        "    \"name\": \"\",\n" +
                        "    \"distance\": 100.0,\n" +
                        "    \"message\": [\"este\", \"\", \"\", \"mensaje\", \"\"]\n" +
                        "    },\n" +
                        "    {\n" +
                        "    \"name\": \"skywalker\",\n" +
                        "    \"distance\": 115.5,\n" +
                        "    \"message\": [\"\", \"es\", \"\", \"\", \"secreto\"]\n" +
                        "    },\n" +
                        "    {\n" +
                        "    \"name\": \"sato\",\n" +
                        "    \"distance\": 142.7,\n" +
                        "    \"message\": [\"este\", \"\", \"un\", \"\", \"\"]\n" +
                        "    }\n" +
                        "    ]\n" +
                        "}")
                .post()
                .then()
                .statusCode(404);
    }

    @Test
    public void determinaPosicionMensajeIncompletoPostFail() {

        given()
                .when()
                .body("{\n" +
                        "\"satellites\": [\n" +
                        "    {\n" +
                        "    \"name\": \"kenobi\",\n" +
                        "    \"distance\": 100.0,\n" +
                        "    \"message\": [\"\", \"\", \"mensaje\", \"\"]\n" +
                        "    },\n" +
                        "    {\n" +
                        "    \"name\": \"skywalker\",\n" +
                        "    \"distance\": 115.5,\n" +
                        "    \"message\": [\"\", \"es\", \"\", \"\", \"secreto\"]\n" +
                        "    },\n" +
                        "    {\n" +
                        "    \"name\": \"sato\",\n" +
                        "    \"distance\": 142.7,\n" +
                        "    \"message\": [\"este\", \"\", \"un\", \"\", \"\"]\n" +
                        "    }\n" +
                        "    ]\n" +
                        "}")
                .post()
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND);
    }

}
