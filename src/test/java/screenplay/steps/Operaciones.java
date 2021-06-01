package screenplay.steps;

import co.com.sofka.calculator.test.task.PostRequest;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import net.serenitybdd.screenplay.rest.questions.LastResponse;
import org.json.JSONException;
import org.json.XML;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import java.util.HashMap;

import static net.serenitybdd.screenplay.rest.questions.ResponseConsequence.seeThatResponse;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Operaciones {
    private Actor actor;
    private String urlBase = "http://www.dneonline.com";
    private String path = "/calculator.asmx";
    private int int1 = 20;
    private int int2 = 4;
    private String body = "<soapenv:Envelope xmlns:soapenv=" +
            "\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tem=\"http://tempuri.org/\"> " +
            "<soapenv:Header/> <soapenv:Body> " +
            "<tem:Add> <tem:intA>" + int1 + "</tem:intA> " +
            "<tem:intB>" + int2 + "</tem:intB> </tem:Add> " +
            "</soapenv:Body> </soapenv:Envelope>";




    @Given("que {string} quiere hacer operaciones con dos numeros")
    public void queQuiereHacerOperacionesConDosNumeros(String name) {
        actor = Actor.named(name);
        actor.whoCan(CallAnApi.at(urlBase));

    }

    @When("restar dos numeros")
    public void restarDosNumeros() {
        HashMap<String, Object> headers = new HashMap<>();
        headers.put("Content-Type", "text/xml;charset=UTF-8");
        headers.put("SOAPAction", "http://tempuri.org/Subtract");
        actor.attemptsTo(PostRequest.execute(headers, path, body));
    }

    @Then("la api respondera con el resultado")
    public void laApiResponderaConElResultado() {
        actor.should(
                seeThatResponse("el servicio web respondio el codigo esperado",
                        response -> response.statusCode(200)));

        try {
            JSONObject xmlJSONObj = XML.toJSONObject(LastResponse.received().answeredBy(actor).asString());
            String result = xmlJSONObj.getJSONObject("soap:Envelope")
                    .getJSONObject("soap:Body")
                    .getJSONObject("AddResponse")
                    .get("AddResult").toString();
            int resultSuma = int1 - int2;
            assertThat(resultSuma).isEqualTo(Integer.parseInt(result));

        } catch (JSONException je) {
            System.out.println(je.toString());
        }

    }

    @When("multiplica los numeros")
    public void multiplicaLosNumeros() {
        HashMap<String, Object> headers = new HashMap<>();
        headers.put("Content-Type", "text/xml;charset=UTF-8");
        headers.put("SOAPAction", "http://tempuri.org/Multiply");
        actor.attemptsTo(PostRequest.execute(headers, path, body));
    }

    @Then("la api respondera el resultado de la multiplicacion")
    public void laApiResponderaElResultadoDeLaMultiplicacion() {
        actor.should(
                seeThatResponse("el servicio web respondio el codigo esperado",
                        response -> response.statusCode(200)));

        try {
            JSONObject xmlJSONObj = XML.toJSONObject(LastResponse.received().answeredBy(actor).asString());
            String result = xmlJSONObj.getJSONObject("soap:Envelope")
                    .getJSONObject("soap:Body")
                    .getJSONObject("AddResponse")
                    .get("AddResult").toString();
            int resultSuma = int1 * int2;
            assertThat(resultSuma).isEqualTo(Integer.parseInt(result));

        } catch (JSONException je) {
            System.out.println(je.toString());
        }
    }



}
