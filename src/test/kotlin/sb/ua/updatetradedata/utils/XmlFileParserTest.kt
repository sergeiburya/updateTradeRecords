package sb.ua.updatetradedata.utils

import com.fasterxml.jackson.dataformat.xml.XmlMapper
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
class XmlFileParserTest {
    @InjectMocks
    private lateinit var xmlFileParser: XmlFileParser

    private val xmlMapper = XmlMapper().registerModule(KotlinModule())

    @Test
    fun parseXmlFileWithValidationShouldParseValidXmlFile() {
        val filePath = "src/test/resources/products.xml"
        val xmlContent = """
            <root>        
                <item>
                    <productId>1</productId>
                    <productName>Product 1</productName>
                </item>
                <item>
                    <productId>2</productId>
                    <productName>Product 2</productName>
                </item>                
            </root>
        """.trimIndent()

        File(filePath).writeText(xmlContent)

        val expectedProducts = listOf(
            Product(productId = 1, productName = "Product 1"),
            Product(productId = 2, productName = "Product 2")
        )

        val result = xmlFileParser.parseXmlFileWithValidation(
            filePath,
            Product::class,
            verifierFactory = null
        )

        assertEquals(expectedProducts, result)
    }

    @Test
    fun parseXmlFileWithValidationShouldHandleValidation() {
        val filePath = "src/test/resources/invalid_name.xml"
        val xmlContent = """
            <root>
                <item>
                    <productId>1</productId>
                    <productName>Product 1</productName>
                </item>
                <item>
                    <productId>2</productId>
                    <productName></productName> 
                </item>                
            </root>
        """.trimIndent()

        File(filePath).writeText(xmlContent)

        val expectedProducts = listOf(
            Product(productId = 1, productName = "Product 1"),
            Product(productId = 2, productName = "Missing product name")
        )

        val result = xmlFileParser.parseXmlFileWithValidation(
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
    fun parseXmlFileWithValidationShouldThrowFileNotFoundException() {
        val filePath = "non_existent_file.xml"

        assertThrows<FileNotFoundException> {
            xmlFileParser.parseXmlFileWithValidation(
                filePath,
                Product::class,
                verifierFactory = null
            )
        }
    }
}
