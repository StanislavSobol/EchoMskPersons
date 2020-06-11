package echomskfan.gmail.com.annotations

@Target(
    AnnotationTarget.CLASS,
    AnnotationTarget.PROPERTY,
    AnnotationTarget.FIELD,
    AnnotationTarget.FUNCTION
)
@Retention(AnnotationRetention.SOURCE)
annotation class AppTodoMajor(val info: String)