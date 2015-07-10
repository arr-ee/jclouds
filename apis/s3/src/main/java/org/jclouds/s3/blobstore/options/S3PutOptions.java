package org.jclouds.s3.blobstore.options;

import org.jclouds.blobstore.options.PutOptions;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

/**
 * A truly immutable PutOptions impl for S3 operations.  Includes a proper builder for this immutable structure;
 * other PutOptions impls would ideally be refactored to do the same.
 */
public class S3PutOptions extends PutOptions {
	
	/* Only option currently supported... ?  But we can imagine other vals being supported here as well. */
	public static final String DEFAULT_CRYPTO_ALGO = "AES256";
	   
	private String cryptoAlgo;
	
	/* In an ideal world the only constructor that would exist is the last one (to full define the data for an
	 * immutable S3PutOptions object).  But without redoing PutOptions and AWSS3PutOptions as well we're forced to
	 * add a few more constructors in order to get access to default vals.  Sigh. */
	@Deprecated
	protected S3PutOptions() {
		
		super();
		this.cryptoAlgo = null;
	}
	
	@Deprecated
	protected S3PutOptions(boolean multipart) {
		
		super(multipart);
		this.cryptoAlgo = null;
	}
	
	protected S3PutOptions(boolean multipart, String crypto) {
		
		super(multipart);
		Preconditions.checkArgument(!Strings.isNullOrEmpty(crypto),"Server-side encryption algorithm can't be null or empty");
		this.cryptoAlgo = crypto;
	}
	   
	public boolean usesServerSideEncryption() {
		return this.cryptoAlgo != null;
	}
	   
	public String getServerSideEncryptionAlgorithm() {
		return this.cryptoAlgo;
	}
	
	public static Builder builder() { return new Builder(); }
	
	public static class Builder {

		/* Define builder state; default values for this state are explicitly set in the constructor. */
		private String cryptoAlgo;
		private boolean multipart;
		
		private Builder() {
			
			this.cryptoAlgo = null;
			this.multipart = false;
		}
		
		public Builder multipart() {
			
			this.multipart = true;
			return this;
		}
		
		public Builder serverSideEncryption() {
			
			this.cryptoAlgo = DEFAULT_CRYPTO_ALGO;
			return this;
		}
		
		public Builder serverSideEncryption(String algo) {
			
			this.cryptoAlgo = algo;
			return this;
		}
		
		public S3PutOptions build() {
			
			return new S3PutOptions(this.multipart, this.cryptoAlgo);
		}
	}
}
