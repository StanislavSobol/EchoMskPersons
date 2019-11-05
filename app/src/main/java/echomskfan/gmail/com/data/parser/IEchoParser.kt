package echomskfan.gmail.com.data.parser

import echomskfan.gmail.com.entity.CastEntity
import echomskfan.gmail.com.entity.PersonEntity

interface IEchoParser {
    fun getCasts(fullUrl: String, personEntity: PersonEntity, pageNum: Int = 1): List<CastEntity>
}