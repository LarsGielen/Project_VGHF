import be.dbproject.models.Genre
import javax.persistence.Entity
import javax.persistence.EntityManager
import javax.persistence.Persistence
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.CriteriaQuery
import javax.persistence.criteria.Root

abstract class BaseRepository<T>(private val entityClass: Class<T>) {
    private val entityManager: EntityManager = Persistence.createEntityManagerFactory("be.dbproject").createEntityManager()

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

    fun updateEntity(entity: T) {
        withTransaction { entityManager.merge(entity) }
    }

    fun deleteEntity(entityId: Long) {
        entityManager.transaction.begin()
        val entity: T? = entityManager.find(entityClass, entityId)
        if (entity != null) {
            withTransaction { entityManager.remove(entity) }
        }
        entityManager.transaction.commit()
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
