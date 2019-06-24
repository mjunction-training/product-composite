package com.training.mjunction.product.composite.config;

import java.util.Arrays;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;

@Configuration
@EnableCaching
public class CacheConfig {

	@Bean
	public JedisConnectionFactory jedisConnectionFactory(@Value("${redis.host:localhost}") final String host,
			@Value("${redis.port:6379}") final int port) {
		return new JedisConnectionFactory(new RedisStandaloneConfiguration(host, port));
	}

	@Bean
	public RedisTemplate<String, String> redisTemplate(final JedisConnectionFactory jedisConnectionFactor) {
		final RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(jedisConnectionFactor);
		redisTemplate.setKeySerializer(new GenericJackson2JsonRedisSerializer());
		redisTemplate.setHashKeySerializer(new GenericJackson2JsonRedisSerializer());
		redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
		redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
		redisTemplate.setDefaultSerializer(new GenericJackson2JsonRedisSerializer());
		redisTemplate.setExposeConnection(true);
		return redisTemplate;
	}

	@Bean
	public RedisCacheManager cacheManager(final JedisConnectionFactory jedisConnectionFactory) {
		return RedisCacheManager.builder(jedisConnectionFactory).disableCreateOnMissingCache().transactionAware()
				.initialCacheNames(new HashSet<>(Arrays.asList("products_composite_cache"))).build();
	}

	@Bean
	public KeyGenerator customKeyGenerator() {
		return (target, method, params) -> {
			final StringBuilder sb = new StringBuilder();
			sb.append(target.getClass().getName());
			sb.append(method.getName());
			for (final Object param : params) {
				sb.append(param.toString());
			}
			return sb.toString();
		};
	}

}
