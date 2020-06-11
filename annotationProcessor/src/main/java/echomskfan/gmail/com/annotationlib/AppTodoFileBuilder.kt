package echomskfan.gmail.com.annotationlib

import java.io.File

internal class AppTodoFileBuilder(private val packageName: String) {

    private val criticalIssues: MutableList<String> = mutableListOf()
    private val majorIssues: MutableList<String> = mutableListOf()
    private val minorIssues: MutableList<String> = mutableListOf()
    private val trivialIssues: MutableList<String> = mutableListOf()

    // TODO content to String?
    private lateinit var content: String

    fun addTodoCritical(info: String) {
        criticalIssues.add(info)
    }

    fun addTodoMajor(info: String) {
        majorIssues.add(info)
    }

    fun addTodoMinor(info: String) {
        minorIssues.add(info)
    }

    fun addTodoTrivial(info: String) {
        trivialIssues.add(info)
    }

    fun build() {
        content = """ 
package $packageName

 /**
${buildJavaDoc("Critical issues:", criticalIssues)}
${buildJavaDoc("Major issues:", majorIssues)}
${buildJavaDoc("Minor issues:", minorIssues)}
${buildJavaDoc("Trivial issues:", trivialIssues)}
*/
class AppTodoBuilder {
    ${buildList("criticalIssues", criticalIssues)} 
    ${buildList("majorIssues", majorIssues)}     
    ${buildList("minorIssues", minorIssues)}    
    ${buildList("trivialIssues", trivialIssues)}     
}
            """.trimIndent()
    }

    fun save(dirName: String) {
        val file = File(dirName, FILE_NAME)
        file.writeText(content)
    }

    fun isEmpty() =
        criticalIssues.isEmpty() && majorIssues.isEmpty() && minorIssues.isEmpty() && trivialIssues.isEmpty()

    private fun buildJavaDoc(title: String, issuesList: List<String>): String {
        if (issuesList.isEmpty()) {
            return ""
        }
        var result = "* $title\n"
        issuesList.forEach { result += "* - $it\n" }
        result = result.deleteLastIfNeeded('\n')
        return result
    }

    private fun buildList(issuesListName: String, issuesList: List<String>): String {
        if (issuesList.isEmpty()) {
            return ""
        }
        var result = "\nprivate val $issuesListName = listOf<String>("
        issuesList.forEach { result += "\"$it\",\n" }
        result = result.deleteLastIfNeeded(',')
        result += ")"
        return result
    }

    private fun String.deleteLastIfNeeded(char: Char): String {
        return if (this.trim().last() == char) this.trim().dropLast(1) else this
    }

    companion object {
        const val FILE_NAME = "AppTodoGenerated.kt"
    }
}

