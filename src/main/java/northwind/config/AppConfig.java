package northwind.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import northwind.model.Product;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.*;

/*
Swagger API 
https://localhost:8443/v2/api-docs

Swagger UI
https://localhost:8443/swagger-ui.html
 * */
@EnableCaching
@Configuration
@ComponentScan(basePackages= {"northwind"})
public class AppConfig {

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	@Bean
	public RedisTemplate<String, Product> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
		RedisTemplate<String, Product> template = new RedisTemplate<>();
		template.setConnectionFactory(redisConnectionFactory);
		template.setKeySerializer(new StringRedisSerializer());
		template.setHashKeySerializer(new StringRedisSerializer());

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		RedisSerializer<Product> serializer = new Jackson2JsonRedisSerializer<>(objectMapper,Product.class);
		//don't want @class metadata in serialized payload
		//RedisSerializer<Product> serializer = new GenericJackson2JsonRedisSerializer(objectMapper);
		template.setValueSerializer(serializer);
		template.setHashValueSerializer(serializer);
		template.afterPropertiesSet();
		return template;
	}

	@Bean
	public RedisCacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule()); // Support LocalDateTime
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		RedisSerializer<Product> serializer = new Jackson2JsonRedisSerializer<>(objectMapper,Product.class);
		//don't want @class metadata in serialized payload
		//RedisSerializer<Product> serializer = new GenericJackson2JsonRedisSerializer(objectMapper);
		// By default, Spring Boot uses JdkSerializationRedisSerializer if no custom serializer is provided.It is not cross-platform (Java-specific serialization).
		RedisCacheConfiguration cacheConfig = RedisCacheConfiguration.defaultCacheConfig()
				.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(serializer));

		return RedisCacheManager.builder(redisConnectionFactory)
				.cacheDefaults(cacheConfig)
				.build();
	}
}
