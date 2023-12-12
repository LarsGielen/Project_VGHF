package be.dbproject.controllers

import be.dbproject.models.DataBaseModels.Review
import be.dbproject.repositories.DocumentStore
import javafx.beans.property.ReadOnlyObjectWrapper
import javafx.fxml.FXML
import javafx.scene.control.Alert
import javafx.scene.control.SelectionMode
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import kotlin.reflect.full.primaryConstructor

class ReviewController {
    @FXML
    private lateinit var tblReviews: TableView<Review>
    @FXML
    fun initialize() {
        //DO Stuff
        initTable()
    }

    private fun initTable() {
        tblReviews.selectionModel.selectionMode = SelectionMode.SINGLE
        tblReviews.columns.clear()

        Review::class.primaryConstructor?.parameters?.forEach { parameter ->
            val col = TableColumn<Review, Any>(parameter.name)
            col.setCellValueFactory { cellFeature ->
                val property = Review::class.members.find { it.name == parameter.name }
                ReadOnlyObjectWrapper(property?.call(cellFeature.value))
            }
            tblReviews.columns.add(col)
        }

        try {
            val items = DocumentStore().getReviewById("81598bd7e10eae52013711bdc100fce9")
            tblReviews.items.addAll(items)
        } catch (e: Exception) {
            e.printStackTrace()
            val alert = Alert(Alert.AlertType.ERROR, "Error loading items.")
            alert.showAndWait()
        }
    }
}