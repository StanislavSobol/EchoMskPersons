package com.gmail.echomskfan.persons.interactor.parser

import echomskfan.gmail.com.entity.CastEntity
import echomskfan.gmail.com.entity.PersonEntity

interface IEchoParser {
    fun getCasts(fullUrl: String, vipEntity: PersonEntity, pageNum: Int = 1): List<CastEntity>
}