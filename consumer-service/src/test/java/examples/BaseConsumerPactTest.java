package examples;

import au.com.dius.pact.consumer.ConsumerPactTest;
import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.PactFragment;
import org.junit.Assert;

import javax.ws.rs.client.ClientBuilder;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class BaseConsumerPactTest extends ConsumerPactTest {
    @Pact(provider = "test_provider", consumer = "test_consumer")
    protected PactFragment createFragment(PactDslWithProvider builder) {
        Map<String, String> headers = new HashMap<>();

        return builder
                .given("test state")
                .uponReceiving("ExampleJavaConsumerPactTest test interaction")
                .path("/")
                .method("GET")
                .headers(headers)
                .willRespondWith()
                .status(200)
                .headers(headers)
                .body("hello test")
                .toFragment();
    }

    @Override
    protected String providerName() {
        return "test_provider";
    }

    @Override
    protected String consumerName() {
        return "test_consumer";
    }

    @Override
    protected void runTest(String url) throws IOException {
        Assert.assertEquals(ClientBuilder.newClient().target(url).request().get().getStatus(), 200);
    }
}
