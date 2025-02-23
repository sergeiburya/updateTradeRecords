package sb.ua.updatetradedata.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement
import com.opencsv.bean.CsvBindByName

/**
 * Class representing a product.
 *
 * This class is used to model product data, including its unique identifier and name.
 * It supports serialization and deserialization from both XML and CSV formats.
 *
 * @author SerhiiBuria
 *
 * @property productId Unique identifier for the product. This field is mandatory and maps to the "productId" column in CSV and the "productId" element in XML.
 * @property productName Name of the product. This field is optional and maps to the "productName" column in CSV and the "productName" element in XML.
 *                      If the product name is missing or null, it defaults to "Missing product name".
 *
 * @constructor Creates a new Product with the specified product ID and name.
 * @param productId The unique identifier for the product.
 * @param productName The name of the product. It defaults to "Missing product name" if it is not provided.
 *
 * @constructor Creates a new Product with default values (productId = 0, productName = null).
 * This constructor is primarily used for deserialization purposes.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
@JacksonXmlRootElement(localName = "product")
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
