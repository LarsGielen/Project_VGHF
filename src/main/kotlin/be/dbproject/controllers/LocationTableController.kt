package be.dbproject.controllers

import be.dbproject.models.Location
import javafx.beans.property.ReadOnlyObjectWrapper
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.TableView
import javafx.scene.control.TableColumn

class LocationTableController {

    @FXML
    private lateinit var tblLocations: TableView<Location>

    @FXML
    private lateinit var btnLocation1: Button

    @FXML
    private lateinit var btnLocation2: Button

    @FXML
    private lateinit var btnLocation3: Button

    @FXML
    fun initialize() {
        btnLocation1.setOnAction { handleLocationButton1() }
        btnLocation2.setOnAction { handleLocationButton2() }
        btnLocation3.setOnAction { handleLocationButton3() }

        initTable()
    }

    private fun initTable() {
        val colId = TableColumn<Location, Int>("ID")
        colId.setCellValueFactory { f -> ReadOnlyObjectWrapper(f.value.id) }

        tblLocations.columns.addAll(colId)
        for (i in 0..<10) {
            tblLocations.items.add(Location(i,i, "locationNameTest", "countryTest", "cityTest", "streetTest" , "houseNumberTest"))
        }
    }

    @FXML
    fun handleLocationButton1() {
        println("Location Button 1 clicked")
    }

    @FXML
    fun handleLocationButton2() {
        println("Location Button 2 clicked")
    }

    @FXML
    fun handleLocationButton3() {
        println("Location Button 3 clicked")
    }
}
