package fr.istic.sir.api;

import io.swagger.v3.oas.annotations.Operation;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("/api")
public class SwaggerResource {

    private static final Logger logger = Logger.getLogger(SwaggerResource.class.getName());

    @GET
    @Operation(summary = "View API Documentation")
    public byte[] viewApiDoc() {
        try {
            return Files.readAllBytes(FileSystems.getDefault().getPath("src/main/webapp/swagger/index.html"));
        } catch (IOException e) {
            logger.log(Level.WARNING, e.getMessage());
            return null;
        }
    }

    @GET
    @Path("{path:.*}")
    @Operation(summary = "Targets API Doc files")
    public byte[] getApiDocFile(@PathParam("path") String path) {
        try {
            return Files.readAllBytes(FileSystems.getDefault().getPath("src/main/webapp/swagger/"+path));
        } catch (IOException e) {
            logger.log(Level.WARNING, e.getMessage());
            return null;
        }
    }

}
