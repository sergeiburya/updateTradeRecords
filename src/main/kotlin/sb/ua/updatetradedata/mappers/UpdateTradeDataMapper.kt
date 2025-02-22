package sb.ua.updatetradedata.mappers

import org.springframework.stereotype.Component
import sb.ua.updatetradedata.dto.response.UpdateTradeDataResponseDto
import sb.ua.updatetradedata.models.UpdateTradeDataRecord

@Component
class UpdateTradeDataMapper :
    ResponseDtoMapper<UpdateTradeDataRecord, UpdateTradeDataResponseDto> {

    override fun mapToDto(model: UpdateTradeDataRecord): UpdateTradeDataResponseDto {
        return UpdateTradeDataResponseDto(
            model.date,
            model.productName,
            model.currency,
            model.price
        )
    }
}
