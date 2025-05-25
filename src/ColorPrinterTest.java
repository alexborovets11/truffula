import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ColorPrinterTest {

  @Test
  void testPrintlnWithRedColorAndReset() {
    // Arrange: Capture the printed output
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream printStream = new PrintStream(outputStream);

    ColorPrinter printer = new ColorPrinter(printStream);
    printer.setCurrentColor(ConsoleColor.RED);

    // Act: Print the message
    String message = "I speak for the trees";
    printer.println(message);


    String expectedOutput = ConsoleColor.RED + "I speak for the trees" + System.lineSeparator() + ConsoleColor.RESET;

    // Assert: Verify the printed output
    assertEquals(expectedOutput, outputStream.toString());
  }


@Test
void testPrintWithBlueColor(){
  ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

  PrintStream printStream = new PrintStream(outputStream);
  ColorPrinter colorPrinter = new ColorPrinter(printStream);

  colorPrinter.setCurrentColor(ConsoleColor.BLUE);

  //Act
  String message = "Test for the blue color";

  colorPrinter.print(message);

  String expected = ConsoleColor.BLUE + "Test for the blue color" + ConsoleColor.RESET;

  //Assert
  assertEquals(expected, outputStream.toString());

 
}
}
