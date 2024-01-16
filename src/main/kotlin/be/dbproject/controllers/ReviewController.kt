package be.dbproject.controllers

import be.dbproject.models.database.Review
import be.dbproject.repositories.DocumentStore
import javafx.beans.property.ReadOnlyObjectWrapper
import javafx.fxml.FXML
import javafx.scene.control.Alert
import javafx.scene.control.SelectionMode
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.scene.text.Text
import javafx.scene.text.TextFlow
import kotlin.reflect.full.primaryConstructor


class ReviewController {
    @FXML
    private lateinit var tblReviews: TableView<Review>

    @FXML
    private lateinit var txtComment : TextFlow
    @FXML
    fun initialize() {
        //DO Stuff
        initTable()
    }

    private fun initTable() {
        tblReviews.selectionModel.selectionMode = SelectionMode.SINGLE
        tblReviews.columns.clear()

        val desiredColumns = listOf("title", "rating", "visitId")
        Review::class.primaryConstructor?.parameters?.forEach { parameter ->
            if (desiredColumns.contains(parameter.name)) {
                val col = TableColumn<Review, Any>(parameter.name)
                col.setCellValueFactory { cellFeature ->
                    val property = Review::class.members.find { it.name == parameter.name }
                    ReadOnlyObjectWrapper(property?.call(cellFeature.value))
                }
                tblReviews.columns.add(col)
            }
        }

        try {
            val items = DocumentStore().getAllReviews()
            tblReviews.items.addAll(items)
        } catch (e: Exception) {
            e.printStackTrace()
            val alert = Alert(Alert.AlertType.ERROR, "Error loading items.")
            alert.showAndWait()
        }

        tblReviews.selectionModel.selectedItemProperty().addListener { _, _, selectedReview ->
            selectedReview?.let {
                printReviewDetails(it)
            }
        }
    }

    private fun printReviewDetails(review: Review) {
        val commentLabel = Text("Comment: \n")
        val commentText = Text(review.comment)

        txtComment.children.setAll(commentLabel, commentText)
    }
}