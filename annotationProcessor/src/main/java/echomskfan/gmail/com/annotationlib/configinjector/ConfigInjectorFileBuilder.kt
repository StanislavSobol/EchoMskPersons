package echomskfan.gmail.com.annotationlib.configinjector

import java.io.File

internal class ConfigInjectorFileBuilder {

    private val injectorsStringBuilder = StringBuilder()
    private val classesFields = mutableMapOf<String, MutableSet<AnyData>>()

    fun add(className: String, fieldName: String, paramName: String, type: Type) {
        classesFields[className]
            ?.add(AnyData(fieldName, paramName, type))
            ?: run { classesFields[className] = mutableSetOf(AnyData(fieldName, paramName, type)) }
    }

    fun buildInjects() {
        classesFields
            .filter { it.value.isNotEmpty() }
            .forEach {
                val data = it.value

                val dataStringBuilder = StringBuilder()
                data.forEach { dataEntry ->
                    dataStringBuilder
                        .append("\t\tobj.${dataEntry.fieldName} = jsonMap[${dataEntry.paramName.quote()}] ")
                        .append(
                            when (dataEntry.type) {
                                Type.BOOLEAN -> "as Boolean"
                                Type.INTEGER -> "as Int"
                            }
                        )
                        .append("\n")
                }

                injectorsStringBuilder
                    .append("\n")
                    .append("\tfun inject(obj: ${it.key}) {")
                    .append("\n")
                    .append(dataStringBuilder.toString())
                    .append("\t}")
                    .append("\n")

                data.clear()
            }
    }

    //    https://stackoverflow.com/questions/28947250/create-a-directory-if-it-does-not-exist-and-then-create-the-files-in-that-direct/28948104
    fun save(dirName: String) {
        String.format(
            FILE_BODY, injectorsStringBuilder.toString().trimEnd()
        ).let { File(dirName, FILE_NAME).writeText(it) }
    }

    private fun String.quote() = "\"" + this + "\""

    enum class Type {
        BOOLEAN, INTEGER
    }

    private data class AnyData(val fieldName: String, val paramName: String, val type: Type)

    companion object {
        private const val PACKAGE_NAME = "echomskfan.gmail.com"
        private const val OBJECT_NAME = "ConfigInjector"
        private const val FILE_NAME = "$OBJECT_NAME.kt"

        private val FILE_BODY: String = """
            package $PACKAGE_NAME

            import android.content.Context
            import org.json.JSONArray
            import java.nio.charset.Charset

            object $OBJECT_NAME {

                private val jsonMap = mutableMapOf<String, Any>()
            
                fun install(appContext: Context, configJsonName: String) {
                
                    fun getConfigFileAsString(appContext: Context, configJsonName: String): String {
                        val inputStream = appContext.assets.open(configJsonName)
                        val size = inputStream.available()
                        val buffer = ByteArray(size)
                        inputStream.read(buffer)
                        inputStream.close()
                        return String(buffer, Charset.forName("UTF-8"))
                    }
                
                    val jsonString = getConfigFileAsString(appContext, configJsonName)
                    val jsonArray = JSONArray(jsonString)
            
                    val size = jsonArray.length()
            
                    for (i in 0 until size) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        jsonArray.getJSONObject(i).names()?.getString(0)?.let {
                            jsonMap[it] = jsonObject.get(it)
                        }
                    }
                }
                %s
            
                private data class AnyData(val fieldName: String, val fieldValue: Any)
            }

            """.trimIndent()
    }
}
