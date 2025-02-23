package sb.ua.updatetradedata.controllers

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import sb.ua.updatetradedata.dto.response.ProductResponseDto
import sb.ua.updatetradedata.mappers.ProductMapper
import sb.ua.updatetradedata.services.impl.ProductServiceImpl

/**
 * Default path to the product data file.
 */
const val DEFAULT_PRODUCTS_FILE_PATH = "src/main/resources/productData.xml"

/**
 * Controller for working with products.
 * @author SerhiiBuria
 * This controller provides an API for loading products from a file and getting a list of all products.
 */
@Tag(name = "Products", description = "API for working with products")
@RestController
@RequestMapping("/products")
class ProductController(
    private val productService: ProductServiceImpl,
    private val productMapper: ProductMapper,
) {

    /**
     * Loads products from a file or string.
     * @param filePath Path to the product data file. Default is [DEFAULT_PRODUCTS_FILE_PATH].
     * @param stringData String of data in JSON, XML, or text format. Used if no file is specified.
     * @return Response with a message that the products were successfully loaded.
     */
    @PostMapping(
        "/load",
        consumes = [
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.TEXT_PLAIN_VALUE]
    )
    @Operation(
        summary = "Load products from file",
        description = "Loads products from a CSV, XML, or JSON file."
    )
    @ApiResponse(responseCode = "200", description = "Products loaded successfully.")
    suspend fun saveProducts(
        @RequestParam(
            required = false,
            defaultValue = DEFAULT_PRODUCTS_FILE_PATH
        ) filePath: String,
        @RequestBody(required = false) stringData: String,
    ): ResponseEntity<String> {
        productService.saveProductFromFile(filePath, stringData = "")

        return ResponseEntity.ok("Products loaded successfully from $filePath")
    }

    /**
     * Gets a list of all products.
     * @return Response with a list of products in the format [ProductResponseDto].
     */
    @GetMapping
    @Operation(summary = "Get all products", description = "Returns a list of all products.")
    @ApiResponse(responseCode = "200", description = "Product list successfully retrieved.")
    suspend fun getAllProducts(): ResponseEntity<List<ProductResponseDto?>> {
        val products = productService.getAllProducts()
            .map { it?.let { it1 -> productMapper.mapToDto(it1) } }

        return ResponseEntity(products, HttpStatus.OK)
    }
}
