package sb.ua.updatetradedata.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.GenericToStringSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer

/**
 * Configuration class for configuring RedisTemplate.
 * @author SerhiiBuria
 * This class configures `RedisTemplate` to work with Redis, where keys are of type `Long`,
 * and values are of type `String`. Used to store and retrieve data in Redis.
 */
@Configuration
class RedisConfig {

    /**
     * Creates and configures a `RedisTemplate` to work with Redis.
     * @param redisConnectionFactory Redis connection factory.
     * @return Configured `RedisTemplate`,
     * where keys are of type `Long` and values are of type `String`.
     */
    @Bean
    fun redisTemplate(redisConnectionFactory: RedisConnectionFactory): RedisTemplate<Long, String> {
        return RedisTemplate<Long, String>().apply {
            connectionFactory = redisConnectionFactory
            keySerializer = GenericToStringSerializer(Long::class.java)
            valueSerializer = StringRedisSerializer()
        }
    }
}
