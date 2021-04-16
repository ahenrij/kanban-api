package fr.istic.taa.jaxrs.utils;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

@Provider
public class CorsFilter implements ContainerResponseFilter {

   @Override
   public void filter(final ContainerRequestContext requestContext,
                      final ContainerResponseContext responseContext) throws IOException {
      responseContext.getHeaders().add("Access-Control-Allow-Origin", "*");
      responseContext.getHeaders().add("Access-Control-Allow-Headers", "origin, content-type, accept, authorization");
      responseContext.getHeaders().add("Access-Control-Allow-Credentials", "true");
      responseContext.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");

      // For HTTP OPTIONS verb/method reply with ACCEPTED status code -- per CORS handshake
      if (requestContext.getMethod().equals("OPTIONS")) {
         responseContext.setStatus(HttpServletResponse.SC_NO_CONTENT);
      }
   }
}