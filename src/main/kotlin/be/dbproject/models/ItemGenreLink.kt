package be.dbproject.models

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "ItemGenreLink")
data class ItemGenreLink(
    @Column
    val genreId: Int
) : DataBaseModel()