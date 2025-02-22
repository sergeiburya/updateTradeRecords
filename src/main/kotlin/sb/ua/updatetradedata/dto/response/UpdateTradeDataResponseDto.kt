package sb.ua.updatetradedata.dto.response

import io.swagger.v3.oas.annotations.media.Schema

/**
 * Data class representing the response with updated trade data.
 * This class is used to pass information about updated trade data,
 * including the date, product name, currency, and price.
 * @property date Date in string format (e.g., "yyyy-MM-dd").
 * @property productName Product name.
 * @property currency Currency in which the price is specified (e.g., "USD", "EUR").
 * @property price Product price.
 */
@Schema(description = "DTO for response with updated trade data")
data class UpdateTradeDataResponseDto(
    @Schema(
        description = "Date in string format",
        example = "20231001"
    )
    val date: String,

    @Schema(
        description = "Product Name",
        example = "Apple"
    )
    val productName: String,
    @Schema(
        description = "The currency in which the price is listed.",
        example = "USD"
    )
    val currency: String,

    @Schema(description = "Product Price", example = "1.23")
    val price: Double,
)
