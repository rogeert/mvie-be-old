package com.rogeert.mviebe.models.entities

import lombok.Data
import org.jetbrains.annotations.NotNull
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToMany

@Entity
@Data
class  Media{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long ? = -1

    var fileName: String ? = "-"

    var size: Long ? = -1

    var resolution: Int ? = -1

}