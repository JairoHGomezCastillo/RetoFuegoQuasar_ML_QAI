import RequestModel.RequestDistMensString;
import RequestModel.RequestDistanciaMensaje;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class TopSecretSplitKenobiTest {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "https://quasar-qa-challenge.prodeng-playground.mercadolibre.com";
        RestAssured.basePath = "/topsecret_split";
        RestAssured.requestSpecification = given().contentType(ContentType.JSON)
                .filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }

    @Test
    public void enviarMensajeConDistanciaStringKenobi() {

        RequestDistMensString requestDMString = new RequestDistMensString();
        requestDMString.setDistance("100.0");
        requestDMString.setMessage(Arrays.asList("este", "", "", "mensaje", ""));

        given().when()
                .body(requestDMString)
                .post("/kenobi")
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(isEmptyOrNullString());
    }

    @Test
    public void enviarMensajeIncompletoKenobi() {

        RequestDistanciaMensaje requestDistanciaMensaje = new RequestDistanciaMensaje();
        requestDistanciaMensaje.setDistance(100.0);
        requestDistanciaMensaje.setMessage(Arrays.asList("", "", "mensaje", ""));

        String respuesta = given().when()
                .body(requestDistanciaMensaje)
                .post("/kenobi")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().jsonPath().getString("");

        assertThat(respuesta, containsString("guardada correctamente para kenobi"));
    }

    @Test
    public void enviarDistanciaMensajeKenobiPost() {

        RequestDistanciaMensaje requestDistanciaMensaje = new RequestDistanciaMensaje();
        requestDistanciaMensaje.setDistance(100.0);
        requestDistanciaMensaje.setMessage(Arrays.asList("este", "", "", "mensaje", ""));

        String respuesta = given()
                .when()
                .body(requestDistanciaMensaje)
                .post("/kenobi")
                .then()
                .statusCode(200)
                .extract().jsonPath().getString("");

        assertThat(respuesta, containsString("guardada correctamente para kenobi"));

    }


}
