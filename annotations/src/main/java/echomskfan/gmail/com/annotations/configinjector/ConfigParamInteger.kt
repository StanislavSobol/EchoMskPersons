package echomskfan.gmail.com.annotations.configinjector

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.SOURCE)
annotation class ConfigParamInteger(val param: String)