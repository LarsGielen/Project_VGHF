package be.dbproject.models

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "ItemType")
data class ItemType(
    @Column
    val name: String
) : DataBaseModel()
{
    override fun toString(): String {
        return name
    }
}