package be.dbproject.models

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "item")
data class Item(
    @Id
    @Column
    @GeneratedValue
    val id: Long = 0,

    @Column
    val typeId: Int,

    @Column
    val platformId: Int?,

    @Column
    val locationId: Int,

    @Column
    val publisherId: Int?,

    @Column
    var name: String,

    @Column
    var price: Double,

    @Column
    val description: String,

    @Column
    val series: String,

    @Column
    val releaseDate: String
) {
    constructor() : this(0, 0, null, 0, null, "", 0.0, "", "", "")
}