import be.dbproject.models.Item
import be.dbproject.repositories.ItemRepository
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import javax.persistence.EntityManager
import javax.persistence.EntityManagerFactory
import javax.persistence.Persistence

class ItemRepositoryTest {
    private lateinit var factory: EntityManagerFactory
    private lateinit var entityManager: EntityManager
    private lateinit var itemRepository: ItemRepository

    @BeforeEach
    fun setUp() {
        factory = Persistence.createEntityManagerFactory("be.dbproject")
        entityManager = factory.createEntityManager()

        itemRepository = ItemRepository(entityManager)
        entityManager.transaction.begin() // Start a transaction for each test
    }

    @AfterEach
    fun tearDown() {
        if (entityManager.transaction.isActive) {
            entityManager.transaction.rollback() // Rollback the transaction to isolate tests
        }
        entityManager.close()
        factory.close()
    }

    @Test
    fun `given item when addItem then item ID should be assigned`() {
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
}