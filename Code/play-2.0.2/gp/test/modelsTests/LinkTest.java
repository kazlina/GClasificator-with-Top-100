package modelsTests;
import org.junit.*;
import java.util.*;
import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;
import models.*;

public class LinkTest {
	@Test
	public void t() {
		String testStringMass = "<a href = http://www.google.com>google</a>" +
				"<a href =http://www.google.com >google</a>" +
				"<a href = http://www.google.com >google</a>" +
				"<a href = \"http://www.google.com\">google</a>" +
				"<a href =http://www.google.com>google</a>" +
				"<a href =\"http://www.google.com\">google</a>" +
				"<a href=\"http://www.google.com\">google</a>";
		//linkPreprocessor (testStringMass, 3);
	}
}
