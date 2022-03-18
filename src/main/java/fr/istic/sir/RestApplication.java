/**
 * JBoss, Home of Professional Open Source
 * Copyright Red Hat, Inc., and individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package fr.istic.sir;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import fr.istic.sir.api.AuthResource;
import fr.istic.sir.api.BoardResource;
import fr.istic.sir.api.CardResource;
import fr.istic.sir.api.SectionResource;
import fr.istic.sir.api.SwaggerResource;
import fr.istic.sir.api.TagResource;
import fr.istic.sir.api.TeamResource;
import fr.istic.sir.api.UserResource;
import fr.istic.sir.utils.AuthFilter;
import fr.istic.sir.utils.CorsFilter;
import io.swagger.v3.jaxrs2.integration.resources.OpenApiResource;

public class RestApplication extends Application {

    @Override
    public Set<Class<?>> getClasses() {

        final Set<Class<?>> clazzes = new HashSet<Class<?>>();

        clazzes.add(OpenApiResource.class);
        clazzes.add(AuthResource.class);
        clazzes.add(UserResource.class);
        clazzes.add(BoardResource.class);
        clazzes.add(SectionResource.class);
        clazzes.add(CardResource.class);
        clazzes.add(TagResource.class);
        clazzes.add(TeamResource.class);
        // Swagger
        clazzes.add(SwaggerResource.class);
        //Filters
        clazzes.add(AuthFilter.class);
        clazzes.add(CorsFilter.class);

        return clazzes;
    }

}
