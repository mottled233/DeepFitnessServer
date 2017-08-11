package util;

import java.io.File;

public class FileJuger {
	public static boolean jugeFileExists(String path){
		File file = new File(path);
		if(file.exists())
			return true;
		return false;
	}
}
