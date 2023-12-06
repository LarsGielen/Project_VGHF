package be.dbproject.models

import javax.persistence.*

@Entity
@Table(name = "Visitor")
data class Visitor(
    @Column
    val firstName: String,

    @Column
    val lastName: String,

    @Column
    val phone: String,

    @Column
    val email: String,

    @ManyToOne
    @JoinColumn(name = "locationID")
    var location: Location,
) : DataBaseModel()
{
    override fun toString(): String {
        return "$firstName $lastName"
    }
}