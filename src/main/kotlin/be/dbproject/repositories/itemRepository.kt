package be.dbproject.repositories

import be.dbproject.models.Item
import javax.persistence.EntityManager
import javax.persistence.Persistence
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.CriteriaQuery
import javax.persistence.criteria.Path
import javax.persistence.criteria.Root
import kotlin.reflect.KProperty1

fun <T, V> Root<T>.get(prop: KProperty1<T, V>): Path<V> = this.get(prop.name)
class ItemRepository {
    private val entityManager: EntityManager = Persistence.createEntityManagerFactory("be.dbproject").createEntityManager()

    fun addItem(item: Item) {
        entityManager.transaction.begin()
        entityManager.persist(item)
        entityManager.transaction.commit()
    }

    fun getAllItems(): List<Item> {
        val criteriaBuilder: CriteriaBuilder = entityManager.criteriaBuilder
        val query: CriteriaQuery<Item> = criteriaBuilder.createQuery(Item::class.java)
        val root: Root<Item> = query.from(Item::class.java)

        query.select(root)

        return entityManager.createQuery(query).resultList
    }

    fun getItemById(itemId: Long): Item? {
        return entityManager.find(Item::class.java, itemId)
    }

    fun getItemsByName(name: String): List<Item> {
        val criteriaBuilder: CriteriaBuilder = entityManager.criteriaBuilder
        val query: CriteriaQuery<Item> = criteriaBuilder.createQuery(Item::class.java)
        val root: Root<Item> = query.from(Item::class.java)

        query.select(root)
        query.where(criteriaBuilder.equal(root.get(Item::name), name))

        return entityManager.createQuery(query).resultList
    }

    fun updateItem(item: Item) {
        entityManager.transaction.begin()
        entityManager.merge(item)
        entityManager.transaction.commit()
    }

    fun deleteItem(itemId: Long) {
        entityManager.transaction.begin()
        val item: Item? = entityManager.find(Item::class.java, itemId)
        if (item != null) {
            entityManager.remove(item)
        }
        entityManager.transaction.commit()
    }
}
