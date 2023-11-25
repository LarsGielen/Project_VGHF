package be.dbproject.models

import javax.persistence.Column
import javax.persistence.GeneratedValue
import javax.persistence.Id

data class ItemGenreLink(
    @Id
    @Column
    @GeneratedValue
    val itemId: Int,

    @Column
    val genreId: Int
)