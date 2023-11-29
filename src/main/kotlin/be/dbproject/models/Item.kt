package be.dbproject.models

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "Item")
data class Item(
    @Id
    @GeneratedValue
    val id: Long = 0,

    @Column
    val typeId: Long,

    @Column
    val platformId: Long?,

    @Column
    val locationId: Long,

    @Column
    val publisherId: Long?,

    @Column
    var name: String,

    @Column
    var price: Double,

    @Column
    var description: String,

    @Column
    var series: String,

    @Column
    var genreId: Long,

    @Column
    var releaseDate: String
) {
    constructor() : this(0, 0, null, 0, null, "", 0.0, "", "", 0,"")
}