package sb.ua.updatetradedata.utils

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.opencsv.bean.BeanVerifier
import org.springframework.stereotype.Component
import java.io.File
import kotlin.reflect.KClass

@Component
class JsonFileParser {
    private val objectMapper = jacksonObjectMapper()

    fun <T : Any> parseJsonFileWithValidation(
        filePath: String,
        modelClass: KClass<T>,
        verifierFactory: ((Int) -> BeanVerifier<in T>)? = null,
    ): List<T> {
        val file = File(filePath)

        val beans: List<T> = objectMapper.readValue(file, object : TypeReference<List<T>>() {})

        val parsedBeans = mutableListOf<T>()

        beans.forEachIndexed { index, bean ->
            val rowNumber = index + 1
            val verifier = verifierFactory?.invoke(rowNumber)

            if (verifier == null || verifier.verifyBean(bean)) {
                parsedBeans.add(bean)
            }
        }

        return parsedBeans
    }
}
