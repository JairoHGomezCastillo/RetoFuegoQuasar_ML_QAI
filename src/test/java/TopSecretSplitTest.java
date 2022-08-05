import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class TopSecretSplitTest {

    private static final Logger logger = LogManager.getLogger(TopSecretSplitTest.class);

    @BeforeAll
    public static void setup() {
        logger.info("Inicalizando Setup");
        RestAssured.baseURI = "https://quasar-qa-challenge.prodeng-playground.mercadolibre.com";
        RestAssured.basePath = "/topsecret_split";
        RestAssured.requestSpecification = given().contentType(ContentType.JSON)
                .filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        logger.info("Setup Exitoso");
    }

    @Test
    void obtenerUbicacionMensajeNaveFail() {
        logger.info("Enviando peticion GET para obtener ubicacion y mensaje de la nave");

        String respuesta = given().when()
                .body("")
                .get()
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract().jsonPath().getString("");

        assertThat(respuesta, containsString("No hay suficiente informaci"));

        logger.info("Fin obtener ubicacion y mensaje de la nave");
    }

    @Test
    void obtenerUbicacionMensajeNave() {
        logger.info("Enviando peticion GET para obtener ubicacion y mensaje de la nave");

        String respuesta = given().when()
                .body("")
                .get()
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("position.x", equalTo(312.01263F))
                .body("position.y", equalTo(1647.3842F))
                .extract().jsonPath().getString("message");

        assertThat(respuesta, equalToIgnoringCase("este es un mensaje secreto"));

        logger.info("Fin obtener ubicacion y mensaje de la nave");
    }



}
