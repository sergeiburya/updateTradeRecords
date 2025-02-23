package sb.ua.updatetradedata.utils

import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.opencsv.bean.BeanVerifier
import org.springframework.stereotype.Component
import sb.ua.updatetradedata.models.RootElement
import java.io.File
import kotlin.reflect.KClass

/**
 * A component responsible for parsing XML files and validating their contents.
 * This class uses Jackson's XML mapper to deserialize XML files into Kotlin objects.
 * It supports parsing files with a generic structure and validating the parsed data
 *      using a provided `BeanVerifier` factory.
 * @author SerhiiBuria
 * @property xmlMapper The Jackson `XmlMapper` instance configured with the `KotlinModule`
 *      to support Kotlin data classes.
 * @see XmlMapper
 * @see KotlinModule
 * @see BeanVerifier
 */
@Component
class XmlFileParser{
    private val xmlMapper = XmlMapper().registerModule(KotlinModule())

    /**
     * Parses an XML file and validates its contents.
     * This method reads an XML file from the specified path, deserializes it into a list of objects
     * of type `T`, and validates each object using a `BeanVerifier` (if provided).
     * @param filePath The path to the XML file to be parsed.
     * @param itemClass The Kotlin class representing the type of objects to be parsed.
     * @param verifierFactory An optional factory function that creates a `BeanVerifier` for each row.
     *        If provided, the verifier is used to validate each parsed object.
     * @return A list of parsed and validated objects of type `T`.
     * @throws IOException If an I/O error occurs while reading the file.
     * @throws JsonProcessingException If the XML content cannot be parsed into the specified type.
     * @param T The type of objects to be parsed. Must be a non-nullable type (`Any`).
     * @see BeanVerifier
     * @see KClass
     */
    fun <T : Any> parseXmlFileWithValidation(
        filePath: String,
        itemClass: KClass<T>,
        verifierFactory: ((Int) -> BeanVerifier<T>)? = null,
    ): List<T> {
        val file = File(filePath)

        val typeFactory = xmlMapper.typeFactory
        val javaType = typeFactory.constructParametricType(
            RootElement::class.java,
            itemClass.java
        )
        val rootElement = xmlMapper.readValue<RootElement<T>>(file, javaType)
        println("Parsed XML data: ${rootElement.items}") //TODO

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
