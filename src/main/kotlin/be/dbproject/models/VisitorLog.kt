package be.dbproject.models

import java.time.LocalDate
import javax.persistence.*

@Entity
@Table(name = "VisitorLog")
data class VisitorLog(
    @ManyToOne
    @JoinColumn(name = "visitorid")
    val visitor: Visitor,

    @ManyToOne
    @JoinColumn(name = "locationid")
    val location: Location,

    @Column
    val date: LocalDate,

    @Column
    val donation: Float
) : DataBaseModel()
{
    override fun toString(): String {
        return "$visitor - $location"
    }
}