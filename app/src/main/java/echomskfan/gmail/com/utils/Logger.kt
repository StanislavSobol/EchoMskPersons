package echomskfan.gmail.com.utils

import android.util.Log

private const val TAG_INFO = "Info"
private const val TAG_ERROR = "Error"

fun logInfo(info: String) {
    Log.d(TAG_INFO, info)
}

fun logError(info: String) {
    Log.e(TAG_ERROR, info)
}

