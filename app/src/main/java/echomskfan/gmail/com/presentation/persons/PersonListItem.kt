package echomskfan.gmail.com.presentation.persons

import echomskfan.gmail.com.data.db.entity.PersonEntity

data class PersonListItem(
    val id: Int,
    val fullName: String,
    val profession: String,
    val info: String,
    val photoUrl: String,
    val fav: Boolean,
    val notification: Boolean
) : IPersonsListItemDelegate {

    companion object {
        fun from(personEntities: List<PersonEntity>): List<PersonListItem> {
            val result = mutableListOf<PersonListItem>()
            personEntities.forEach { result.add(oneFrom(it)) }
            return result
        }

        private fun oneFrom(personEntity: PersonEntity): PersonListItem {
            return PersonListItem(
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