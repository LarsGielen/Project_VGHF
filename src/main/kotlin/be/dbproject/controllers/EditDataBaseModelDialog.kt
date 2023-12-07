package be.dbproject.controllers

import be.dbproject.models.DataBaseModels.*
import be.dbproject.repositories.Repository
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.layout.AnchorPane
import javafx.scene.layout.GridPane
import javafx.stage.Modality
import javafx.stage.Stage
import javafx.stage.Window
import org.controlsfx.control.CheckComboBox
import java.time.LocalDate
import kotlin.reflect.KClass
import kotlin.reflect.KMutableProperty
import kotlin.reflect.KParameter
import kotlin.reflect.full.*

class EditDataBaseModelDialog<T : DataBaseModel>(
    private val entityClass: KClass<T>,
    private val owner : Window,
    private val entity : T? = null,
    private val onExit : (newElement :  T) -> Unit
) {

    private val stage = Stage()

    private val grid: GridPane
    private val okBtn: Button
    private val cancelBtn: Button

    private inner class Option<V : DataBaseModel>(val entity: V?) {
        override fun toString(): String {
            if (entity == null) {
                return "None"
            }
            return entity.toString()
        }
    }

    init {
        val fxmlLoader = FXMLLoader(javaClass.classLoader.getResource("EditDataBaseModelDialog.fxml"))
        val dialogPane = fxmlLoader.load<AnchorPane>()


        stage.title = when (entity){
            null -> "Create a new ${entityClass.simpleName?.lowercase()}"
            else -> "Edit ${entityClass.simpleName?.lowercase()}: $entity"
        }
        stage.initModality(Modality.APPLICATION_MODAL)
        stage.initOwner(owner)
        stage.scene = Scene(dialogPane)

        grid = stage.scene.lookup("#grid") as GridPane
        okBtn = stage.scene.lookup("#okBtn") as Button
        cancelBtn = stage.scene.lookup("#cancelBtn") as Button

        okBtn.setOnAction { onOkeButton() }
        cancelBtn.setOnAction { stage.close() }

        // Source: https://stackoverflow.com/questions/51687098/kotlin-get-property-in-the-same-order-they-are-declared
        entityClass.primaryConstructor?.parameters?.forEach { parameter ->
            when (parameter.type.classifier) {
                String::class -> createTextField(parameter)
                Int::class -> createTextField(parameter)
                Double::class -> createTextField(parameter)
                LocalDate::class -> createDatePicker(parameter)

                // -- Custom Types
                Platform::class -> createComboBoxForType(Platform::class, parameter)
                ItemType::class -> createComboBoxForType(ItemType::class, parameter)
                Location::class -> createComboBoxForType(Location::class, parameter)
                Publisher::class -> createComboBoxForType(Publisher::class, parameter)
                LocationType::class -> createComboBoxForType(LocationType::class, parameter)
                Visitor::class -> createComboBoxForType(Visitor::class, parameter)
                Set::class -> {
                    when (parameter.type.arguments.first().type?.classifier) {
                        Genre::class -> createCheckComboBoxForType(Genre::class, parameter)
                        else -> throw IllegalArgumentException("Unsupported property type for set: $parameter")
                    }
                }
                else -> throw IllegalArgumentException("Unsupported property type: $parameter")
            }
        }

        stage.showAndWait()
    }

    private fun onOkeButton() {
        val updatedEntity = updateEntityFromInputFields(entity)
        updatedEntity?.let {
            onExit(it)
            stage.close()
        }
    }

    private fun createTextField(parameter: KParameter) {
        val textField = TextField()
        entity?.let {entity ->
            val property = entityClass.declaredMemberProperties.first { it.name == parameter.name }
            textField.text = property.call(entity).toString()
        }

        grid.add(Label(parameter.name), 0, parameter.index)
        grid.add(textField, 1, parameter.index, 2, 1)
    }

    private fun createDatePicker(parameter: KParameter) {
        val datePicker = DatePicker().apply {
            prefWidth = Double.MAX_VALUE
        }

        entity?.let {entity ->
            val property = entityClass.declaredMemberProperties.first { it.name == parameter.name }
            datePicker.value = property.call(entity) as LocalDate
        }

        grid.add(Label(parameter.name), 0, parameter.index)

        // DatePicker crashed het programma als de prefWidth = Double.MAX_VALUE terwijl de colspan > 1 is.
        // Maar dit enkel als het scherm niet groot genoeg is?
        grid.add(datePicker, 1, parameter.index, 1, 1)
    }

    private inline fun <reified V : DataBaseModel> createComboBoxForType(comboBoxClass: KClass<V>, parameter : KParameter) {
        val comboBox = ComboBox<Option<V>>().apply {
            prefWidth = Double.MAX_VALUE


            if (parameter.type.isMarkedNullable) {
                items.add(Option(null))
                value = Option(null)
            }

            Repository(comboBoxClass).getAllEntities().forEach {
                items.add(Option(it))
            }
        }

        entity?.let { entity ->
            val property = entityClass.declaredMemberProperties.first { it.name == parameter.name }
            val value = property.call(entity) as V?
            comboBox.value = Option(value)
        }

        grid.add(Label(parameter.name), 0, parameter.index)
        grid.add(comboBox, 1, parameter.index)
        val addButton = Button("+").apply {
            setOnAction {
                EditDataBaseModelDialog(V::class, owner) {newEntity ->
                    Repository(V::class).addEntity(newEntity)
                    comboBox.items.add(Option(newEntity))
                }
            }
        }
        grid.add(addButton, 2, parameter.index)
    }

    private inline fun <reified V : DataBaseModel> createCheckComboBoxForType(comboBoxClass: KClass<V>, parameter : KParameter) {
        val checkComboBox = CheckComboBox<V>().apply {
            prefWidth = Double.MAX_VALUE
            items.addAll(Repository(comboBoxClass).getAllEntities())
        }

        entity?.let { entity ->
            val property = entityClass.declaredMemberProperties.first { it.name == parameter.name }
            val values = property.call(entity) as Set<V>

            values.forEach {checkComboBox.checkModel.check(it) }
        }

        grid.add(Label(parameter.name), 0, parameter.index)
        grid.add(checkComboBox, 1, parameter.index)
        val addButton = Button("+").apply {
            setOnAction {
                EditDataBaseModelDialog(V::class, owner) {newEntity ->
                    Repository(V::class).addEntity(newEntity)
                    checkComboBox.items.add(newEntity)
                }
            }
        }
        grid.add(addButton, 2, parameter.index)
    }

    private fun updateEntityFromInputFields(entity: T?): T? {

        val updatedEntity = when(entity) {
            null -> entityClass.createInstance()
            else -> entity
        }

        var errorFlag : Boolean = false

        entityClass.primaryConstructor!!.parameters.forEachIndexed { index, parameter ->
            val inputField = grid.children.find { GridPane.getRowIndex(it) == index && GridPane.getColumnIndex(it) == 1 }

            val value : Any? = when (inputField!!::class) {
                TextField::class -> {
                    if ( (inputField as TextField).text.isNotBlank()) {
                        if (parameter.type.isSubtypeOf(Number::class.starProjectedType)) {
                            when (parameter.type.classifier) {
                                Int::class -> inputField.text.toIntOrNull()
                                else -> inputField.text.toDoubleOrNull()
                            }
                        }
                        else {
                            inputField.text
                        }
                    }
                    else {
                        null
                    }
                }
                DatePicker::class ->  (inputField as DatePicker).value
                ComboBox::class -> (inputField as ComboBox<Option<*>>).value?.entity
                CheckComboBox::class -> (inputField as CheckComboBox<*>).checkModel.checkedItems.toHashSet()

                else -> throw IllegalArgumentException("Unsupported property type: ${parameter.type}")
            }

            // Source: https://stackoverflow.com/questions/44304480/how-to-set-delegated-property-value-by-reflection-in-kotlin
            val property = entityClass.declaredMemberProperties.first { it.name == parameter.name }
            if (property is KMutableProperty<*>) {
                try {
                    property.setter.call(updatedEntity, value)
                    inputField.style = "-fx-control-inner-background: white;"
                }
                catch (e: Exception) {
                    inputField.style = "-fx-control-inner-background: lightcoral;"
                    if (property.returnType.isSubtypeOf(DataBaseModel::class.starProjectedType)) {
                        inputField.style = "-fx-background-color: lightcoral;"
                    }

                    errorFlag = true
                }
            }
        }

        if (errorFlag) {
            return null
        }
        return updatedEntity
    }
}