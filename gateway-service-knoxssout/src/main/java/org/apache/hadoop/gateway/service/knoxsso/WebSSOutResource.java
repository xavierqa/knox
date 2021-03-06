/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.hadoop.gateway.service.knoxsso;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import org.apache.hadoop.gateway.i18n.messages.MessagesFactory;
import org.apache.hadoop.gateway.util.Urls;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_XML;
import static javax.ws.rs.core.Response.ok;

import java.net.URISyntaxException;

@Path( WebSSOutResource.RESOURCE_PATH )
public class WebSSOutResource {
  private static final String JWT_COOKIE_NAME = "hadoop-jwt";
  static final String RESOURCE_PATH = "/api/v1/webssout";
  static final String KNOXSSO_RESOURCE_PATH = "/api/v1/websso";
  private static KnoxSSOutMessages log = MessagesFactory.get( KnoxSSOutMessages.class );
  
  private String domainSuffix = null;

  @Context
  private HttpServletRequest request;

  @Context
  private HttpServletResponse response;

  @Context
  ServletContext context;

  @PostConstruct
  public void init() {
  }

  @GET
  @Produces({APPLICATION_JSON, APPLICATION_XML})
  public Response doGet() {
    boolean rc = removeAuthenticationToken(response);
    if (rc) {
      return ok().entity("{ \"loggedOut\" : true }").build();
    } else {
      return ok().entity("{ \"loggedOut\" : false }").build();
    }
  }

  @POST
  @Produces({APPLICATION_JSON, APPLICATION_XML})
  public Response doPost() {
    boolean rc = removeAuthenticationToken(response);
    if (rc) {
      return ok().entity("{ \"loggedOut\" : true }").build();
    } else {
      return ok().entity("{ \"loggedOut\" : false }").build();
    }
  }

  private boolean removeAuthenticationToken(HttpServletResponse response) {
    boolean rc = true;
    Cookie c = new Cookie(JWT_COOKIE_NAME, null);
    c.setMaxAge(0);
    c.setPath("/");
    try {
      c.setDomain(Urls.getDomainName(request.getRequestURL().toString(), domainSuffix));
    } catch (URISyntaxException e) {
      log.problemWithCookieDomainUsingDefault();
      // we are probably not going to be able to
      // remove the cookie due to this error but it
      // isn't necessarily not going to work.
      rc = false;
    }
    response.addCookie(c);
    
    return rc;
  }
}
