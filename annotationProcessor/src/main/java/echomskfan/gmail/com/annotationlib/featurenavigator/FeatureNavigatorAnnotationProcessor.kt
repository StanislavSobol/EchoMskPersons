package echomskfan.gmail.com.annotationlib.featurenavigator

import com.google.auto.service.AutoService
import echomskfan.gmail.com.annotationlib.AnnotationProcessorException
import echomskfan.gmail.com.annotations.featurenavigator.ForFeatureNavigator
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.Processor
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement

// TODO !!!
// http://hannesdorfmann.com/annotation-processing/annotationprocessing101#processing-rounds

@AutoService(Processor::class)
class FeatureNavigatorAnnotationProcessor : AbstractProcessor() {
    private var roundNum = 0

    override fun getSupportedAnnotationTypes(): MutableSet<String> {
        return mutableSetOf(ForFeatureNavigator::class.java.name)
    }

    override fun getSupportedSourceVersion(): SourceVersion = SourceVersion.latest()

    override fun process(
        annotations: MutableSet<out TypeElement>?,
        roundEnvironment: RoundEnvironment?
    ): Boolean {
        if (roundEnvironment == null) {
            throw AnnotationProcessorException(
                "RoundEnvironment got lost"
            )
        }

        val fileBuilder =
            FeatureNavigatorFileBuilder()

        roundEnvironment.getElementsAnnotatedWith(ForFeatureNavigator::class.java)?.forEach {
            it?.let { enclosingElement ->
                enclosingElement.getAnnotation(ForFeatureNavigator::class.java)?.let { annotation ->
                    if (annotation.enabled) {
                        fileBuilder.add(enclosingElement, annotation.onlyForDebug)
                    }
                }
            }
        }

        fileBuilder.first = roundNum++.toString()

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