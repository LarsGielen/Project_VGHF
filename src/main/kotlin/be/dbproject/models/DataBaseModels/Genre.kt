package be.dbproject.models.DataBaseModels

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "Genre")
data class Genre (
    @Column
    var name: String
) : DataBaseModel()
{
    constructor() : this (
        ""
    )

    override fun toString(): String {
        return name
    }
}
