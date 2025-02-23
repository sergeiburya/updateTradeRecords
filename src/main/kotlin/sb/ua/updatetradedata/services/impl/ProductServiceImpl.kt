package sb.ua.updatetradedata.services.impl

import org.springframework.stereotype.Service
import sb.ua.updatetradedata.models.Product
import sb.ua.updatetradedata.repositories.ProductRepository
import sb.ua.updatetradedata.utils.CsvFileParser
import sb.ua.updatetradedata.utils.DataValidator
import sb.ua.updatetradedata.utils.JsonFileParser
import sb.ua.updatetradedata.utils.XmlFileParser

/**
 * Service for working with products.
 * @author SerhiiBuria
 * This service is responsible for storing products in Redis, getting the product name by its ID
 * and getting a list of all products. It also supports loading products from CSV, XML, JSON files
 * or from a data string.
 * @property productRepository Repository for working with products in Redis.
 * @property csvFileParser Parser for CSV files.
 * @property xmlFileParser Parser for XML files.
 * @property jsonFileParser Parser for JSON files.
 */
@Service
class ProductServiceImpl(
    private val productRepository: ProductRepository,
    private val csvFileParser: CsvFileParser,
    private val xmlFileParser: XmlFileParser,
    private val jsonFileParser: JsonFileParser
) {
    /**
     * Stores products from a file or string of data in Redis.
     * @param filePath The path to the file (CSV, XML, or JSON).
     * @param stringData The string of data if the file is not used.
     * @throws IllegalArgumentException If the file format is not supported or the data is missing.
     */
    fun saveProductFromFile(filePath: String, stringData: String) {
        val products = when {
            filePath.endsWith(".csv") -> getProductFromCsvFile(filePath)
            filePath.endsWith(".xml") -> getProductFromXmlFile(filePath)
            filePath.endsWith(".json") -> getProductFromJsonFile(filePath)
            stringData.isNotEmpty() || stringData.isNotBlank() -> getProductFromString(stringData)
            else -> throw IllegalArgumentException("Unsupported file format or missing data")
        }

        for (product in products) {
            val productId = product.productId
            val productName = product.productName ?:"Missing product name"
            productRepository.saveProduct(productId, productName)
        }
    }

    /**
     * Gets the product name by its ID.
     * @param productId The product ID.
     * @return The product name if it exists in Redis,
     * or "Missing Product Name" if the product is not found.
     */
    fun getProductNameByProductId(productId: Long): String? {
        return productRepository.getProductNameByProductId(productId) ?: "Missing Product Name"
    }

    /**
     * Gets a list of all products stored in Redis.
     * @return A list of products. Each product is represented by a [Product] object,
     * which contains the product ID and name.
     */
    fun getAllProducts(): List<Product?> {
        return productRepository.getAllProducts()
    }

    /**
     * Gets products from a CSV file.
     * @param filePath The path to the CSV file.
     * @return List of products.
     */
    fun getProductFromCsvFile(filePath: String) : List<Product> {
        val products = csvFileParser.parseCsvFileWithValidation(
            filePath,
            Product::class,
            verifierFactory = { rowNumber ->
                DataValidator<Product>(
                    nameFieldName = "productName",
                    idFieldName = "tradeId",
                    rowNumber = rowNumber
                )
            }
        )
        return products
    }

    /**
     * Gets products from an XML file.
     * @param filePath Path to the XML file.
     * @return List of products.
     */
    fun getProductFromXmlFile(filePath: String) : List<Product> {
        val products = xmlFileParser.parseXmlFileWithValidation(
            filePath,
            Product::class,
            verifierFactory = { rowNumber ->
                DataValidator<Product>(
                    nameFieldName = "productName",
                    idFieldName = "tradeId",
                    rowNumber = rowNumber
                )
            }
        )
        return products
    }

    /**
     * Gets products from a JSON file.
     * @param filePath Path to the JSON file.
     * @return List of products.
     */
    fun getProductFromJsonFile(filePath: String) : List<Product> {
        val products = jsonFileParser.parseJsonFileWithValidation(
            filePath,
            Product::class,
            verifierFactory = { rowNumber ->
                DataValidator<Product>(
                    nameFieldName = "productName",
                    idFieldName = "tradeId",
                    rowNumber = rowNumber
                )
            }
        )
        return products
    }

    /**
     * Gets products from a data string.
     * @param stringData A data string in CSV, XML, or JSON format.
     * @return A list of products.
     */
    fun getProductFromString(stringData: String): List<Product> {
        return when {
            stringData.trimStart().startsWith("{") ||
                    stringData.trimStart().startsWith("[") -> getProductFromJsonFile(stringData)
            stringData.trimStart().startsWith("<") -> getProductFromXmlFile(stringData)
            else -> getProductFromCsvFile(stringData)
        }
    }
}
