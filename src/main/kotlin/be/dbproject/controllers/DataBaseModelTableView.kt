package be.dbproject.controllers

import be.dbproject.models.DataBaseModels.DataBaseModel
import be.dbproject.repositories.Repository
import be.dbproject.repositories.RepositoryException
import javafx.beans.property.ReadOnlyObjectWrapper
import javafx.fxml.FXML
import javafx.scene.control.*
import javafx.util.StringConverter
import kotlin.reflect.KClass
import kotlin.reflect.KProperty1
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.primaryConstructor

class DataBaseModelTableView<T : DataBaseModel>(private val entityClass: KClass<T>) {

    @FXML
    private lateinit var tableView: TableView<T>

    @FXML
    private lateinit var createElementBtn: Button

    @FXML
    private lateinit var deleteElementBtn: Button

    @FXML
    private lateinit var editElementBtn: Button

    @FXML
    private lateinit var filterMenu: ComboBox<KProperty1<T, *>>

    @FXML
    private lateinit var searchBar: TextField

    @FXML
    fun initialize() {
        createElementBtn.setOnAction { handleNewItem() }
        deleteElementBtn.setOnAction { handleDeleteItem() }
        editElementBtn.setOnAction { handleEditItem() }

        initFilterMenu()
        initTable()
    }

    private fun initFilterMenu() {
        filterMenu.apply {
            items.addAll(entityClass.declaredMemberProperties)
            converter = object : StringConverter<KProperty1<T, *>>() {
                override fun toString(property: KProperty1<T, *>?): String {
                    return property?.let { property.name } ?: ""
                }

                override fun fromString(string: String?): KProperty1<T, *>? {
                    return null
                }
            }
        }
    }

    private fun initTable() {
        tableView.apply {
            selectionModel.selectionMode = SelectionMode.SINGLE
            columns.clear()
            setRowFactory {
                val row = TableRow<T>()
                row.setOnMouseClicked { event ->
                    if (event.clickCount == 2 && !row.isEmpty) {
                        handleEditItem()
                    }
                }
                row
            }
        }

        entityClass.primaryConstructor?.parameters?.forEach { parameter ->
            val col = TableColumn<T, Any>(parameter.name)
            col.setCellValueFactory { cellFeature ->
                val property = entityClass.declaredMemberProperties.find { it.name == parameter.name }
                ReadOnlyObjectWrapper(property?.get(cellFeature.value))
            }
            tableView.columns.add(col)
        }

        val items = Repository(entityClass).getAllEntities()
        tableView.items.addAll(items)
    }

    private fun handleNewItem() = openItemDialog()

    private fun handleDeleteItem() {
        val selectedItem = tableView.selectionModel.selectedItem

        if (selectedItem != null) {
            Repository(entityClass).deleteEntity(selectedItem)
            tableView.items.remove(selectedItem)
        } else {
            val alert = Alert(Alert.AlertType.WARNING, "Select an item to delete.")
            alert.showAndWait()
        }
    }

    private fun handleEditItem() {
        val selectedItem = tableView.selectionModel.selectedItem

        if (selectedItem != null) {
            openItemDialog(selectedItem)
        } else {
            Alert(Alert.AlertType.WARNING, "Select an item to edit.").showAndWait()
        }
    }

    private fun openItemDialog(entity: T? = null) {
        EditDataBaseModelDialog(entityClass, tableView.scene.window, entity) { newEntity ->
            try {
                if (entity == null) {
                    Repository(entityClass).addEntity(newEntity)
                    tableView.items.add(newEntity)
                }
                else {
                    Repository(entityClass).updateEntity(entity)
                    tableView.refresh()
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
