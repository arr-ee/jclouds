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
package org.jclouds.aws.s3.blobstore.options;

import org.jclouds.s3.domain.CannedAccessPolicy;
import org.jclouds.s3.domain.ObjectMetadata;
import org.jclouds.s3.options.PutObjectOptions;
import org.jclouds.s3.reference.S3Headers;

/**
 * Contains options supported in the AWS S3 REST API for the PUT object operation
 *
 * @see PutObjectOptions
 */
public class AWSS3PutObjectOptions extends PutObjectOptions {

	public static final ObjectMetadata.StorageClass DEFAULT_STORAGE_CLASS = ObjectMetadata.StorageClass.STANDARD;

	private PutObjectOptions s3Options;
	private ObjectMetadata.StorageClass storageClass;

	protected AWSS3PutObjectOptions(PutObjectOptions s3Options, ObjectMetadata.StorageClass storageClass) {

		this.s3Options = s3Options;
		this.storageClass = storageClass;
	}

	@Override
	public CannedAccessPolicy getAcl() { return this.s3Options.getAcl(); }

	@Override
	public boolean usesServerSideEncryption() { return this.s3Options.usesServerSideEncryption(); }

	public ObjectMetadata.StorageClass getStorageClass() {
		return storageClass;
	}
	
	public static Builder builder() { return new Builder(); }

	public static class Builder extends PutObjectOptions.Builder {

		private ObjectMetadata.StorageClass storageClass;

		protected Builder() {

			this.storageClass = DEFAULT_STORAGE_CLASS;
		}

		public Builder storageClass(ObjectMetadata.StorageClass storageClass) {

			this.storageClass = storageClass;
			return this;
		}

		public AWSS3PutObjectOptions build() {

			AWSS3PutObjectOptions rv = new AWSS3PutObjectOptions(super.build(),this.storageClass);
			if (storageClass != ObjectMetadata.StorageClass.STANDARD) {
				rv.replaceHeader(S3Headers.STORAGE_CLASS, this.storageClass.toString());
			}
			return rv;
		}
	}
}
