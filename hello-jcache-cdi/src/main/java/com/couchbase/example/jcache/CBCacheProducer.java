package com.couchbase.example.jcache;

import java.util.logging.Logger;

import javax.cache.Cache;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;


@ApplicationScoped
public class CBCacheProducer {
	
	private static final Logger LOG = Logger.getLogger(CBCacheProducer.class.getName());
	
	@Produces
	@CBObjectCache
	public Cache<String, Object> createCache() {
		
		LOG.info("Producing cache ...");			
		return CBObjectCacheFactory.create();
	}
	
}
