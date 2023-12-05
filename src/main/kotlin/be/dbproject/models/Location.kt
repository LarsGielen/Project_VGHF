package be.dbproject.models

import javax.persistence.*

@Entity
@Table(name = "Location")
data class Location(
    @ManyToOne
    @JoinColumn(name = "locationType")
    val locationType: LocationType,

    @Column
    val locationName: String,

    @Column
    val country: String,

    @Column
    val city: String,

    @Column
    val street: String,

    @Column
    val houseNumber: String
) : DataBaseModel()
{
    override fun toString(): String {
        return "$locationType : $locationName"
    }
}