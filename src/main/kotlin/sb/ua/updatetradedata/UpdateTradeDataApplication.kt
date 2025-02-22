package sb.ua.updatetradedata

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class UpdateTradeDataApplication

fun main(args: Array<String>) {
    runApplication<UpdateTradeDataApplication>(*args)
    println("Successfully...")
}
