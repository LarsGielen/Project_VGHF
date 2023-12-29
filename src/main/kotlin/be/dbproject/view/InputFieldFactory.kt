package be.dbproject.view

import be.dbproject.controllers.EditDatabaseModelDialog
import be.dbproject.models.database.*
import be.dbproject.repositories.Repository
import javafx.scene.Node
import javafx.scene.control.*
import javafx.scene.layout.HBox
import java.time.LocalDate
import kotlin.reflect.KClass

class InputFieldFactory () {

    @Throws(IllegalArgumentException::class)
    public fun <T : Any> createInputFieldForClass(
        kClass : KClass<T>,
        isNullable : Boolean = false,
        isCollection: Boolean = false,
        defaultValue : Any? = null,
        onValueChange: (() -> Unit)? = null
    ) : Node {

        if (isCollection) {
            return when (kClass) {
                Genre::class -> createCheckComboBox(Genre::class, defaultValue as Collection<Genre>?) {
                    onValueChange?.invoke()
                }

                else -> throw IllegalArgumentException("Unsupported class for collections: ${kClass.simpleName}")
            }
        }

        return when (kClass) {
            String::class -> createTextField(defaultValue.toString()) { onValueChange?.invoke() }
            Int::class -> createTextField(defaultValue.toString()) { onValueChange?.invoke() }
            Double::class -> createTextField(defaultValue.toString()) { onValueChange?.invoke() }
            LocalDate::class -> createDatePicker(defaultValue as LocalDate?) { onValueChange?.invoke() }

            // -- Custom Database Types
            Platform::class -> createComboBox(Platform::class, defaultValue as Platform?, isNullable) { onValueChange?.invoke() }
            ItemType::class -> createComboBox(ItemType::class, defaultValue as ItemType?, isNullable) { onValueChange?.invoke() }
            Location::class -> createComboBox(Location::class,  defaultValue as Location?, isNullable) { onValueChange?.invoke() }
            Publisher::class -> createComboBox(Publisher::class, defaultValue as Publisher?, isNullable) { onValueChange?.invoke() }
            LocationType::class -> createComboBox(LocationType::class, defaultValue as LocationType?, isNullable) { onValueChange?.invoke() }
            Visitor::class -> createComboBox(Visitor::class, defaultValue as Visitor?, isNullable) { onValueChange?.invoke() }
            Genre::class -> createComboBox(Genre::class, defaultValue as Genre?, isNullable) { onValueChange?.invoke() }

            else -> throw IllegalArgumentException("Unsupported class: ${kClass.simpleName}")
        }
    }

    private fun createTextField(defaultValue : String, onValueChange: (() -> Unit)?) : TextField{

        return TextField().apply {
            if (defaultValue != "null") text = defaultValue
            textProperty().addListener { _, _, _ -> onValueChange?.invoke() }
        }
    }

    private fun createDatePicker(defaultValue: LocalDate? = null, onValueChange: (() -> Unit)?) : DatePicker {

        return DatePicker().apply {
            prefWidth = Double.MAX_VALUE
            value = defaultValue

            valueProperty().addListener {_, _, _ -> onValueChange?.invoke()}
        }
    }

    private fun <T : DatabaseModel> createComboBox(
        kClass : KClass<T>,
        defaultValue: T? = null,
        isNullable: Boolean,
        onValueChange: (() -> Unit)?
    ) : ComboBoxWithButton<T> {

        val comboBoxWithButton = ComboBoxWithButton<T>{
            onValueChange?.invoke()
        }

        comboBoxWithButton.comboBox.apply {
            if (isNullable) {
                items.add(null)
                value = null
            }

            Repository(kClass).getAllEntities().forEach {
                items.add(it)
            }

            defaultValue?.let { value = it }
        }

        comboBoxWithButton.button.setOnAction {
            EditDatabaseModelDialog(kClass, comboBoxWithButton.scene.window) { newEntity ->
                Repository(kClass).addEntity(newEntity)
                comboBoxWithButton.comboBox.items.add(newEntity)
            }
        }

        return comboBoxWithButton
    }

    private fun <T : DatabaseModel> createCheckComboBox(
        kClass : KClass<T>,
        defaultValues: Collection<T>? = null,
        onValueChange: (() -> Unit)?
    ) : HBox {

        val checkComboBoxWithButton = CheckComboBoxWithButton<T> {
            onValueChange?.invoke()
        }

        checkComboBoxWithButton.checkComboBox.apply {
            Repository(kClass).getAllEntities().forEach {
                items.add(it)
            }

            defaultValues?.forEach {
                checkModel.check(it)
            }
        }

        checkComboBoxWithButton.button.setOnAction {
            EditDatabaseModelDialog(kClass, checkComboBoxWithButton.scene.window) { newEntity ->
                Repository(kClass).addEntity(newEntity)
                checkComboBoxWithButton.checkComboBox.items.add(newEntity)
            }
        }

        return checkComboBoxWithButton
    }
}