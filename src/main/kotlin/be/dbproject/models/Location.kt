package be.dbproject.models

data class Location(
    val id: Int,
    val locationType: Int,
    val locationName: String,
    val country: String,
    val city: String,
    val street: String,
    val houseNumber: String
)
