package be.dbproject.controllers

import be.dbproject.models.Item

import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.SelectionMode
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView

import javafx.beans.property.ReadOnlyObjectWrapper
class ItemsTableController {

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

        initTable()
    }

    private fun initTable() {
        tblItems.selectionModel.selectionMode = SelectionMode.SINGLE
        tblItems.columns.clear()

        // TODO: Vervang dit met de werkelijke kolomnamen en gegevensklassen
        val columnNames = arrayOf("Naam", "Eigenschap", "Prijs", "NogIets?")

        // Voeg kolommen toe aan de tabel
        for (colName in columnNames) {
            val col = TableColumn<Item, String>(colName)
            col.setCellValueFactory { f -> ReadOnlyObjectWrapper(f.value.name)}
            tblItems.columns.add(col)
        }

        // Voeg placeholder-gegevens toe aan de tabel
        for (i in 0..<10) {
            tblItems.items.add(Item(i.toLong(), i, i,i,i,"Test", i.toDouble(), "Test","Test", "Test"))
        }
    }

    @FXML
    fun handleButton1() {
        println("Button 1 clicked")
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
