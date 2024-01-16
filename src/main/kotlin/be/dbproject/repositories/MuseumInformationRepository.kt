package be.dbproject.repositories

import be.dbproject.models.database.Location
import be.dbproject.models.database.VisitorLog
import java.time.LocalDate
import javax.persistence.EntityManager
import javax.persistence.NoResultException
import javax.persistence.TypedQuery
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.CriteriaQuery
import javax.persistence.criteria.Predicate
import javax.persistence.criteria.Root


class MuseumInformationRepository {
    private val entityManager: EntityManager = EntityManagerSingleton.instance

    public fun getMuseumDonationAmountForYear(year : Int, museumName : String) : Double {
        val criteriaBuilder: CriteriaBuilder = entityManager.criteriaBuilder
        val criteriaQuery: CriteriaQuery<Double> = criteriaBuilder.createQuery(Double::class.java)

        val root: Root<VisitorLog> = criteriaQuery.from(VisitorLog::class.java)
        criteriaQuery.select(criteriaBuilder.sum(root["donation"]))

        val dates = getYearDates(year)
        val dateRangePredicate: Predicate = criteriaBuilder.between(root["date"], dates[0], dates[1])
        val museumNamePredicate: Predicate = criteriaBuilder.equal(root.get<Location>("location").get<String>("locationName"), museumName);
        val combinedPredicate: Predicate = criteriaBuilder.and(dateRangePredicate, museumNamePredicate)

        criteriaQuery.where(combinedPredicate)
        val query: TypedQuery<Double> = entityManager.createQuery(criteriaQuery)

        return try {
            val combinedDonation = query.singleResult
            combinedDonation ?: 0.0
        } catch (e: NoResultException) {
            0.0
        }
    }

    public fun getMuseumVisitorAmountForYear(year : Int, museumName : String) : Int {
        val criteriaBuilder = entityManager.criteriaBuilder
        val criteriaQuery = criteriaBuilder.createQuery(
            Long::class.java
        )

        val root: Root<VisitorLog> = criteriaQuery.from(VisitorLog::class.java)

        val dates = getYearDates(year)
        val dateRangePredicate: Predicate = criteriaBuilder.between(root.get("date"), dates[0], dates[1])
        val museumNamePredicate: Predicate = criteriaBuilder.equal(root.get<Location>("location").get<String>("locationName"), museumName);
        val combinedPredicate: Predicate = criteriaBuilder.and(dateRangePredicate, museumNamePredicate)

        criteriaQuery.where(combinedPredicate).select(criteriaBuilder.count(root))
        val query = entityManager.createQuery(criteriaQuery)

        return try {
            val combined = query.singleResult?.toInt()
            combined ?: 0
        } catch (e: NoResultException) {
            0
        }
    }

    private fun getYearDates(year : Int) : List<LocalDate> {
        val beginDate = LocalDate.of(year, 1, 1)
        val endDate = LocalDate.of(year, 12, 31)

        return listOf(beginDate, endDate)
    }
}
