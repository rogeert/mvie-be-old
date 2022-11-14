package com.rogeert.mviebe.models.entities

import lombok.Data
import org.jetbrains.annotations.NotNull
import javax.persistence.*


@Data
@Entity
class Episode {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long ? = -1

    @NotNull
    var mdbId: Long ? = -1

    @ManyToOne
    var media: Media ? = null

    @NotNull
    var title:String ? = "-"

    @NotNull
    var episode: Int ? = -1

    @NotNull
    var season: Int ? = -1
}