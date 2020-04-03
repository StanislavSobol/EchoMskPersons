package echomskfan.gmail.com.presentation.personinfo

import echomskfan.gmail.com.data.db.entity.PersonEntity

data class PersonInfoViewEntity(
    val fullName: String,
    val profession: String,
    val info: String,
    val photoUrl: String
) {
    companion object {
        fun from(personEntity: PersonEntity): PersonInfoViewEntity {
            with(personEntity) {
                return PersonInfoViewEntity(getFullName(), profession, info, photoUrl)
            }
        }
    }
}