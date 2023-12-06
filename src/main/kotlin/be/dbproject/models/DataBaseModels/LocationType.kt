package be.dbproject.models.DataBaseModels

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "LocationType")
data class LocationType(
    @Column
    var name: String
) : DataBaseModel()
{
    override fun toString(): String {
        return name
    }
}