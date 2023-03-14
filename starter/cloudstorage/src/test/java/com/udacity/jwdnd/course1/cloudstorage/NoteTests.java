package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.pages.HomePage;

import com.udacity.jwdnd.course1.cloudstorage.persistence.Note;
import com.udacity.jwdnd.course1.cloudstorage.util.BaseTest;
import org.junit.jupiter.api.*;
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



	//Write a test that deletes a note and verifies that the note is no longer displayed.
	@Test
	public void deleteNoteTest() {
		HomePage homePage = goToHomePageAndCreateNote();
		createNote(NOTE_TITLE, NOTE_DESCRIPTION);
		while (true) {
			try {
				homePage.goToNavNotesTab(driver);
				deleteNote(homePage);
			} catch (Exception e) {
				break;
			}
		}

		Assertions.assertTrue(homePage.noNotes(driver));
	}

	//Write a test that creates a note, and verifies it is displayed.
	@Test
	public void displayingExistingNoteTest() {
		HomePage homePage = goToHomePageAndCreateNote();
		homePage.goToNavNotesTab(driver);
		Note note = homePage.getFirstNote();
		Assertions.assertEquals(NOTE_TITLE, note.getNoteTitle());
		Assertions.assertEquals(NOTE_DESCRIPTION, note.getNoteDescription());
		homePage.logout();
	}

	//Write a test that edits an existing note and verifies that the changes are displayed.
	@Test
	public void modifyingExistingNoteTest() {
		HomePage homePage = goToHomePageAndCreateNote();
		String modifiedNoteTitle = "My Modified Note";
		String modifiedNoteDescription = "This is my modified note.";
		homePage.editNote(driver, NOTE_TITLE, NOTE_DESCRIPTION);
		homePage.modifyNote(modifiedNoteTitle, modifiedNoteDescription);
		homePage.saveNoteChanges(driver);
		homePage.goToNavNotesTab(driver);
		Note note = homePage.getFirstNote();
		Assertions.assertEquals(modifiedNoteTitle, note.getNoteTitle());
		Assertions.assertEquals(modifiedNoteDescription, note.getNoteDescription());
	}




	private HomePage goToHomePageAndCreateNote() {
		HomePage homePage = getHomePage(driver, baseURL);
		createNote(NOTE_TITLE, NOTE_DESCRIPTION);
		return homePage;
	}

	private void createNote(String noteTitle, String noteDescription) {
		HomePage homePage = new HomePage(driver);
		homePage.goToNavNotesTab(driver);
		homePage.createNote();
		homePage.setNoteTitle(noteTitle, driver);
		homePage.setNoteDescription(noteDescription, driver);
		homePage.saveNoteChanges(driver);
		homePage.goToNavNotesTab(driver);
	}

	private void deleteNote(HomePage homePage) throws Exception {
		homePage.deleteNote(driver);
	}
}
