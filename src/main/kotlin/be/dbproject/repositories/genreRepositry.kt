package be.dbproject.repositories
import be.dbproject.models.Genre
import be.dbproject.models.Item
import javax.persistence.Persistence
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.CriteriaQuery
import javax.persistence.criteria.Path
import javax.persistence.criteria.Root
import kotlin.reflect.KProperty1


class genreRepositry {
    private val entityManager = Persistence.createEntityManagerFactory("be.dbproject").createEntityManager()

    fun addItem(genre: Genre) {
        entityManager.transaction.begin()
        entityManager.persist(genre)
        entityManager.transaction.commit()
    }

    fun getAlleGenres(): List<Genre> {
        val criteriaBuilder: CriteriaBuilder = entityManager.criteriaBuilder
        val query: CriteriaQuery<Genre> = criteriaBuilder.createQuery(Genre::class.java)
        val root: Root<Genre> = query.from(Genre::class.java)

        query.select(root)

        return entityManager.createQuery(query).resultList
    }

    fun getGenreByName(name: String): List<Genre> {
        val criteriaBuilder: CriteriaBuilder = entityManager.criteriaBuilder
        val query: CriteriaQuery<Genre> = criteriaBuilder.createQuery(Genre::class.java)
        val root: Root<Genre>? = query.from(Genre::class.java)

        query.select(root)
        if (root != null) {
            query.where(criteriaBuilder.equal(root.get(Genre::name), name))
        }

        return entityManager.createQuery(query).resultList
    }

    fun updateItem(genre: Genre) {
        entityManager.transaction.begin()
        entityManager.merge(genre)
        entityManager.transaction.commit()
    }

    fun deleteItem(genreId: Long) {
        entityManager.transaction.begin()
        val genre: Genre? = entityManager.find(Genre::class.java, genreId)
        if (genre != null) {
            entityManager.remove(genre)
        }
        entityManager.transaction.commit()
    }
}