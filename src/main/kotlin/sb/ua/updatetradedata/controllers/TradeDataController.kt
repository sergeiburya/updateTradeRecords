package sb.ua.updatetradedata.controllers

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import sb.ua.updatetradedata.dto.response.UpdateTradeDataResponseDto
import sb.ua.updatetradedata.mappers.UpdateTradeDataMapper
import sb.ua.updatetradedata.services.impl.TradeDataRecordServiceImpl

/**
 * Default path to the trading data file.
 */
const val DEFAULT_TRADE_DATA_FILES_PATH = "src/main/resources/tradeData.xml"

/**
 * Controller for working with trade data.
 * @author SerhiiBuria
 * This controller provides an API for updating trade data from a file or data string.
 */
@Tag(
    name = "Trade Data Controller",
    description = "API for working with trade data"
)
@RestController
@RequestMapping("/tradeData")
class TradeDataController(
    private val tradeDataRecordService: TradeDataRecordServiceImpl,
    private val updateTradeDataMapper: UpdateTradeDataMapper,
) {

    /**
     * Updates trade data from a file or data string.
     * @param filePath Path to the trade data file. Default is [DEFAULT_TRADE_DATA_FILES_PATH].
     * @return Response with a list of updated trade data in the format [UpdateTradeDataResponseDto].
     */
    @PostMapping("/update")
    @Operation(summary = "Update Trade Data", description = "Updates trade data from a file.")
    @ApiResponse(responseCode = "200", description = "Trade data successfully updated.")
    suspend fun updateTradeData(
        @RequestParam(
            required = false,
            defaultValue = DEFAULT_TRADE_DATA_FILES_PATH
        ) filePath: String):
            ResponseEntity<List<UpdateTradeDataResponseDto>> {

        val updatedTradeData =
            tradeDataRecordService.updateTradeDataRecords(filePath)
                .map { updateTradeDataMapper.mapToDto(it) }

        return ResponseEntity.ok(updatedTradeData)
    }
}
