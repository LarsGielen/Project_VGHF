package be.dbproject.models

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "VisitorLog")
data class VisitorLog(
    @Column
    val visitorId: Int,

    @Column
    val date: String,

    @Column
    val donation: Double
) : DataBaseModel()