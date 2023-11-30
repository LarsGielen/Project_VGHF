package be.dbproject.models

import javax.persistence.*

@Entity
@Table(name = "Publisher")
data class Publisher(
    @Id
    @GeneratedValue
    val id: Long = 0,

    @Column
    val name: String,

    @Column
    val description: String,

    @Column
    val website: String?
)