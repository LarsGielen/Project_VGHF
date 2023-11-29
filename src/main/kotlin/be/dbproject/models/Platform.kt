package be.dbproject.models

import javax.persistence.*

@Entity
@Table(name = "Platform")
data class Platform(
    @Id
    @GeneratedValue
    val id: Int,

    @Column
    val name: String,

    @Column
    val description: String,

    @Column
    val releaseDate: String
)
