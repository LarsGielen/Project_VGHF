package be.dbproject.repositories
import be.dbproject.models.Genre
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.CriteriaQuery
import javax.persistence.criteria.Root


class GenreRepository : BaseRepository<Genre>(Genre::class.java) {
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

}