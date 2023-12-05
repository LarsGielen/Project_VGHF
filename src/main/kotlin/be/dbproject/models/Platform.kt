package be.dbproject.models

import java.time.LocalDate
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "Platform")
data class Platform(
    @Column
    val name: String,

    @Column
    val description: String,

    @Column
    val releaseDate: LocalDate
) : DataBaseModel()
{
    override fun toString() : String{
        return name
    }
}
