package project.ultimatechat.entities

open class SendableContact(
    var id: String = "",
    nickName: String = "",
    dateOfRegistration: Long = 0L,
    pathToProfilePicture: String = "",
    lastActivityTime: Long = 0L
) {
    var nickName: String? = nickName
    var dateOfRegistration: Long? = dateOfRegistration
    var dateOfBirth: Long = System.currentTimeMillis()
    var pathToProfilePicture: String? = pathToProfilePicture
    var lastActivityTime: Long? = lastActivityTime


}