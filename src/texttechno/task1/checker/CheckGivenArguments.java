package texttechno.task1.checker;

import java.io.File;

/**
 * 
 * @author Michael Czaja
 *
 */
public class CheckGivenArguments {
/**
 * Checks if an input is valid path to file
 * @param path
 * Path of interest.
 * @return
 * False if input is not a path otherwise true
 */
	public static Boolean checkIfPath(String path) {
		Boolean isPath = false;
		if (path.contains(":") || path.contains(".") || path.contains("/") || path.contains("\\")) {
			isPath = true;
		}
		return isPath;
	}

	public static Boolean checkIfFileExits(String path) {
		return (new File(path).exists());
	}

	public static Boolean checkIfFileExits(File file) {
		return file.exists();
	}

}
