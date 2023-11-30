package be.dbproject.controllers

import be.dbproject.models.Visitor
import javafx.beans.property.ReadOnlyObjectWrapper

import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.TableView
import javafx.scene.control.TableColumn

class VisitorTableController {

    @FXML
    private lateinit var tblVisitors: TableView<Visitor>

    @FXML
    private lateinit var btnVisitor1: Button

    @FXML
    private lateinit var btnVisitor2: Button

    @FXML
    private lateinit var btnVisitor3: Button

    @FXML
    fun initialize() {
        btnVisitor1.setOnAction { handleVisitorButton1() }
        btnVisitor2.setOnAction { handleVisitorButton2() }
        btnVisitor3.setOnAction { handleVisitorButton3() }

        initTable()
    }
    
    private fun initTable() {
        val colId = TableColumn<Visitor, Int>("ID")
        colId.setCellValueFactory { f -> ReadOnlyObjectWrapper(f.value.id) }

        tblVisitors.columns.addAll(colId)
        for (i in 0..<10) {
            tblVisitors.items.add(Visitor(i,"firstNameTest", "lastNameTest", "phoneTest", "emailTest", i))
        }
    }

    @FXML
    fun handleVisitorButton1() {
        println("Visitor Button 1 clicked")
    }

    @FXML
    fun handleVisitorButton2() {
        println("Visitor Button 2 clicked")
    }

    @FXML
    fun handleVisitorButton3() {
        println("Visitor Button 3 clicked")
    }
}
