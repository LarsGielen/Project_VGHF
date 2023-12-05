package be.dbproject.models

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "Visitor")
data class Visitor(
    @Column
    val firstName: String,

    @Column
    val lastName: String,

    @Column
    val phone: String,

    @Column
    val email: String,

    @Column
    val locationId: Int
) : DataBaseModel()