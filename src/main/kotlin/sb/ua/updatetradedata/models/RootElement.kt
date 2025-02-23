package sb.ua.updatetradedata.models

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty

data class RootElement<T>(
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "items")
    val items: List<T>
)

