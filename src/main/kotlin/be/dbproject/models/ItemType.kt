package be.dbproject.models

import javax.persistence.*

@Entity
@Table(name = "ItemType")
data class ItemType(
    @Id
    @GeneratedValue
    val id: Long = 0,

    @Column
    val name: String
) {
    override fun toString(): String {
        return name
    }
}