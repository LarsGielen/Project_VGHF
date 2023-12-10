package be.dbproject.repositories

import be.dbproject.models.database.DatabaseModel
import javax.persistence.EntityManager
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.CriteriaQuery
import javax.persistence.criteria.Path
import javax.persistence.criteria.Root
import kotlin.reflect.KClass

// Source:  https://stackoverflow.com/questions/64521046/whats-the-correct-way-to-iterate-through-properties-of-a-singleton-in-kotlin
//          https://kotlinlang.org/docs/reflection.html
class Repository<T : DatabaseModel>(private val entityClass: KClass<T>) {
    private val entityManager: EntityManager = EntityManagerSingleton.instance

    enum class QueryType {
        LIKE, EQUAL, GREATER_THAN, LESS_THAN, RANGE
    }

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

    fun getEntityByColumn(
        columNames: List<String>,
        columnTypes: List<KClass<*>>,
        searchParam: String,
        queryType: QueryType
    ): List<T> {

        if (columnTypes.isEmpty() || columNames.isEmpty() || searchParam.isBlank()) return getAllEntities()
        if (columnTypes.count() != columNames.count()) return listOf() // TODO: handle error

        // create query and root
        val criteriaBuilder: CriteriaBuilder = entityManager.criteriaBuilder
        val query: CriteriaQuery<T> = criteriaBuilder.createQuery(entityClass.java)
        val root: Root<T> = query.from(entityClass.java)

        // create path to column
        var path : Path<*> = root
        for (i in columNames.dropLast(1).indices) {
            path = updatePath(path, columnTypes[i], columNames[i])
        }

        when (queryType) {
            QueryType.LIKE ->
                query.select(root).where(criteriaBuilder.like(path.get(columNames.last()), "%$searchParam%"))

            QueryType.EQUAL ->
                query.select(root).where(criteriaBuilder.equal(path.get<String>(columNames.last()), searchParam))

            QueryType.GREATER_THAN ->
                query.select(root).where(criteriaBuilder.greaterThan(path.get(columNames.last()), searchParam))

            QueryType.LESS_THAN ->
                query.select(root).where(criteriaBuilder.lessThan(path.get(columNames.last()), searchParam))

            QueryType.RANGE ->
                throw NotImplementedError()
        }

        return entityManager.createQuery(query).resultList
    }

    private fun <V : Any> updatePath(path : Path<*>, kClass: KClass<V>, string : String) : Path<V> {
        return path.get(string)
    }

    private fun getEntityByColumnLike(path: Path<*>, column: String, searchParam: String): List<T> {
        val criteriaBuilder: CriteriaBuilder = entityManager.criteriaBuilder
        val query: CriteriaQuery<T> = criteriaBuilder.createQuery(entityClass.java)
        val root: Root<T> = query.from(entityClass.java)

        query.select(root).where(criteriaBuilder.like(path.get(column), searchParam))

        return entityManager.createQuery(query).resultList
    }

    private fun <ColumnType> getEntityByColumnEqual(column: String, searchParam: ColumnType, columnType: ColumnType): List<T> {
        val criteriaBuilder: CriteriaBuilder = entityManager.criteriaBuilder
        val query: CriteriaQuery<T> = criteriaBuilder.createQuery(entityClass.java)
        val root: Root<T> = query.from(entityClass.java)

        query.select(root).where(criteriaBuilder.equal(root.get<ColumnType>(column), searchParam))

        return entityManager.createQuery(query).resultList
    }

    private fun getEntityByColumnGreaterThan(column: String, searchParam: Double): List<T> {
        val criteriaBuilder: CriteriaBuilder = entityManager.criteriaBuilder
        val query: CriteriaQuery<T> = criteriaBuilder.createQuery(entityClass.java)
        val root: Root<T> = query.from(entityClass.java)

        query.select(root).where(criteriaBuilder.greaterThan(root.get(column), searchParam))

        return entityManager.createQuery(query).resultList
    }

    private fun getEntityByColumnLessThan(column: String, searchParam: Double): List<T> {
        val criteriaBuilder: CriteriaBuilder = entityManager.criteriaBuilder
        val query: CriteriaQuery<T> = criteriaBuilder.createQuery(entityClass.java)
        val root: Root<T> = query.from(entityClass.java)

        query.select(root).where(criteriaBuilder.lessThan(root.get(column), searchParam))

        return entityManager.createQuery(query).resultList
    }

    @Throws(RepositoryException::class)
    fun updateEntity(entity: T) {
        withTransaction { entityManager.merge(entity) }
    }

    @Throws(RepositoryException::class)
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
