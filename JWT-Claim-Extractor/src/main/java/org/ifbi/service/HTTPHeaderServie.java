/*
 * Copyright (c) 2016, WSO2 Inc. (http://wso2.com) All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ifbi.service;

import com.google.gson.Gson;
import com.nimbusds.jwt.ReadOnlyJWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.wso2.msf4j.Request;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import java.text.ParseException;


/**
 * This is the Microservice resource class.
 * See <a href="https://github.com/wso2/msf4j#getting-started">https://github.com/wso2/msf4j#getting-started</a>
 * for the usage of annotations.
 *
 * @since 1.0.0-SNAPSHOT
 */
@Path("/header")

public class HTTPHeaderServie {

    private static final String JWT_HEADER = "X-JWT-Assertion";
    private static final String AUTH_TYPE_JWT = "JWT";

    @GET
    @Path("/all")
    public String get(@Context Request request) throws ParseException {
        System.out.println("GET invoked");
        System.out.println("HTTP Headers:");
        request.getHeaders().entrySet().stream().
                forEach(entry -> System.out.println("Header: " + entry.getKey() + " Value: " + entry.getValue()));
        System.out.println("JWT Claims :");
        if (request.getHeaders() != null) {
            String jwtHeader = request.getHeaders().get(JWT_HEADER);
            if (jwtHeader != null) {
                SignedJWT signedJWT = SignedJWT.parse(jwtHeader);
                ReadOnlyJWTClaimsSet readOnlyJWTClaimsSet = signedJWT.getJWTClaimsSet();
                if (readOnlyJWTClaimsSet != null) {
                    readOnlyJWTClaimsSet.getAllClaims().entrySet().stream().
                            forEach(entry -> System.out.println("Claim URI: " + entry.getKey() + " Claim Value: " + entry.getValue()));
                }
            }
        }
        Gson gson = new Gson();
        String jsonOut = gson.toJson(request.getHeaders());
        System.out.println("JSON Response: " + jsonOut);
        return jsonOut;
    }

    @POST
    @Path("/")
    public void post() {
        // TODO: Implementation for HTTP POST request
        System.out.println("POST invoked");
    }

    @PUT
    @Path("/")
    public void put() {
        // TODO: Implementation for HTTP PUT request
        System.out.println("PUT invoked");
    }

    @DELETE
    @Path("/")
    public void delete() {
        // TODO: Implementation for HTTP DELETE request
        System.out.println("DELETE invoked");
    }
}
