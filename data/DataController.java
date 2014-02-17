package data;
import java.text.CollationElementIterator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.mysql.jdbc.PreparedStatement;

/**
 * This class supplies interfaces to work with data needed with respect to the conducted Study works.
 * 		Supplies interfaces to read from .gpr files into mysql database
 * 		Supplies interfaces to query data from mysql database
 *
 * @author kalmbach
 *
 */
public class DataController {
	/**
	 * Connection String for mysql database
	 */
	private String jdbcConnectionString;
	/**
	 * Name of mysql database user
	 */
	private String user;
	/**
	 * Password for database user
	 */
	private String password;
	/**
	 * Url to connect to mysql database
	 */
	private String databaseUrl;
	/**
	 * Connection to mysql database. A global variable so it is instanciated only once. Very expensive. 
	 */
	Connection databaseConnection;
	/**
	 * Stores (so far) used header names of sql table
	 * @TODO Implement this as sql query to dynamically get header information
	 */
	List<String> rawDataHeader;
	/**
	 * List of integers representing antibodies
	 * @TODO should be dynamic
	 */
	List<Integer> reporterIds;
	
	/**
	 * Constructor for class DataController. Takes a username and password.
	 * @param username	username for mysql database
	 * @param password	password for user specified with username for mysql databse
	 */	
	public DataController(String username, String password) {
		this.user = username;
		this.password = password;
		this.jdbcConnectionString = "com.mysql.jdbc.Driver";
		this.databaseUrl = "jdbc:mysql://localhost/StudyWorks";
		
		
		try {
			this.databaseConnection = DriverManager.getConnection(this.databaseUrl, this.user, this.password);
		}
		catch(SQLException e) {
			System.err.println("Failed to create connection to database: url " + this.databaseUrl +
					"user " + this.user);
			e.printStackTrace();
		}
	}

	/**
	 * Reads all .gpr files from a directory into mysql database and includes identifier from sample name
	 * @param path						path to directory containing .gpr files
	 * @throws FileNotFoundException	thrown if path not found
	 * @throws IOException				thrown if directoryPath is not a directory
	 */
	public void addGprRecordsToDatabase(String directoryPath) throws FileNotFoundException, IOException {
		String line;
		FileInputStream instream = null;
		DataInputStream indata = null;
		BufferedReader reader = null;
		File directory = null;

		directory = new File(directoryPath);
		if(!directory.isDirectory()) {
			throw new IOException("Path " + directoryPath + " is not a directory !!");
		}
		else {
			File[] files = directory.listFiles(new FilenameFilter()  {

				@Override
				public boolean accept(File dir, String name) {
					return name.toLowerCase().endsWith(".gpr");
				}
			});

			instream = new FileInputStream(directory);
			indata = new DataInputStream(instream);
			reader = new BufferedReader(new InputStreamReader(indata));

			for(File file : files) {
				instream = new FileInputStream(file);
				indata = new DataInputStream(instream);
				reader = new BufferedReader(new InputStreamReader(indata));

				loadGprFile(reader, file.getName().substring(file.getName().indexOf("GSE")
						,file.getName().indexOf("GSE") + 11));
			}

			reader.close();
			indata.close();
			instream.close();

		}
	}

	/**
	 * Reads with filePath specified .gpr file into database
	 * @param filePath					Path to gpr file
	 * @throws FileNotFoundException	thrown if file does not exist
	 * @throws IOException				thrown if file is has not extension .gpr or is no file at all
	 */
	public void addGprRecordToDatabase(String filePath) throws FileNotFoundException, IOException {
		FileInputStream instream = null;
		DataInputStream indata = null;
		BufferedReader reader = null;

		File gpr = new File(filePath);
		if(!gpr.exists())
			throw new FileNotFoundException("File " + filePath + " does not exist!");
		else if(!gpr.getAbsolutePath().toLowerCase().endsWith(".gpr"))
			throw new IOException("File " + filePath + " is not a .gpr file!");
		else {
			instream = new FileInputStream(gpr);
			indata = new DataInputStream(instream);
			reader = new BufferedReader(new InputStreamReader(indata));

			loadGprFile(reader, filePath.substring(filePath.indexOf("GSE"), filePath.indexOf("GSE") + 11));

			reader.close();
			indata.close();
			instream.close();
		}
	}

