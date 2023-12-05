package be.dbproject.repositories

import be.dbproject.models.DataBaseModel
import javax.persistence.EntityManager
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.CriteriaQuery
import javax.persistence.criteria.Root

class Repository<T : DataBaseModel>(private val entityClass: Class<T>) {
    protected val entityManager: EntityManager = EntityManagerSingleton.instance

    fun addEntity(entity: T) {
        withTransaction { entityManager.persist(entity) }
    }

    fun getAllEntities(): List<T> {
        val criteriaBuilder: CriteriaBuilder = entityManager.criteriaBuilder
        val query: CriteriaQuery<T> = criteriaBuilder.createQuery(entityClass)
        val root: Root<T> = query.from(entityClass)
        query.select(root)
        return entityManager.createQuery(query).resultList
    }

    fun getEntityById(entityId: Long): T? {
        return entityManager.find(entityClass, entityId)
    }

    fun getEntityByColumn(column: String, searchString: String): List<T> {
        val criteriaBuilder: CriteriaBuilder = entityManager.criteriaBuilder
        val query: CriteriaQuery<T> = criteriaBuilder.createQuery(entityClass)
        val root: Root<T>? = query.from(entityClass)

        query.select(root)
        root?.let {
            query.where(criteriaBuilder.equal(it.get<String>(column), searchString))
        }

        return entityManager.createQuery(query).resultList
    }

    fun updateEntity(entity: T) {
        withTransaction { entityManager.merge(entity) }
    }

    fun deleteEntity(entity: T) {
        withTransaction {
            entityManager.find(entityClass, entity.id)?.let {
                entityManager.remove(it)
            }
        }
    }

    private fun withTransaction(action: () -> Unit) {
        val transaction = entityManager.transaction
        try {
            transaction.begin()
            action()
            transaction.commit()
        } catch (e: Exception) {
            transaction.rollback()
            throw e
        }
    }
}
