package sb.ua.updatetradedata.utils

import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.opencsv.bean.BeanVerifier
import org.springframework.stereotype.Component
import java.io.File
import kotlin.reflect.KClass

@Component
class XmlFileParser {
    private val xmlMapper = XmlMapper()

    fun <T : Any> parseXmlFileWithValidation(
        filePath: String,
        modelClass: KClass<T>,
        verifierFactory: ((Int) -> BeanVerifier<T>)? = null
    ): List<T> {
        val file = File(filePath)
        val rootNode = xmlMapper.readTree(file)
        val elements = rootNode.elements()

        val parsedBeans = mutableListOf<T>()
        var rowNumber = 0

        for (node in elements) {
            rowNumber++
            val bean = xmlMapper.treeToValue(node, modelClass.java)
            val verifier = verifierFactory?.invoke(rowNumber)

            if (verifier == null || verifier.verifyBean(bean)) {
                parsedBeans.add(bean)
            }
        }

        return parsedBeans
    }
}
