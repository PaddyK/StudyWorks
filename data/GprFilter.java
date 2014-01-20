package data;

import java.io.File;
import java.io.FilenameFilter;

public class GprFilter implements FilenameFilter {

	@Override
	public boolean accept(File dir, String name) {
		return name.toLowerCase().endsWith(".gpr");
	}

}