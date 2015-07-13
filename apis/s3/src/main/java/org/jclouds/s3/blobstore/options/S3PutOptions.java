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

import org.jclouds.blobstore.options.PutOptions;

/**
 * A truly immutable PutOptions impl for S3 operations.  Includes a proper builder for this immutable structure;
 * other PutOptions impls would ideally be refactored to do the same.
 */
public class S3PutOptions extends PutOptions {
	
	private boolean useServerSideEncryption;
	
	/* In an ideal world the only constructor that would exist is the last one (to full define the data for an
	 * immutable S3PutOptions object).  But without redoing PutOptions and AWSS3PutOptions as well we're forced to
	 * add a few more constructors in order to get access to default vals.  Sigh. */
	@Deprecated
	protected S3PutOptions() {
		
		super();
		this.useServerSideEncryption = false;
	}
	
	@Deprecated
	protected S3PutOptions(boolean multipart) {
		
		super(multipart);
		this.useServerSideEncryption = false;
	}
	
	protected S3PutOptions(boolean multipart, boolean useServerSideEncryption) {
		
		super(multipart);
		this.useServerSideEncryption = useServerSideEncryption;
	}
	   
	public boolean usesServerSideEncryption() {
		return this.useServerSideEncryption;
	}
	   	
	public static Builder builder() { return new Builder(); }
	
	public static class Builder {

		/* Define builder state; default values for this state are explicitly set in the constructor. */
		private boolean useServerSideEncryption;
		private boolean multipart;
		
		private Builder() {
			
			this.useServerSideEncryption = false;
			this.multipart = false;
		}
		
		public Builder multipart() {
			
			this.multipart = true;
			return this;
		}
		
		public Builder serverSideEncryption() {
			
			this.useServerSideEncryption = true;
			return this;
		}
				
		public S3PutOptions build() {
			
			return new S3PutOptions(this.multipart, this.useServerSideEncryption);
		}
	}
}
