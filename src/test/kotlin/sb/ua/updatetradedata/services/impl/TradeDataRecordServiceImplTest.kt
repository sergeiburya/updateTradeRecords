package sb.ua.updatetradedata.services.impl

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import sb.ua.updatetradedata.models.TradeDataRecord
import sb.ua.updatetradedata.models.UpdateTradeDataRecord
import sb.ua.updatetradedata.utils.CsvFileParser
import sb.ua.updatetradedata.utils.DataValidator
import sb.ua.updatetradedata.utils.JsonFileParser
import sb.ua.updatetradedata.utils.XmlFileParser

@ExtendWith(MockitoExtension::class)
class TradeDataRecordServiceImplTest{
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
            tradeDataRecordService.updateTradeDataRecords(filePath, "")
        }
    }

//    @Test
//    fun updateTradeDataRecordsShouldReturnUpdatedTradeDataFromCSVFile() {
//        val filePath = "test.csv"
//        val tradeDataRecords = listOf(testTradeDataRecord)
//        val updatedTradeDataRecords = listOf(testUpdateTradeDataRecord)
//
//        `when`(csvFileParser.parseCsvFileWithValidation(
//            eq(filePath),
//            eq(TradeDataRecord::class),
//            any()
//        )).thenReturn(tradeDataRecords)
//
//        `when`(productService.getProductNameByProductId(testTradeDataRecord.productId))
//            .thenReturn("Product 1")
//
//        val result = tradeDataRecordService.updateTradeDataRecords(filePath, "")
//
//        assertEquals(updatedTradeDataRecords, result)
//    }

//    @Test
//    fun getTradeDataFromCsvFileShouldReturnListTradeData() {
//        val filePath = "test.csv"
//        val tradeDataRecords = listOf(testTradeDataRecord)
//
//        `when`(csvFileParser.parseCsvFileWithValidation(
//            eq(filePath),
//            eq(TradeDataRecord::class),
//            any()
//        )).thenReturn(tradeDataRecords)
//
//        val result = tradeDataRecordService.getTradeDataFromCsvFile(filePath)
//
//        assertEquals(tradeDataRecords, result)
//    }

//    @Test
//    fun getTradeDataFromXmlFileShouldReturnListTradeData() {
//        val filePath = "test.xml"
//        val tradeDataRecords = listOf(testTradeDataRecord)
//
//        `when`(xmlFileParser.parseXmlFileWithValidation(
//            eq(filePath),
//            eq(TradeDataRecord::class),
//            any()
//        )).thenReturn(tradeDataRecords)
//
//        val result = tradeDataRecordService.getTradeDataFromXmlFile(filePath)
//
//        assertEquals(tradeDataRecords, result)
//    }

//    @Test
//    fun getTradeDataFromJsonFileShouldReturnListTradeData() {
//        val filePath = "test.json"
//        val tradeDataRecords = listOf(testTradeDataRecord)
//
//        `when`(jsonFileParser.parseJsonFileWithValidation(
//            eq(filePath),
//            eq(TradeDataRecord::class),
//            any()
//        )).thenReturn(tradeDataRecords)
//
//        val result = tradeDataRecordService.getTradeDataFromJsonFile(filePath)
//
//        assertEquals(tradeDataRecords, result)
//    }

//    @Test
//    fun getTradeDataFromStringShouldReturnListTradeDataFromJSONString() {
//        val jsonString = """[{"date": "20231001", "productId": 1, "currency": "USD", "price": 1.23}]"""
//        val tradeDataRecords = listOf(testTradeDataRecord)
//
//        `when`(jsonFileParser.parseJsonFileWithValidation(
//            eq(jsonString),
//            eq(TradeDataRecord::class),
//            any()
//        )).thenReturn(tradeDataRecords)
//
//        val result = tradeDataRecordService.getTradeDataFromString(jsonString)
//
//        assertEquals(tradeDataRecords, result)
//    }

//    @Test
//    fun getTradeDataFromStringShouldReturnListTradeDataFromXMLString() {
//        val xmlString =
//                "<tradeDataRecords>" +
//                    "<tradeDataRecord>" +
//                        "<date>20231001</date>" +
//                        "<productId>1</productId>" +
//                        "<currency>USD</currency>" +
//                        "<price>1.23</price>" +
//                    "</tradeDataRecord>" +
//                "</tradeDataRecords>"
//        val tradeDataRecords = listOf(testTradeDataRecord)
//
//        `when`(xmlFileParser.parseXmlFileWithValidation(
//            eq(xmlString),
//            eq(TradeDataRecord::class),
//            any()
//        )).thenReturn(tradeDataRecords)
//
//        val result = tradeDataRecordService.getTradeDataFromString(xmlString)
//
//        assertEquals(tradeDataRecords, result)
//    }

//    @Test
//    fun getTradeDataFromStringShouldReturnListTradeDataFromCSVString() {
//        val csvString = "date,productId,currency,price\n20231001,1,USD,1.23"
//        val tradeDataRecords = listOf(testTradeDataRecord)
//
//        `when`(csvFileParser.parseCsvFileWithValidation(
//            eq(csvString),
//            eq(TradeDataRecord::class),
//            any()
//        )).thenReturn(tradeDataRecords)
//
//        val result = tradeDataRecordService.getTradeDataFromString(csvString)
//
//       assertEquals(tradeDataRecords, result)
//    }
}
