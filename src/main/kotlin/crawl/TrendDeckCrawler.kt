package crawl

import org.openqa.selenium.By
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.support.ui.WebDriverWait

class TrendDeckCrawler {

    fun runSeleninum() {
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe")

        val chromeOptions = ChromeOptions().apply {
            addArguments("--headless")
//            addArguments("--lang=en-us")
            addArguments("application/html;charset=euc-kr")
        }
        val driver = ChromeDriver(chromeOptions)
        val driverWait = WebDriverWait(driver, 5)
        driver.get(SITE_URL)

        driverWait.until {
            val elements = it.findElements(By.className("deck-tile"))

            for (element in elements) {
                val deckName = element.findElement(By.className("deck-name")).text
                val dustCost = element.findElement(By.className("dust-cost")).text
                val winRate = element.findElement(By.className("win-rate")).text
                val gameCount = element.findElement(By.className("game-count")).text
                val duration = element.findElement(By.className("duration")).text

                println("$deckName $dustCost $winRate $gameCount $duration ")

                val cards = element.findElements(By.className("card-icon"))
                    .map { it.getAttribute("aria-label") }
                    .forEach { println(it) }
                println()
            }

//            val sets = listOf("UTF-8", "euc-kr", "ISO-8859-1", "US-ASCII", "x-windows-949", "ASCII")
//            for (firstSet in sets) {
//                for (secondSet in sets) {
//                    println("$firstSet / $secondSet")
//                    val element = elements[0]
//                    val deckName = element.findElement(By.className("deck-name")).text
//                    val dustCost = element.findElement(By.className("dust-cost")).text
//                    val winRate = element.findElement(By.className("win-rate")).text
//                    val gameCount = element.findElement(By.className("game-count")).text
//                    val duration = element.findElement(By.className("duration")).text
//
//                    println("$deckName $dustCost $winRate $gameCount $duration ")
//
//                    val cards = element.findElements(By.className("card-icon"))
//                        .map {
//                            it.getAttribute("aria-label")
//                                .toByteArray(Charset.forName(firstSet))
//                                .toString(Charset.forName(secondSet))
//                        }.forEach { println(it) }
//                    println("아니 시발 뭔데? \n")
//
//                }
//            }
            // ㅋㅋ앰병 인텔리제이 인코딩 문제였네 ㅋㅋ 내 3시간 돌려줘
        }
    }

    companion object {
        private const val SITE_URL = "https://hsreplay.net/decks/trending/"
    }
}

fun main() {
    val crawler = TrendDeckCrawler()
//    crawler.runHtmlUnit()
    crawler.runSeleninum()
}
