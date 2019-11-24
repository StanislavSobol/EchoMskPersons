package echomskfan.gmail.com.data.parser

import echomskfan.gmail.com.data.db.entity.CastEntity
import echomskfan.gmail.com.data.db.entity.PersonEntity

interface IEchoParser {
    fun getCasts(personEntity: PersonEntity, pageNum: Int = 1): List<CastEntity>
}