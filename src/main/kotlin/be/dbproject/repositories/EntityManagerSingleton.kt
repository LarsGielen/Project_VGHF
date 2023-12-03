package be.dbproject.repositories

import javax.persistence.Persistence

object EntityManagerSingleton {
    val instance = Persistence.createEntityManagerFactory("be.dbproject").createEntityManager()
}