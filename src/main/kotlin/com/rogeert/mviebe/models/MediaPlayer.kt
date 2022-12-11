package com.rogeert.mviebe.models

import com.fasterxml.jackson.annotation.JsonProperty

class MediaPlayer {

    @JsonProperty("action")
    var action : String = ""

    @JsonProperty("seconds")
    var seconds : Int = 0

    @JsonProperty("username")
    var username : String = ""

}

enum class PlayerAction{
    PLAY,PAUSE,SEEK
}