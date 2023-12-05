package be.dbproject.models

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "Publisher")
data class Publisher(
    @Column
    val name: String,

    @Column
    val description: String,

    @Column
    val website: String?
) : DataBaseModel()
{
    override fun toString(): String {
        return name
    }
}