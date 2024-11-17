package project.ultimatechat.other

object RegistrationDataCheck {

    fun ifPhoneNumberOk(phoneNumber: String): checkResult {
        val isValid = phoneNumber.matches(Regex("^[0-9]{9,10}$"))
        return if (isValid) {
            checkResult(true, "")
        } else {
            checkResult(false, "INVALID PHONE NUMBER")
        }
    }

    fun ifEmailOk(email: String): checkResult {
        val isValid = email.matches(Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"))
        return if (isValid) {
            checkResult(true, "")
        } else {
            checkResult(false, "INVALID EMAIL")
        }
    }
}

class checkResult(val result: Boolean, val errorCode: String)
