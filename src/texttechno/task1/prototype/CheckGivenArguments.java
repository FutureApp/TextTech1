package texttechno.task1.prototype;

import java.io.File;

public class CheckGivenArguments {

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
