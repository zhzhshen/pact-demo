package examples;

import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.PactProviderRule;
import au.com.dius.pact.consumer.PactVerification;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.PactFragment;
import org.junit.Rule;
import org.junit.Test;

import javax.ws.rs.client.ClientBuilder;

import static org.junit.Assert.assertEquals;

public class JUnitConsumerPactTest {
    private static final String BASE_URL = "http://localhost:8080";
    @Rule
    public PactProviderRule mockProvider = new PactProviderRule("test_provider", "localhost", 8080, this);

    @Pact(provider = "test_provider", consumer = "test_consumer")
    public PactFragment createFragment(PactDslWithProvider builder) {
        return builder
                .given("test state")
                .uponReceiving("ExampleJavaConsumerPactRuleTest test interaction")
                .path("/")
                .method("GET")
                .willRespondWith()
                .status(200)
                .body("test success")
                .toFragment();
    }

    @Test
    @PactVerification("test_provider")
    public void runTest() {
        String expectedResponse = "test success";
        assertEquals(ClientBuilder.newClient().target(BASE_URL).request().get().getStatus(),
                200);
        assertEquals(ClientBuilder.newClient()
                        .target(BASE_URL)
                        .request().get()
                        .readEntity(String.class),
                expectedResponse);
    }
}
