package sb.ua.updatetradedata.utils

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.system.CapturedOutput
import org.springframework.boot.test.system.OutputCaptureExtension
import sb.ua.updatetradedata.models.Product
import sb.ua.updatetradedata.models.TradeDataRecord
import java.io.StringReader

@ExtendWith(OutputCaptureExtension::class)
class CsvFileParserTest {
    private val csvFileParser = CsvFileParser()

    @Test
    fun testParseValidCsvFile() {
        val csvContent = """
        productId,productName
        1,Apple
        2,Banana
    """.trimIndent()

        val products = csvFileParser.parseCsvFileWithValidation(
            StringReader(csvContent),
            Product::class,
            verifierFactory = null
        )

        assertEquals(2, products.size)
        assertEquals(1L, products[0].productId)
        assertEquals("Apple", products[0].productName)
    }

    @Test
    fun testParseCsvWithMissingProductName() {
        val csvContent = """
        productId,productName
        1,
    """.trimIndent()

        val products = csvFileParser.parseCsvFileWithValidation(
            StringReader(csvContent),
            Product::class,
            verifierFactory = { rowNumber ->
                DataValidator<Product>(
                    nameFieldName = "productName",
                    idFieldName = "productId",
                    rowNumber = rowNumber
                )
            }
        )

        assertEquals(1, products.size)
        assertEquals("Missing product name", products[0].productName)
    }

    @Test
    fun testParseCsvWithInvalidDate(output: CapturedOutput) {
        val csvContent = """
        date,productId,currency,price
        13012023,1,USD,1.23
    """.trimIndent()

        csvFileParser.parseCsvFileWithValidation(
            StringReader(csvContent),
            TradeDataRecord::class,
            verifierFactory = { rowNumber ->
                DataValidator<TradeDataRecord>(
                    dateFieldName = "date",
                    idFieldName = "productId",
                    rowNumber = rowNumber
                )
            }
        )
        assertTrue(output.all.contains("Invalid date format: 13012023"))
    }

    @Test
    fun testParseCsvWithEmptyLine() {
        val csvContent = """
        productId,productName
        
        1,Apple
    """.trimIndent()

        val products = csvFileParser.parseCsvFileWithValidation(
            StringReader(csvContent),
            Product::class,
            verifierFactory = null
        )

        assertEquals(1, products.size)
        assertEquals("Apple", products[0].productName)
    }
}
