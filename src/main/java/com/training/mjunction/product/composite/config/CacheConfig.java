package com.training.mjunction.product.composite.config;

import java.util.Arrays;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.transaction.TransactionAwareCacheDecorator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
@EnableCaching
public class CacheConfig {

	@Bean
	public JedisConnectionFactory jedisConnectionFactory(@Value("${redis.host:localhost}") final String host,
			@Value("${redis.port:6379}") final int port) {
		return new JedisConnectionFactory(new RedisStandaloneConfiguration(host, port));
	}

	@Bean
	public RedisTemplate<Object, Object> redisTemplate(final JedisConnectionFactory jedisConnectionFactor) {
		final RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(jedisConnectionFactor);
		redisTemplate.setExposeConnection(true);
		return redisTemplate;
	}

	@Bean
	public CacheManager cacheManager(final JedisConnectionFactory jedisConnectionFactory) {
		return RedisCacheManager.builder(jedisConnectionFactory).disableCreateOnMissingCache().transactionAware()
				.initialCacheNames(new HashSet<>(Arrays.asList("product_cache"))).build();
	}

	@Bean
	public Cache cache(final CacheManager cacheManager) {
		return new TransactionAwareCacheDecorator(cacheManager.getCache("product_cache"));
	}

}
