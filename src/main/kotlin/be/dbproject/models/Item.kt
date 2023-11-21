package be.dbproject.models

data class Item(
    val id: Int,
    val typeId: Int,
    val platformId: Int?,
    val locationId: Int,
    val publisherId: Int?,
    val name: String,
    val price: Double,
    val description: String,
    val series: String,
    val releaseDate: String
)