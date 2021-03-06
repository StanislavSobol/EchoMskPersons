package echomskfan.gmail.com.annotationlib.configinjector

import com.google.auto.service.AutoService
import echomskfan.gmail.com.annotationlib.AnnotationProcessorException
import echomskfan.gmail.com.annotations.configinjector.ConfigParamBoolean
import echomskfan.gmail.com.annotations.configinjector.ConfigParamInteger
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.Processor
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement

// [https://stackoverflow.com/questions/17660469/get-field-class-in-annotations-processor]
// [http://hannesdorfmann.com/annotation-processing/annotationprocessing101#processing-rounds]

@AutoService(Processor::class)
class ConfigInjectorAnnotationProcessor : AbstractProcessor() {

    private val fileBuilder =
        ConfigInjectorFileBuilder()

    override fun getSupportedAnnotationTypes(): MutableSet<String> {
        return mutableSetOf(
            ConfigParamBoolean::class.java.name,
            ConfigParamInteger::class.java.name
        )
    }

    override fun getSupportedSourceVersion(): SourceVersion = SourceVersion.latest()

    override fun process(annotations: MutableSet<out TypeElement>?, roundEnvironment: RoundEnvironment?): Boolean {
        if (roundEnvironment == null) {
            throw AnnotationProcessorException("RoundEnvironment got lost")
        }
        roundEnvironment.getElementsAnnotatedWith(ConfigParamBoolean::class.java)?.forEach {
            fileBuilder.add(
                className = it.enclosingElement.toString(),
                fieldName = it.simpleName.toString().replace("\$annotations", ""),
                paramName = it.getAnnotation(ConfigParamBoolean::class.java).param,
                type = ConfigInjectorFileBuilder.Type.BOOLEAN
            )
        }
        roundEnvironment.getElementsAnnotatedWith(ConfigParamInteger::class.java)?.forEach {
            fileBuilder.add(
                className = it.enclosingElement.toString(),
                fieldName = it.simpleName.toString().replace("\$annotations", ""),
                paramName = it.getAnnotation(ConfigParamInteger::class.java).param,
                type = ConfigInjectorFileBuilder.Type.INTEGER
            )
        }

        fileBuilder.run {
            buildInjects()
            save(dirName = processingEnv.options[KAPT_KOTLIN_GENERATED_OPTION_NAME]!!)
        }

        return true
    }

    companion object {
        const val KAPT_KOTLIN_GENERATED_OPTION_NAME = "kapt.kotlin.generated"
    }
}