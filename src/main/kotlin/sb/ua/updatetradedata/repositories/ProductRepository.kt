package sb.ua.updatetradedata.repositories

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.ScanOptions
import org.springframework.stereotype.Repository
import sb.ua.updatetradedata.models.Product

/**
 * Repository for working with products stored in Redis.
 * @author SerhiiBuria
 * @property redisTemplate Redis template for performing data storage and retrieval operations.
 */
@Repository
class ProductRepository(
    private val redisTemplate: RedisTemplate<Long, String>,
) {
    /**
     * Stores the product in Redis.
     * @param productId Product ID.
     * @param productName Product name.
     */
    fun saveProduct(productId: Long, productName: String) {
        redisTemplate.opsForValue().set(productId, productName)
    }

    /**
     * Gets the product name by its ID.
     * @param productId The product ID.
     * @return The product name if it exists in Redis, or `null` if the product is not found.
     */
    fun getProductNameByProductId(productId: Long): String? {
        return redisTemplate.opsForValue().get(productId)
    }

    /**
     * Gets a list of all products stored in Redis.
     * @return A list of products.
     * Each product is represented by a [Product] object, which contains the product ID and name.
     * If the product is not found, `null` is returned in the corresponding list item.
     */
    fun getAllProducts(): List<Product?> {
        val scanOptions = ScanOptions.scanOptions().match("*").count(100).build()
        val cursor = redisTemplate.connectionFactory?.connection?.scan(scanOptions)
         val keys = mutableListOf<Long>()
        cursor?.use { cur ->
            while (cur.hasNext()) {
                val key = cur.next()
                key.toString(Charsets.UTF_8).toLongOrNull()?.let { keys.add(it) }
            }
        }

        return  keys.map { key ->
            redisTemplate.opsForValue().get(key)?.let { productName ->
                Product(productId = key, productName = productName)
            }
        }
    }
}
