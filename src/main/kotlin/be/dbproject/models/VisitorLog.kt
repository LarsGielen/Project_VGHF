package be.dbproject.models

data class VisitorLog(
    val locationId: Int,
    val visitorId: Int,
    val date: String,
    val donation: Double
)