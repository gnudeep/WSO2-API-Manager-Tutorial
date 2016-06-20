/*
 * Copyright (c) 2008, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.wso2.carbon.registry.samples.handler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.registry.core.Registry;
import org.wso2.carbon.registry.core.Resource;
import org.wso2.carbon.registry.core.exceptions.RegistryException;
import org.wso2.carbon.registry.core.jdbc.handlers.Handler;
import org.wso2.carbon.registry.core.jdbc.handlers.RequestContext;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ProjectProposalMediaTypeHandler extends Handler {

    private static final Log log = LogFactory.getLog(ProjectProposalMediaTypeHandler.class);

    public static final String DATA_SOURCE_NAME = "jdbc/WSO2AM_DB";
    private static volatile DataSource dataSource = null;

    public Resource get(RequestContext requestContext) throws RegistryException {
        return null;
    }

    public void put(RequestContext requestContext) throws RegistryException {
        Resource resource = requestContext.getResource();
        Object o = resource.getContent();
        String resourcePath = requestContext.getResourcePath().getPath();
        Registry registry = requestContext.getRegistry();

        Connection connection = null;
        PreparedStatement prepStmt = null;
        ResultSet rs = null;

        String apiParentPath = requestContext.getParentPath();
        String pattern = "(.*)/(.*)/(.*)/(.*)";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(apiParentPath);

        String apiName = null;
        String apiVersion = null;

        if (m.find()) {
            apiName = m.group(3);
            apiVersion = m.group(4);
        } else {
            log.info("No match found");
        }

        String query = "select subscriber.USER_ID from AM_API as API, AM_SUBSCRIPTION as subs, AM_SUBSCRIBER as " +
                "subscriber, AM_APPLICATION as app  where API.API_ID = subs.API_ID AND subs.application_id = " +
                "app.application_id and subscriber.SUBSCRIBER_ID = app.subscriber_id and API.API_NAME = ? and " +
                "API.API_VERSION = ?";
        conInitialize();
        List<String> subscribers = new ArrayList<String>();

        try {
            connection = dataSource.getConnection();
            prepStmt = connection.prepareStatement(query);
            prepStmt.setString(1, apiName);
            prepStmt.setString(2, apiVersion);
            rs = prepStmt.executeQuery();

            if (rs.next()) {
                log.info("Subscriber : " + rs.getString("USER_ID"));
                subscribers.add(rs.getString("USER_ID"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public void importResource(RequestContext requestContext)
            throws RegistryException {

    }

    public void delete(RequestContext requestContext) throws RegistryException {

    }

    public void putChild(RequestContext requestContext)
            throws RegistryException {

    }

    public void importChild(RequestContext requestContext)
            throws RegistryException {

    }

    public static void conInitialize() {
        if (dataSource != null) {
            return;
        }

        synchronized (ProjectProposalMediaTypeHandler.class) {
            if (dataSource == null) {
                try {
                    Context ctx = new InitialContext();
                    dataSource = (DataSource) ctx.lookup(DATA_SOURCE_NAME);
                } catch (NamingException e) {
                    e.printStackTrace();
                }
            } else {

            }
        }
    }

}
