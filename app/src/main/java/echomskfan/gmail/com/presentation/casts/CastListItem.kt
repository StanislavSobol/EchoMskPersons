package echomskfan.gmail.com.presentation.casts

import echomskfan.gmail.com.entity.PersonEntity

data class CastListItem(
    val id: Int,
    val fullName: String,
    val profession: String,
    val info: String,
    val photoUrl: String,
    val fav: Boolean,
    val notification: Boolean
) : ICastsListItemDelegate {

    companion object {
        fun from(personEntities: List<PersonEntity>): List<CastListItem> {
            val result = mutableListOf<CastListItem>()
            personEntities.forEach { result.add(oneFrom(it)) }
            return result
        }

        fun oneFrom(personEntity: PersonEntity): CastListItem {
            return CastListItem(
                id = personEntity.id,
                fullName = personEntity.firstName + " " + personEntity.lastName,
                profession = personEntity.profession,
                info = personEntity.info,
                photoUrl = personEntity.photoUrl,
                fav = personEntity.fav,
                notification = personEntity.notification
            )
        }
    }

}