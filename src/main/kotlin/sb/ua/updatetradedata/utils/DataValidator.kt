package sb.ua.updatetradedata.utils

import com.opencsv.bean.BeanVerifier
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import kotlin.reflect.KClass
import kotlin.reflect.KMutableProperty1
import kotlin.reflect.full.isSubclassOf
import kotlin.reflect.full.memberProperties

/**
 * Validator for validating data in beans.
 * @author SerhiiBuria
 * This class implements the [BeanVerifier] interface and is used to validate data in beans,
 * such as checking the date format, the presence of a product name, etc.
 * To expand the capabilities of the class, you need to add fields that need to be validated
 * and add an implementation of the validation method.
 * @property dateFieldName The name of the field that contains the date for validation.
 * @property nameFieldName The name of the field that contains the product name for validation.
 * @property idFieldName The name of the field that contains the identifier for logging.
 * @property rowNumber The number of the row that is processed (used for logging).
 */
@Component
class DataValidator<T : Any>(
    private val dateFieldName: String? = null,
    private val nameFieldName: String? = null,
    private val idFieldName: String? = null,
    private val rowNumber: Int? = null
) : BeanVerifier<T> {
    private val logger = LoggerFactory.getLogger(DataValidator::class.java)
    private val dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd")

    /**
     * Checks the bean for compliance with the validation rules.
     * @param bean The bean to be checked.
     * @return `true` if the bean complies with the validation rules, `false` otherwise.
     */
    override fun verifyBean(bean: T): Boolean {
        var isValid = true
        val identifier = getIdentifier(bean)

        dateFieldName?.let { fieldName ->
            isValid = isValid and validateDate(bean, fieldName, identifier)
        }

        nameFieldName?.let { fieldName ->
            validateProductName(bean, fieldName, identifier)
        }

        return isValid
    }

    /**
     * Checks the date format of the specified field.
     * @param bean The bean that contains the date field.
     * @param fieldName The name of the field that contains the date.
     * @param identifier The identifier to log (for example, a line number or record ID).
     * @return `true` if the date is valid, `false` otherwise.
     */
    private fun validateDate(bean: T,
                             fieldName: String,
                             identifier: String): Boolean {

        val dateField = bean::class.memberProperties.find { it.name == fieldName }
            ?: throw IllegalArgumentException("Field '$fieldName' not found in ${bean::class.simpleName}")

        val dateValue = dateField.getter.call(bean) as? String
            ?: return false.also {
                logger.error("[$identifier] Field '$fieldName' is null or not a String")
            }

        return try {
            LocalDate.parse(dateValue, dateFormatter)
            true
        } catch (e: DateTimeParseException) {
            logger.error("[$identifier] Invalid date format: $dateValue")
            false
        }
    }

    /**
     * Checks for the presence of a product name in the specified field.
     * If the name is missing, sets the default value ("Missing product name").
     * @param bean The bean that contains the product name field.
     * @param fieldName The name of the field that contains the product name.
     * @param identifier The identifier to log (for example, a line number or record ID).
     */
    private fun validateProductName(bean: T, fieldName: String, identifier: String) {
        val nameField = bean::class.memberProperties.find { it.name == fieldName }
            ?: throw IllegalArgumentException("Field '$fieldName' not found in ${bean::class.simpleName}")

        val nameValue = nameField.getter.call(bean) as? String

        if (nameValue.isNullOrBlank()) {
            logger.warn("[$identifier] Missing product name. Setting default value.")
            if (nameField.returnType.classifier?.let { it as? KClass<*> }?.isSubclassOf(String::class) == true) {
                (nameField as? KMutableProperty1<T, String>)?.set(bean, "Missing product name")
            }
        }
    }

    /**
     * Gets the identifier for logging.
     * @param bean The bean for which to get the identifier.
     * @return The identifier (for example, the value of an identifier field or a row number).
     */
    private fun getIdentifier(bean: T): String {
        return idFieldName?.let { fieldName ->
            bean::class.memberProperties.find { it.name == fieldName }?.getter?.call(bean)?.toString()
        } ?: "Row ${rowNumber ?: "unknown"}"
    }
}
