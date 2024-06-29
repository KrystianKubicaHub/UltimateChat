package project.ultimatechat

import java.net.URL

open class SendableContact {
    var id: Int = -1
    private var nickName: String? = null
    private var dateOfRegistration: Long? = null
    var pathToProfilePicture: URL? = null
    private var lastActivityTime: Long? = null

    constructor(id: Int, nickName: String, dataOfRegistration: Long, pathToProfilePicture: URL, lastActivityTime: Long){
        this.id = id
        this.nickName = nickName
        this.dateOfRegistration = dataOfRegistration
        this.pathToProfilePicture = pathToProfilePicture
        this.lastActivityTime = lastActivityTime
    }
}