	/**
	 * Reads all records from the gpr file with which reader is initialized into RawData table
	 * @param reader	BufferedReader initialized with gpr file
	 * @param id		Patient ID extracted from file name
	 */
	private void loadGprFile(BufferedReader reader, String id) {
		String line;
		String statement = "INSERT INTO RawData() VALUES(";


		try
		{
			while((line = reader.readLine()) != null) {
				line = line.replaceAll("Error", "NULL");
				line = line.replace('\t', ',');
				line = id + "," + line;
				/** @TODO Implement this */
			}
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Reads data for specified antibodies from each patient from raw data.
	 * @param antibodyIds	List of ids from antibodies as specified in table ArrayDesign for which information should
	 * 						be retrieved from table Rawdata
	 * @param header		List of mysql database column headers which should be included in statement
	 * 						IMPORTANT header label (has/has not PD) and Patient_id are included by default
	 * @return				List of RawDataMinimal elements
	 */
	public Hashtable<String, ArrayList<RawDataMini>> readMinimalRawDataFromDb(List<Integer> antibodyIds
			,List<String> header) {
		ResultSet data;
		RawDataMini tmp;
		Boolean isPatientIdFirst = true;
		// Create a list of result sets for each unique patient id to be later able 
		// to process them more precisely
		Hashtable<String, ArrayList<RawDataMini>> rawData = null;
		PreparedStatement statement = null;
		/** @TODO dynamically create select part */
		String stmnt = "SELECT rd.Patient_id" +
				",IF(sdr.Sample_source_name LIKE 'PD%', 'yes', 'no') AS Label " +
				",ards.DatabaseId";
		for(String s : header)
			stmnt += ",rd." + s;
		
		stmnt += " FROM RawData AS rd INNER JOIN SamleAndDataRelationship AS sdr " +
					"ON rd.Patient_id LIKE TRIM(TRAILING ' 1' FROM sdr.PatientId) " +
                    "INNER JOIN ArrayDesign ards ON rd.Bez LIKE CONCAT('%', ards.DatabaseId, '%') " +
				"WHERE " +
                    "(sdr.Sample_source_name LIKE 'PD%' OR sdr.Sample_source_name LIKE 'CO%' OR sdr.Sample_source_name LIKE 'CY%') " +
                    "AND ards.idArrayDesign IN (";
		
		for(int i = 0; i < antibodyIds.size(); i++)
			stmnt += "?,";
		
		// Remove trailing comma
		stmnt = stmnt.substring(0, stmnt.length() - 1) + ") ORDER BY rd.Patient_id;";

		try {
			stmnt = "SELECT rd.Patient_id,IF(sdr.Sample_source_name LIKE 'PD%', 'yes', 'no') AS Label ,ards.DatabaseId,rd.F635_Mean FROM RawData AS rd INNER JOIN SamleAndDataRelationship AS sdr ON rd.Patient_id LIKE TRIM(TRAILING ' 1' FROM sdr.PatientId) INNER JOIN ArrayDesign ards ON rd.Bez LIKE CONCAT('%', ards.DatabaseId, '%') WHERE (sdr.Sample_source_name LIKE 'PD%' OR sdr.Sample_source_name LIKE 'CO%' OR sdr.Sample_source_name LIKE 'CY%') AND ards.idArrayDesign IN (8327, 7841) ORDER BY rd.Patient_id;";
			statement = (PreparedStatement)this.databaseConnection.prepareStatement(stmnt);
			System.out.println(statement);		
			// Add reporterIds aka antibody ids to statement
//			for(int i = 0; i < antibodyIds.size(); i++)
//				statement.setInt(i + 1, antibodyIds.get(i));

			//<message>
				System.out.println("Begin querying database. This may take some time...");
			//</message>
			data = statement.executeQuery();
			//<message>
				System.out.println("Querying databse completed");
			//</message>
			rawData = new Hashtable<String, ArrayList<RawDataMini>>();

			while(data.next()) {
				tmp = new RawDataMini();
				// In each .gpr file are two entries for each antibody. SQL statement returns
				// results ordered after patient id and id string of antibody. This means result
				// consists (in case of 10 antibodies) of a block of 20 rows for each patient. This
				// block in turn consists of 10 blocks. Those then blocks consist of two rows containing
				// queried information for the same antibody for the same patient.
				// Information for the same antibody are successive (because of the order) thus the
				// isPatientIdFirst suffix. This makes the different anibody information later distingishuable
				tmp.setPatientId(data.getString("Patient_id") + "_" + isPatientIdFirst.toString());
				tmp.setLabel(data.getString("Label"));
				tmp.setAntibodyId(data.getString("DatabaseId"));
				
				for(String hdr : header)
					tmp.setAttributeFromHeader(hdr, (double)data.getInt(hdr));
				
				// Check if key exists, if not create new array list
				if(!rawData.keySet().contains(tmp.getPatientId())) {
					rawData.put(tmp.getPatientId(), new ArrayList<RawDataMini>());
				}
				// Add entry to array list
				rawData.get(tmp.getPatientId()).add(tmp);
				
				isPatientIdFirst = !isPatientIdFirst;
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {
				statement.close();
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return rawData;		
	}
	
	/**
	 * Writes the data of a given set of raw data items to a csv file. Method readInstancesFromCSV
	 * assumes first attribute in csv file is patientid, second attribute is label!!!
	 * {@link}WekaController.java
	 * @param destination	Path to destination file
	 * @param data			Set of raw data lines from database
	 */
	public void writeRawDataMiniToFile(String destination, Hashtable <String, ArrayList<RawDataMini>> data, 
			List<String> header, List<Integer> reporterIds) {
		File dest = new File(destination);
		FileWriter writer = null;
		String patientId = "";
		String line = "";
		String separatorSymbol = "\t";
		List<RawDataMini> records;
		Enumeration<String> keys = data.keys();
		
		// Create header line
		// @TODO this is not working at the moment as antibodies are ordered alphabetically and not as specified
		// with reporter Id --> get ids and append them
		line = "PatientId" + separatorSymbol + "Label";
		ArrayList<RawDataMini> forHeader = data.get(data.keys().nextElement());
		Collections.sort(forHeader);
		for(RawDataMini mini : forHeader) 
			for(String h : header)
				line +=  separatorSymbol + mini.getAntibodyId() + "_" + h;
		System.out.println(line);
			
		try {
			writer = new FileWriter(dest.getAbsolutePath());
			writer.append(line + System.getProperty("line.separator"));
			while(keys.hasMoreElements()) {
				patientId = keys.nextElement();
				records = data.get(patientId);
				
				Collections.sort(records);
				
				line = patientId + separatorSymbol + records.get(0).getLabel();	
				
				for(RawDataMini d : records) {				
					for(String h : header) {
						line += separatorSymbol + d.getAttributeFromHeader(h);		
						System.out.println(d.getAntibodyId());
					}
				}
				System.out.println();
				writer.append(line + System.getProperty("line.separator"));
				line = "";
			}
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				writer.close();
			}
			catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Intended for files being created by using select into outfile, does essentially the same as
	 * readMinimalRawData just not directly from database but from file, order of column header needs
	 * to be specified by user, though.
	 * This method assumes semicolon separated columns
	 * @TODO design statement which includes column headers (if possible, low priority)
	 * @TODO change RawDataMini with method setting attribute based on header
	 * 
	 * @param header	ArrayList specifying header in order in which they appear in file 
	 * @param path		Path to source file
	 * 
	 * @return
	 */
	public Hashtable<String, ArrayList<RawDataMini>> convertDatabaseExportToFeatureVector(ArrayList<String> header, String source) {
		Hashtable<String, ArrayList<RawDataMini>> rawdata = null;
		BufferedReader reader = null;
		String line;
		String[] attributes;
		RawDataMini row;
		
		Boolean isPatientIdFirst = true;
		
		try {
			reader = new BufferedReader(new InputStreamReader(new DataInputStream(new FileInputStream(source))));
			rawdata = new Hashtable<String, ArrayList<RawDataMini>>();
			//@TODO make here a line and not a row for each attribute
			while((line = reader.readLine()) != null) {
				attributes = line.split(";");
				
				if(!rawdata.keySet().contains(attributes[0] + "_" + isPatientIdFirst.toString()))
					rawdata.put(attributes[0] + "_" + isPatientIdFirst.toString(), new ArrayList<RawDataMini>());
				
				row = new RawDataMini();
				row.setPatientId(attributes[0] + "_" + isPatientIdFirst.toString());
				row.setF635mean(Double.parseDouble(attributes[3]));
				row.setLabel(attributes[2]);
				row.setAntibodyId(attributes[1]);
				rawdata.get(attributes[0] + "_" + isPatientIdFirst.toString()).add(row);
				
				// Toggle lines
				isPatientIdFirst = !isPatientIdFirst;
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				reader.close();
			}
			catch(IOException e) {
				e.printStackTrace();
			}
		}
		
		return rawdata;
	}
	
	/**
	 * Takes a result of data and calculates for each array list, i.e. for each records found for a patientID
	 * the mean of each specified feature.
	 * @param header	Header strings for each feature
	 * @param data		data, either from database or from database export
	 */
	public void calculateMeanForFeaturesOfSamePatient(ArrayList<String> header, Hashtable <String
			,ArrayList<RawDataMini>> data) {
		ArrayList<RawDataMini> patientData;
		RawDataMini newRecord;
		Enumeration<String> num = data.keys();
		double mean = 0;
		
		while(num.hasMoreElements())
		{
			patientData = data.get(num.nextElement());
			
			newRecord = new RawDataMini();
			newRecord.setPatientId(patientData.get(0).getPatientId());
			newRecord.setLabel(patientData.get(0).getLabel());
			newRecord.setAntibodyId(patientData.get(0).getAntibodyId());
			
			for(String hdr : header) {
				for(RawDataMini record : patientData) 
					mean += Double.parseDouble(record.getAttributeFromHeader(hdr));
				
				newRecord.setAttributeFromHeader(hdr, mean/patientData.size());
			}
			patientData.clear();
			patientData.add(newRecord);				
		}
	}
	
	public void closeConnection() {
		try {
			this.databaseConnection.close();
		}
		catch(SQLException e) {
			System.err.println("Error on closing connection to database!\n");
			e.printStackTrace();
		}
	}
}