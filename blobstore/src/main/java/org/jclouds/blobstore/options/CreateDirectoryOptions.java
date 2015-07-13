/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jclouds.blobstore.options;

import com.google.common.base.Optional;


/**
 * Base options class for creating directories.  Currently doesn't have much in it since the primary
 * support for this kind of thing is using server-side encryption for S3 create ops... but that's
 * a side point.
 */
public class CreateDirectoryOptions {

	public static CreateDirectoryOptions DEFAULTS = new CreateDirectoryOptions();
	
	/* A quick hack to get around something of a scoping problem.  Directory creation is handled at the 
	 * blobstore level which prevents service-specific customizations via server-specific subclasses...
	 * the handler for multipart uploads is (by design) supposed to be agnostic about the specific service
	 * in play.  This is patently ridiculous (as the very existence of DirectoryCreationOptions indicates)
	 * but it's what we have.
	 * 
	 * Considered a number of options here, including allowing server-specific Guice modules to inject their
	 * own translators... but everything flounders on the fact that in the end the multipart upload impl 
	 * (at the blobstore level) would have to have knowledge of the service used... and there's no way to do that.
	 * 
	 * Compromise with this approach; by allowing CreateDirectoryOptions instances to create a PutOptions object
	 * based on their data we can do everything in terms of the blobstore interfaces only.  This is kinda dumb
	 * (there's no real reason we should assume that directory creation _requires_ a PUT at all) but not
	 * completely dumb.  We can use Optional to indicate when certain answers don't make sense for a given
	 * impl.... and if we find it necessary to generate other types of configs we can add them later. */	
	public Optional<PutOptions> toPutOptions() {
		
		return Optional.absent();
	}
}
