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
package org.jclouds.s3.blobstore.options;

import org.jclouds.blobstore.options.CreateDirectoryOptions;
import org.jclouds.blobstore.options.PutOptions;

import com.google.common.base.Optional;

/* Not bothering with a builder for now because this class is so basic.  If additional properties become
 * necessary it might be worth moving to something more complex.
 * 
 * My kingdom for traits... */
public class S3CreateDirectoryOptions extends CreateDirectoryOptions {

	public static final S3CreateDirectoryOptions ENCRYPT = new S3CreateDirectoryOptions(true);
	public static final S3CreateDirectoryOptions NO_ENCRYPT = new S3CreateDirectoryOptions(false);
	
	private final boolean useServerSideEncryption;
	private final Optional<PutOptions> putOptions;
	
	private S3CreateDirectoryOptions(boolean encrypt) {
		
		this.useServerSideEncryption = encrypt;
		
		S3PutOptions.Builder builder = S3PutOptions.builder();
		if (this.useServerSideEncryption) {
			builder.serverSideEncryption();
		}
		putOptions = Optional.of((PutOptions)builder.build());
	}
	
	public boolean usesServerSideEncryption() {
		return this.useServerSideEncryption;
	}

	@Override
	public Optional<PutOptions> toPutOptions() { return this.putOptions; }
}
