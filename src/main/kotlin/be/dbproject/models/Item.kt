package be.dbproject.models

import java.time.LocalDate
import javax.persistence.*

@Entity
@Table(name = "Item")
data class Item(
    @ManyToOne
    @JoinColumn(name = "typeid", nullable = false)
    val itemType: ItemType,

    @ManyToOne
    @JoinColumn(name = "platformid")
    val platform: Platform? = null,

    @ManyToOne
    @JoinColumn(name = "locationid", nullable = false)
    val location: Location,

    @ManyToOne
    @JoinColumn(name = "publisherid")
    val publisher: Publisher? = null,

    @Column(name = "name", nullable = false)
    val name: String,

    @Column(name = "price", nullable = false)
    val price: Double,

    @Column(name = "description", nullable = false)
    val description: String,

    @Column(name = "series", nullable = false)
    val series: String,

    @Column(name = "releaseDate", nullable = false)
    val releaseDate: LocalDate,

    @ManyToMany
    @JoinTable(
        name = "ItemGenreLink",
        joinColumns = [JoinColumn(name = "itemid")],
        inverseJoinColumns = [JoinColumn(name = "genreid")]
    )
    var genres: Set<Genre>
) : DataBaseModel()