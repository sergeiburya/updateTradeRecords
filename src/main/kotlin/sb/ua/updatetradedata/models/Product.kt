package sb.ua.updatetradedata.models

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.opencsv.bean.CsvBindByName

/**
 * Class representing the product.
 * @author SerhiiBuria *
 * @property productId Unique identifier for the product.
 * @property productName Product name.
 */
data class Product (
    @CsvBindByName(column = "productId")
    @JacksonXmlProperty(localName = "productId")
    val productId: Long,
    @CsvBindByName(column = "productName")
    @JacksonXmlProperty(localName = "productName")
    var productName: String? = "Missing product name"
) {
    constructor() :this(0,null)
}
