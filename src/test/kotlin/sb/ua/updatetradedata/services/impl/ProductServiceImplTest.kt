package sb.ua.updatetradedata.services.impl

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import sb.ua.updatetradedata.models.Product
import sb.ua.updatetradedata.repositories.ProductRepository
import sb.ua.updatetradedata.utils.CsvFileParser
import sb.ua.updatetradedata.utils.JsonFileParser
import sb.ua.updatetradedata.utils.XmlFileParser

@ExtendWith(MockitoExtension::class)
class ProductServiceImplTest {
    @Mock
    private lateinit var productRepository: ProductRepository

    @Mock
    private lateinit var csvFileParser: CsvFileParser

    @Mock
    private lateinit var xmlFileParser: XmlFileParser

    @Mock
    private lateinit var jsonFileParser: JsonFileParser

    @InjectMocks
    private lateinit var productService: ProductServiceImpl

    private val testProduct = Product(productId = 1, productName = "Apple")

    @BeforeEach
    fun setUp() {
    }

    @Test
    fun saveProductFromFileShouldThrowIllegalArgumentExceptionForUnsupportedFileFormat() {
        val filePath = "unsupported"

        assertThrows<IllegalArgumentException> {
            productService.saveProductFromFile(filePath, "")
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
    fun getProductNameByProductIdShouldReturnDefaultMessageIfProductNotFound() {
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

//    @Test
//    fun saveProductFromFileShouldSaveProductsFromCSVFile() {
//        val filePath = "src/test/resources/productTest.csv"
//        val expectedProducts = listOf(
//            Product(productId = 1, productName = "Product 1"),
//            Product(productId = 2, productName = "Product 2")
//        )
//
//        `when`(csvFileParser.parseCsvFileWithValidation(
//            eq(filePath),
//            eq(Product::class),
//            any()
//        )).thenReturn(expectedProducts)
//
//        productService.saveProductFromFile(filePath, "")
//
//        for (product in expectedProducts) {
//            verify(productRepository, times(1))
//                .saveProduct(product.productId, product.productName)
//        }
//    }

//    @Test
//    fun getProductFromCsvFileShouldReturnListProducts() {
//        val filePath = "src/test/resources/productTest.csv"
//        val expectedProducts = listOf(
//            Product(productId = 1, productName = "Product 1"),
//            Product(productId = 2, productName = "Product 2")
//        )
//
//        val result = productService.getProductFromCsvFile(filePath)
//
//        assertEquals(expectedProducts, result)
//    }

//    @Test
//    fun getProductFromXmlFileShouldReturnListProducts() {
//        val filePath = "test.xml"
//        val products = listOf(testProduct)
//        `when`(xmlFileParser.parseXmlFileWithValidation(
//            eq(filePath),
//            eq(Product::class),
//            any()
//        )).thenReturn(products)
//
//        val result = productService.getProductFromXmlFile(filePath)
//
//        assertEquals(products, result)
//    }

//    @Test
//    fun getProductFromJsonFileShouldReturnListProducts() {
//        val filePath = "test.json"
//        val products = listOf(testProduct)
//        `when`(jsonFileParser.parseJsonFileWithValidation(
//            eq(filePath),
//            eq(Product::class),
//            any()
//        )).thenReturn(products)
//
//        val result = productService.getProductFromJsonFile(filePath)
//
//        assertEquals(products, result)
//    }

//    @Test
//    fun getProductFromStringShouldReturnListProductsFromJSONString() {
//        val jsonString = """[{"productId": 1, "productName": "Apple"}]"""
//        val products = listOf(testProduct)
//        `when`(jsonFileParser.parseJsonFileWithValidation(
//            eq(jsonString),
//            eq(Product::class),
//            any()
//        )).thenReturn(products)
//
//        val result = productService.getProductFromString(jsonString)
//
//        assertEquals(products, result)
//    }

//    @Test
//    fun getProductFromStringShouldReturnListProductsFromXMLString() {
//        val xmlString =
//            "<products><product><productId>1</productId><productName>Apple</productName></product></products>"
//        val products = listOf(testProduct)
//        `when`(xmlFileParser.parseXmlFileWithValidation(
//            eq(xmlString),
//            eq(Product::class),
//            any()
//        )).thenReturn(products)
//
//        val result = productService.getProductFromString(xmlString)
//
//        assertEquals(products, result)
//    }

//    @Test
//    fun getProductFromStringShouldReturnListProductsFromCSVString() {
//        val csvString = "productId,productName\n1,Apple"
//        val products = listOf(testProduct)
//        `when`(csvFileParser.parseCsvFileWithValidation(
//            eq(csvString),
//            eq(Product::class),
//            any()
//        )).thenReturn(products)
//
//        val result = productService.getProductFromString(csvString)
//
//        assertEquals(products, result)
//    }

}
