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
package org.jclouds.aws.s3.blobstore.internal;

import org.jclouds.blobstore.options.PutOptions;
import org.jclouds.s3.blobstore.options.S3PutOptions;
import org.jclouds.s3.options.PutObjectOptions;

import com.google.common.base.Function;

/**
 * Encapsulation of common functionality for converting from a PutOptions instance to PutObjectOptions
 * for AWS S3 operations.
 */
public class AWSS3PutOptionsToPutObjectOptions implements
Function<PutOptions, AWSS3PutOptionsToPutObjectOptions.ReturnValue> {
	
	// Oh look, it's a tuple!  Or at least it would be if, you know, Java didn't have opinions about such things.
	public static class ReturnValue {
		
		private final PutObjectOptions mainOptions;
		private final PutObjectOptions partOptions;
		
		public ReturnValue(PutObjectOptions mainOptions, PutObjectOptions partOptions) {
			
			this.mainOptions = mainOptions;
			this.partOptions = partOptions;
		}

		public PutObjectOptions getMainOptions() {
			return mainOptions;
		}

		public PutObjectOptions getPartOptions() {
			return partOptions;
		}
	}

	@Override
	public ReturnValue apply(PutOptions putOptions) {

		if (putOptions == null || (! (putOptions instanceof S3PutOptions))) { 
			return new ReturnValue(PutObjectOptions.DEFAULTS, PutObjectOptions.DEFAULTS); }

		assert (putOptions instanceof S3PutOptions);
		S3PutOptions s3Option = (S3PutOptions)putOptions;
		
		/* 
		 * OPSC-7247
		 * 
		 * PUT ops for individual parts should _not_ include the server-side encryption header, so return
		 * a pair of PutObjectOptions in all cases to support this transparently.
		 */
		return new ReturnValue(
				s3Option.usesServerSideEncryption() ? 
						PutObjectOptions.builder().serverSideEncryption().build() :
							PutObjectOptions.DEFAULTS,
				PutObjectOptions.DEFAULTS);
	}
}
