package echomskfan.gmail.com.presentation.persons

import echomskfan.gmail.com.entity.PersonEntity

data class PersonListItem(
    val id: Int,
    val fullName: String,
    val profession: String,
    val info: String
) : IPersonsListItemDelegate {

    companion object {
        fun from(personEntities: List<PersonEntity>): List<PersonListItem> {
            fun oneFrom(personEntity: PersonEntity): PersonListItem {
                return PersonListItem(
                    id = personEntity.id,
                    fullName = personEntity.firstName + " " + personEntity.lastName,
                    profession = personEntity.profession,
                    info = personEntity.info
                )
            }

            val result = mutableListOf<PersonListItem>()
            personEntities.forEach { result.add(oneFrom(it)) }
            return result
        }
    }

}