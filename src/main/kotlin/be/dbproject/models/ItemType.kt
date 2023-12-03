package be.dbproject.models

import javax.persistence.*

@Entity
@Table(name = "ItemType")
data class ItemType(
    @Column
    val name: String
) {
    @Id
    @GeneratedValue
    val id: Long = 0

    override fun toString(): String {
        return name
    }
}