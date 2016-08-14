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

package org.myperl;

import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * This is the Microservice resource class.
 * See <a href="https://github.com/wso2/msf4j#getting-started">https://github.com/wso2/msf4j#getting-started</a>
 * for the usage of annotations.
 *
 * @since 0.1-SNAPSHOT
 */
@Path("/math")
public class MathService {

    private static ScriptEngineManager manager = new ScriptEngineManager();

    @GET
    @Path("/")
    public String get(@QueryParam("exper") String expression
    ) throws ScriptException {
        ScriptEngine engine = manager.getEngineByName("nashorn");
        return engine.eval(expression).toString();
    }

    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String post(String payload) throws ScriptException, ParseException {
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(payload);
        String expression = (String) jsonObject.get("exper");
        ScriptEngine engine = manager.getEngineByName("nashorn");
        JSONObject result = new JSONObject();
        result.put("result", engine.eval(expression).toString());
        return result.toString();
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
