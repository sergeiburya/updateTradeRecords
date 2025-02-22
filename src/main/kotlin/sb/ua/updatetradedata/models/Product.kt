package sb.ua.updatetradedata.models

import com.opencsv.bean.CsvBindByName

/**
 * Class representing the product.
 * @author SerhiiBuria *
 * @property productId Unique identifier for the product.
 * @property productName Product name.
 */
data class Product (
    @CsvBindByName(column = "productId")
    val productId: Long,
    @CsvBindByName(column = "productName")
    var productName: String
) {
    constructor() :this(0,"")
}
