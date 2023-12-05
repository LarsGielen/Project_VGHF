package be.dbproject.models

import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.MappedSuperclass

@MappedSuperclass
open class DataBaseModel(
    @Id
    @GeneratedValue
    val id: Long = 0,
)