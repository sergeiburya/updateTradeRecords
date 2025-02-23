package sb.ua.updatetradedata.utils

import com.fasterxml.jackson.databind.JsonMappingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.junit.jupiter.MockitoExtension
import sb.ua.updatetradedata.models.Product
import java.io.File
import java.io.FileNotFoundException

@ExtendWith(MockitoExtension::class)
class JsonFileParserTest {
    @InjectMocks
    private lateinit var jsonFileParser: JsonFileParser

    private val jsonMapper = ObjectMapper().registerModule(KotlinModule())

    @Test
    fun parseJsonFileWithValidationShouldParseValidJsonFile() {
        val filePath = "src/test/resources/valid_products.json"
        val jsonContent = """
            {
                "items": [
                    {
                        "productId": 1,
                        "productName": "Product 1"
                    },
                    {
                        "productId": 2,
                        "productName": "Product 2"
                    }
                ]
            }
        """.trimIndent()

        File(filePath).writeText(jsonContent)

        val expectedProducts = listOf(
            Product(productId = 1, productName = "Product 1"),
            Product(productId = 2, productName = "Product 2")
        )

        val result = jsonFileParser.parseJsonFileWithValidation(
            filePath,
            Product::class,
            verifierFactory = null
        )

        assertEquals(expectedProducts, result)
    }

    @Test
    fun parseJsonFileWithValidationShouldHandleValidation() {
        val filePath = "src/test/resources/products_with_invalid_name.json"
        val jsonContent = """
            {
                "items": [
                    {
                        "productId": 1,
                        "productName": "Product 1"
                    },
                    {
                        "productId": 2,
                        "productName": ""
                    }
                ]
            }
        """.trimIndent()

        File(filePath).writeText(jsonContent)

        val expectedProducts = listOf(
            Product(productId = 1, productName = "Product 1"),
            Product(productId = 2, productName = "Missing product name")
        )

        val result = jsonFileParser.parseJsonFileWithValidation(
            filePath,
            Product::class,
            verifierFactory = { rowNumber ->
                DataValidator<Product>(
                    nameFieldName = "productName",
                    idFieldName = "productId",
                    rowNumber = rowNumber
                )
            }
        )

        assertEquals(expectedProducts, result)
    }

    @Test
    fun parseJsonFileWithValidationShouldThrowFileNotFoundException() {
        val filePath = "non_existent_file.json"

        assertThrows<FileNotFoundException> {
            jsonFileParser.parseJsonFileWithValidation(
                filePath,
                Product::class,
                verifierFactory = null
            )
        }
    }

    @Test
    fun parseJsonFileWithValidationShouldThrowJsonMappingExceptionForInvalidJson() {
        val filePath = "src/test/resources/invalid_products.json"
        val invalidJsonContent = """
            {
                "items": [
                    {
                        "productId": 1,
                        "productName":
                    }
                ]
            }
        """.trimIndent()

        File(filePath).writeText(invalidJsonContent)

        assertThrows<JsonMappingException> {
            jsonFileParser.parseJsonFileWithValidation(
                filePath,
                Product::class,
                verifierFactory = null
            )
        }
    }
}
