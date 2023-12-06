package be.dbproject.controllers

import be.dbproject.models.*
import be.dbproject.repositories.Repository
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.layout.GridPane
import javafx.scene.layout.VBox
import javafx.stage.Modality
import javafx.stage.Stage
import javafx.stage.Window
import org.controlsfx.control.CheckComboBox
import java.time.LocalDate
import kotlin.reflect.KClass
import kotlin.reflect.KMutableProperty
import kotlin.reflect.KParameter
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.isSubtypeOf
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.full.starProjectedType

class EditDataBaseModelDialog<T : DataBaseModel>(private val entityClass: KClass<T>, owner : Window, private val entity : T? = null) {

    private val stage = Stage()

    private val grid: GridPane
    private val okBtn: Button
    private val cancelBtn: Button

    init {
        val fxmlLoader = FXMLLoader(javaClass.classLoader.getResource("EditDataBaseModelDialog.fxml"))
        val dialogPane = fxmlLoader.load<VBox>()

        stage.title = "Edit Item"
        stage.initModality(Modality.APPLICATION_MODAL)
        stage.initOwner(owner)
        stage.scene = Scene(dialogPane)

        grid = stage.scene.lookup("#grid") as GridPane
        okBtn = stage.scene.lookup("#okBtn") as Button
        cancelBtn = stage.scene.lookup("#cancelBtn") as Button

        okBtn.setOnAction { onOkeButton() }
        cancelBtn.setOnAction { onCancelButton() }

        // Source: https://stackoverflow.com/questions/51687098/kotlin-get-property-in-the-same-order-they-are-declared
        entityClass.primaryConstructor?.parameters?.forEachIndexed { index, parameter ->
            // Create label
            val label = Label(parameter.name)
            grid.add(label, 0, index)

            // Create input field
            val inputField = when (parameter.type.classifier) {
                String::class -> createTextField(parameter)
                Int::class -> createTextField(parameter)
                Float::class -> createTextField(parameter)
                LocalDate::class -> createDatePicker(parameter)

                // -- Custom Types
                Platform::class -> createComboBoxForType(Platform::class, parameter)
                ItemType::class -> createComboBoxForType(ItemType::class, parameter)
                Location::class -> createComboBoxForType(Location::class, parameter)
                Publisher::class -> createComboBoxForType(Publisher::class, parameter)
                Set::class -> {
                    when (parameter.type.arguments.first().type?.classifier) {
                        Genre::class -> createCheckComboBoxForType(Genre::class, parameter)
                        else -> throw IllegalArgumentException("Unsupported property type for set: $parameter")
                    }
                }

                else -> throw IllegalArgumentException("Unsupported property type: $parameter")
            }

            grid.add(inputField, 1, index)
        }

        stage.showAndWait()
    }

    private fun onOkeButton() {
        if (entity == null) {
            val newEntity = createEntityFromInputFields()
            Repository(entityClass).addEntity(newEntity)
        }
        else {
            val newEntity = updateEntityFromInputFields(entity)
            Repository(entityClass).updateEntity(newEntity)
        }

        stage.close()
    }

    private fun onCancelButton() {
        stage.close()
    }

    private fun createTextField(parameter: KParameter) : TextField {
        val textField = TextField()
        entity?.let {entity ->
            val property = entityClass.declaredMemberProperties.first { it.name == parameter.name }
            textField.text = property.call(entity).toString()
        }

        return textField
    }

    private fun createDatePicker(parameter: KParameter) : DatePicker {
        val datePicker = DatePicker()
        entity?.let {entity ->
            val property = entityClass.declaredMemberProperties.first { it.name == parameter.name }
            datePicker.value = property.call(entity) as LocalDate
        }

        return datePicker
    }

    private fun <V : DataBaseModel> createComboBoxForType(comboBoxClass: KClass<V>, parameter : KParameter) : ComboBox<V> {
        val comboBox = ComboBox<V>().apply {
            items.addAll(Repository(comboBoxClass).getAllEntities())
        }

        entity?.let { entity ->
            val property = entityClass.declaredMemberProperties.first { it.name == parameter.name }
            comboBox.value = property.call(entity) as V?
        }

        return comboBox
    }

    private fun <V : DataBaseModel> createCheckComboBoxForType(comboBoxClass: KClass<V>, parameter : KParameter) : CheckComboBox<V> {
        val comboBox = CheckComboBox<V>().apply {
            items.addAll(Repository(comboBoxClass).getAllEntities())
        }

        entity?.let { entity ->
            val property = entityClass.declaredMemberProperties.first { it.name == parameter.name }
            val values = property.call(entity) as Set<V>

            values.forEach {comboBox.checkModel.check(it) }
        }

        return comboBox
    }

    private fun createEntityFromInputFields(): T {
        val constructor = entityClass.primaryConstructor
        val parameters = constructor!!.parameters

        val parameterValues = parameters.mapIndexed { index, parameter ->
            val inputField = grid.children.find { GridPane.getRowIndex(it) == index && GridPane.getColumnIndex(it) == 1 }

            val value = when (inputField!!::class) {
                TextField::class -> {
                    if (parameter.type.isSubtypeOf(Number::class.starProjectedType)) {
                        (inputField as TextField).text.toFloat()
                    }
                    else {
                        (inputField as TextField).text
                    }
                }
                DatePicker::class -> (inputField as DatePicker).value
                ComboBox::class -> (inputField as ComboBox<*>).value
                CheckComboBox::class -> (inputField as CheckComboBox<*>).checkModel.checkedItems.toHashSet()

                else -> throw IllegalArgumentException("Unsupported property type: ${parameter.type}")
            }

            parameter to value
        }.toMap()

        return constructor.callBy(parameterValues)
    }

    private fun updateEntityFromInputFields(entity: T): T {

        entityClass.primaryConstructor!!.parameters.forEachIndexed { index, parameter ->
            val inputField = grid.children.find { GridPane.getRowIndex(it) == index && GridPane.getColumnIndex(it) == 1 }

            val value = when (inputField!!::class) {
                TextField::class -> {
                    if (parameter.type.isSubtypeOf(Number::class.starProjectedType)) {
                        (inputField as TextField).text.toFloat()
                    }
                    else {
                        (inputField as TextField).text
                    }
                }
                DatePicker::class -> (inputField as DatePicker).value
                ComboBox::class -> (inputField as ComboBox<*>).value
                CheckComboBox::class -> (inputField as CheckComboBox<*>).checkModel.checkedItems.toHashSet()

                else -> throw IllegalArgumentException("Unsupported property type: ${parameter.type}")
            }

            // Source: https://stackoverflow.com/questions/44304480/how-to-set-delegated-property-value-by-reflection-in-kotlin
            val property = entityClass.declaredMemberProperties.first { it.name == parameter.name }
            if (property is KMutableProperty<*>) {
                property.setter.call(entity, value)
            }
        }

        return entity
    }
}