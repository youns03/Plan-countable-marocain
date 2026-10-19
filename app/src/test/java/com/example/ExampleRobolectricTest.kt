package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.PlanComptableRepository
import com.example.model.ElementLevel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

    @Test
    fun `read string from context`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("Plan Comptable", appName)
    }

    @Test
    fun `all 10 classes are populated`() {
        val classes = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 0)
        for (c in classes) {
            val list = PlanComptableRepository.getElementsByClass(c)
            assertTrue("Class $c should have elements", list.isNotEmpty())
            val classRoot = list.firstOrNull { it.level == ElementLevel.CLASSE }
            assertNotNull("Class $c root element should exist", classRoot)
        }
    }

    @Test
    fun `code lookup returns exact match`() {
        val client = PlanComptableRepository.getElementByCode("3421")
        assertNotNull(client)
        assertEquals("Clients", client?.officialFrenchTitle)

        val banque = PlanComptableRepository.getElementByCode("5141")
        assertNotNull(banque)

        val fournisseur = PlanComptableRepository.getElementByCode("4411")
        assertNotNull(fournisseur)
    }

    @Test
    fun `bilingual search works for French and Arabic`() {
        val searchFr = PlanComptableRepository.search("banque")
        assertTrue("Search for 'banque' should return results", searchFr.isNotEmpty())

        val searchAr = PlanComptableRepository.search("زبائن")
        assertTrue("Search for 'زبائن' should return results", searchAr.isNotEmpty())

        val searchCode = PlanComptableRepository.search("342")
        assertTrue("Search for '342' should return results", searchCode.isNotEmpty())
    }

    @Test
    fun `deconstruction tool decomposes code accurately`() {
        val result = PlanComptableRepository.deconstructCode("3421")
        assertEquals(4, result.steps.size)
        assertEquals("3", result.steps[0].digits)
        assertEquals("34", result.steps[1].digits)
        assertEquals("342", result.steps[2].digits)
        assertEquals("3421", result.steps[3].digits)
    }

    @Test
    fun `audit stats are complete for all ten classes`() {
        val stats = PlanComptableRepository.getAuditStats()
        assertEquals(10, stats.size)
        assertTrue(stats.all { it.totalCount > 0 })
    }
}
