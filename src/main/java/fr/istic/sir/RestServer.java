package fr.istic.sir;

import io.undertow.Undertow;
import org.jboss.resteasy.plugins.server.undertow.UndertowJaxrsServer;

import java.util.logging.Logger;

/**
 * RESTFull microservice, based on JAX-RS and JBoss Undertow
 */
public class RestServer {

    private static final Logger logger = Logger.getLogger(RestServer.class.getName());

    public static void main(String[] args) {

        UndertowJaxrsServer ut = new UndertowJaxrsServer();
        RestApplication ta = new RestApplication();
        ut.deploy(ta);
        ut.start(
                Undertow.builder().addHttpListener(8090, "localhost")
        );
        logger.info("KanbanBoard Api running!");
    }
}
