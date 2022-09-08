package com.rogeert.mviebe.models.entities

import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToMany


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    var id: Long ? = -1

    var name: String ? = "-"
    var surname: String ? = "-"
    var username: String ? = "-"
    var email: String ? = "-"
    var password: String ? = "-"

    @ManyToMany(cascade = [CascadeType.DETACH])
    var roles: ArrayList<Role> ? = ArrayList()
}