package net.eusashead.vertx.docker;

import java.lang.management.ManagementFactory;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;

public class HttpVerticle extends AbstractVerticle {

	@Override
	public void start(Future<Void> fut) {
		
		final String name = ManagementFactory.getRuntimeMXBean().getName();

		System.out.println("Starting HttpVerticle: " + name);


		vertx
		.createHttpServer()
		.requestHandler(r -> {
			System.out.println("Request received on: " + name);
			
            JsonObject entity = new JsonObject()
                    .put("message", "Hello world!")
                    .put("result", "q.result()")
                    .put("servedBy", name);
            r.response().end(entity.encode());

		})
		.listen(8080, result -> {
			if (result.succeeded()) {
				System.out.println("Web verticle registered.");
				fut.complete();
			} else {
				fut.fail(result.cause());
			}
		});
	}
}
