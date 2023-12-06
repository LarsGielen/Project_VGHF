package be.dbproject.repositories

import be.dbproject.models.DataBaseModels.DataBaseModel
import javax.persistence.EntityManager
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.CriteriaQuery
import javax.persistence.criteria.Root
import kotlin.reflect.KClass

// Source:  https://stackoverflow.com/questions/64521046/whats-the-correct-way-to-iterate-through-properties-of-a-singleton-in-kotlin
//          https://kotlinlang.org/docs/reflection.html
class Repository<T : DataBaseModel>(private val entityClass: KClass<T>) {
    private val entityManager: EntityManager = EntityManagerSingleton.instance

    @Throws(RepositoryException::class)
    fun addEntity(entity: T) {
        withTransaction { entityManager.persist(entity) }
    }

    fun getAllEntities(): List<T> {
        val criteriaBuilder: CriteriaBuilder = entityManager.criteriaBuilder
        val query: CriteriaQuery<T> = criteriaBuilder.createQuery(entityClass.java)
        val root: Root<T> = query.from(entityClass.java)
        query.select(root)
        return entityManager.createQuery(query).resultList
    }

    fun getEntityById(entityId: Long): T? {
        return entityManager.find(entityClass.java, entityId)
    }

    fun getEntityByColumn(column: String, searchString: String): List<T> {
        val criteriaBuilder: CriteriaBuilder = entityManager.criteriaBuilder
        val query: CriteriaQuery<T> = criteriaBuilder.createQuery(entityClass.java)
        val root: Root<T>? = query.from(entityClass.java)

        query.select(root)
        root?.let {
            query.where(criteriaBuilder.equal(it.get<String>(column), searchString))
        }

        return entityManager.createQuery(query).resultList
    }

    @Throws(RepositoryException::class)
    fun updateEntity(entity: T) {
        withTransaction { entityManager.merge(entity) }
    }

    fun deleteEntity(entity: T) {
        withTransaction {
            entityManager.find(entityClass.java, entity.id)?.let {
                entityManager.remove(it)
            }
        }
    }

    @Throws(RepositoryException::class)
    private fun withTransaction(action: () -> Unit) {
        val transaction = entityManager.transaction
        try {
            transaction.begin()
            action()
            transaction.commit()
        }
        catch (e: Exception) {
            transaction.rollback()
            throw RepositoryException(e.message)
        }
    }
}
