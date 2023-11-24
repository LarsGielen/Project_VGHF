package be.dbproject.models

data class Visitor(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val phone: String,
    val email: String,
    val locationId: Int
)