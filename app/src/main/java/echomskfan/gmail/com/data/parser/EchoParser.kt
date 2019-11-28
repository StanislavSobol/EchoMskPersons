package echomskfan.gmail.com.data.parser

import echomskfan.gmail.com.data.db.entity.CastEntity
import echomskfan.gmail.com.data.db.entity.PersonEntity
import echomskfan.gmail.com.utils.catchThrowable
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.io.IOException

class EchoParser : IEchoParser {

    override fun getCasts(personEntity: PersonEntity, pageNum: Int): List<CastEntity> {
        val fullUrl = "$BASE_URL${personEntity.url}/archive/$pageNum"

        if (fullUrl.isEmpty()) {
            return listOf()
        }

        val document: Document?
        try {
            try {
                document = Jsoup.connect(fullUrl).get()
            } catch (e: IllegalArgumentException) {
                catchThrowable(e)
                return listOf()
            }

        } catch (e: IOException) {
            catchThrowable(e)
            return listOf()
        }

        return document?.let { parseItems(document, personEntity, pageNum) } ?: listOf()
    }

    @Synchronized
    private fun parseItems(document: Document, personEntity: PersonEntity, pageNum: Int): List<CastEntity> {
        val result = mutableListOf<CastEntity>()
        val divs = document.getElementsByTag("div")
        for (div in divs) {
            val divsPrevcontent = div.getElementsByClass("prevcontent")
            for (divPrevcontent in divsPrevcontent) {

                var person = ""
                try {
                    person = divPrevcontent.getElementsByTag("p")[0]
                        .getElementsByTag("a")[0]
                        .getElementsByClass("about")[0]
                        .getElementsByTag("strong").text()
                } catch (e: IndexOutOfBoundsException) {
                    catchThrowable(e)
                }

                if (!person.contains(personEntity.lastName)) {
                    continue
                }

                var fullTextURL = ""
                var type = ""
                var subtype = ""
                var shortText = ""
                var mp3Url = ""
                var mp3Duration = 0
                var formattedDate = ""

                try {

                    type = divPrevcontent.getElementsByClass("section")[0]
                        .getElementsByTag("a")[0]
                        .getElementsByTag("strong")[0].text()

                } catch (e: IndexOutOfBoundsException) {
                    catchThrowable(e)
                }

                try {
                    subtype = divPrevcontent.getElementsByClass("section")[0]
                        .getElementsByTag("a")[1]
                        .getElementsByTag("span")[0].text()

                } catch (e: IndexOutOfBoundsException) {
                    catchThrowable(e)
                }

                try {
                    shortText = divPrevcontent.getElementsByTag("p")[1]
                        .getElementsByTag("a")[0].text()
                } catch (e: IndexOutOfBoundsException) {
                    catchThrowable(e)
                }

                if (shortText.isEmpty()) {
                    try {
                        shortText = divPrevcontent.getElementsByClass("txt")[0].text()
                    } catch (e: IndexOutOfBoundsException) {
                        catchThrowable(e)
                    }
                }

                try {
                    mp3Url = divPrevcontent.getElementsByClass("mediamenu")[0]
                        .getElementsByTag("a")[3].attr("href")
                } catch (e: IndexOutOfBoundsException) {
                }

                if (mp3Url.isEmpty()) {
                    try {
                        mp3Url = divPrevcontent.getElementsByClass("mediamenu")[0]
                            .getElementsByTag("a")[2].attr("href")
                    } catch (e: IndexOutOfBoundsException) {
                        catchThrowable(e)
                    }
                }

                try {
                    val myMp3Duration = divPrevcontent.getElementsByClass("mediamenu")[0]
                        .getElementsByTag("a")[0]
                        .getElementsByTag("span")[2].text()
                    mp3Duration = Integer.valueOf(myMp3Duration.substring(0, 2)) * 60 + Integer.valueOf(
                        myMp3Duration.substring(
                            3,
                            5
                        )
                    )

                } catch (e: IndexOutOfBoundsException) {
                    catchThrowable(e)
                }

                try {
                    fullTextURL = divPrevcontent.getElementsByClass("meta")[0]
                        .getElementsByTag("a")[2].attr("data-url")

                } catch (e: IndexOutOfBoundsException) {
                    catchThrowable(e)
                }

                try {
                    formattedDate = divPrevcontent.getElementsByClass("datetime")[0].attr("title")
                } catch (e: IndexOutOfBoundsException) {
                    catchThrowable(e)
                }

                val item = CastEntity(
                    id = CastEntity.generateKey(personEntity.id, formattedDate),
                    fullTextURL = fullTextURL,
                    personId = personEntity.id,
                    pageNum = pageNum,
                    type = type,
                    subtype = subtype,
                    shortText = shortText,
                    mp3Url = mp3Url,
                    mp3Duration = mp3Duration,
                    formattedDate = formattedDate,
                    date = CastEntity.dateFromString(formattedDate)
                )

                if (!result.contains(item)) {
                    if (item.fullTextURL.isNotEmpty() || item.mp3Url.isNotEmpty()) {
                        result.find { it.fullTextURL == item.fullTextURL } ?: run {
                            result.add(item)
                        }
                    }
                }
            }
        }
        return result
    }

    companion object {
        private const val BASE_URL = "https://echo.msk.ru/"
    }
}


