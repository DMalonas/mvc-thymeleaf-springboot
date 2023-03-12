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

/**
 * Tests for Note Creation, Viewing, Editing, and Deletion.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class NoteTests   {

	private static final String LOCALHOST = "http://localhost:";
	private static final String LOGIN = "/login";
	private static final String SIGNUP= "/signup";
	private static final String HOME = "/home";

	private static final String NOTE = "/note";

	private static final String USERNAME = "jSmith";
	private static final String PASSWORD = "1234";
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

	/**
	 * Test that edits an existing note and verifies that the changes are displayed.
	 */
//	@Test
//	public void testDelete() {
//		String noteTitle = "My Note";
//		String noteDescription = "This is my note.";
//		HomePage homePage = signUpAndLogin();
//		createNote(noteTitle, noteDescription, homePage);
//		homePage.navToNotesTab();
//		homePage = new HomePage(driver);
//		Assertions.assertFalse(homePage.noNotes(driver));
//		deleteNote(homePage);
//		Assertions.assertTrue(homePage.noNotes(driver));
//	}

	private void deleteNote(HomePage homePage) {
		homePage.deleteNote(driver);
	}


	//Write a test that creates a note, and verifies it is displayed.
	@Test
	public void testCreateAndDisplay() {
		String noteTitle = "My Note";
		String noteDescription = "This is my note.";
		driver.get(LOCALHOST + this.port + "/signup");
		Assertions.assertEquals(baseURL + SIGNUP, driver.getCurrentUrl());
		SignUpPage signupPage = new SignUpPage(driver);
		signupPage.signupAndGoToLogin(USERNAME, PASSWORD, USERNAME, PASSWORD);
		driver.get(baseURL + LOGIN);
		Assertions.assertEquals(baseURL + LOGIN, driver.getCurrentUrl());
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(USERNAME, PASSWORD);
		createNote(noteTitle, noteDescription);
		HomePage homePage = new HomePage(driver);
		homePage.goToNavNotesTab(driver);
		Note note = homePage.getFirstNote();
		Assertions.assertEquals(noteTitle, note.getNoteTitle());
		Assertions.assertEquals(noteDescription, note.getNoteDescription());
		homePage.logout();
	}

	//Write a test that edits an existing note and verifies that the changes are displayed.
	@Test
	public void testModify() {
		String noteTitle = "My Note";
		String noteDescription = "This is my note.";
		driver.get(LOCALHOST + this.port + "/signup");
		SignUpPage signupPage = new SignUpPage(driver);
		signupPage.signupAndGoToLogin(USERNAME, PASSWORD, USERNAME, PASSWORD);
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(USERNAME, PASSWORD);
		createNote(noteTitle, noteDescription);
		HomePage homePage = new HomePage(driver);
		String modifiedNoteTitle = "My Modified Note";
		String modifiedNoteDescription = "This is my modified note.";
		homePage.editNote(driver, noteTitle, noteDescription);
		homePage.modifyNote(modifiedNoteTitle, modifiedNoteDescription);
		homePage.saveNoteChanges(driver);
		homePage.goToNavNotesTab(driver);
		Note note = homePage.getFirstNote();
		Assertions.assertEquals(modifiedNoteTitle, note.getNoteTitle());
		Assertions.assertEquals(modifiedNoteDescription, note.getNoteDescription());
	}


	//Write a test that deletes a note and verifies that the note is no longer displayed.
	@Test
	public void testDelete() {
		driver.get(LOCALHOST + this.port + "/signup");
		SignUpPage signupPage = new SignUpPage(driver);
		signupPage.signupAndGoToLogin(USERNAME, PASSWORD, USERNAME + "1", PASSWORD+ "1");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(USERNAME+ "1", PASSWORD+ "1");

		String noteTitle = "My Note";
		String noteDescription = "This is my note.";
		HomePage homePage = new HomePage(driver);

		createNote(noteTitle, noteDescription);
		homePage.goToNavNotesTab(driver);
		Note note = homePage.getFirstNote();
		Assertions.assertFalse(homePage.noNotes(driver));
		deleteNote(homePage);
		Assertions.assertTrue(homePage.noNotes(driver));
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

	private void getHomePage() {
		driver.get(LOCALHOST + port + HOME);
	}
}
