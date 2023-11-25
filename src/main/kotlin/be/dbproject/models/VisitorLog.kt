package be.dbproject.models

import javax.persistence.Column
import javax.persistence.GeneratedValue
import javax.persistence.Id

data class VisitorLog(
    @Id
    @Column
    @GeneratedValue
    val locationId: Int,

    @Column
    val visitorId: Int,

    @Column
    val date: String,

    @Column
    val donation: Double
)