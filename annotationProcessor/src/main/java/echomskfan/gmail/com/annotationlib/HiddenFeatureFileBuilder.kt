package echomskfan.gmail.com.annotationlib

import java.io.File
import javax.lang.model.element.Element

internal class HiddenFeatureFileBuilder(private val packageName: String) {

    private val allowedElements = hashSetOf<String>()
    var first = ""

//    init {
//
//        allowedElements.add("test")
//    }

    private var content: String? = null

    fun isEmpty() = allowedElements.isEmpty()

    fun add(enclosingElement: Element) {
        first = enclosingElement.toString()

        if (enclosingElement.kind.isClass) {
            allowedElements.add(enclosingElement.toString())
//            throw AnnotationProcessorException("FUCK: ${enclosingElement.toString()}")
        }

    }

    fun build() {
        content = """ 
            package $packageName
            
            // $first
            

            class HiddenFeatureGenerated {
                private val allowedElements = hashSetOf<String>()

                init {
                                 ${initMap()}
                }
            
            }
            """.trimIndent()
    }

    fun save(dirName: String) {
        // throw AnnotationProcessorException("FUCK: save")

        content?.let { File(dirName, FILE_NAME).writeText(it) }
    }

    private fun initMap(): String {
        var result = "////  size = ${allowedElements.size}"
        allowedElements.forEach { result += "allowedElements.add(\"$it\")\n" }
//        allowedElements.forEach { throw AnnotationProcessorException("FUCK4")}
        //     throw AnnotationProcessorException("FUCK: ${result}")
        return result
    }

    companion object {
        const val FILE_NAME = "HiddenFeatureGenerated.kt"
    }
}

