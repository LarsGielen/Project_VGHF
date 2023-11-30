package be.dbproject.models

import javax.persistence.*

@Entity
@Table(name = "Genre")
data class Genre(
    @Id
    @GeneratedValue
    val id: Long = 0,

    @Column
    var name: String
)
