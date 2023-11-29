package be.dbproject.models

import javax.persistence.*

@Entity
@Table(name = "ItemType")
data class ItemType(
    @Id
    @Column
    @GeneratedValue
    val id: Int,

    @Column
    val name: String
)