package echomskfan.gmail.com.presentation.casts

import echomskfan.gmail.com.data.db.entity.CastEntity
import echomskfan.gmail.com.utils.logInfo
import java.util.*

data class CastListItem(
    val id: String,
    val typeSubtype: String,
    val shortText: String,
    val mp3Url: String,
    val mp3Duration: Int,
    val formattedDate: String,
    val date: Date,
    val pageNum: Int,
    var fav: Boolean = false
) : ICastsListItemDelegate {
    fun same(new: CastListItem): Boolean {
        return typeSubtype == new.typeSubtype &&
                shortText == new.shortText &&
                mp3Url == new.mp3Url &&
                mp3Duration == new.mp3Duration &&
                fav == new.fav
    }

    companion object {
        fun from(personEntities: List<CastEntity>): List<CastListItem> {
            val result = mutableListOf<CastListItem>()
            personEntities.forEach { result.add(oneFrom(it)) }
            return result
        }

        private fun oneFrom(castEntity: CastEntity): CastListItem {
            return CastListItem(
                id = castEntity.id,
                typeSubtype = castEntity.getTypeSubtype(),
                shortText = castEntity.shortText,
                mp3Url = castEntity.mp3Url,
                mp3Duration = castEntity.mp3DurationSec,
                formattedDate = castEntity.formattedDate,
                date = castEntity.date,
                pageNum = castEntity.pageNum,
                fav = castEntity.fav
            )
        }

        fun printInfo(list: List<CastListItem>) {
            logInfo("List of CastListItem:")
            logInfo("Size: ${list.size}")

            val pages = hashSetOf<Int>()
            list.forEach { pages.add(it.pageNum) }

            logInfo("Pages: $pages")
        }
    }
}