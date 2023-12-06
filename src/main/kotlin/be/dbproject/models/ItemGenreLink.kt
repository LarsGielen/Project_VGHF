package be.dbproject.models

import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "ItemGenreLink")
data class ItemGenreLink(
    @ManyToOne
    @JoinColumn(name = "itemid")
    var item: Item,

    @ManyToOne
    @JoinColumn(name = "genreid")
    var genre: Genre
) : DataBaseModel()
{
    override fun toString(): String {
        return "$item -> $genre"
    }
}