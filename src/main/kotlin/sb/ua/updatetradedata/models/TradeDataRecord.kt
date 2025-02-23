package sb.ua.updatetradedata.models

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
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
    @JacksonXmlProperty(localName = "date")
    val date: String,
    @CsvBindByName(column = "productId")
    @JacksonXmlProperty(localName = "productId")
    val productId: Long,
    @CsvBindByName(column = "currency")
    @JacksonXmlProperty(localName = "currency")
    val currency: String,
    @CsvBindByName(column = "price")
    @JacksonXmlProperty(localName = "price")
    val price: Double,
) {
    constructor() :this("",0,"",0.0)
}
