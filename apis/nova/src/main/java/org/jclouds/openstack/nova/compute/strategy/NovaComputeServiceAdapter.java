/**
 * Licensed to jclouds, Inc. (jclouds) under one or more
 * contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  jclouds licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jclouds.openstack.nova.compute.strategy;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.jclouds.openstack.nova.options.CreateServerOptions.Builder.withMetadata;
import static org.jclouds.openstack.nova.options.ListOptions.Builder.withDetails;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.jclouds.compute.ComputeService;
import org.jclouds.compute.ComputeServiceAdapter;
import org.jclouds.compute.domain.Template;
import org.jclouds.domain.Location;
import org.jclouds.domain.LoginCredentials;
import org.jclouds.openstack.nova.NovaClient;
import org.jclouds.openstack.nova.domain.Flavor;
import org.jclouds.openstack.nova.domain.Image;
import org.jclouds.openstack.nova.domain.RebootType;
import org.jclouds.openstack.nova.domain.Server;
import org.jclouds.openstack.nova.options.ListOptions;

import com.google.common.collect.ImmutableSet;

/**
 * defines the connection between the {@link NovaClient} implementation and the jclouds
 * {@link ComputeService}
 * 
 */
@Singleton
public class NovaComputeServiceAdapter implements ComputeServiceAdapter<Server, Flavor, Image, Location> {

   protected final NovaClient client;

   @Inject
   protected NovaComputeServiceAdapter(NovaClient client) {
      this.client = checkNotNull(client, "client");
   }

   @Override
   public NodeAndInitialCredentials<Server> createNodeWithGroupEncodedIntoName(String group, String name,
            Template template) {
      Server server = client.createServer(name, template.getImage().getId(), template.getHardware().getId(),
               withMetadata(template.getOptions().getUserMetadata()).withSecurityGroup(group));

      return new NodeAndInitialCredentials<Server>(server, server.getId() + "", LoginCredentials.builder().password(
               server.getAdminPass()).build());
   }

   @Override
   public Iterable<Flavor> listHardwareProfiles() {
      return client.listFlavors(withDetails());

   }

   @Override
   public Iterable<Image> listImages() {
      return client.listImages(withDetails());
   }

   @Override
   public Iterable<Server> listNodes() {
      return client.listServers(ListOptions.Builder.withDetails());
   }

   @Override
   public Iterable<Location> listLocations() {
      // Not using the adapter to determine locations
      return ImmutableSet.<Location>of();
   }

   @Override
   public Server getNode(String id) {
      int serverId = Integer.parseInt(id);
      return client.getServer(serverId);
   }
   
   @Override
   public Image getImage(String id) {
      int imageId = Integer.parseInt(id);
      return client.getImage(imageId);
   }

   @Override
   public void destroyNode(String id) {
      int serverId = Integer.parseInt(id);
      // if false server wasn't around in the first place
      client.deleteServer(serverId);
   }

   @Override
   public void rebootNode(String id) {
      int serverId = Integer.parseInt(id);
      // if false server wasn't around in the first place
      client.rebootServer(serverId, RebootType.HARD);
   }

   @Override
   public void resumeNode(String id) {
      throw new UnsupportedOperationException("suspend not supported");
   }

   @Override
   public void suspendNode(String id) {
      throw new UnsupportedOperationException("suspend not supported");
   }

}