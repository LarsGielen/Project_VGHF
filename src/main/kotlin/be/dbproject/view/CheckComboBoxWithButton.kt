package be.dbproject.view

import javafx.collections.ListChangeListener
import javafx.scene.control.Button
import javafx.scene.layout.HBox
import javafx.util.StringConverter
import org.controlsfx.control.CheckComboBox

class CheckComboBoxWithButton <T>(onValueChange: (() -> Unit)? = null) : HBox() {

    val checkComboBox : CheckComboBox<T>
        get() = field

    val button : Button
        get() = field

    init {
        prefWidth = Double.MAX_VALUE

        checkComboBox = CheckComboBox<T>().apply {
            converter = object : StringConverter<T>() {
                override fun toString(entity: T?): String {
                    return entity?.let { entity.toString() } ?: "None"
                }

                override fun fromString(string: String?): T? {
                    return null
                }
            }

            prefWidthProperty().bind(widthProperty())
        }

        val listener = ListChangeListener<T> {
            onValueChange?.invoke()
        }
        checkComboBox.checkModel.checkedItems.addListener(listener)

        button = Button("+").apply {
            minWidth = 25.0
            maxWidth = 25.0
        }

        children.add(checkComboBox)
        children.add(button)
    }
}