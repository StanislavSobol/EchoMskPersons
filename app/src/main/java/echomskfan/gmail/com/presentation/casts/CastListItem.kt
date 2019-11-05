package echomskfan.gmail.com.presentation.casts

import echomskfan.gmail.com.entity.CastEntity

data class CastListItem(
    val type: String, // Интервью
    val subtype: String, // Персонально Ваш
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
            return CastListItem(
                type = castEntity.type,
                subtype = castEntity.subtype,
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