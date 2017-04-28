package zoo;

import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.PactProviderRule;
import au.com.dius.pact.consumer.PactVerification;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.PactFragment;
import org.junit.Rule;
import org.junit.Test;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

public class ZooConsumerPactTest {
    @Rule
    public PactProviderRule mockProvider = new PactProviderRule("zoo_provider", "localhost", 8080, this);
    private final String BASE_URL = "http://localhost:8080";
    private PactDslJsonBody animalList;

    @Pact(consumer = "zoo_consumer")
    public PactFragment createFragment(PactDslWithProvider builder) {
        animalList = new PactDslJsonBody()
                .stringValue("tiger", "10")
                .stringValue("monkey", "100");
        return builder
                .given("test state")
                .uponReceiving("index of zoo")
                .path("/")
                .method("GET")
                .willRespondWith()
                .status(200)
                .body("zoo index")

                .uponReceiving("animal list")
                .path("/animals")
                .method("GET")
                .willRespondWith()
                .status(200)
                .body(animalList)
                .toFragment();
    }

    @Test
    @PactVerification
    public void runTest() {
        Response response = ClientBuilder.newClient().target(BASE_URL).request().get();
        assertEquals(response.getStatus(), 200);
        assertThat(response.readEntity(String.class), is("zoo index"));

        response = ClientBuilder.newClient().target(BASE_URL + "/animals").request().get();
        assertEquals(response.getStatus(), 200);
        assertThat(response.readEntity(String.class), is(animalList.toString()));
    }
}
