package be.dbproject.controllers

import be.dbproject.ProjectMain
import be.dbproject.models.database.*
import be.dbproject.repositories.MuseumInformationRepository
import be.dbproject.repositories.Repository
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.ComboBox
import javafx.scene.control.Label
import javafx.stage.Modality
import javafx.stage.Stage
import java.time.LocalDate
import kotlin.reflect.KClass

class MainController {

    @FXML
    lateinit var labelVisitorAmount: Label

    @FXML
    lateinit var labelDonationRevenue: Label

    @FXML
    lateinit var comboxYear: ComboBox<Any>

    @FXML
    lateinit var comboxMuseumName: ComboBox<Any>

    @FXML
    private lateinit var btnItemDetails: Button

    @FXML
    private lateinit var btnLocationDetails: Button

    @FXML
    private lateinit var btnVisitorDetails: Button

    @FXML
    private lateinit var btnManageGenres: Button

    @FXML
    private lateinit var btnManagePlatforms: Button

    @FXML
    private lateinit var btnManagePublishers: Button

    @FXML
    private lateinit var btnManageItemType: Button

    @FXML
    private lateinit var btnVisitorLogDetails: Button

    @FXML
    private lateinit var btnReviewDetails: Button

    @FXML
    fun initialize() {
        btnItemDetails.setOnAction { openTableView(Item::class) }
        btnLocationDetails.setOnAction { openTableView(Location::class) }
        btnVisitorDetails.setOnAction { openTableView(Visitor::class) }
        btnManageGenres.setOnAction { openTableView(Genre::class) }
        btnManagePlatforms.setOnAction { openTableView(Platform::class) }
        btnManagePublishers.setOnAction { openTableView(Publisher::class) }
        btnManageItemType.setOnAction { openTableView(ItemType::class) }
        btnVisitorLogDetails.setOnAction { openTableView(VisitorLog::class) }
        btnReviewDetails.setOnAction { openReviewsView()}

        museumInsights()
    }

    private fun museumInsights() {
        val locations = Repository(Location::class).getEntitiesByColumn(
            listOf("locationType", "name"),
            listOf(LocationType::class, String::class),
            "Museum",
            Repository.QueryType.EQUAL
        )

        val years = buildList {

            for (i in 0..10){
                this.add( LocalDate.now().year - i )
            }
        }

        comboxMuseumName.apply {
            items.clear()
            items.addAll(locations)
            valueProperty().addListener { _, _, _ -> setInsightLabels() }
        }

        comboxYear.apply {
            items.clear()
            items.addAll(years)
            valueProperty().addListener { _, _, _ -> setInsightLabels() }
        }
    }

    private fun setInsightLabels() {
        val location = comboxMuseumName.selectionModel.selectedItem as Location?
        val year = comboxYear.selectionModel.selectedItem as Int?

        if (year == null || location == null)
            return

        labelDonationRevenue.text = MuseumInformationRepository().getMuseumDonationAmountForYear(year, location.locationName).toString()
        labelVisitorAmount.text = MuseumInformationRepository().getMuseumVisitorAmountForYear(year, location.locationName).toString()
    }

    private fun <T : DatabaseModel> openTableView(entityType: KClass<T>) {
        try {
            val stage = Stage()
            val loader = FXMLLoader(javaClass.classLoader.getResource("DataBaseModelTableView.fxml"))
            loader.setController(DatabaseModelTableView(entityType))
            val root: Parent = loader.load()

            val scene = Scene(root)

            stage.setScene(scene)
            stage.title = entityType.simpleName + " Table View"
            stage.initOwner(ProjectMain.rootStage)
            stage.initModality(Modality.WINDOW_MODAL)
            stage.show()

            stage.setOnCloseRequest {
                museumInsights()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    private fun openReviewsView(){
        try {
            val stage = Stage()
            val loader = FXMLLoader(javaClass.classLoader.getResource("ReviewsView.fxml"))
            val root: Parent = loader.load()

            val scene = Scene(root)

            stage.setScene(scene)
            stage.title = "Reviews"
            stage.initOwner(ProjectMain.rootStage)
            stage.initModality(Modality.WINDOW_MODAL)
            stage.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
