package echomskfan.gmail.com.presentation.casts

import echomskfan.gmail.com.entity.CastEntity

data class CastListItem(
    val typeSubtype: String, // Интервью / Персонально Ваш
    val shortText: String,
    val mp3Url: String,
    val mp3Duration: Int,
    val formattedDate: String,
    val pageNum: Int,
    var fav: Boolean = false
) : ICastsListItemDelegate {

    companion object {
        fun from(personEntities: List<CastEntity>): List<CastListItem> {
            val result = mutableListOf<CastListItem>()
            personEntities.forEach { result.add(oneFrom(it)) }
            return result
        }

        private fun oneFrom(castEntity: CastEntity): CastListItem {
            fun getTypeSubtype(type: String, subtype: String): String {
                var result = type
                if (!subtype.isEmpty()) {
                    result += ": $subtype"
                }
                return result
            }

            return CastListItem(
                typeSubtype = getTypeSubtype(castEntity.type, castEntity.subtype),
                shortText = castEntity.shortText,
                mp3Url = castEntity.mp3Url,
                mp3Duration = castEntity.mp3Duration,
                formattedDate = castEntity.formattedDate,
                pageNum = castEntity.pageNum,
                fav = castEntity.fav
            )
        }


    }
}