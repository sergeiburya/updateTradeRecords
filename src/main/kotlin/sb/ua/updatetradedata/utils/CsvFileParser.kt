package sb.ua.updatetradedata.utils

import com.opencsv.bean.BeanVerifier
import com.opencsv.bean.CsvToBeanBuilder
import org.springframework.stereotype.Component
import java.io.FileReader
import java.io.Reader
import kotlin.reflect.KClass

/**
 * Component for parsing CSV files with data validation.
 */
@Component
class CsvFileParser {

    /**
     * Parses a CSV file with data validation.
     * @author SerhiiBuria
     * @param input Path to the CSV file or String Data.
     * @param modelClass The class of the model into which the data will be parsed.
     * @param verifierFactory Factory for creating validators for the fields of the model to be parsed.
     * @return List of objects of the model type into which the data was parsed.
     */
    fun <T : Any> parseCsvFileWithValidation(
        input: Any,
        modelClass: KClass<T>,
        verifierFactory: ((Int) -> BeanVerifier<T>)? = null,
    ): List<T> {
        val reader = when (input) {
            is String -> FileReader(input)
            is Reader -> input
            else -> throw IllegalArgumentException(
                "Unsupported input type. Expected String (file path) or Reader."
            )
        }

        val csvToBean = CsvToBeanBuilder<T>(reader)
            .withType(modelClass.java)
            .withIgnoreLeadingWhiteSpace(true)
            .withIgnoreEmptyLine(true)
            .build()

        val parsedBeans = mutableListOf<T>()

        csvToBean.iterator().withIndex().forEach { (index, bean) ->
            val rowNumber = index + 1
            val verifier = verifierFactory?.invoke(rowNumber)


            if (verifier == null || verifier.verifyBean(bean)) {
                parsedBeans.add(bean)
            }
        }

        return parsedBeans
    }

}
