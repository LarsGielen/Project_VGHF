package be.dbproject.models

import javax.persistence.*

@Entity
@Table(name = "LocationType")
data class LocationType(
    @Id
    @GeneratedValue
    val id: Int,

    @Column
    val name: String
)