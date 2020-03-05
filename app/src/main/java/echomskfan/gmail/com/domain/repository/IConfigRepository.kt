package echomskfan.gmail.com.domain.repository

interface IConfigRepository {
    val isDebugPanelEnabled: Boolean
    val isDisclaimerEnabled: Boolean
}