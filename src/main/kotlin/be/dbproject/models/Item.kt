package be.dbproject.models

import java.util.*
import javax.persistence.*

@Entity
@Table(name = "Item")
data class Item(
    @Id
    @GeneratedValue
    val id: Long = 0,

    @Column
    var typeId: Long,

    @Column
    val platformId: Long?,

    @Column
    val locationId: Long,

    @Column
    val publisherId: Long,

    @Column
    var name: String,

    @Column
    var price: Double,

    @Column
    var description: String,

    @Column
    var series: String,

    @ManyToMany
    @JoinTable(
        name = "ItemGenreLink",
        joinColumns = [JoinColumn(name = "itemid")],
        inverseJoinColumns = [JoinColumn(name = "genreid")]
    )
    var genres: Set<Genre> = HashSet(),

    @Column
    @Temporal(TemporalType.DATE)
    var releaseDate: Date
) {
    //constructor() : this(0, 0, null, 0, null, "", 0.0, "", "", 0,"")
}