package be.dbproject.models.DataBaseModels

import java.time.LocalDate
import javax.persistence.*

@Entity
@Table(name = "VisitorLog")
data class VisitorLog(
    @ManyToOne
    @JoinColumn(name = "visitorid")
    var visitor: Visitor,

    @ManyToOne
    @JoinColumn(name = "locationid")
    var location: Location,

    @Column
    var date: LocalDate,

    @Column
    var donation: Double
) : DataBaseModel()
{
    override fun toString(): String {
        return "$visitor - $location"
    }
}