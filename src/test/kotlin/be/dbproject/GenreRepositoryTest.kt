package be.dbproject.repositories

import be.dbproject.models.Genre
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import javax.persistence.EntityManager
import javax.persistence.Persistence

class GenreRepositoryTest {
    private lateinit var entityManager: EntityManager
    private lateinit var genreRepository: GenreRepository

    @BeforeEach
    fun setUp() {
        entityManager = Persistence.createEntityManagerFactory("be.dbproject").createEntityManager()
        genreRepository = GenreRepository()
    }

    @AfterEach
    fun tearDown() {
        entityManager.close()
    }

    @Test
    fun `addGenre should add Genre`() {
        val genre = Genre("test")

        genreRepository.addEntity(genre)
        assertTrue(genre.id > 0 , "Genre should have been assigned an ID.")
    }

    @Test
    fun `getAllGenres should return all genres`() {
        val genre1 = Genre(1, name = "Test Genre 1")
        val genre2 = Genre(2, name = "Test Genre 2")

        genreRepository.addEntity(genre1)
        genreRepository.addEntity(genre2)

        val genres = genreRepository.getAllEntities()
        assertEquals(2, genres.size, "Should return all genres.")
        assertTrue(genres.contains(genre1), "Genres list should contain genre1.")
        assertTrue(genres.contains(genre2), "Genres list should contain genre2.")
    }

    @Test
    fun `getGenreByName should return genres with matching name`() {
        val genreName = "Test Genre"

        val genre1 = Genre(3, name = genreName)
        val genre2 = Genre(4, name = "Another Genre")

        genreRepository.addEntity(genre1)
        genreRepository.addEntity(genre2)

        val genres = genreRepository.getGenreByName(genreName)
        assertEquals(1, genres.size, "Only one genre with the specified name should be returned.")
        assertEquals(genre1, genres[0], "Returned genre should match the one with the specified name.")
    }

    @Test
    fun `given genre when updating properties and updateGenre then properties changed in DB`() {
        val genre = Genre(5, name = "Test Genre")

        genreRepository.addEntity(genre)

        with(genre) {
            name = "Updated Genre"
        }
        genreRepository.updateEntity(genre)

        val updatedGenre = genreRepository.getEntityById(genre.id)

        assertNotNull(updatedGenre, "Genre with ID ${genre.id} should exist in the database.")
        assertEquals("Updated Genre", updatedGenre!!.name, "Name should have been changed.")
    }

    @Test
    fun `deleteGenre should remove genre from the database`() {
        val genre = Genre(6, name = "Genre to be deleted")

        genreRepository.addEntity(genre)
        val genreId = genre.id

        // Ensure that the genre exists in the database before deletion
        assertNotNull(genreRepository.getEntityById(genreId), "Genre with ID $genreId should exist before deletion.")

        genreRepository.deleteEntity(genreId)

        // Ensure that the genre has been removed from the database after deletion
        assertNull(genreRepository.getEntityById(genreId), "Genre with ID $genreId should not exist after deletion.")
    }
}
