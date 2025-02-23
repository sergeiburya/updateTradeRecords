package sb.ua.updatetradedata.services.impl

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import sb.ua.updatetradedata.models.Product
import sb.ua.updatetradedata.repositories.ProductRepository

@ExtendWith(MockitoExtension::class)
class ProductServiceImplTest {
    @Mock
    private lateinit var productRepository: ProductRepository

    @InjectMocks
    private lateinit var productService: ProductServiceImpl

    private val testProduct = Product(productId = 1, productName = "Apple")


    @Test
    fun saveProductFromFileShouldThrowIllegalArgumentExceptionForUnsupportedFileFormat() {
        val filePath = "unsupported"

        assertThrows<IllegalArgumentException> {
            productService.saveProductFromFile(filePath)
        }
    }

    @Test
    fun getProductNameByProductIdShouldReturnProductNameIfExists() {
        val productId = 1L
        val productName = "Apple"
        `when`(productRepository.getProductNameByProductId(productId)).thenReturn(productName)

        val result = productService.getProductNameByProductId(productId)

        assertEquals(productName, result)
    }

    @Test
    fun getProductNameByProductIdShouldReturnDefaultNameIfProductNotFound() {
        val productId = 1L
        `when`(productRepository.getProductNameByProductId(productId)).thenReturn(null)

        val result = productService.getProductNameByProductId(productId)

        assertEquals("Missing Product Name", result)
    }

    @Test
    fun getAllProductsShouldReturnListProducts() {
        val products = listOf(testProduct)
        `when`(productRepository.getAllProducts()).thenReturn(products)

        val result = productService.getAllProducts()

        assertEquals(products, result)
    }
}
