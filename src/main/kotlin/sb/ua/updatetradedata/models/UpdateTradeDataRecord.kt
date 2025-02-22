package sb.ua.updatetradedata.models

/**
 * A class representing an updated trade data record.
 * @author SerhiiBuria *
 * @property date Date in "yyyyMMdd" format.
 * @property productName Product Name.
 * @property currency Currency.
 * @property price Price.
 */
data class UpdateTradeDataRecord(
    val date: String,
    var productName: String,
    val currency: String,
    val price: Double
)
