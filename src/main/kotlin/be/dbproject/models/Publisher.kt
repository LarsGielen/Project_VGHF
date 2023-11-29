package be.dbproject.models

import javax.persistence.*

@Entity
@Table(name = "Publisher")
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