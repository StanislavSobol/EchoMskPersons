package echomskfan.gmail.com.annotationlib.featurenavigator

import java.io.File
import javax.lang.model.element.Element

internal class FeatureNavigatorFileBuilder() {

    private val allowedElements = hashSetOf<AllowedPair>()
    var first = ""

    private var content: String? = null

    fun isEmpty() = allowedElements.isEmpty()

    fun add(enclosingElement: Element, forDebug: Boolean) {
        first = enclosingElement.toString()

        if (enclosingElement.kind.isClass) {
            allowedElements.add(AllowedPair(enclosingElement.toString(), forDebug))
        }
    }

    fun build() {
        content = """ 
            package $PACKAGE_NAME
            
            import android.content.Context
            import android.view.Gravity
            import android.widget.Toast
            import android.widget.Toast.LENGTH_LONG
            import androidx.fragment.app.Fragment
            
            object $OBJECT_NAME {
            
                var appContext: Context?= null 
                var isBuildConfigDebug: Boolean? = null
            
                private val allowedElements = hashSetOf<AllowedPair>()

                init {
                   ${buildAllowedElements()}
                }
                
                fun <T : Fragment> navigate(fragmentClass: Class<T>, function: () -> Unit) {
                        if (appContext == null) {
                            throwExceptionForAppContextNull()
                        }               
                        
                        if (isBuildConfigDebug == null) {
                            throwExceptionForIsBuildConfigDebugNull()
                        }
                
                        if (isFeatureEnabled(fragmentClass)) {
                            function.invoke()
                        } else {
                            Toast.makeText(
                                appContext,
                                "The feature is not available. See annotation @ForFeatureNavigator for fragment " + fragmentClass.simpleName,
                                LENGTH_LONG
                            ).apply { setGravity(Gravity.CENTER, 0, 0) }.show()
                        }
                    }
                
                    fun <T : Fragment> isFeatureEnabled(
                        fragmentClass: Class<T>
                    ): Boolean {
                        if (isBuildConfigDebug == null) {
                            throwExceptionForIsBuildConfigDebugNull()
                        }
                        
                        allowedElements.find { it.className == fragmentClass.name }?.let {
                            return if (!(isBuildConfigDebug as Boolean) && it.onlyForDebug) {
                                false
                            } else {
                                true
                            }
                        }
                        return false
                    }
                    
                    private fun throwExceptionForAppContextNull():Nothing {
                        throw IllegalStateException("The application Context must be set first")
                    } 
                    
                    private fun throwExceptionForIsBuildConfigDebugNull():Nothing {
                        throw IllegalStateException("The \"isBuildConfigDebug\" property must be set first")
                    }
                
                private data class AllowedPair(val className: String, val onlyForDebug: Boolean)
            }
            """.trimIndent()
    }

    fun save(dirName: String) {
        content?.let {
            File(
                dirName,
                FILE_NAME
            ).writeText(it)
        }
    }

    private fun buildAllowedElements(): String {
        var result = ""
        allowedElements.forEach { result += "allowedElements.add(AllowedPair(\"${it.className}\", ${it.forDebug}))\n" }
        return result.dropLast(1)
    }

    private data class AllowedPair(val className: String, val forDebug: Boolean)

    companion object {
        const val PACKAGE_NAME = "echomskfan.gmail.com.annotationlib"
        const val OBJECT_NAME = "FeatureNavigator"
        const val FILE_NAME = "$OBJECT_NAME.kt"
    }
}

