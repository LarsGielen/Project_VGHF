package be.dbproject.models

import javax.persistence.Column
import javax.persistence.GeneratedValue
import javax.persistence.Id

data class ItemType(
    @Id
    @Column
    @GeneratedValue
    val id: Int,

    @Column
    val name: String
)