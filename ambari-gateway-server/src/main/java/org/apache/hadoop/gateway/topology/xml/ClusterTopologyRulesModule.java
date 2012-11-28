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
package org.apache.hadoop.gateway.topology.xml;

import org.apache.commons.digester3.binder.AbstractRulesModule;
import org.apache.hadoop.gateway.topology.ClusterComponent;
import org.apache.hadoop.gateway.topology.ClusterTopology;

public class ClusterTopologyRulesModule extends AbstractRulesModule {

  private static final String ROOT_TAG = "cluster";
  private static final String NAME_TAG = "name";
  private static final String VERSION_TAG = "version";
  private static final String COMPONENT_TAG = "component";
  private static final String ROLE_TAG = "role";
  private static final String URL_TAG = "url";

  @Override
  protected void configure() {
    forPattern( ROOT_TAG )
        .createObject().ofType( ClusterTopology.class );
    forPattern( ROOT_TAG + "/" + NAME_TAG )
        .setBeanProperty();
    forPattern( ROOT_TAG + "/" + VERSION_TAG )
        .setBeanProperty();
    forPattern( ROOT_TAG + "/" + COMPONENT_TAG )
        .createObject().ofType( ClusterComponent.class )
        .then().setNext( "addComponent" );
    forPattern( ROOT_TAG + "/" + COMPONENT_TAG + "/" + ROLE_TAG )
        .setBeanProperty();
    forPattern( ROOT_TAG + "/" + COMPONENT_TAG + "/" + URL_TAG )
        .setBeanProperty();
  }

}