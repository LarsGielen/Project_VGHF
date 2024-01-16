package be.dbproject.models.DataBaseModels

data class Review(
    val _id : String,
    val _rev : String,
    val title : String,
    val rating : Int,
    val comment : String,
    val visitId : Long,
)