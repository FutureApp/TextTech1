package texttechno.task2.Compare;

import java.io.File;

public class Properties_SingleTone {

	
	private static final Properties_SingleTone YOURSELF = new Properties_SingleTone(); 
    
	private String compare;
	private File file01;
	private File file02;
	
    private Properties_SingleTone() { 
    } 
         
    public static Properties_SingleTone getInstance() { 
      return YOURSELF; 
    }

	
    public String getCompare() {
		return compare;
	}

	public void setCompare(String compare) {
		this.compare = compare;
	}

	public File getFile01() {
		return file01;
	}

	public void setFile01(File file01) {
		this.file01 = file01;
	}

	public File getFile02() {
		return file02;
	}

	public void setFile02(File file02) {
		this.file02 = file02;
	}




}
