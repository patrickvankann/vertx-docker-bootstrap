package net.eusashead.vertx.docker;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;

@RunWith(VertxUnitRunner.class)
public class HttpVerticleTest {

	private Vertx vertx;

	@Before
	public void setUp(TestContext context) {
		vertx = Vertx.vertx();
		vertx.deployVerticle(HttpVerticle.class.getName(),
				context.asyncAssertSuccess());
	}

	@After
	public void tearDown(TestContext context) {
		vertx.close(context.asyncAssertSuccess());
	}

	
	@Test
	public void whenHttpRequestThenItReturnsOK(final TestContext context) {
		final Async async = context.async();

		final WebClient client = WebClient.create(vertx);
		
		client
			.get(8080, "localhost", "/")
			.send(ar -> {
				context.assertTrue(ar.succeeded());
				HttpResponse<Buffer> response = ar.result();
				context.assertTrue(response.statusCode() == 200);
				Buffer body = response.body();
				context.assertTrue(body.toString().contains("Hello"));
				async.complete();
			});
	}
	
}