package be.dbproject.controllers

import be.dbproject.models.DataBaseModels.DataBaseModel
import be.dbproject.repositories.Repository
import be.dbproject.repositories.RepositoryException
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
        editElementBtn.setOnAction { handleEditItem() }

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

    private fun handleNewItem() = openItemDialog()

    private fun handleDeleteItem() {
        val selectedItem = tblItems.selectionModel.selectedItem

        if (selectedItem != null) {
            Repository(entityClass).deleteEntity(selectedItem)
            tblItems.items.remove(selectedItem)
        } else {
            val alert = Alert(Alert.AlertType.WARNING, "Select an item to delete.")
            alert.showAndWait()
        }
    }

    private fun handleEditItem() {
        val selectedItem = tblItems.selectionModel.selectedItem

        if (selectedItem != null) {
            openItemDialog(selectedItem)
        } else {
            Alert(Alert.AlertType.WARNING, "Select an item to edit.").showAndWait()
        }
    }

    private fun openItemDialog(entity: T? = null) {
        EditDataBaseModelDialog(entityClass, tblItems.scene.window, entity) {newEntity ->
            try {
                if (entity == null) {
                    Repository(entityClass).addEntity(newEntity)
                    tblItems.items.add(newEntity)
                }
                else {
                    Repository(entityClass).updateEntity(entity)
                    tblItems.refresh()
                }
            }
            catch (e : RepositoryException) {
                Alert(Alert.AlertType.ERROR).apply {
                    title = "Error while updating/adding ${entityClass.simpleName?.lowercase()}"
                    headerText = null
                    contentText = "An error occurred while attempting to update or add " +
                            "a ${entityClass.simpleName?.lowercase()}. " +
                            "Please ensure that all required unique fields are unique."
                    showAndWait()
                }
            }
        }
    }
}
