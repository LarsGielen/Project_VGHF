package be.dbproject.models

import javax.persistence.*

@Entity
@Table(name = "VisitorLog")
data class VisitorLog(
    @Id
    @GeneratedValue
    val locationId: Long = 0,

    @Column
    val visitorId: Int,

    @Column
    val date: String,

    @Column
    val donation: Double
)