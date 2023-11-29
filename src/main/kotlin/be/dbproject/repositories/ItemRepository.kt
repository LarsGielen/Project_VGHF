package be.dbproject.repositories

import be.dbproject.models.Item
import javax.persistence.Persistence
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.CriteriaQuery
import javax.persistence.criteria.Root

class ItemRepository : BaseRepository<Item>(Item::class.java) {
    private val entityManager = Persistence.createEntityManagerFactory("be.dbproject").createEntityManager()

    fun getItemsByName(name: String): List<Item> {
        val criteriaBuilder: CriteriaBuilder = entityManager.criteriaBuilder
        val query: CriteriaQuery<Item> = criteriaBuilder.createQuery(Item::class.java)
        val root: Root<Item> = query.from(Item::class.java)

        query.select(root)
        query.where(criteriaBuilder.equal(root.get(Item::name), name))

        return entityManager.createQuery(query).resultList
    }
}
