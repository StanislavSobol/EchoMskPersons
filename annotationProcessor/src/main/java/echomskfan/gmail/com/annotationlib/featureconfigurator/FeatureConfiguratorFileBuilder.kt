package echomskfan.gmail.com.annotationlib.featureconfigurator

import java.io.File

internal class FeatureConfiguratorFileBuilder {

    private val dataStringBuilder = StringBuilder()

    fun addBooleanDataFormProcessor(className: String, fieldName: String, paramName: String) {
        dataStringBuilder
            .append("\n")
            .append("addBoolean($className::class, ${fieldName.quote()}, ${paramName.quote()})")
    }

    fun addTestSting(testString: String) {
        dataStringBuilder.append("$testString")
    }

    fun save(dirName: String) {
        String.format(FILE_BODY, dataStringBuilder.toString()).let { File(dirName, FILE_NAME).writeText(it) }
    }

    private fun String.quote() = "\"" + this + "\""

    companion object {
        const val PACKAGE_NAME = "echomskfan.gmail.com"
        const val OBJECT_NAME = "FeatureConfigurator"
        const val FILE_NAME = "$OBJECT_NAME.kt"

        private val FILE_BODY: String = """
            
            package $PACKAGE_NAME

            import android.content.Context
            import org.json.JSONArray
            import java.nio.charset.Charset
            import kotlin.reflect.KClass

            object $OBJECT_NAME {

                private val jsonMap = mutableMapOf<String, Any>()
                private val booleans = mutableMapOf<KClass<*>, BooleanData>()

                fun install(appContext: Context, configJsonName: String) {
                    val jsonString = getConfigFileAsString(appContext, configJsonName)
                    val jsonArray = JSONArray(jsonString)

                    val size = jsonArray.length()

                    for (i in 0 until size) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        jsonArray.getJSONObject(i).names()?.getString(0)?.let {
                            jsonMap[it] = jsonObject.get(it)
                        }
                    }

                    fromProcessor()
                }

                fun bind(objectToBind: Any) {
                    booleans[objectToBind::class]?.let {
                        objectToBind::class.java.getDeclaredField(it.fieldName).run {
                            isAccessible = true
                            set(objectToBind, it.fieldValue)
                        }
                    }
                }

                private fun addBoolean(clazz: KClass<*>, fieldName: String, paramName: String) {
                    jsonMap[paramName]?.let { booleans[clazz] = BooleanData(fieldName, it as Boolean) }
                }

                private fun fromProcessor() {
                    %s
                }

                private fun getConfigFileAsString(appContext: Context, configJsonName: String): String {
                    val inputStream = appContext.assets.open(configJsonName)
                    val size = inputStream.available()
                    val buffer = ByteArray(size)
                    inputStream.read(buffer)
                    inputStream.close()
                    return String(buffer, Charset.forName("UTF-8"))
                }

                private data class BooleanData(val fieldName:String, val fieldValue:Boolean)
            }

            """.trimIndent()
    }
}
