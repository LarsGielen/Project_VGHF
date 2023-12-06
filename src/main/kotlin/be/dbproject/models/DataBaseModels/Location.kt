package be.dbproject.models.DataBaseModels

import javax.persistence.*

@Entity
@Table(name = "Location")
data class Location(
    @ManyToOne
    @JoinColumn(name = "locationType")
    var locationType: LocationType,

    @Column
    var locationName: String,

    @Column
    var country: String,

    @Column
    var city: String,

    @Column
    var street: String,

    @Column
    var houseNumber: Int
) : DataBaseModel()
{
    constructor() : this (
        LocationType(),
        "",
        "",
        "",
        "",
        0
    )

    override fun toString(): String {
        return "$locationType : $locationName"
    }
}