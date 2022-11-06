package com.rogeert.mviebe.models.entities

import com.fasterxml.jackson.annotation.JsonIgnore
import lombok.AllArgsConstructor
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter
import javax.persistence.*

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    var id: Long ? = -1

    @JsonIgnore
    @Lob
    var content : ByteArray ? = null


    fun getLink(baseUrl: String):String{
    return "$baseUrl/images/$id";
    }
}
