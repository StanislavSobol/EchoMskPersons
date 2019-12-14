package echomskfan.gmail.com.domain.assetextractor

import echomskfan.gmail.com.data.db.entity.PersonEntity

interface IAssetExtractor {
    fun getPersons(): List<PersonEntity>

    fun getCastsForPersonAndPage(personId: Int, pageNum: Int): String
}