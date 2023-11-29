package be.dbproject.repositories

import be.dbproject.models.Item
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
            genreId = 0,
            name = "Test Item",
            price = 19.99,
            description = "Test description",
            series = "Test Series",
            releaseDate = "2023-01-01"
        )

        itemRepository.addEntity(item)
        assertTrue(item.id > 0, "Item should have been assigned an ID.")
    }

    @Test
    fun `getAllEntities should return all entities`() {
        val item1 = Item(
            typeId = 1,
            platformId = 2,
            locationId = 3,
            publisherId = 4,
            genreId = 0,
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
            genreId = 0,
            name = "Test Item 2",
            price = 19.99,
            description = "Test description",
            series = "Test Series",
            releaseDate = "2023-01-01"
        )

        itemRepository.addEntity(item1)
        itemRepository.addEntity(item2)

        val items = itemRepository.getAllEntities()
        assertEquals(2, items.size, "Should return all items.")
        assertTrue(items.contains(item1), "Items list should contain item1.")
        assertTrue(items.contains(item2), "Items list should contain item2.")
    }

    @Test
    fun `getEntityById should return specific entity`() {
        val item = Item(
            typeId = 1,
            platformId = 2,
            locationId = 3,
            publisherId = 4,
            genreId = 0,
            name = "Test Item",
            price = 19.99,
            description = "Test description",
            series = "Test Series",
            releaseDate = "2023-01-01"
        )

        itemRepository.addEntity(item)
        val retrievedItem = itemRepository.getEntityById(item.id)
        assertNotNull(retrievedItem, "Should retrieve the item.")
        assertEquals(item, retrievedItem, "Retrieved item should match the added item.")
    }

    @Test
    fun `updateEntity should update entity in the database`() {
        val item = Item(
            typeId = 1,
            platformId = 2,
            locationId = 3,
            genreId = 0,
            publisherId = 4,
            name = "Test Item",
            price = 19.99,
            description = "Test description",
            series = "Test Series",
            releaseDate = "2023-01-01"
        )

        itemRepository.addEntity(item)

        with(item) {
            name = "Updated Item"
            price = 29.99
        }
        itemRepository.updateEntity(item)

        val updatedItem = itemRepository.getEntityById(item.id)

        assertNotNull(updatedItem, "Item with ID ${item.id} should exist in the database.")
        assertEquals("Updated Item", updatedItem!!.name, "Name should have been changed.")
        assertEquals(29.99, updatedItem.price, "Price should have been changed.")
    }

    @Test
    fun `deleteEntity should remove entity from the database`() {
        val item = Item(
            typeId = 1,
            platformId = 2,
            locationId = 3,
            publisherId = 4,
            genreId = 0,
            name = "Item to be deleted",
            price = 19.99,
            description = "Test description",
            series = "Test Series",
            releaseDate = "2023-01-01"
        )

        itemRepository.addEntity(item)
        val itemId = item.id

        // Ensure that the item exists in the database before deletion
        assertNotNull(itemRepository.getEntityById(itemId), "Item with ID $itemId should exist before deletion.")

        itemRepository.deleteEntity(itemId)

        // Ensure that the item has been removed from the database after deletion
        assertNull(itemRepository.getEntityById(itemId), "Item with ID $itemId should not exist after deletion.")
    }
}
