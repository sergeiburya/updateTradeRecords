package sb.ua.updatetradedata.dto.response

import io.swagger.v3.oas.annotations.media.Schema

/**
 * Data class representing the response with product information.
 * This class is used to pass information about the product,
 * including its ID and name.
 * @property productId The unique identifier of the product.
 * @property productName The name of the product.
 */
@Schema(description = "DTO for response with product information")
data class ProductResponseDto(
    @Schema(
        description = "Unique Product Identifier",
        example = "123"
    )
    val productId: Long,

    @Schema(description = "Product Name", example = "Apple")
    val productName: String,
)
