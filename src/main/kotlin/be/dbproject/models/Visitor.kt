package be.dbproject.models

import javax.persistence.*

@Entity
@Table(name = "Visitor")
data class Visitor(
    @Id
    @Column
    @GeneratedValue
    val id: Int,

    @Column
    val firstName: String,

    @Column
    val lastName: String,

    @Column
    val phone: String,

    @Column
    val email: String,

    @Column
    val locationId: Int
)