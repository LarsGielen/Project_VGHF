package be.dbproject.models.DataBaseModels

data class Review(
    val id : String,
    val title : String,
    val rating : Int,
    val comment : String,
    val visitId : Long,
)