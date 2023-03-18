package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.pages.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.persistence.Note;
import com.udacity.jwdnd.course1.cloudstorage.util.BaseTest;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.springframework.boot.test.context.SpringBootTest;
import static com.udacity.jwdnd.course1.cloudstorage.util.UtilTests.getHomePage;

/**
 * Tests for Note Creation, Viewing, Editing, and Deletion.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class NoteTests extends BaseTest {

	private final static String NOTE_TITLE = "My Note";
	private final static String NOTE_DESCRIPTION = "This is my note.";
	public static final String NOTE = "note";


	//Write a test that deletes a note and verifies that the note is no longer displayed.
	@Test
	public void deleteNoteTest() {
		HomePage homePage = getHomePageAndCreateNote();
		while (true) {
			try {
				homePage.goToTab(driver, 1);
				homePage.deleteNote(driver);
			} catch (Exception e) {
				break;
			}
		}
		Assertions.assertTrue(homePage.areElementsAbsent(driver, "note", By.id("noteTitle"), By.id("noteDescription")));
	}

	@Test
	public void displayingExistingNoteTest() {
		HomePage homePage = getHomePageAndCreateNote();
		homePage.goToTab(driver, 1);
		Note note = (Note) homePage.popObject(NOTE);
		Assertions.assertAll(
				() -> Assertions.assertEquals(NOTE_TITLE, note.getNoteTitle()),
				() -> Assertions.assertEquals(NOTE_DESCRIPTION, note.getNoteDescription())
		);
		homePage.logout();
	}

	@Test
	public void modifyingExistingNoteTest() {
		HomePage homePage = getHomePageAndCreateNote();
		String modifiedNoteTitle = "My Modified Note";
		String modifiedNoteDescription = "This is my modified note.";
		homePage.goToTab(driver, 1);
		homePage.modifyNote(driver, NOTE_TITLE, NOTE_DESCRIPTION, modifiedNoteTitle, modifiedNoteDescription);
		homePage.goToTab(driver, 1);
		Note note = (Note) homePage.popObject(NOTE);
		Assertions.assertAll(
				() -> Assertions.assertEquals(modifiedNoteTitle, note.getNoteTitle()),
				() -> Assertions.assertEquals(modifiedNoteDescription, note.getNoteDescription())
		);
	}

	private HomePage getHomePageAndCreateNote() {
		HomePage homePage = getHomePage(driver, baseURL);
		homePage.goToTab(driver, 1);
		homePage.createNote();
		homePage.setNoteTitle(NOTE_TITLE, driver);
		homePage.setNoteDescription(NOTE_DESCRIPTION, driver);
		homePage.saveNoteChanges(driver);
		homePage.goToTab(driver, 3);
		return homePage;
	}
}
