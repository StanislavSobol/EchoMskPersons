package echomskfan.gmail.com.annotations.featureconfigurator

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.SOURCE)
annotation class FeatureToggleLong(val param: String)