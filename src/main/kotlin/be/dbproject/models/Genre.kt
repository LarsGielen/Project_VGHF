package be.dbproject.models

import javax.persistence.*

@Entity
@Table(name = "Genre")
data class Genre(
    @Id
    @GeneratedValue
    var id: Long,

    @Column
    var name: String
)
