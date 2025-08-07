import android.util.Patterns

object ValidationUtils {

    fun isNameValid(name: String): Boolean {
        return name.trim().isNotEmpty()
    }

    fun isPhoneValid(phone: String): Boolean {
        return phone.length == 10 && phone.all { it.isDigit() }
    }

    fun isEmailValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches()
    }
}
