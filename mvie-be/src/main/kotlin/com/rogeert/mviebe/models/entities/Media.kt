package com.rogeert.mviebe.models.entities

import lombok.Data
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
@Data
data class Media(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long ? = -1,
    var movieId: Long ? = -1,

    var url: String ? = "-",
    var size: Long ? = -1


)