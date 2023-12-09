package be.dbproject.repositories

import be.dbproject.models.database.DatabaseModel
import javax.persistence.EntityManager
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.CriteriaQuery
import javax.persistence.criteria.Root
import kotlin.reflect.KClass
import kotlin.reflect.KProperty1
import kotlin.reflect.full.starProjectedType

// Source:  https://stackoverflow.com/questions/64521046/whats-the-correct-way-to-iterate-through-properties-of-a-singleton-in-kotlin
//          https://kotlinlang.org/docs/reflection.html
class Repository<T : DatabaseModel>(private val entityClass: KClass<T>) {
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

    fun getEntityByColumn(column: KProperty1<*, *>, searchParam: String): List<T> {
        if (searchParam.isEmpty()) {
            return getAllEntities()
        }

        val result = when (column.returnType) {
            String::class.starProjectedType -> getEntityByColumnString(column.name, "%$searchParam%")
            Double::class.starProjectedType -> getEntityByColumnDouble(column.name, searchParam.toDouble())
            Int::class.starProjectedType -> getEntityByColumnInt(column.name, searchParam.toInt())
            else -> throw RepositoryException("returnType not supported")
        }
        return result
    }

    fun getEntityByColumnString(column: String, searchParam: String): List<T> {
        val criteriaBuilder: CriteriaBuilder = entityManager.criteriaBuilder
        val query: CriteriaQuery<T> = criteriaBuilder.createQuery(entityClass.java)
        val root: Root<T> = query.from(entityClass.java)
        query.select(root)
        query.where(criteriaBuilder.like(root.get(column), searchParam))

        return entityManager.createQuery(query).resultList
    }

    fun getEntityByColumnInt(column: String, searchParam: Int): List<T> {
        val criteriaBuilder: CriteriaBuilder = entityManager.criteriaBuilder
        val query: CriteriaQuery<T> = criteriaBuilder.createQuery(entityClass.java)
        val root: Root<T> = query.from(entityClass.java)
        query.select(root)
        query.where(criteriaBuilder.equal(root.get<Int>(column), searchParam))

        return entityManager.createQuery(query).resultList
    }

    fun getEntityByColumnDouble(column: String, searchParam: Double): List<T> {
        val criteriaBuilder: CriteriaBuilder = entityManager.criteriaBuilder
        val query: CriteriaQuery<T> = criteriaBuilder.createQuery(entityClass.java)
        val root: Root<T> = query.from(entityClass.java)
        query.select(root)
        query.where(criteriaBuilder.equal(root.get<Double>(column), searchParam))

        return entityManager.createQuery(query).resultList
    }

    fun <V> getEntityByColumnDataBaseModel(column: String, searchParam: String): List<T> {
        val criteriaBuilder: CriteriaBuilder = entityManager.criteriaBuilder
        val query: CriteriaQuery<T> = criteriaBuilder.createQuery(entityClass.java)
        val root: Root<T> = query.from(entityClass.java)

        root.join<T, V>("name") // TODO

        query.select(root)
        query.where(criteriaBuilder.equal(root.get<Int>("name"), searchParam)) // TODO

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
