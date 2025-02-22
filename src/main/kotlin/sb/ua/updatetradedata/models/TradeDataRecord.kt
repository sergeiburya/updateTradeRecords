package sb.ua.updatetradedata.models

import com.opencsv.bean.CsvBindByName

/**
 * A class representing a trade data record.
 * @author SerhiiBuria *
 * @property date Date in "yyyyMMdd" format.
 * @property productId Product identifier.
 * @property currency Currency.
 * @property price Price.
 */
data class TradeDataRecord (
    @CsvBindByName(column = "date")
    val date: String,
    @CsvBindByName(column = "productId")
    val productId: Long,
    @CsvBindByName(column = "currency")
    val currency: String,
    @CsvBindByName(column = "price")
    val price: Double,
) {
    constructor() :this("",0,"",0.0)
}
