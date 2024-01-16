package be.dbproject.repositories

import be.dbproject.models.database.Review
import org.lightcouch.CouchDbClient


class DocumentStore {
    private var dbClient: CouchDbClient = CouchDbClient()
    fun getReviewById(reviewId: String): Review? {
        val review = dbClient.find(Review::class.java, reviewId)
        dbClient.shutdown()
        return review
    }

    fun getAllReviews(): List<Review> {
        val reviews = dbClient.view("_all_docs").includeDocs(true).query(Review::class.java)
        dbClient.shutdown()
        return reviews
    }

    fun addReview(review: Review) {
        dbClient.save(review)
        dbClient.shutdown()
    }
}