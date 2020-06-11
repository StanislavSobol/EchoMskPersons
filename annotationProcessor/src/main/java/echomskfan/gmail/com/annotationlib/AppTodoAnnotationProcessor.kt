package echomskfan.gmail.com.annotationlib

import echomskfan.gmail.com.annotations.*
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement

//@AutoService(Processor::class)
class AppTodoAnnotationProcessor : AbstractProcessor() {

    override fun getSupportedAnnotationTypes(): MutableSet<String> {
        return mutableSetOf(
            AppTodoMainPackage::class.java.name,
            AppTodoCritical::class.java.name,
            AppTodoMajor::class.java.name,
            AppTodoMinor::class.java.name,
            AppTodoTrivial::class.java.name
        )
    }

    override fun getSupportedSourceVersion(): SourceVersion = SourceVersion.latest()

    override fun process(
        annotations: MutableSet<out TypeElement>?,
        roundEnvironment: RoundEnvironment?
    ): Boolean {
        if (roundEnvironment == null) {
            // TODO File name to consts
            throw AnnotationProcessorException("Enable to generate AppTodoFile")
        }

        var pack: String? = javaClass.`package`.name
        roundEnvironment.getElementsAnnotatedWith(AppTodoMainPackage::class.java)
            ?.forEach {
                if (pack == null) {
                    pack = processingEnv.elementUtils.getPackageOf(it).toString()
                }
            }

        val fileBuilder = AppTodoFileBuilder(pack ?: "")

        roundEnvironment.getElementsAnnotatedWith(AppTodoCritical::class.java)?.forEach {
            fileBuilder.addTodoCritical(it.getAnnotation(AppTodoCritical::class.java).info)
        }

        roundEnvironment.getElementsAnnotatedWith(AppTodoMajor::class.java)?.forEach {
            fileBuilder.addTodoMajor(it.getAnnotation(AppTodoMajor::class.java).info)
        }

        roundEnvironment.getElementsAnnotatedWith(AppTodoMinor::class.java)?.forEach {
            fileBuilder.addTodoMinor(it.getAnnotation(AppTodoMinor::class.java).info)
        }

        roundEnvironment.getElementsAnnotatedWith(AppTodoTrivial::class.java)?.forEach {
            fileBuilder.addTodoTrivial(it.getAnnotation(AppTodoTrivial::class.java).info)
        }

        if (fileBuilder.isEmpty()) {
            return false
        }

        fileBuilder.run {
            build()
            save(dirName = processingEnv.options[KAPT_KOTLIN_GENERATED_OPTION_NAME]!!)
        }

        return true
    }

    companion object {
        const val KAPT_KOTLIN_GENERATED_OPTION_NAME = "kapt.kotlin.generated"
    }
}