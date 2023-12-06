package be.dbproject.controllers

import be.dbproject.models.DataBaseModel
import be.dbproject.repositories.Repository
import javafx.beans.property.ReadOnlyObjectWrapper
import javafx.fxml.FXML
import javafx.scene.control.*
import kotlin.reflect.KClass
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.primaryConstructor

class DataBaseModelTableView<T : DataBaseModel>(private val entityClass: KClass<T>) {

    @FXML
    private lateinit var tblItems: TableView<T>

    @FXML
    private lateinit var createElementBtn: Button

    @FXML
    private lateinit var deleteElementBtn: Button

    @FXML
    private lateinit var editElementBtn: Button

    @FXML
    fun initialize() {
        createElementBtn.setOnAction { handleNewItem() }
        deleteElementBtn.setOnAction { handleDeleteItem() }
        editElementBtn.setOnAction { handleEditItems() }

        initTable()
    }

    private fun initTable() {
        tblItems.selectionModel.selectionMode = SelectionMode.SINGLE
        tblItems.columns.clear()

        entityClass.primaryConstructor?.parameters?.forEach { parameter ->
            val col = TableColumn<T, Any>(parameter.name)
            col.setCellValueFactory { cellFeature ->
                val property = entityClass.declaredMemberProperties.find { it.name == parameter.name }
                ReadOnlyObjectWrapper(property?.get(cellFeature.value))
            }
            tblItems.columns.add(col)
        }

        try {
            val items = Repository(entityClass).getAllEntities()
            tblItems.items.addAll(items)
        } catch (e: Exception) {
            e.printStackTrace()
            val alert = Alert(Alert.AlertType.ERROR, "Error loading items.")
            alert.showAndWait()
        }
    }

    @FXML
    fun handleNewItem() = openItemDialog("Add Item",  null)

    @FXML
    fun handleDeleteItem() {
        val selectedItem = tblItems.selectionModel.selectedItem

        if (selectedItem != null) {
            try {
                Repository(entityClass).deleteEntity(selectedItem)
                tblItems.items.remove(selectedItem)
            } catch (e: Exception) {
                val alert = Alert(Alert.AlertType.ERROR, "Error deleting item.")
                alert.showAndWait()
            }
        } else {
            val alert = Alert(Alert.AlertType.WARNING, "Select an item to delete.")
            alert.showAndWait()
        }
    }

    @FXML
    fun handleEditItems() {
        val selectedItem = tblItems.selectionModel.selectedItem

        if (selectedItem != null) {
            openItemDialog("Edit Item", selectedItem)
        } else {
            val alert = Alert(Alert.AlertType.WARNING, "Select an item to edit.")
            alert.showAndWait()
        }
    }

    private fun openItemDialog(title: String, entity: T?) {
        EditDataBaseModelDialog(entityClass, tblItems.scene.window, entity)
    }
}
