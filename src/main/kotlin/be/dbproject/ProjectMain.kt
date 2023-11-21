package be.dbproject

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage

class ProjectMain : Application() {

    fun getRootStage(): Stage? {return rootStage}

    @Throws(Exception::class)
    override fun start(stage: Stage) {

        rootStage = stage
        val loader = FXMLLoader(javaClass.classLoader.getResource("main.fxml"))
        val root: Parent = loader.load()

        val scene = Scene(root)

        stage.title = "VGHF Management System"
        stage.setScene(scene)
        stage.show()

        println("Test")
    }

    companion object {
        var rootStage: Stage? = null
            private set

        @JvmStatic
        fun main(args: Array<String>) {
            launch(ProjectMain::class.java)
        }
    }
}
