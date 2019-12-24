package net.eusashead.vertx.docker;

import java.lang.management.ManagementFactory;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonObject;

public class HttpVerticle extends AbstractVerticle {

	// Get the name of the Verticle
	private static final String NAME = ManagementFactory.getRuntimeMXBean().getName();
	
	@Override
	public void start(final Promise<Void> fut) {
		
		// Return the name in the response body
		vertx
		.createHttpServer()
		.requestHandler(r -> {
			
            JsonObject entity = new JsonObject()
                    .put("message", "Hello, world!")
                    .put("servedBy", NAME);
            r.response().end(entity.encode());

		})
		.listen(8080, result -> {
			if (result.succeeded()) {
				fut.complete();
			} else {
				fut.fail(result.cause());
			}
		});
	}
}
