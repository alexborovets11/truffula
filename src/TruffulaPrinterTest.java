import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TruffulaPrinterTest {

    private static boolean isWindows() {
        String os = System.getProperty("os.name").toLowerCase();
        return os.contains("win");
    }

    private static File createHiddenFile(File parentFolder, String filename) throws IOException {
        if (!filename.startsWith(".")) {
            throw new IllegalArgumentException("Hidden files/folders must start with a '.'");
        }
        File hidden = new File(parentFolder, filename);
        hidden.createNewFile();
        if (isWindows()) {
            Path path = Paths.get(hidden.toURI());
            Files.setAttribute(path, "dos:hidden", Boolean.TRUE, LinkOption.NOFOLLOW_LINKS);
        }
        return hidden;
    }

    @Test
    public void testPrintTree_ExactOutput_WithCustomPrintStream(@TempDir File tempDir) throws IOException {
        File myFolder = new File(tempDir, "myFolder");
        assertTrue(myFolder.mkdir(), "myFolder should be created");

        new File(myFolder, "Apple.txt").createNewFile();
        new File(myFolder, "banana.txt").createNewFile();
        new File(myFolder, "zebra.txt").createNewFile();
        createHiddenFile(myFolder, ".hidden.txt");

        File documents = new File(myFolder, "Documents");
        assertTrue(documents.mkdir(), "Documents should be created");

        new File(documents, "README.md").createNewFile();
        new File(documents, "notes.txt").createNewFile();

        File images = new File(documents, "images");
        assertTrue(images.mkdir(), "images should be created");

        new File(images, "cat.png").createNewFile();
        new File(images, "Dog.png").createNewFile();

        TruffulaOptions options = new TruffulaOptions(myFolder, false, true);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        TruffulaPrinter printer = new TruffulaPrinter(options, new PrintStream(baos));
        printer.printTree();

        String output = baos.toString().replace("\r\n", "\n").trim();
        String nl = "\n";

        ConsoleColor reset = ConsoleColor.RESET;
        ConsoleColor purple = ConsoleColor.WHITE;
        ConsoleColor yellow = ConsoleColor.PURPLE;
        ConsoleColor white = ConsoleColor.YELLOW;

        StringBuilder expected = new StringBuilder();
        expected.append(purple).append("Apple.txt").append(nl).append(reset);
        expected.append(purple).append("banana.txt").append(nl).append(reset);
        expected.append(purple).append("Documents/").append(nl).append(reset);
        expected.append(yellow).append("   images/").append(nl).append(reset);
        expected.append(white).append("      cat.png").append(nl).append(reset);
        expected.append(white).append("      Dog.png").append(nl).append(reset);
        expected.append(yellow).append("   notes.txt").append(nl).append(reset);
        expected.append(yellow).append("   README.md").append(nl).append(reset);
        expected.append(purple).append("zebra.txt").append(nl).append(reset);

        String expectedOutput = expected.toString().replace("\r\n", "\n").trim();

        String cleanedExpected = expectedOutput.replaceAll("\u001B\\[[;\\d]*m", "").trim();
        String cleanedActual = output.replaceAll("\u001B\\[[;\\d]*m", "").trim();

        assertEquals(cleanedExpected, cleanedActual);
    }

    @Test
    public void testPrintTree_EmptyFolder(@TempDir File tempDir) {
        TruffulaOptions options = new TruffulaOptions(tempDir, false, false);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        TruffulaPrinter printer = new TruffulaPrinter(options, new PrintStream(out));

        printer.printTree();

        assertEquals("", out.toString().trim());
    }

    @Test
    public void testPrintTree_FileOnlyDirectory(@TempDir File tempDir) throws IOException {
        new File(tempDir, "a.txt").createNewFile();
        new File(tempDir, "B.txt").createNewFile();
        new File(tempDir, "c.txt").createNewFile();

        TruffulaOptions options = new TruffulaOptions(tempDir, false, false);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        TruffulaPrinter printer = new TruffulaPrinter(options, new PrintStream(out));
        printer.printTree();

        String output = out.toString().trim();
        assertTrue(output.contains("a.txt"));
        assertTrue(output.contains("B.txt"));
        assertTrue(output.contains("c.txt"));
    }

    @Test
    public void testPrintTree_InvalidRoot() {
        File fake = new File("nonexistent_dir");
        TruffulaOptions options = new TruffulaOptions(fake, false, false);
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        TruffulaPrinter printer = new TruffulaPrinter(options, new PrintStream(out));
        printer.printTree();

        assertTrue(out.toString().trim().contains("Error: Path does not exist."));
    }

    @Test
    public void testPrintTree_HidesHiddenFiles_WhenShowHiddenFalse(@TempDir File tempDir) throws IOException {
        File visible = new File(tempDir, "visible.txt");
        visible.createNewFile();

        createHiddenFile(tempDir, ".secret.txt");

        TruffulaOptions options = new TruffulaOptions(tempDir, false, false);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        TruffulaPrinter printer = new TruffulaPrinter(options, new PrintStream(out));
        printer.printTree();

        String output = out.toString().trim();
        assertTrue(output.contains("visible.txt"));
        assertFalse(output.contains(".secret.txt"));
    }

    @Test
    public void testPrintTree_ShowsHiddenFiles_WhenShowHiddenTrue(@TempDir File tempDir) throws IOException {
        File visible = new File(tempDir, "visible.txt");
        visible.createNewFile();

        createHiddenFile(tempDir, ".secret.txt");

        TruffulaOptions options = new TruffulaOptions(tempDir, true, false);  // showHidden = true
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        TruffulaPrinter printer = new TruffulaPrinter(options, new PrintStream(out));
        printer.printTree();

        String output = out.toString().trim();
        assertTrue(output.contains("visible.txt"));
        assertTrue(output.contains(".secret.txt"));
    }

    @Test
    void testColorCyclesAcrossDepths(@TempDir File tempDir) throws IOException {
        File level0 = tempDir;
        File level1 = new File(level0, "sub");
        File level2 = new File(level1, "nested");
    
        assertTrue(level1.mkdir(), "Level 1 folder should be created");
        assertTrue(level2.mkdir(), "Level 2 folder should be created");
    
        new File(level0, "root.txt").createNewFile();
        new File(level1, "subfile.txt").createNewFile();
        new File(level2, "nestedfile.txt").createNewFile();
    
        TruffulaOptions options = new TruffulaOptions(tempDir, false, true);
    
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        TruffulaPrinter printer = new TruffulaPrinter(options, new PrintStream(baos));
        printer.printTree();
    
        String output = baos.toString();
    
        assertTrue(output.contains(ConsoleColor.WHITE.toString()), "Missing WHITE color at depth 0");
        assertTrue(output.contains(ConsoleColor.PURPLE.toString()), "Missing PURPLE color at depth 1");
        assertTrue(output.contains(ConsoleColor.YELLOW.toString()), "Missing YELLOW color at depth 2");
    }

    @Test
    void testAllWhiteWhenColorDisabled(@TempDir File tempDir) throws IOException {
        File root = new File(tempDir, "testFolder");
        assertTrue(root.mkdir());
    
        File subdir = new File(root, "sub");
        File file = new File(subdir, "file.txt");
    
        assertTrue(subdir.mkdir());
        assertTrue(file.createNewFile());
    
        TruffulaOptions options = new TruffulaOptions(root, true, false); // color disabled
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        TruffulaPrinter printer = new TruffulaPrinter(options, new PrintStream(out));
        printer.printTree();
    
        String result = out.toString().replace("\r\n", "\n");
    
        for (String line : result.split("\n")) {
            assertFalse(line.matches(".*\\u001B\\[[;\\d]*m.*"), "Line contains ANSI color: " + line);
        }
    }
}