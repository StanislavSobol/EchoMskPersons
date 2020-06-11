package echomskfan.gmail.com.annotationlib

import com.google.auto.service.AutoService
import echomskfan.gmail.com.annotations.HiddenFeature
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.Processor
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement

// TODO !!!
// http://hannesdorfmann.com/annotation-processing/annotationprocessing101#processing-rounds

@AutoService(Processor::class)
class HiddenFeatureAnnotationProcessor : AbstractProcessor() {

    override fun getSupportedAnnotationTypes(): MutableSet<String> {
        return mutableSetOf(
            HiddenFeature::class.java.name
        )
    }

    override fun getSupportedSourceVersion(): SourceVersion = SourceVersion.latest()

    override fun process(
        annotations: MutableSet<out TypeElement>?,
        roundEnvironment: RoundEnvironment?
    ): Boolean {
        if (roundEnvironment == null) {
            // TODO File name to consts
            throw AnnotationProcessorException("Enable to generate HiddenFeatureProcFile")
        }

        var pack: String? = javaClass.`package`.name
        roundEnvironment.getElementsAnnotatedWith(HiddenFeature::class.java)
            ?.forEach {
                if (pack == null) {
                    pack = processingEnv.elementUtils.getPackageOf(it).toString()
                }
            }

        val fileBuilder = HiddenFeatureFileBuilder(pack ?: "")

//        fileBuilder.first = "fucking"
//
//        val nots = roundEnvironment.getElementsAnnotatedWith(HiddenFeature::class.java)
//        if (nots == null) {
//            fileBuilder.first = "nots == null"
//        }
//
//        fileBuilder.first = "size == ${nots.size}"
//
//        roundEnvironment.getElementsAnnotatedWith(HiddenFeature::class.java).forEach {
//            fileBuilder.first = "fucking old"
//        }

        roundEnvironment.getElementsAnnotatedWith(HiddenFeature::class.java)?.forEach {
            it?.let { enclosingElement ->
                enclosingElement.getAnnotation(HiddenFeature::class.java)?.let { annotation ->
                    if (!annotation.hidden) {
                        fileBuilder.add(enclosingElement)
                        // throw AnnotationProcessorException("FUCK passNum = $roundNum")
                    }
                }
            }

//            if (it.enclosingElement == null)
//            throw AnnotationProcessorException("FUCK3")

        }

        //      fileBuilder.first = "fucking10"

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

    var roundNum = 0

}