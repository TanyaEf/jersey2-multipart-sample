/*
 * Copyright © 2005 - 2016. TIBCO Software Inc. All Rights Reserved.
 */
package com.jaspersoft.client.sample.multipart;

import org.glassfish.jersey.media.multipart.MultiPart;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.media.multipart.file.StreamDataBodyPart;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
        final String jrsUrl = "http://build-master.jaspersoft.com:5580/jrs-pro-feature-full-domain-api";
//        final String jrsUrl = "http://localhost:8080/jasperserver-pro";
        WebTarget webTarget = client.target(jrsUrl + "/rest_v2/resources/public" +
                "?j_password=superuser&j_username=superuser");
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        MultiPart multiPart = new MultiPart();
        multiPart.setMediaType(MediaType.MULTIPART_FORM_DATA_TYPE);
        StreamDataBodyPart streamDataBodyPart = new StreamDataBodyPart("resource",
                classloader.getResourceAsStream("resource.json"),
                "resource",
                MediaType.valueOf("application/repository.domain+json"));
        multiPart.bodyPart(streamDataBodyPart);
        streamDataBodyPart = new StreamDataBodyPart("securityFile",
                classloader.getResourceAsStream("securitySchema.xml"),
                "securityFile",
                MediaType.valueOf("application/xml"));
        multiPart.bodyPart(streamDataBodyPart);
        streamDataBodyPart = new StreamDataBodyPart("bundles.bundle[0]",
                classloader.getResourceAsStream("supermart_domain.properties"),
                "bundles.bundle[0]",
                MediaType.valueOf("application/properties"));
        multiPart.bodyPart(streamDataBodyPart);
        streamDataBodyPart = new StreamDataBodyPart("bundles.bundle[1]",
                classloader.getResourceAsStream("supermart_domain_es.properties"),
                "bundles.bundle[1]",
                MediaType.valueOf("application/properties"));
        multiPart.bodyPart(streamDataBodyPart);
        Response response = webTarget.request(MediaType.APPLICATION_JSON_TYPE).accept("application/repository.domain+json")
                .post(Entity.entity(multiPart, multiPart.getMediaType()));
        System.out.println("Response status: " + response.getStatus());
        final String s = response.readEntity(String.class);
        System.out.println("Response body: " + s);

    }
}
