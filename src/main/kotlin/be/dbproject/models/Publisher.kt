package be.dbproject.models

import javax.persistence.Column
import javax.persistence.GeneratedValue
import javax.persistence.Id

data class Publisher(
    @Id
    @Column
    @GeneratedValue
    val id: Int,

    @Column
    val name: String,

    @Column
    val description: String,

    @Column
    val website: String?
)