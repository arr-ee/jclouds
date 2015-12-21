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
		
		public ReturnValue(boolean useServerSideEncryption) {
			
			PutObjectOptions.Builder builder = PutObjectOptions.builder();
			if (useServerSideEncryption) { builder.serverSideEncryption(); }
			this.mainOptions = builder.build();
		}

		public PutObjectOptions getMainOptions() {
			return mainOptions;
		}

		/* Part options are always fixed at defaults due to OPSC-7247.  This may change if we start supporting
		 * additional S3 options (ACLs etc.) in future ops. */
		public PutObjectOptions getPartOptions() {
			return PutObjectOptions.DEFAULTS;
		}
	}

	/* We can get away with this because yes/no to server-side encryption is the only axis of variation at this point. */
	private static final ReturnValue USE_SERVER_SIDE_ENCRYPTION = new ReturnValue(true);
	private static final ReturnValue NO_SERVER_SIDE_ENCRYPTION = new ReturnValue(false);

	@Override
	public ReturnValue apply(PutOptions putOptions) {

		if (putOptions == null || (! (putOptions instanceof S3PutOptions))) { 
			return NO_SERVER_SIDE_ENCRYPTION;
		}

		assert (putOptions instanceof S3PutOptions);
		
		S3PutOptions s3Option = (S3PutOptions)putOptions;
		return s3Option.usesServerSideEncryption() ? USE_SERVER_SIDE_ENCRYPTION : NO_SERVER_SIDE_ENCRYPTION;
	}
}
