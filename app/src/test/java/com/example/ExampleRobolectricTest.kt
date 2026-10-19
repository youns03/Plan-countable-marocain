package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.PlanComptableRepository
import com.example.data.PreferencesRepository
import com.example.model.ElementLevel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
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
    fun `all uids are unique across all elements`() {
        val all = PlanComptableRepository.allElements
        val uids = all.map { it.uid }
        val duplicates = uids.groupingBy { it }.eachCount().filter { it.value > 1 }
        assertTrue("Duplicate UIDs found: $duplicates", duplicates.isEmpty())
        assertTrue("Expected over 1000 accounts, found ${all.size}", all.size > 1000)
    }

    @Test
    fun `account 11 in Class 1 and account 11 in Class 0 do not collide`() {
        val class1_11 = PlanComptableRepository.getByCodeInClass("11", 1)
        val class0_11 = PlanComptableRepository.getByCodeInClass("11", 0)

        assertNotNull("Class 1 '11' must exist", class1_11)
        assertNotNull("Class 0 '11' must exist", class0_11)

        assertEquals("1:11", class1_11?.uid)
        assertEquals("0:11", class0_11?.uid)
        assertNotEquals(class1_11?.uid, class0_11?.uid)

        assertTrue(class1_11?.officialFrenchTitle?.contains("CAPITAUX PROPRES") == true)
        assertTrue(class0_11?.officialFrenchTitle?.contains("Réouverture") == true)
    }

    @Test
    fun `breadcrumbs for 1111 stay strictly in Class 1`() {
        val elem1111 = PlanComptableRepository.getByCodeInClass("1111", 1)
        assertNotNull(elem1111)

        val crumbs = PlanComptableRepository.getBreadcrumbs(elem1111!!.uid)
        assertEquals(4, crumbs.size)
        assertEquals("1", crumbs[0].code)
        assertEquals("11", crumbs[1].code)
        assertEquals("111", crumbs[2].code)
        assertEquals("1111", crumbs[3].code)

        assertTrue("All ancestors must belong to Class 1", crumbs.all { it.classNumber == 1 })
    }

    @Test
    fun `parent hierarchy integrity across all elements`() {
        val all = PlanComptableRepository.allElements
        for (elem in all) {
            val parentUid = elem.parentUid
            if (parentUid != null) {
                val parentElem = PlanComptableRepository.getElementByUid(parentUid)
                assertNotNull("Parent $parentUid of element ${elem.uid} must exist", parentElem)
            }
        }
    }

    @Test
    fun `search with eastern and western digits`() {
        val resultsWestern = PlanComptableRepository.search("3421")
        val resultsEastern = PlanComptableRepository.search("٣٤٢١")

        assertTrue("Western search must find results", resultsWestern.isNotEmpty())
        assertEquals(resultsWestern.first().uid, resultsEastern.firstOrNull()?.uid)
        assertEquals("3421", resultsWestern.first().code)
    }

    @Test
    fun `searching 11 with All filter returns both classes without crashing`() {
        val results = PlanComptableRepository.search("11")
        val class1Result = results.firstOrNull { it.uid == "1:11" }
        val class0Result = results.firstOrNull { it.uid == "0:11" }

        assertNotNull("Must contain Class 1 '11'", class1Result)
        assertNotNull("Must contain Class 0 '11'", class0Result)
    }

    @Test
    fun `deconstructing 99 does not produce false provision rule`() {
        val result = PlanComptableRepository.deconstructCode("99")
        assertNull("Class 9 '99' should not trigger provision special rule", result.specialRuleNoteAr)

        val resultClass2 = PlanComptableRepository.deconstructCode("2834")
        assertNotNull("Class 2 '28' should trigger amortisation rule", resultClass2.specialRuleNoteAr)
        assertTrue(resultClass2.specialRuleNoteAr?.contains("استهلاك") == true)
    }

    @Test
    fun `favorites do not cross-contaminate between classes`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val prefs = PreferencesRepository.getInstance(context)

        val uidClass1 = "1:11"
        val uidClass0 = "0:11"

        if (prefs.isFavorite(uidClass1)) prefs.toggleFavorite(uidClass1)
        if (prefs.isFavorite(uidClass0)) prefs.toggleFavorite(uidClass0)

        prefs.toggleFavorite(uidClass1)
        assertTrue(prefs.isFavorite(uidClass1))
        assertFalse(prefs.isFavorite(uidClass0))

        // clean up
        prefs.toggleFavorite(uidClass1)
    }

    @Test
    fun `audit stats are complete and computed dynamically`() {
        val stats = PlanComptableRepository.getAuditStats()
        assertEquals(10, stats.size)
        val total = stats.sumOf { it.totalCount }
        assertTrue("Total elements must match size of allElements", total == PlanComptableRepository.allElements.size)
    }
}
