package app.android.o2onelabsproject.base

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity : AppCompatActivity() {
    private var toastMessage: Toast? = null

    fun showToast(message:String?){
        if (message != null) {
            cancelToastMessage()
            if (applicationContext != null && message.isNotBlank()) {
                toastMessage = Toast.makeText(applicationContext, message, Toast.LENGTH_LONG)
                toastMessage?.show()
            }
        }
    }

    internal fun cancelToastMessage() {
        if (toastMessage != null)
            toastMessage!!.cancel()
    }
}
