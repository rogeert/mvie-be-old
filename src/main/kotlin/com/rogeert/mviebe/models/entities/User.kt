package com.rogeert.mviebe.models.entities

import com.corundumstudio.socketio.SocketIOClient
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
    var active: Boolean ? = false

    @Transient
    var partyCode : String? = null
    @Transient
    var socket : SocketIOClient? = null

    @OneToMany(cascade = [CascadeType.DETACH],fetch = FetchType.EAGER,)
    var roles: Set<Role> = HashSet()
}