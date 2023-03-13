package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.pages.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.pages.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.pages.SignUpPage;
import com.udacity.jwdnd.course1.cloudstorage.persistence.Note;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import static com.udacity.jwdnd.course1.cloudstorage.util.UtilTests.*;
import static com.udacity.jwdnd.course1.cloudstorage.util.UtilTests.getHomePage;

/**
 * Tests for Note Creation, Viewing, Editing, and Deletion.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class NoteTests   {

	private final static String NOTE_TITLE = "My Note";
	private final static String NOTE_DESCRIPTION = "This is my note.";

	@LocalServerPort
	private int port;
	private static WebDriver driver;
	public String baseURL;
	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
	}

	@AfterAll
	public static void afterAll() {
		driver.quit();
		driver = null;
	}

	@BeforeEach
	public void beforeEach() {
		baseURL = LOCALHOST + port;
	}


	private void deleteNote(HomePage homePage) throws Exception {
		homePage.deleteNote(driver);
	}

	//Write a test that deletes a note and verifies that the note is no longer displayed.
	@Test
	public void testDelete() {
		HomePage homePage = goToHomePageAndCreateNote();
		createNote(NOTE_TITLE, NOTE_DESCRIPTION);
		Assertions.assertFalse(homePage.noNotes(driver));
		while (true) {
			try {
				homePage.goToNavNotesTab(driver);
				deleteNote(homePage);
			} catch (Exception e) {
				System.out.println("s0s0s");
				break;
			}
		}

		Assertions.assertTrue(homePage.noNotes(driver));
	}

	//Write a test that creates a note, and verifies it is displayed.
	@Test
	public void testCreateAndDisplay() {
		HomePage homePage = goToHomePageAndCreateNote();
		homePage.goToNavNotesTab(driver);
		Note note = homePage.getFirstNote();
		Assertions.assertEquals(NOTE_TITLE, note.getNoteTitle());
		Assertions.assertEquals(NOTE_DESCRIPTION, note.getNoteDescription());
		homePage.logout();
	}

	//Write a test that edits an existing note and verifies that the changes are displayed.
	@Test
	public void testModify() {
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

}
