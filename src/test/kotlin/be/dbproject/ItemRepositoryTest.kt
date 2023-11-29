import be.dbproject.models.Item
import be.dbproject.repositories.ItemRepository
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import javax.persistence.EntityManager
import javax.persistence.Persistence

class ItemRepositoryTest {
    private lateinit var entityManager: EntityManager
    private lateinit var itemRepository: ItemRepository

    @BeforeEach
    fun setUp() {
        entityManager = Persistence.createEntityManagerFactory("be.dbproject").createEntityManager()
        itemRepository = ItemRepository()
    }

    @AfterEach
    fun tearDown() {
        entityManager.close()
    }

    @Test
    fun `addItem should add Item`() {
        val item = Item(
            typeId = 1,
            platformId = 2,
            locationId = 3,
            publisherId = 4,
            name = "Test Item",
            price = 19.99,
            description = "Test description",
            series = "Test Series",
            releaseDate = "2023-01-01"
        )

        itemRepository.addItem(item)
        assertTrue(item.id > 0, "Item should have been assigned an ID.")
    }
    @Test
    fun `getItemsByName should return items with matching name`() {
        val itemName = "Test Item"

        val item1 = Item(
            typeId = 1,
            platformId = 2,
            locationId = 3,
            publisherId = 4,
            name = itemName,
            price = 19.99,
            description = "Test description",
            series = "Test Series",
            releaseDate = "2023-01-01"
        )

        val item2 = Item(
            typeId = 2,
            platformId = 3,
            locationId = 4,
            publisherId = 5,
            name = "Another Item",
            price = 19.99,
            description = "Test description",
            series = "Test Series",
            releaseDate = "2023-01-01"
        )

        itemRepository.addItem(item1)
        itemRepository.addItem(item2)

        val items = itemRepository.getItemsByName(itemName)
        assertEquals(1, items.size, "Only one item with the specified name should be returned.")
        assertEquals(item1, items[0], "Returned item should match the one with the specified name.")
        }
    @Test
    fun `getAllItems should return all items`(){
        val item1 = Item(
            typeId = 1,
            platformId = 2,
            locationId = 3,
            publisherId = 4,
            name = "Test Item 1",
            price = 19.99,
            description = "Test description",
            series = "Test Series",
            releaseDate = "2023-01-01"
        )

    val item2 = Item(
        typeId = 2,
        platformId = 3,
        locationId = 4,
        publisherId = 5,
        name = "Test Item 2",
        price = 19.99,
        description = "Test description",
        series = "Test Series",
        releaseDate = "2023-01-01"
    )

    itemRepository.addItem(item1)
    itemRepository.addItem(item2)

    val items = itemRepository.getAllItems();
    assertEquals(item1, items[0])
    assertEquals(item2, items[1])

}
    @Test
    fun `given item when updating properties and updateItem then properties changed in DB`() {
        val item = Item(
            typeId = 1,
            platformId = 2,
            locationId = 3,
            publisherId = 4,
            name = "Test Item",
            price = 19.99,
            description = "Test description",
            series = "Test Series",
            releaseDate = "2023-01-01"
        )

        itemRepository.addItem(item)

        with(item) {
            name = "Updated Item"
            price = 29.99
        }
        itemRepository.updateItem(item)

        val updatedItem = itemRepository.getItemById(item.id)
        assertNotNull(updatedItem, "Item with ID ${item.id} should exist in the database.")
        assertEquals("Updated Item", updatedItem!!.name, "Name should have been changed.")
        assertEquals(29.99, updatedItem.price, "Price should have been changed.")
    }

    @Test
    fun `deleteItem should remove item from the database`() {
        val item = Item(
            typeId = 1,
            platformId = 2,
            locationId = 3,
            publisherId = 4,
            name = "Item to be deleted",
            price = 19.99,
            description = "Test description",
            series = "Test Series",
            releaseDate = "2023-01-01"
        )

        itemRepository.addItem(item)
        val itemId = item.id

        // Ensure that the item exists in the database before deletion
        assertNotNull(itemRepository.getItemById(itemId), "Item with ID $itemId should exist before deletion.")

        itemRepository.deleteItem(itemId)

        // Ensure that the item has been removed from the database after deletion
        assertNull(itemRepository.getItemById(itemId), "Item with ID $itemId should not exist after deletion.")
    }
}