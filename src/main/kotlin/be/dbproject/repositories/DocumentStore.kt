package be.dbproject.repositories

import be.dbproject.models.DataBaseModels.Review
import org.lightcouch.CouchDbClient


class DocumentStore {
    var dbClient: CouchDbClient = CouchDbClient()
    fun getReviewById(reviewId: String): Review? {
        val review = dbClient.find(Review::class.java, reviewId)
        dbClient.shutdown()
        return review
    }

    fun getAllReviews(): List<Review> {
        val reviews = dbClient.view("reviews/_all_docs").includeDocs(true).query(Review::class.java)
        dbClient.shutdown()
        return reviews
    }

}