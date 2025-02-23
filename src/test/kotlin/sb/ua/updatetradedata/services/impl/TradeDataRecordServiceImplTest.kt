package sb.ua.updatetradedata.services.impl

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import sb.ua.updatetradedata.models.TradeDataRecord
import sb.ua.updatetradedata.models.UpdateTradeDataRecord
import sb.ua.updatetradedata.utils.CsvFileParser
import sb.ua.updatetradedata.utils.JsonFileParser
import sb.ua.updatetradedata.utils.XmlFileParser

@ExtendWith(MockitoExtension::class)
class TradeDataRecordServiceImplTest {
    @Mock
    private lateinit var productService: ProductServiceImpl

    @Mock
    private lateinit var csvFileParser: CsvFileParser

    @Mock
    private lateinit var xmlFileParser: XmlFileParser

    @Mock
    private lateinit var jsonFileParser: JsonFileParser

    @InjectMocks
    private lateinit var tradeDataRecordService: TradeDataRecordServiceImpl

    private val testTradeDataRecord = TradeDataRecord(
        date = "20231001",
        productId = 1,
        currency = "USD",
        price = 1.23
    )

    private val testUpdateTradeDataRecord = UpdateTradeDataRecord(
        date = "20231001",
        productName = "Product 1",
        currency = "USD",
        price = 1.23
    )

    @BeforeEach
    fun setUp() {
    }

    @Test
    fun updateTradeDataRecordsShouldThrowIllegalArgumentExceptionForUnsupportedFileFormat() {
        val filePath = "unsupported"

        assertThrows<IllegalArgumentException> {
            tradeDataRecordService.updateTradeDataRecords(filePath)
        }
    }

}
