package net.eusashead.vertx.docker;

import static io.fabric8.kubernetes.assertions.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.URL;

import org.arquillian.cube.kubernetes.annotations.Named;
import org.arquillian.cube.kubernetes.annotations.PortForward;
import org.arquillian.cube.kubernetes.api.Session;
import org.assertj.core.api.Assertions;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.fabric8.kubernetes.api.model.Service;
import io.fabric8.kubernetes.client.KubernetesClient;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@RunWith(Arquillian.class)
public class ArquillianITCase {
	
	@ArquillianResource
    KubernetesClient client;
	
	@ArquillianResource
    Session session;
	
	@Named("vertx-docker-bootstrap")
    @PortForward
    @ArquillianResource
    private URL route;
	
	@Named("vertx-docker-bootstrap")
    @PortForward
    @ArquillianResource
    Service testService;

	@Test
	public void whenInitialisedThenRouteNotNull() throws Exception {
		assertNotNull(route);
	}
	
    @Test
    public void whenDeploymentCheckedThenPodsAreReady() throws Exception {
        assertThat(client).deployments().pods().isPodReadyForPeriod();
    }

    @Test
    public void whenDeployedThenServiceIsStarted() throws IOException {
    	assertThat(testService).isNotNull();
        assertThat(testService.getSpec()).isNotNull();
        assertThat(testService.getStatus()).isNotNull();
        Assertions.assertThat(testService.getSpec().getPorts()).isNotNull();
        Assertions.assertThat(testService.getSpec().getPorts()).isNotEmpty();
    }
    
    @Test
    public void whenRouteIsCalledThenExpectedResponseReturned() throws Exception {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().get().url(route).build();

        Response response = okHttpClient.newCall(request).execute();

        assertNotNull(response);
        assertEquals(200, response.code());
        assertTrue(response.body().string().contains("Hello"));
    }
}
