package echomskfan.gmail.com.annotations.featurenavigator

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
annotation class ForFeatureNavigator(val enabled: Boolean, val onlyForDebug: Boolean = false)