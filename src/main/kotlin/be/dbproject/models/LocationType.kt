package be.dbproject.models

import javax.persistence.*

@Entity
@Table(name = "LocationType")
data class LocationType(
    @Column
    val name: String
)
{
    @Id
    @GeneratedValue
    val id: Long = 0

    override fun toString(): String {
        return name
    }
}