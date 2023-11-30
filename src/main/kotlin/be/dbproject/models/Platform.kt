package be.dbproject.models

import java.time.LocalDate
import javax.persistence.*

@Entity
@Table(name = "Platform")
data class Platform(
    @Id
    @GeneratedValue
    val id: Long = 0,

    @Column
    val name: String,

    @Column
    val description: String,

    @Column
    val releaseDate: LocalDate
)
