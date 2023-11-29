package be.dbproject.models

import javax.persistence.*

@Entity
@Table(name = "Location")
data class Location(
    @Id
    @GeneratedValue
    val id: Int,

    @Column
    val locationType: Int,

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
)
