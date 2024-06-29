package project.ultimatechat

import java.net.URL

open class SendableContact {
    var id: Int = -1
    var nickName: String? = null
    var dateOfRegistration: Long? = null
    var pathToProfilePicture: String? = null
    var lastActivityTime: Long? = null

    constructor(id: Int, nickName: String, dataOfRegistration: Long, pathToProfilePicture: String, lastActivityTime: Long){
        this.id = id
        this.nickName = nickName
        this.dateOfRegistration = dataOfRegistration
        this.pathToProfilePicture = pathToProfilePicture
        this.lastActivityTime = lastActivityTime
    }
}