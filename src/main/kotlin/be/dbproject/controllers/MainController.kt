package be.dbproject.controllers

import be.dbproject.ProjectMain
import be.dbproject.models.Item
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.layout.AnchorPane
import javafx.stage.Modality
import javafx.stage.Stage

class MainController {

    @FXML
    private lateinit var btnItemDetails: Button

    @FXML
    private lateinit var btnLocationDetails: Button

    @FXML
    private lateinit var btnVisitorDetails: Button

    @FXML
    private lateinit var btnManageItems: Button

    @FXML
    private lateinit var btnManageLocations: Button

    @FXML
    private lateinit var btnManageVisitors: Button


    @FXML
    fun initialize() {
        btnItemDetails.setOnAction { handleItemDetailsButton() }
        btnLocationDetails.setOnAction { handleLocationDetailsButton() }
        btnVisitorDetails.setOnAction { handleVisitorDetailsButton() }
        btnManageItems.setOnAction { handleManageItemsButton() }
        btnManageLocations.setOnAction { handleManageLocationsButton() }
        btnManageVisitors.setOnAction { handleManageVisitorsButton() }
    }

    @FXML
    fun handleItemDetailsButton() {
        println("Item Details button clicked")
        try {
            val stage = Stage()
            val loader = FXMLLoader(javaClass.classLoader.getResource("DataBaseModelTableView.fxml"))
            loader.setController(DataBaseModelTableView(Item::class))
            val root: Parent = loader.load()

            val scene = Scene(root)

            stage.setScene(scene)
            stage.title = "Items"
            stage.initOwner(ProjectMain.rootStage)
            stage.initModality(Modality.WINDOW_MODAL)
            stage.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @FXML
    fun handleLocationDetailsButton() {
        println("Location Details button clicked")
        try {
            val stage = Stage()
            val root = FXMLLoader.load<Any>(javaClass.classLoader.getResource("LocationTableView.fxml")) as AnchorPane
            val scene = Scene(root)

            stage.setScene(scene)
            stage.title = "Locations"
            stage.initOwner(ProjectMain.rootStage)
            stage.initModality(Modality.WINDOW_MODAL)
            stage.show()

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @FXML
    fun handleVisitorDetailsButton() {
        println("Visitor Details button clicked")
        try {
            val stage = Stage()
            val root = FXMLLoader.load<Any>(javaClass.classLoader.getResource("VisitorTableView.fxml")) as AnchorPane
            val scene = Scene(root)

            stage.setScene(scene)
            stage.title = "Visitors"
            stage.initOwner(ProjectMain.rootStage)
            stage.initModality(Modality.WINDOW_MODAL)
            stage.show()

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @FXML
    fun handleManageItemsButton() {
        println("Manage Items button clicked")
    }

    @FXML
    fun handleManageLocationsButton() {
        println("Manage Locations button clicked")
    }

    @FXML
    fun handleManageVisitorsButton() {
        println("Manage Visitors button clicked")
    }
}
