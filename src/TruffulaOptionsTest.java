import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class TruffulaOptionsTest {

  @Test
  void testValidDirectoryIsSet(@TempDir File tempDir) throws FileNotFoundException {
    File directory = new File(tempDir, "subfolder");
    directory.mkdir();
    String directoryPath = directory.getAbsolutePath();
    String[] args = {"-nc", "-h", directoryPath};

    TruffulaOptions options = new TruffulaOptions(args);

    assertEquals(directory.getAbsolutePath(), options.getRoot().getAbsolutePath());
    assertTrue(options.isShowHidden());
    assertFalse(options.isUseColor());
  }

  @Test
  void testFlagsInReverseOrder(@TempDir File tempDir) throws FileNotFoundException {
    File dir = new File(tempDir, "subfolder");
    dir.mkdir();
    String path = dir.getAbsolutePath();
    String[] args = {"-nc", "-h", path};

    TruffulaOptions options = new TruffulaOptions(args);

    assertEquals(path, options.getRoot().getAbsolutePath());
    assertTrue(options.isShowHidden());
    assertFalse(options.isUseColor());
  }

  @Test
  void testOnlyHiddenFlag(@TempDir File tempDir) throws FileNotFoundException {
    File dir = new File(tempDir, "subfolder");
    dir.mkdir();
    String path = dir.getAbsolutePath();
    String[] args = {"-h", path};

    TruffulaOptions options = new TruffulaOptions(args);

    assertEquals(path, options.getRoot().getAbsolutePath());
    assertTrue(options.isShowHidden());
    assertTrue(options.isUseColor());
  }

  @Test
  void testOnlyNoColorFlag(@TempDir File tempDir) throws FileNotFoundException {
    File dir = new File(tempDir, "testDir");
    dir.mkdir();
    String path = dir.getAbsolutePath();
    String[] args = {"-nc", path};

    TruffulaOptions options = new TruffulaOptions(args);

    assertEquals(path, options.getRoot().getAbsolutePath());
    assertFalse(options.isShowHidden());
    assertFalse(options.isUseColor());
  }

  @Test
  void testConstructorAndGetters_AllTrue() {
    File root = new File("/some/path");
    TruffulaOptions options = new TruffulaOptions(root, true, true);

    assertEquals(root, options.getRoot());
    assertTrue(options.isShowHidden());
    assertTrue(options.isUseColor());
  }

  @Test
  void testConstructor_ShowHiddenFalse_UseColorTrue() {
    File root = new File("/another/path");
    TruffulaOptions options = new TruffulaOptions(root, false, true);

    assertEquals(root, options.getRoot());
    assertFalse(options.isShowHidden());
    assertTrue(options.isUseColor());
  }

  @Test
  void testConstructor_ShowHiddenTrue_UseColorFalse() {
    File root = new File("/different/path");
    TruffulaOptions options = new TruffulaOptions(root, true, false);

    assertEquals(root, options.getRoot());
    assertTrue(options.isShowHidden());
    assertFalse(options.isUseColor());
  }

  @Test
  void testConstructor_AllFalse() {
    File root = new File("/final/path");
    TruffulaOptions options = new TruffulaOptions(root, false, false);

    assertEquals(root, options.getRoot());
    assertFalse(options.isShowHidden());
    assertFalse(options.isUseColor());
  }
}