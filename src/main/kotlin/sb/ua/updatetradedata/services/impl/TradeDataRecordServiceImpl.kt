package sb.ua.updatetradedata.services.impl

import org.springframework.stereotype.Service
import sb.ua.updatetradedata.models.TradeDataRecord
import sb.ua.updatetradedata.models.UpdateTradeDataRecord
import sb.ua.updatetradedata.utils.CsvFileParser
import sb.ua.updatetradedata.utils.DataValidator
import sb.ua.updatetradedata.utils.JsonFileParser
import sb.ua.updatetradedata.utils.XmlFileParser

/**
 * Service for updating trade data.
 * @author SerhiiBuria
 * This service is responsible for updating trade data obtained from CSV, XML or JSON files,
 * as well as from a data string. It uses other services for parsing and validating data. *
 * @property productService Service for working with products.
 * @property csvFileParser Parser for CSV files.
 * @property xmlFileParser Parser for XML files.
 * @property jsonFileParser Parser for JSON files.
 */
@Service
class TradeDataRecordServiceImpl(
    private val productService: ProductServiceImpl,
    private val csvFileParser: CsvFileParser,
    private val xmlFileParser: XmlFileParser,
    private val jsonFileParser: JsonFileParser
) {
    /**
     * Updates the trade data obtained from a file or data string.
     * @param filePath The path to the file (CSV, XML, or JSON).
     * @param stringData The data string if a file is not used.
     * @return A list of updated trade data, where each record contains the product name.
     * @throws IllegalArgumentException If the file format is not supported or the data is missing.
     */
    fun updateTradeDataRecords(filePath: String, stringData: String): List<UpdateTradeDataRecord> {
       val tradeDataRecords = when {
           filePath.endsWith(".csv") -> getTradeDataFromCsvFile(filePath)
           filePath.endsWith(".xml") -> getTradeDataFromXmlFile(filePath)
           filePath.endsWith(".json") -> getTradeDataFromJsonFile(filePath)
           stringData.isNotEmpty() || stringData.isNotBlank() -> getTradeDataFromString(stringData)
           else -> throw IllegalArgumentException("Unsupported file format or missing data")
       }

       val updatedTradeDataRecords = tradeDataRecords.map { tradeDataRecord ->
           val productName =
               productService.getProductNameByProductId(tradeDataRecord.productId)
           UpdateTradeDataRecord(
               tradeDataRecord.date,
               productName ?: "Missing Product Name",
               tradeDataRecord.currency,
               tradeDataRecord.price
           )
       }
       return updatedTradeDataRecords
    }

    /**
     * Gets trade data from a CSV file.
     * @param filePath The path to the CSV file.
     * @return List of trade data.
     */
    fun getTradeDataFromCsvFile(filePath: String): List<TradeDataRecord> {
        val trades = csvFileParser.parseCsvFileWithValidation(
            filePath,
            TradeDataRecord::class,
            verifierFactory = { rowNumber ->
                DataValidator<TradeDataRecord>(
                    dateFieldName = "date",
                    idFieldName = "tradeId",
                    rowNumber = rowNumber
                )
            }
        )
        return trades
    }

    /**
     * Gets trading data from an XML file.
     * @param filePath Path to the XML file.
     * @return List of trading data.
     */
    fun getTradeDataFromXmlFile(filePath: String): List<TradeDataRecord> {
        val trades = xmlFileParser.parseXmlFileWithValidation(
            filePath,
            TradeDataRecord::class,
            verifierFactory = { rowNumber ->
                DataValidator<TradeDataRecord>(
                    dateFieldName = "date",
                    idFieldName = "tradeId",
                    rowNumber = rowNumber
                )
            }
        )
        return trades
    }

    /**
     * Gets trade data from a JSON file.
     * @param filePath Path to the JSON file.
     * @return List of trade data.
     */
    fun getTradeDataFromJsonFile(filePath: String): List<TradeDataRecord> {
        val trades = jsonFileParser.parseJsonFileWithValidation(
            filePath,
            TradeDataRecord::class,
            verifierFactory = { rowNumber ->
                DataValidator<TradeDataRecord>(
                    dateFieldName = "date",
                    idFieldName = "tradeId",
                    rowNumber = rowNumber
                )
            }
        )
        return trades
    }

    /**
     * Gets trade data from a data string.
     * @param stringData A data string in CSV, XML, or JSON format.
     * @return A list of trade data.
     */
    fun getTradeDataFromString(stringData: String): List<TradeDataRecord> {
        return when {
            stringData.trimStart().startsWith("{") ||
                    stringData.trimStart().startsWith("[") -> getTradeDataFromJsonFile(stringData)
            stringData.trimStart().startsWith("<") -> getTradeDataFromXmlFile(stringData)
            else -> getTradeDataFromCsvFile(stringData)
        }
    }
}
