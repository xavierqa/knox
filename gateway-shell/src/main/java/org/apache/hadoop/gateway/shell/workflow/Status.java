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
package org.apache.hadoop.gateway.shell.workflow;

import org.apache.hadoop.gateway.shell.AbstractRequest;
import org.apache.hadoop.gateway.shell.AbstractResponse;
import org.apache.hadoop.gateway.shell.Hadoop;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;

import java.util.concurrent.Callable;

class Status {

  static class Request extends AbstractRequest {

    private String jobId;

    Request( Hadoop hadoop ) {
      super( hadoop );
    }

    public Request jobId( String jobId ) {
      this.jobId = jobId;
      return this;
    }

    protected Callable<Response> callable() {
      return new Callable<Response>() {
        @Override
        public Response call() throws Exception {
          URIBuilder uri = uri( Workflow.SERVICE_PATH, "/job/", jobId );
          HttpGet request = new HttpGet( uri.build() );
          return new Response( execute( request ) );
        }
      };
    }

  }

  static class Response extends AbstractResponse {

    Response( HttpResponse response ) {
      super( response );
    }

  }

}