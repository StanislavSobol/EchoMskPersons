package echomskfan.gmail.com.presentation.casts

import echomskfan.gmail.com.data.db.entity.CastEntity
import java.util.*

data class CastListItem(
    val id: String,
    val typeSubtype: String, // Интервью / Персонально Ваш
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
            fun getTypeSubtype(type: String, subtype: String): String {
                var result = type
                if (subtype.isNotEmpty()) {
                    result += ": $subtype"
                }
                return result
            }

            return CastListItem(
                id = castEntity.id,
                typeSubtype = getTypeSubtype(castEntity.type, castEntity.subtype),
                shortText = castEntity.shortText,
                mp3Url = castEntity.mp3Url,
                mp3Duration = castEntity.mp3Duration,
                formattedDate = castEntity.formattedDate,
                date = castEntity.date,
                pageNum = castEntity.pageNum,
                fav = castEntity.fav
            )
        }
    }
}