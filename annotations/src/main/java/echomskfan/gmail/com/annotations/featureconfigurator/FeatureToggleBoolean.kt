package echomskfan.gmail.com.annotations.featureconfigurator

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.SOURCE)
annotation class FeatureToggleBoolean(val param: String)