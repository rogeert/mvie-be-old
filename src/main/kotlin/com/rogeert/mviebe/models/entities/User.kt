package com.rogeert.mviebe.models.entities

import com.corundumstudio.socketio.SocketIOClient
import com.fasterxml.jackson.annotation.JsonIgnore
import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor
import org.hibernate.annotations.Where
import javax.persistence.*


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
    var image: String ? = "-"

    @JsonIgnore
    var active: Boolean ? = false

    @JsonIgnore
    @Transient
    var partyCode : String? = null

    @JsonIgnore
    @Transient
    var socket : SocketIOClient? = null

    @OneToMany(cascade = [CascadeType.DETACH],fetch = FetchType.EAGER,)
    var roles: Set<Role> = HashSet()
}