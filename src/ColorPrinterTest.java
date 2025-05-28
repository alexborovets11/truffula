import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ColorPrinterTest {

  @Test
  void testPrintlnWithRedColorAndReset() {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream printStream = new PrintStream(outputStream);

    ColorPrinter printer = new ColorPrinter(printStream);
    printer.setCurrentColor(ConsoleColor.RED);

    String message = "I speak for the trees";
    printer.println(message);

    String expectedOutput = ConsoleColor.RED + message + System.lineSeparator() + ConsoleColor.RESET;
    assertEquals(expectedOutput, outputStream.toString());
  }

  @Test
  void testPrintWithBlueColor() {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream printStream = new PrintStream(outputStream);
    ColorPrinter colorPrinter = new ColorPrinter(printStream);

    colorPrinter.setCurrentColor(ConsoleColor.BLUE);
    colorPrinter.println("Test for the blue color");

    String expected = ConsoleColor.BLUE + "Test for the blue color" + System.lineSeparator() + ConsoleColor.RESET;
    assertEquals(expected, outputStream.toString());
  }

  @Test
  void testPrintWithPurpleColor() {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream printStream = new PrintStream(outputStream);
    ColorPrinter colorPrinter = new ColorPrinter(printStream);

    colorPrinter.setCurrentColor(ConsoleColor.PURPLE);
    colorPrinter.println("Test for the purple color");

    String expected = ConsoleColor.PURPLE + "Test for the purple color" + System.lineSeparator() + ConsoleColor.RESET;
    assertEquals(expected, outputStream.toString());
  }

  @Test
  void testPrintWithBlackColor() {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream printStream = new PrintStream(outputStream);
    ColorPrinter colorPrinter = new ColorPrinter(printStream);

    colorPrinter.setCurrentColor(ConsoleColor.BLACK);
    colorPrinter.print("Test for the black color");

    String expected = ConsoleColor.BLACK + "Test for the black color" + ConsoleColor.RESET;
    assertEquals(expected, outputStream.toString());
  }

  @Test
  void testPrintWithGreenColor() {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream printStream = new PrintStream(outputStream);
    ColorPrinter printer = new ColorPrinter(printStream);

    printer.setCurrentColor(ConsoleColor.GREEN);
    printer.print("Green message");

    String expectedOutput = ConsoleColor.GREEN + "Green message" + ConsoleColor.RESET;
    assertEquals(expectedOutput, outputStream.toString());
  }

  @Test
  void testChangeColorMidway() {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream printStream = new PrintStream(outputStream);
    ColorPrinter printer = new ColorPrinter(printStream);

    printer.setCurrentColor(ConsoleColor.YELLOW);
    printer.print("Yellow");
    printer.setCurrentColor(ConsoleColor.CYAN);
    printer.print("Cyan");

    String expectedOutput = ConsoleColor.YELLOW + "Yellow" + ConsoleColor.RESET +
                            ConsoleColor.CYAN + "Cyan" + ConsoleColor.RESET;
    assertEquals(expectedOutput, outputStream.toString());
  }

  @Test
  void testPrintWithoutResetKeepsColor() {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream printStream = new PrintStream(outputStream);
    ColorPrinter printer = new ColorPrinter(printStream);
  
    printer.setCurrentColor(ConsoleColor.GREEN);
    printer.print("First", false);  
    printer.print("Second", true); 
  
    String expectedOutput = ConsoleColor.GREEN + "First" + 
                            ConsoleColor.GREEN + "Second" + 
                            ConsoleColor.RESET;
  
    assertEquals(expectedOutput, outputStream.toString());
  }


  @Test
  void testPrintMultipleLines() {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream printStream = new PrintStream(outputStream);
    ColorPrinter printer = new ColorPrinter(printStream);

    printer.setCurrentColor(ConsoleColor.BLUE);
    printer.println("Line 1");
    printer.println("Line 2");

    String expectedOutput = ConsoleColor.BLUE + "Line 1" + System.lineSeparator() + ConsoleColor.RESET +
                            ConsoleColor.BLUE + "Line 2" + System.lineSeparator() + ConsoleColor.RESET;
    assertEquals(expectedOutput, outputStream.toString());
  }

  @Test
  void testPrintWithNullColor() {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream printStream = new PrintStream(outputStream);
    ColorPrinter printer = new ColorPrinter(printStream);

    printer.setCurrentColor(null);
    printer.print("Plain text");

    assertEquals("Plain text", outputStream.toString());
  }

  @Test
  void testPrintlnWithoutResetKeepsColor() {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream printStream = new PrintStream(outputStream);
    ColorPrinter printer = new ColorPrinter(printStream);

    printer.setCurrentColor(ConsoleColor.YELLOW);
    printer.println("Hello", false);
    printer.print("World");

    String expected = ConsoleColor.YELLOW + "Hello" + System.lineSeparator() +
                      ConsoleColor.YELLOW + "World" + ConsoleColor.RESET;

    assertEquals(expected, outputStream.toString());
  }

  @Test
  void testPrintlnAddsNewLine() {
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    ColorPrinter printer = new ColorPrinter(new PrintStream(out));

    printer.setCurrentColor(ConsoleColor.PURPLE);
    printer.println("hello");

    String expected = ConsoleColor.PURPLE + "hello" + System.lineSeparator() + ConsoleColor.RESET;
    assertEquals(expected, out.toString());
  } 

  @Test
    void testDefaultColorPrint() {
      ByteArrayOutputStream out = new ByteArrayOutputStream();
      ColorPrinter printer = new ColorPrinter(new PrintStream(out));
  
      printer.print("plain text");
  
      String expected = ConsoleColor.WHITE + "plain text" + ConsoleColor.RESET;
      assertEquals(expected, out.toString());
  }

  @Test
  void testSetColorAndReset() {
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    ColorPrinter printer = new ColorPrinter(new PrintStream(out));

    printer.setCurrentColor(ConsoleColor.YELLOW);
    printer.print("yellow text");
    printer.setCurrentColor(ConsoleColor.RESET);
    printer.print("reset text");

    String expected = ConsoleColor.YELLOW + "yellow text" + ConsoleColor.RESET +
                      ConsoleColor.RESET + "reset text" + ConsoleColor.RESET;
    assertEquals(expected, out.toString());
  }
}