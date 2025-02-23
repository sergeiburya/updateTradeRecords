package sb.ua.updatetradedata.mappers

import org.springframework.stereotype.Component
import sb.ua.updatetradedata.dto.response.ProductResponseDto
import sb.ua.updatetradedata.models.Product

@Component
class ProductMapper : ResponseDtoMapper<Product, ProductResponseDto> {
    override fun mapToDto(model: Product): ProductResponseDto {
        return ProductResponseDto(
            model.productId,
            model.productName ?: "Missing Product Name"
        )
    }
}
