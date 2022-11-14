package com.rogeert.mviebe.models.entities

import lombok.Data
import org.jetbrains.annotations.NotNull
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToOne


@Entity
@Data
class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long ? = -1

    @NotNull
    var mdbId: Long ? = -1

    @ManyToOne
    var media: Media ? = null

    @NotNull
    var title: String ? = ""

}