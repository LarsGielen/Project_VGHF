package be.dbproject.models

import javax.persistence.*

@Entity
@Table(name = "LocationType")
data class LocationType(
    @Id
    @GeneratedValue
    val id: Long = 0,

    @Column
    val name: String
)