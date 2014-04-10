package jkind.examples;

import static org.junit.Assert.assertArrayEquals;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.List;

import jkind.Main;
import jkind.lustre.Node;
import jkind.lustre.Program;
import jkind.translation.Translate;

import org.antlr.v4.runtime.RecognitionException;
import org.junit.Test;

public class TranslateTest {
	@Test
	public void translateExamples() throws RecognitionException, IOException {
		File testingDirectory = new File("../testing");
		File translatedDirectory = new File(testingDirectory, "translated");
		Charset defaultCharset = Charset.defaultCharset();

		for (File file : testingDirectory.listFiles()) {
			if (file.isFile() && file.toString().endsWith(".lus")) {
				Program program = Main.parseLustre(file.toString());
				Node main = Translate.translate(program);
				String[] actual = main.toString().split("\r?\n");

				String filename = file.toPath().getFileName().toString();
				File translated = new File(translatedDirectory, filename);
				List<String> expected = Files.readAllLines(translated.toPath(), defaultCharset);
				
				assertArrayEquals(filename, expected.toArray(), actual);
			}
		}
	}
}
