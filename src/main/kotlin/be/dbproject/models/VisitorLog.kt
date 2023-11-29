package be.dbproject.models

import javax.persistence.*

@Entity
@Table(name = "VisitorLog")
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