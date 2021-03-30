package fr.istic.taa.jaxrs.utils;

import fr.istic.taa.jaxrs.dto.UserDto;
import io.jsonwebtoken.JwtException;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.security.Principal;


@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthFilter implements ContainerRequestFilter {

    private static final String AUTHENTICATION_SCHEME = "Bearer";

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        // Get the Authorization header from the request
        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        // Validate the Authorization header
        if (!isTokenBasedAuthentication(authorizationHeader)) {
            abortWithUnauthorized(requestContext, "No token provided !");
            return;
        }

        // Extract the token from the Authorization header
        String token = authorizationHeader.substring(AUTHENTICATION_SCHEME.length()).trim();

        try {
            // Validate the token and add userId
            validateToken(token, requestContext);
        } catch (JwtException e) {
            abortWithUnauthorized(requestContext, "Token invalid or expired !");
        } catch (Exception e) {
            abortWithUnauthorized(requestContext, "");
        }
    }

    private boolean isTokenBasedAuthentication(String authorizationHeader) {
        return authorizationHeader != null && authorizationHeader.toLowerCase()
                .startsWith(AUTHENTICATION_SCHEME.toLowerCase() + " ");
    }

    private void abortWithUnauthorized(ContainerRequestContext requestContext, String message) {

        // Abort the filter chain with a 401 status code response
        // The WWW-Authenticate header is sent along with the response
        requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                .header(HttpHeaders.WWW_AUTHENTICATE, AUTHENTICATION_SCHEME)
                .entity(message)
                .build());
    }

    private void validateToken(String token, ContainerRequestContext requestContext) throws Exception {

        UserDto userDto = JwtUtil.parseToken(token);

        final SecurityContext currentSecurityContext = requestContext.getSecurityContext();
        requestContext.setSecurityContext(new SecurityContext() {
            @Override
            public Principal getUserPrincipal() {
                //send user id over @Secured requests
                return () -> String.valueOf(userDto.getId());
            }

            @Override
            public boolean isUserInRole(String s) {
                return true;
            }

            @Override
            public boolean isSecure() {
                return currentSecurityContext.isSecure();
            }

            @Override
            public String getAuthenticationScheme() {
                return AUTHENTICATION_SCHEME;
            }
        });
    }
}