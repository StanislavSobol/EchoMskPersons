package echomskfan.gmail.com.domain.assetextractor

import android.content.Context
import android.content.res.AssetManager
import echomskfan.gmail.com.data.db.entity.PersonEntity
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.powermock.api.mockito.PowerMockito
import org.powermock.api.mockito.PowerMockito.*
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.modules.junit4.PowerMockRunner
import java.io.InputStream

//@RunWith(MockitoJUnitRunner::class)
@RunWith(PowerMockRunner::class)
@PrepareForTest(value = [AssetExtractor::class])
class AssetExtractorTest {

    lateinit var assetExtractor: AssetExtractor

    @Mock
    lateinit var appContext: Context

    @Mock
    lateinit var assetManager: AssetManager

    @Mock
    lateinit var inputStream: InputStream

    @Before
    fun setup() {
        PowerMockito.mock(AssetExtractor::class.java)

        PowerMockito.`when`(appContext.assets).thenReturn(assetManager)
        `when`(assetManager.open(PERSONS_JSON_NAME)).thenReturn(inputStream)
        doNothing().`when`(inputStream).close()

        assetExtractor = spy(AssetExtractor(appContext))
//        assetExtractor = AssetExtractor(appContext)
        `when`(assetExtractor.getString(PERSONS_JSON_NAME)).thenReturn(PERSONS_JSON_FILE_TEXT)
    }

    @Test
    fun tes() {
        val expectedPersons: List<PersonEntity> = listOf()
        val actualPersons = assetExtractor.getPersons()

        assertEquals(expectedPersons, actualPersons)
    }

    private companion object {
        const val PERSONS_JSON_NAME: String = "persons.json"
        const val PERSON_CASTS_PREFIX: String = "person_page_"
        const val PERSON_CASTS_SEPARATOR: String = "_"
        const val HTML_EXT: String = ".html"

        private val PERSONS_JSON_FILE_TEXT: String = """
[
  {
    "id": 1,
    "url": "/guests/813104-echo",
    "firstName": "Александр",
    "lastName": "Невзоров",
    "profession": "публицист",
    "info": "23 декабря 1987 года вышла в эфир и просуществовала шесть лет информационная программа Ленинградского телевидения «600 секунд», шеф-редактором и ведущим которой стал Александр Невзоров. Передача стала культовой и вошла в Книгу рекордов Гиннеса как самый рейтинговый телепроект.Как репортер и как солдат был участником войн в Югославии, Приднестровье, Карабахе, Прибалтике, Ираке и Чечне, одним из первых, вместе с генералом Рохлиным вошел в Грозный.О чеченской войне Невзоровым сняты документальный фильм «Ад» (1995) и художественная лента «Чистилище» (1997).",
    "photoUrl": "https://echo.msk.ru/files/avatar2/2771274.jpg"
  },
  {
    "id": 2,
    "url": "/contributors/324",
    "firstName": "Юлия",
    "lastName": "Латынина",
    "profession": "обозреватель «Эха Москвы»",
    "info": "Кандидат филологических наук, известный журналист и писатель. Обозреватель «Новой газеты». С 2003 года — постоянный автор и ведущая еженедельной программы «Код доступа». (сб.:19:00)Была научным сотрудником Института экономики переходного периода (с 1997 г.), экономическим обозревателем газет «Сегодня» (1994-95 гг.) и «Известия» (1995-97 гг.), журнала «Эксперт» (1997-98 гг.), ежемесячника «Совершенно секретно» (1999-2000 гг.) и «Еженедельного журнала». Вела передачи «Рублевая зона» на канале НТВ, была соведущей программы «Другое время» на ОРТ, автором программы «Есть мнение» на ТВС (2002-03 гг.).Сейчас является сотрудником «Новой газеты» (с 2001 г.), колумнистом «Ежедневного журнала» (с 2005 г.), комментатором газеты «Коммерсантъ» (с 2006 г.).Член Союза российских писателей. Автор известных экономических триллеров. Лауреат премий им. Г. Меир (1997 г.), Александра II (1997 г.) за работы в области экономической журналистики, Ассоциации русскоязычных писателей Израиля (1997 г.). В 1999 г. признана «Человеком года» в номинации «Журналистика» Русским биографическим институтом, лауреат премии имени Герда Буцериуса «Молодая пресса Восточной Европы» (2004 г.).",
    "photoUrl": "https://echo.msk.ru/files/avatar2/783858.jpg"
  },
  {
    "id": 3,
    "url": "/contributors/4769",
    "firstName": "Виктор",
    "lastName": "Шендерович",
    "profession": "писатель",
    "info": "Родился в 1958 году.\nВ детском саду узнал цену коллективизму.\nВ 1975 году закончил без сожаления среднюю школу.\nПо первому диплому — культпросветработник высшей квалификации, отчего до сих пор вздрагивает.\nВ 1980-1982 гг. служил в Советской Армии, где научился говорить матом. Выжил и демобилизовался, но до сих пор вздрагивает от словосочетания «священный долг».\nПеред тем как начать писать, некоторое время читал…\nС «Эхо Москвы» сотрудничает постоянно. Автор и ведущий еженедельной программы «Эхо Москвы» — «Плавленый сырок». Автор еженедельных комментариев.",
    "photoUrl": "https://2.cdn.echo.msk.ru/files/avatar2/3059457.jpg"
  },
  {
    "id": 4,
    "url": "/guests/7788",
    "firstName": "Алексей",
    "lastName": "Навальный",
    "profession": "адвокат, политик, блогер",
    "info": "Российский оппозиционный лидер, юрист, политический и общественный деятель, получивший известность своими расследованиями о коррупции в России. Позиционирует себя в качестве главного оппонента руководству России во главе с Владимиром Путиным[14][15]. Создатель «Фонда борьбы с коррупцией», объединяющего дочерние проекты: «Умное голосование», «Профсоюз Навального», «РосПил», «РосЖКХ», «РосЯма», «РосВыборы», «Добрая машина правды».",
    "photoUrl": "https://2.cdn.echo.msk.ru/files/avatar2/1022564.jpg"
  },
  {
    "id": 5,
    "url": "/contributors/5013",
    "firstName": "Сергей",
    "lastName": "Пархоменко",
    "profession": "журналист",
    "info": "Родился 13 марта 1964 в Москве. Окончил факультет журналистики МГУ. Политический обозреватель «Независимой газеты». Один из основателей газеты «Сегодня». Главный редактор журнала «Итоги», затем главный редактор «Еженедельного журнала». В 2003-2009 гг. руководил несколькими книжными издательствами: «Иностранка», «Колибри», «Corpus». В 2009-2011 годах — главный редактор издательства «Вокруг Света».\n\nОдин из основателей Московской хартии журналистов.\n\nС «Эхо Москвы» тесно сотрудничает много лет, участвуя в различных программах в качестве приглашенного эесперта. Вместе с сыном Петром Пархоменко вел программу «Два Пархоменки Два». С 2003 года является автором и ведущим еженедельной аналитической программы «Суть событий».",
    "photoUrl": "https://2.cdn.echo.msk.ru/files/avatar2/737220.jpg"
  },
  {
    "id": 6,
    "url": "/programs/kurs/",
    "firstName": "Дмитрий",
    "lastName": "Потапенко",
    "profession": "экономист, предприниматель",
    "info": "Российский предприниматель, экономист, управляющий партнёр компании «Management Development Group Inc», радиоведущий.",
    "photoUrl": "https://2.cdn.echo.msk.ru/files/avatar2/2414910.jpg"
  }
]

            """.trimIndent()
    }

}