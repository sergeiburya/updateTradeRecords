package sb.ua.updatetradedata.models

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement

/**
 * Class representing a root element in an XML structure.
 * This class is used to model a root element that contains a list of items of a generic type `T`.
 * It supports serialization and deserialization from XML format using Jackson XML annotations.
 * @author SerhiiBuria
 * @property items A list of items of type `T`. Each item is represented as an `<item>` element in the XML.
 *       The `@JacksonXmlElementWrapper(useWrapping = false)` annotation ensures that the list items
 *       are not wrapped in an additional container element.
 * @constructor Creates a new RootElement with the specified list of items.
 * @param items The list of items to be included in the root element.
 * @param T The type of items contained in the list. This allows the class to be generic
 *       and reusable for different types.
 * @see JacksonXmlRootElement
 * @see JacksonXmlElementWrapper
 * @see JacksonXmlProperty
 */
@JacksonXmlRootElement(localName = "root")
data class RootElement<T>(
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "item")
    val items: List<T>
)
