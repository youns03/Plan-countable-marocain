package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.PlanComptableRepository
import com.example.data.PreferencesRepository
import com.example.model.ElementLevel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class PlanComptableIntegrityTest {

    @Test
    fun `test all elements have globally unique UIDs`() {
        val allElements = PlanComptableRepository.allElements
        assertTrue("Total elements should be greater than 1100", allElements.size > 1100)

        val uids = allElements.map { it.uid }
        val uniqueUids = uids.toSet()

        assertEquals("Every element must have a unique UID (classNumber:code)", allElements.size, uniqueUids.size)
    }

    @Test
    fun `test all non-null parentUIDs exist in repository`() {
        val allElements = PlanComptableRepository.allElements
        for (element in allElements) {
            val parentUid = element.parentUid
            if (parentUid != null) {
                val parent = PlanComptableRepository.getByUid(parentUid)
                assertNotNull("Parent UID $parentUid for element ${element.uid} must exist in the repository", parent)
            }
        }
    }

    @Test
    fun `test account 1111 breadcrumb hierarchy starts at Class 1 not Class 0`() {
        val elem1111 = PlanComptableRepository.getByUid("1:1111")
        assertNotNull("1:1111 must exist", elem1111)

        val breadcrumbs = PlanComptableRepository.getBreadcrumbs("1:1111")
        assertEquals(4, breadcrumbs.size)

        assertEquals("1:1", breadcrumbs[0].uid)
        assertEquals(1, breadcrumbs[0].classNumber)
        assertEquals("COMPTES DE FINANCEMENT PERMANENT", breadcrumbs[0].officialFrenchTitle)

        assertEquals("1:11", breadcrumbs[1].uid)
        assertEquals(1, breadcrumbs[1].classNumber)
        assertEquals("CAPITAUX PROPRES", breadcrumbs[1].officialFrenchTitle)

        assertEquals("1:111", breadcrumbs[2].uid)
        assertEquals(1, breadcrumbs[2].classNumber)

        assertEquals("1:1111", breadcrumbs[3].uid)
        assertEquals(1, breadcrumbs[3].classNumber)
    }

    @Test
    fun `test Class 0 elements stay within Class 0 hierarchy`() {
        val elem011 = PlanComptableRepository.getByUid("0:11")
        assertNotNull("0:11 must exist in Class 0", elem011)
        assertEquals(0, elem011?.classNumber)

        val breadcrumbs = PlanComptableRepository.getBreadcrumbs("0:11")
        assertTrue("Class 0 breadcrumbs must only contain classNumber == 0", breadcrumbs.all { it.classNumber == 0 })
    }

    @Test
    fun `test search for duplicate code 11 returns elements from both Class 1 and Class 0 without crash`() {
        val results = PlanComptableRepository.search("11")
        val classes = results.map { it.classNumber }.toSet()

        assertTrue("Search for '11' must contain elements from Class 1", classes.contains(1))
        assertTrue("Search for '11' must contain elements from Class 0", classes.contains(0))

        val uids = results.map { it.uid }
        assertEquals(uids.size, uids.toSet().size)
    }

    @Test
    fun `test eastern arabic numerals conversion`() {
        val converted = PlanComptableRepository.convertArabicIndicNumerals("٣٤٢١")
        assertEquals("3421", converted)

        val searchEastern = PlanComptableRepository.search("٣٤٢١")
        assertTrue("Search with Eastern Arabic digits '٣٤٢١' should return clients 3421", searchEastern.any { it.code == "3421" })
    }

    @Test
    fun `test special rules are properly scoped`() {
        val deconstruct99 = PlanComptableRepository.deconstructCode("99")
        assertNull("99 in Class 9 should not trigger provision rule", deconstruct99.specialRuleNoteAr)

        val deconstruct88 = PlanComptableRepository.deconstructCode("88")
        assertNull("88 should not trigger amortisation rule", deconstruct88.specialRuleNoteAr)

        val deconstruct2834 = PlanComptableRepository.deconstructCode("2834")
        assertNotNull("2834 in Class 2 must trigger amortisation rule", deconstruct2834.specialRuleNoteAr)
        assertTrue(deconstruct2834.specialRuleNoteAr!!.contains("الاستهلاك"))

        val deconstruct3942 = PlanComptableRepository.deconstructCode("3942")
        assertNotNull("3942 in Class 3 must trigger provision rule", deconstruct3942.specialRuleNoteAr)
        assertTrue(deconstruct3942.specialRuleNoteAr!!.contains("مؤونة"))
    }

    @Test
    fun `test favorites isolation between Class 1 and Class 0 for code 11`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val prefs = PreferencesRepository.getInstance(context)

        val uidClass1 = "1:11"
        val uidClass0 = "0:11"

        if (prefs.isFavorite(uidClass1)) prefs.toggleFavorite(uidClass1)
        if (prefs.isFavorite(uidClass0)) prefs.toggleFavorite(uidClass0)

        prefs.toggleFavorite(uidClass1)

        assertTrue("1:11 must be favorite", prefs.isFavorite(uidClass1))
        assertFalse("0:11 must NOT be favorite when only 1:11 was favorited", prefs.isFavorite(uidClass0))

        prefs.toggleFavorite(uidClass1)
    }

    @Test
    fun `test deconstruct 1111 resolves to Capitaux Propres not Class 0`() {
        val result = PlanComptableRepository.deconstructCode("1111")
        assertEquals(4, result.steps.size)

        val stepRubrique = result.steps[1]
        assertEquals("11", stepRubrique.digits)
        assertNotNull("Step 11 must be resolved", stepRubrique.element)
        assertEquals(1, stepRubrique.element?.classNumber)
        assertEquals("CAPITAUX PROPRES", stepRubrique.element?.officialFrenchTitle)
    }
}
