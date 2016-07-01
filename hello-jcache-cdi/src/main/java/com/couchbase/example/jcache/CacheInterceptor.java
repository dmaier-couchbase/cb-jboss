package com.couchbase.example.jcache;

import java.io.Serializable;

import javax.cache.Cache;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

@Cached
@Interceptor
public class CacheInterceptor implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	@CBObjectCache
	private Cache<String, Object> cache;
	
	public CacheInterceptor() {
	}
	
	@AroundInvoke
	public Object log(InvocationContext ctx) throws Exception {
				
		String name = ctx.getParameters()[0].toString();		
    	String hello = "Hello " + name + "!";	
		cache.put("hello_msg", hello);
		
		return ctx.proceed();
	}
	
}
