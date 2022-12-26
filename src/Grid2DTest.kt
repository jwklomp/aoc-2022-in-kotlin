import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

data class TestClass(
    var isFillable: Boolean = false,
    val name: String = ""
)


internal class Grid2DTest {

    lateinit var testGrid: Grid2D<Int>

    @BeforeEach
    fun setUp() {
        val list2D = (1..16).toList().chunked(4)
        testGrid = Grid2D(list2D)
    }

    @AfterEach
    fun tearDown() {
    }

    @Test
    internal fun `getCell gets the value of the cell`() {
        assertEquals(1, testGrid.getCell(0, 0).value)
        assertEquals(2, testGrid.getCell(1, 0).value)
        assertEquals(7, testGrid.getCell(2, 1).value)
        assertEquals(4, testGrid.getCell(3, 0).value)
    }

    @Test
    internal fun `getAllCell gets all the cells`() {
        val allCells = testGrid.getAllCells()
        assertEquals(16, allCells.size)
        assertEquals(1, allCells[0].value)
        assertEquals(0, allCells[0].x)
        assertEquals(0, allCells[0].y)
    }

    @Test
    fun getSurrounding() {
    }

    @Test
    fun getAdjacent() {
    }

    @Test
    fun testComplexObject() {
        val list2D = (1..16).toList().map { TestClass() }.chunked(4)
        val tgc = Grid2D(list2D)
        tgc.getCell(1, 1).value.isFillable = true
        println(tgc)
    }
}
