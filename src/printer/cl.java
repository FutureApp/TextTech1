package printer;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class cl {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		File file = new File("C:\\Users\\admin\\git\\TextTech1\\Result\\Task\\TOKENS_Schiller-Die-Raeuber-clear.txt");
		List<String> first = FileUtils.readLines(file, "iso-8859-1");
		System.out.println(first.get(0).equals(""));
//		System.out.println( "\\u" + Integer.toHexString(first.get(0).charAt(0) | 0x10000).substring(1) );
		
	}

}
