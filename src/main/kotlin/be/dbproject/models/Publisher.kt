package be.dbproject.models

import javax.persistence.*

@Entity
@Table(name = "Publisher")
data class Publisher(
    @Column
    val name: String,

    @Column
    val description: String,

    @Column
    val website: String?
)
{
    @Id
    @GeneratedValue
    val id: Long = 0

    override fun toString(): String {
        return name
    }
}