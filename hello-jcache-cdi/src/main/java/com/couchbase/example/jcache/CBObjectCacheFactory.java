package com.couchbase.example.jcache;

import java.util.logging.Logger;

import javax.cache.Cache;
import javax.cache.CacheManager;

import com.couchbase.client.java.env.DefaultCouchbaseEnvironment;
import com.couchbase.client.jcache.CouchbaseConfiguration;
import com.couchbase.client.jcache.KeyConverter;
import com.couchbase.client.jcache.spi.CouchbaseCachingProvider;


/**
 * Uses Couchbase's JCache implementation for caching Java objects
 * 
 * This factory can be used without dependency injection.
 * It was just created for testing purposes.
 * 
 * 
 * @author david.maier@couchbase.com
 *
 */
public class CBObjectCacheFactory {
	

	
	public static final Logger LOG = Logger.getLogger(CBObjectCacheFactory.class.getName());
	
	/**
	 * A global timeout value
	 */
	public static final int TIMEOUT = 10000;
	
	/**
	 * The name of the cache
	 */
	public static final String CACHE_NAME = "cb_jb_cache";
	
	/**
	 * The name of the bucket to use
	 */
	public static final String CACHE_BUCKET = "jb_cache";
	
	/**
	 * The password of the cache bucket
	 */
	public static final String CACHE_PWD = "test";
	
	/**
	 * The Couchbase cluster which should be used for caching purposes
	 */
	public static final String CACHE_HOST = "192.168.7.141";
	
	
	private static Cache<String, Object> cache;
	
	
	/**
	 * Initialize the cache manually
	 */
	public static Cache<String, Object> create() {

		
		LOG.info("name = " + CACHE_NAME);
		LOG.info("host = " + CACHE_HOST);
		LOG.info("bucket = " + CACHE_BUCKET);
		LOG.info("pwd = " + CACHE_PWD);
		
		if (cache == null) {	
	
			LOG.info("Creating the cache ...");
			
			// A Couchbase Cache Provider by connecting to a remote host
			CouchbaseCachingProvider provider = new CouchbaseCachingProvider();
			provider.setBootstrap(CACHE_HOST);
			provider.setEnvironment(DefaultCouchbaseEnvironment.builder()
					.connectTimeout(TIMEOUT)
					.managementTimeout(TIMEOUT)
					.kvTimeout(TIMEOUT)
					.build());

			LOG.info("Bootstrap endpoint set to " + provider.getBoostrap());
			
			// A Couchbase cache config
			CouchbaseConfiguration<String, Object> cfg = CouchbaseConfiguration
					.builder(CACHE_NAME, KeyConverter.STRING_KEY_CONVERTER).useSharedBucket(CACHE_BUCKET, CACHE_PWD)
					.withPrefix("cached::").build();

			LOG.info("Config is " + cfg.toString());
			
			//A Couchbase cache manager
			CacheManager mgr = provider.getCacheManager();

			LOG.info("Manager is " +  mgr.toString());
			
			return cache = mgr.createCache(CACHE_NAME, cfg);

		} else {
			return cache;
		}
	}
	
	
	public Cache<String, Object> get() {
		return cache;
	}
	
	
}
