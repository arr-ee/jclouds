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
	
	/* Directory creation is handled at the blobstore level which prevents service-specific customizations
	 * at an impl level.  To get around this problem impls of this class are allowed to define how they
	 * should be translated into PutOptions impls.  This is only a semi-ridiculous way to handle this problem;
	 * we use Optional to allow impls to indicate that they have no sane way of converting themselves into
	 * a PutOptions impl.
	 * 
	 * It's entirely reasonable that some services may not use PUT for directory creation... if this becomes
	 * an issue we'll have to add additional conversion functions here. */
	public Optional<PutOptions> toPutOptions() {
		
		return Optional.absent();
	}
}
