package echomskfan.gmail.com.annotationlib.featureconfigurator

import com.google.auto.service.AutoService
import echomskfan.gmail.com.annotationlib.AnnotationProcessorException
import echomskfan.gmail.com.annotations.featureconfigurator.FeatureToggleBoolean
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.Processor
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement

// [https://stackoverflow.com/questions/17660469/get-field-class-in-annotations-processor]
// [http://hannesdorfmann.com/annotation-processing/annotationprocessing101#processing-rounds]

@AutoService(Processor::class)
class FeatureConfiguratorAnnotationProcessor : AbstractProcessor() {

    private val fileBuilder = FeatureConfiguratorFileBuilder()

    override fun getSupportedAnnotationTypes(): MutableSet<String> {
        return mutableSetOf(FeatureToggleBoolean::class.java.name)
    }

    override fun getSupportedSourceVersion(): SourceVersion = SourceVersion.latest()

    override fun process(annotations: MutableSet<out TypeElement>?, roundEnvironment: RoundEnvironment?): Boolean {
        if (roundEnvironment == null) {
            throw AnnotationProcessorException("RoundEnvironment got lost")
        }
        roundEnvironment.getElementsAnnotatedWith(FeatureToggleBoolean::class.java)?.forEach {
            fileBuilder.addBooleanDataFormProcessor(
                className = it.enclosingElement.toString(),
                fieldName = it.simpleName.toString().replace("\$annotations", ""),
                paramName = it.getAnnotation(FeatureToggleBoolean::class.java).param
            )
        }
        fileBuilder.save(dirName = processingEnv.options[KAPT_KOTLIN_GENERATED_OPTION_NAME]!!)
        return true
    }

    companion object {
        const val KAPT_KOTLIN_GENERATED_OPTION_NAME = "kapt.kotlin.generated"
    }
}