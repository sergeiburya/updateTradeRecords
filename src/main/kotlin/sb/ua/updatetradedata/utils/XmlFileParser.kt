package sb.ua.updatetradedata.utils

import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.opencsv.bean.BeanVerifier
import org.springframework.stereotype.Component
import sb.ua.updatetradedata.models.RootElement
import java.io.File
import kotlin.reflect.KClass

@Component
class XmlFileParser{
    private val xmlMapper = XmlMapper()

    fun <T : Any> parseXmlFileWithValidation(
        filePath: String,
        itemClass: KClass<T>,
        verifierFactory: ((Int) -> BeanVerifier<T>)? = null,
    ): List<T> {
        val file = File(filePath)
        val typeFactory = xmlMapper.typeFactory
        val javaType = typeFactory.constructParametricType(RootElement::class.java, itemClass.java)

        val rootElement = xmlMapper.readValue<RootElement<T>>(file, javaType)
        val parsedBeans = mutableListOf<T>()
        var rowNumber = 0

        for (item in rootElement.items) {
            rowNumber++
            val verifier = verifierFactory?.invoke(rowNumber)

            if (verifier == null || verifier.verifyBean(item)) {
                parsedBeans.add(item)
            }
        }
        return parsedBeans
    }

}
