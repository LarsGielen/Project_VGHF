package be.dbproject.controllers

import be.dbproject.models.Item
import be.dbproject.repositories.ItemRepository
import javafx.beans.property.ReadOnlyObjectWrapper
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.SelectionMode
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import java.util.*
import javax.persistence.EntityManager

class ItemsTableController {
    private lateinit var entityManager: EntityManager
    private lateinit var itemRepository: ItemRepository

    @FXML
    private lateinit var tblItems: TableView<Item>

    @FXML
    private lateinit var btn1: Button

    @FXML
    private lateinit var btn2: Button

    @FXML
    private lateinit var btn3: Button

    @FXML
    fun initialize() {
        btn1.setOnAction { handleButton1() }
        btn2.setOnAction { handleButton2() }
        btn3.setOnAction { handleButton3() }

        this.itemRepository = ItemRepository();
        initTable()
    }

    private fun initTable() {
        tblItems.selectionModel.selectionMode = SelectionMode.SINGLE
        tblItems.columns.clear()

        val itemClass = Item::class.java
        val properties = itemClass.declaredFields

        for (property in properties) {
            val col = TableColumn<Item, Any>(property.name.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() })
            col.setCellValueFactory { cellData ->
                val field = itemClass.getDeclaredField(property.name)
                field.isAccessible = true
                ReadOnlyObjectWrapper(field.get(cellData.value))
            }
            tblItems.columns.add(col)
        }

        try {
            val items = itemRepository.getAllItems()
            tblItems.items.addAll(items)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @FXML
    fun handleButton1() {
        println("Button 1 clicked")

        val dummyItem1 = Item(typeId = 1, platformId = 1, locationId = 1, publisherId = 1, name = "Dummy Item 1", price = 9.99, description = "This is a dummy item.", series = "Dummy Series", releaseDate = "2023-01-01")
        val dummyItem2 = Item(typeId = 2, platformId = 2, locationId = 2, publisherId = 2, name = "Dummy Item 2", price = 14.99, description = "Another dummy item.", series = "Dummy Series", releaseDate = "2023-01-02")
        try {
            itemRepository.addItem(dummyItem1)
            itemRepository.addItem(dummyItem2)

            // Vernieuw de tabel na het toevoegen van dummy-items
            // Het vernieuwen van de tabel kan best in een aparte functie als deze binnenkort vaker gebruikt wordt.
            val items = itemRepository.getAllItems()
            tblItems.items.setAll(items)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            entityManager.close()
        }


    }

    @FXML
    fun handleButton2() {
        println("Button 2 clicked")
    }

    @FXML
    fun handleButton3() {
        println("Button 3 clicked")
    }
}
