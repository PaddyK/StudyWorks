package concurrent;

import java.util.List;

import data.DataController;

public class DbWriter extends Thread {
	
	DataController dctrl;
	LoocConcurrentList looc;
	String sqlFolder;
	
	public DbWriter(LoocConcurrentList list, String username, String password, String sqlOutput) {
		if(username != null && password != null)
			dctrl = new DataController(username, password);
		else {
			dctrl = new DataController();
			System.err.println("Username and password for database were not specified for DBWriter");
		}
		sqlFolder = sqlOutput;
		looc = list;
	}
	
	public DbWriter(LoocConcurrentList list, String sqlOutput) {
		sqlFolder = sqlOutput;
		dctrl = null;
		looc = list;
	}
	
	@Override
	public void run() {
		Looc l;
		while(!isInterrupted()) {
			if((l = looc.poll()) != null) {
				List<String> list = l.generateInsertStatement();
				dctrl.writeToTabSeparatedFile(sqlFolder + "SQLDump_" + l.getId(), implode(list));
				
				if(dctrl != null)
					try {
						dctrl.executeBatch(list);
					} catch (Exception e) {
						e.printStackTrace();
					}
			}
		}		
	}
	
	private String implode(List<String> strings) {
		String ret = "";
		for(String s : strings)
			ret += s + System.getProperty("line.separator");
		return ret;
	}
	
}