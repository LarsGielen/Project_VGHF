package be.dbproject.models

import javax.persistence.Column
import javax.persistence.GeneratedValue
import javax.persistence.Id

data class Location(
    @Id
    @Column
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
