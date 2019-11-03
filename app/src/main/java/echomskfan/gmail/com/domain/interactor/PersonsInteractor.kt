package echomskfan.gmail.com.domain.interactor

import android.annotation.SuppressLint
import echomskfan.gmail.com.domain.repository.IRepository
import echomskfan.gmail.com.entity.PersonEntity
import echomskfan.gmail.com.utils.catchThrowable
import echomskfan.gmail.com.utils.fromIoToMain
import io.reactivex.Single

class PersonsInteractor(private val repository: IRepository) : IPersonsInteractor {

    @SuppressLint("CheckResult")
    override fun copyPersonsFromXmlToDb() {
        repository.copyPersonsFromXmlToDb().fromIoToMain().subscribe({}, { error -> catchThrowable(error) })
    }

    override fun getPersons(): Single<List<PersonEntity>> = repository.getPersons().fromIoToMain()
}