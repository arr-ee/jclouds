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

import org.jclouds.s3.domain.CannedAccessPolicy;
import org.jclouds.s3.options.PutObjectOptions;

import static org.jclouds.s3.reference.S3Headers.CANNED_ACL;
import static org.jclouds.s3.reference.S3Headers.SERVER_SIDE_ENCRYPTION;

public class S3PutObjectOptions extends PutObjectOptions {
	
	/* Currently the only value supported for server-side encryption */
	public static final String DEFAULT_CRYPTO_ALGORITHM = "AES256";

	private boolean useServerSideEncryption;
	
	protected S3PutObjectOptions(boolean useServerSideEncryption) {

		this.useServerSideEncryption = useServerSideEncryption;
	}
	
	public boolean usesServerSideEncryption() { return this.useServerSideEncryption; }
	   	
	public static Builder builder() { return new Builder(); }
	
	public static class Builder {

		private boolean useServerSideEncryption;
		private CannedAccessPolicy acl;
		
		private Builder() {

			this.useServerSideEncryption = false;
			this.acl = CannedAccessPolicy.PRIVATE;
		}
		
		public Builder serverSideEncryption() {

			this.useServerSideEncryption = true;
			return this;
		}
		
		public Builder acl(CannedAccessPolicy acl) {
			
			this.acl = acl;
			return this;
		}
		
		public S3PutObjectOptions build() {

			S3PutObjectOptions rv = new S3PutObjectOptions(this.useServerSideEncryption);
			if (this.useServerSideEncryption) {
				rv.replaceHeader(SERVER_SIDE_ENCRYPTION, DEFAULT_CRYPTO_ALGORITHM);
			}
			
			/* Duplicates some logic from PutObjectOptions.  This kind of thing _can_ be handled by subclassing 
			 * builders, but down that path there be dragons. */
			if (!acl.equals(CannedAccessPolicy.PRIVATE))
				rv.replaceHeader(CANNED_ACL, acl.toString());
			
			return rv;
		}
	}
}
