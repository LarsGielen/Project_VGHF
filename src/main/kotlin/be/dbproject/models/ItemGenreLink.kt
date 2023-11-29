package be.dbproject.models

import javax.persistence.*

@Entity
@Table(name = "ItemGenreLink")
data class ItemGenreLink(
    @Id
    @Column
    @GeneratedValue
    val itemId: Int,

    @Column
    val genreId: Int
)