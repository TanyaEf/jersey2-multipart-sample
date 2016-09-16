/*
 * Copyright © 2005 - 2016. TIBCO Software Inc. All Rights Reserved.
 */
package com.jaspersoft.client.sample.multipart;

import org.glassfish.jersey.media.multipart.MultiPart;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.media.multipart.file.FileDataBodyPart;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;

/**
 * <p></p>
 *
 * @author yaroslav.kovalchyk
 * @version $Id$
 */
public class Application {
    public static void main(String...args){
        Client client = ClientBuilder.newBuilder()
                .register(MultiPartFeature.class).build();
        WebTarget webTarget = client.target("http://localhost:8080/jasperserver-pro/rest_v2/resources/public" +
                "?j_password=superuser&j_username=superuser");
        MultiPart multiPart = new MultiPart();
        multiPart.setMediaType(MediaType.MULTIPART_FORM_DATA_TYPE);
        FileDataBodyPart fileDataBodyPart = new FileDataBodyPart("resource",
                new File("C:/Users/yaroslav.kovalchyk/Downloads/supermartDomain/resource.json"),
                MediaType.valueOf("application/repository.domain+json"));
        multiPart.bodyPart(fileDataBodyPart);
        fileDataBodyPart = new FileDataBodyPart("securityFile",
                new File("C:/Users/yaroslav.kovalchyk/Downloads/supermartDomain/securitySchema.xml"),
                MediaType.valueOf("application/xml"));
        multiPart.bodyPart(fileDataBodyPart);
        fileDataBodyPart = new FileDataBodyPart("bundles.bundle[0]",
                new File("C:/Users/yaroslav.kovalchyk/Downloads/supermartDomain/supermart_domain.properties"),
                MediaType.valueOf("application/properties"));
        multiPart.bodyPart(fileDataBodyPart);
        fileDataBodyPart = new FileDataBodyPart("bundles.bundle[1]",
                new File("C:/Users/yaroslav.kovalchyk/Downloads/supermartDomain/supermart_domain_es.properties"),
                MediaType.valueOf("application/properties"));
        multiPart.bodyPart(fileDataBodyPart);
        Response response = webTarget.request(MediaType.APPLICATION_JSON_TYPE).accept("application/repository.domain+json")
                .post(Entity.entity(multiPart, multiPart.getMediaType()));
        System.out.println("Response status: " + response.getStatus());
        final String s = response.readEntity(String.class);
        System.out.println("Response body: " + s);

    }
}
