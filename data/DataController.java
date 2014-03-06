package data;
import java.text.CollationElementIterator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
import java.sql.Statement;

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
	 * List of Strings denoting column header of table containing data processed with protoArraya
	*/
	List<String> protoHeader;
	
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
		
		protoHeader = new ArrayList<String>();
		protoHeader.add("PatientId");
		protoHeader.add("DatabaseId");
		protoHeader.add("UltimateOrfId");
		protoHeader.add("ArrayId");
		protoHeader.add("Block");
		protoHeader.add("Reihe");
		protoHeader.add("Spalte");
		protoHeader.add("ProteinAmount");
		protoHeader.add("LaserSignal");
		protoHeader.add("Background");
		protoHeader.add("SignalUsed");
		protoHeader.add("SignalRank");
		protoHeader.add("ZFactor");
		protoHeader.add("ZScore");
		protoHeader.add("CiPValue");
		protoHeader.add("Cv");
		protoHeader.add("SignificanceCal");
		protoHeader.add("GenePixFlags");
		protoHeader.add("Description");
		
		
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
//			stmnt = "SELECT rd.Patient_id,IF(sdr.Sample_source_name LIKE 'PD%', 'yes', 'no') AS Label ,ards.DatabaseId,rd.F635_Mean FROM RawData AS rd INNER JOIN SamleAndDataRelationship AS sdr ON rd.Patient_id LIKE TRIM(TRAILING ' 1' FROM sdr.PatientId) INNER JOIN ArrayDesign ards ON rd.Bez LIKE CONCAT('%', ards.DatabaseId, '%') WHERE (sdr.Sample_source_name LIKE 'PD%' OR sdr.Sample_source_name LIKE 'CO%' OR sdr.Sample_source_name LIKE 'CY%') AND ards.idArrayDesign IN (8327, 7841) ORDER BY rd.Patient_id;";
			statement = (PreparedStatement)this.databaseConnection.prepareStatement(stmnt);
			// Add reporterIds aka antibody ids to statement
			for(int i = 0; i < antibodyIds.size(); i++)
				statement.setInt(i + 1, antibodyIds.get(i));

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
		// with reporter Id --> get ids and append them
		line = "PatientId" + separatorSymbol + "Label";
		ArrayList<RawDataMini> forHeader = data.get(data.keys().nextElement());
		Collections.sort(forHeader);
		for(RawDataMini mini : forHeader) 
			for(String h : header)
				line +=  separatorSymbol + mini.getAntibodyId() + "_" + h;
			
		try {
			writer = new FileWriter(dest.getAbsolutePath());
			writer.append(line + System.getProperty("line.separator"));
			while(keys.hasMoreElements()) {
				patientId = keys.nextElement();
				records = data.get(patientId);
				
				Collections.sort(records);
				
				line = patientId + separatorSymbol + records.get(0).getLabel();	
				
				for(RawDataMini d : records) {		
//					System.out.println(d.getSet() + "\t" + records.size());
					for(String h : header) {
						line += separatorSymbol + d.getAttributeFromHeader(h);
					}
				}
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
	 * This method assumes semicolon, comma or tab separated columns
	 * @TODO design statement which includes column headers (if possible, low priority)
	 * @TODO change RawDataMini with method setting attribute based on header
	 * 
	 * @param header	ArrayList specifying header in order in which they appear in file 
	 * @param path		Path to source file
	 * 
	 * @return
	 */
	public Hashtable<String, ArrayList<RawDataMini>> convertDatabaseExportToFeatureVector(String source) {
		Hashtable<String, ArrayList<RawDataMini>> rawdata = null;
		BufferedReader reader = null;
		String regex = ",|;|\t";

		String line;
		String[] attributes;
		String[] header;
		RawDataMini row;
		
		Boolean isPatientIdFirst = true;
		
		try {
			reader = new BufferedReader(new InputStreamReader(new DataInputStream(new FileInputStream(source))));
			rawdata = new Hashtable<String, ArrayList<RawDataMini>>();
			
			line = reader.readLine();
			if(line != null)
				header = line.split(regex);
			else {
				System.err.println("Database Export corrupted - no header line found");
				return null;
			}
			
			//@TODO make here a line and not a row for each attribute
			while((line = reader.readLine()) != null) {
				attributes = line.split(regex);

				if(extractPatientId(attributes[0]) != null) {
					if(!rawdata.keySet().contains(attributes[0] + "_" + isPatientIdFirst.toString()))
						rawdata.put(attributes[0] + "_" + isPatientIdFirst.toString(), new ArrayList<RawDataMini>());
					
					row = new RawDataMini();
					for(int i = 0; i < header.length; i++)
						row.setAttributeFromHeader(header[i], attributes[i]);

					rawdata.get(attributes[0] + "_" + isPatientIdFirst.toString()).add(row);
					// Toggle lines
					isPatientIdFirst = !isPatientIdFirst;
					
//					row.setPatientId(attributes[0] + "_" + isPatientIdFirst.toString());
//					row.setF635mean(Double.parseDouble(attributes[3]));
//					row.setLabel(attributes[1]);
//					row.setAntibodyId(attributes[1]);
				}
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
	 * The same as converDatabaseExportToFeatureVector calculates the mean for each of the two records
	 * of each patient ID, though
	 * @param header	Header strings for each feature
	 * @param data		data, either from database or from database export
	 */
	public Hashtable<String, ArrayList<RawDataMini>> createFeatureVectorMean(String source) {
		Hashtable<String, ArrayList<RawDataMini>> rawdata = null;
		BufferedReader reader = null;
		String regex = ",|;|\t";

		String line;
		String[] attributes;
		String[] header;
		RawDataMini row;
		
		try {
			reader = new BufferedReader(new InputStreamReader(new DataInputStream(new FileInputStream(source))));
			rawdata = new Hashtable<String, ArrayList<RawDataMini>>();
			
			line = reader.readLine();
			if(line != null)
				header = line.split(regex);
			else {
				System.err.println("Database Export corrupted - no header line found");
				return null;
			}

			while((line = reader.readLine()) != null) {
				row = null;
				attributes = line.split(regex);

				// check if slot for a patient already exists
				if(extractPatientId(attributes[0]) != null) {
					row = new RawDataMini(); // Create new record and new slot in table
					if(!rawdata.keySet().contains(attributes[0])) {
						rawdata.put(attributes[0], new ArrayList<RawDataMini>());
						rawdata.get(attributes[0]).add(row);
					}
					else {
						for(RawDataMini mini : rawdata.get(attributes[0])) {// Check if current Antibody
							if(mini.getAntibodyId().equals(attributes[2])) // ID already exists in a record
							{											   // If so use existing record
								row = mini;                                // to set attribute, else create
								break;                                     // and add new record to list
							}
						}
						// If no record with matching databaseId was found, add new record to set
						if(row.patientId == null)
							rawdata.get(attributes[0]).add(row);
					}

					for(int i = 0; i < header.length; i++){
						row.setAttributeFromHeader(header[i], attributes[i]);
					}
				}
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
	 * Assumes multiple files to import. Therefore path to directory containing analyzed files and
	 * NO OTHER files is given.
	 * 
	 * @param sourceFolder	Path to folder containing result files
	 */
	public void importProtoArrayProcessedDataInDatabase(String sourceFolder) {
		File[] children;		
		File dir = new File(sourceFolder);
		
		if(!dir.isDirectory() || !dir.exists())
			System.err.println("Path" + sourceFolder + " is not a directory or does not exist!");
		else {
			children = dir.listFiles();
			int i = 1;
			for(File child : children){	
				System.out.print("Importing file " + i++ + " ...");
				importProtoArrayProcessedDataInDatabase(child);
				System.out.println("\tFinish!");
			}
		}
	}
	
	/**
	 * Adds the content of the result file created by PotoArray to database. This file has to be
	 * tab separated
	 * 
	 * @param file	File containing result data
	 */
	public void importProtoArrayProcessedDataInDatabase(File file) {
		Statement statement;
		File imprt = createTemporaryFileForImport(file);
		String stmnt = "LOAD DATA LOCAL INFILE '" + imprt.getAbsolutePath() + "' INTO TABLE " +
				"ProtoArrayProcessedData FIELDS TERMINATED BY ';' (";
		for(String s : this.protoHeader)
			stmnt += s + ",";
		stmnt = stmnt.substring(0, stmnt.length() - 1) + ");";

		try{
			statement = this.databaseConnection.createStatement();
			statement.execute(stmnt);
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		imprt.delete();
	}
	
	/**
	 * Converts raw data to arff file
	 * @param destination
	 * @param data
	 */
	public void writeArffFile(String destination, List<String> header, String relationName,
			Hashtable<String, ArrayList<RawDataMini>> data) {
		
		File dest = new File(destination);
		FileWriter writer = null;
		String patientId = "";
		String line = "";
		String separatorSymbol = "\t";
		String headerSec = "@RELATION " + relationName;
		String dataSec;
		ArrayList<RawDataMini> records;
		Enumeration<ArrayList<RawDataMini>> enumeration = data.elements();
		
		// Create header line
		// with reporter Id --> get ids and append them
		headerSec = System.getProperty("line.separator") + "@ATTRIBUTE PatientId string" + 
				System.getProperty("line.separator") + "@ATTRIBUTE class {yes,no}";
		ArrayList<RawDataMini> forHeader = data.elements().nextElement();
		Collections.sort(forHeader);
		for(RawDataMini mini : forHeader) {
			for(String h : header) {
				headerSec += System.getProperty("line.separator");
				if(!h.equalsIgnoreCase("Label") && !h.equalsIgnoreCase("PatientId"))
					headerSec += "@ATTRIBUTE " + mini.getAntibodyId() + "_" + h + " " +
						((isNumerical(mini.getAttributeFromHeader(h)))? "numeric" : "string");
			}
		}
		headerSec += System.getProperty("line.separator") + "@DATA";		
			
		try {
			writer = new FileWriter(dest.getAbsolutePath());
			writer.append(headerSec);
			while(enumeration.hasMoreElements()) {
				records = enumeration.nextElement();
				patientId = records.get(0).getPatientId();
				
				Collections.sort(records);
				
				line = patientId + separatorSymbol + records.get(0).getLabel();	
				
				for(RawDataMini d : records) {		
//					System.out.println(d.getSet() + "\t" + records.size());
					for(String h : header) {
						line += separatorSymbol + d.getAttributeFromHeader(h);
					}
				}
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
	 * With the study came a excel file providing with proto array processed data for different
	 * classes. This function takes a csv export of a sheet of this file, where all columns and rows
	 * have to be deleted except for the rows containing data and the columns containing antibody id
	 * and RFU values
	 * @param source
	 * @param destination
	 */
	public void writeGivenProcessedDataFromCSVToArff(String source, String destination) {
		BufferedReader reader = null;
		FileWriter writer = null;
		String line;
		String[] attributes;
		StringBuffer header;
		StringBuffer data;
		
		char[] bitmap;
		int columnsCount;
		int posLineBreak;
		int count = 0;
		
		// #################################################################################
		// Step one create bitmap for parkinson diseased
		// #################################################################################
		
		// <Message>
		System.out.println("Create Bitmap storing location of PD patients...");
		// </Message>
		try {
			reader = new BufferedReader(new InputStreamReader(new DataInputStream(
					new FileInputStream(source))));
			line = reader.readLine();
			attributes = line.split("\t");
			columnsCount = attributes.length;
			bitmap = new char[columnsCount - 1]; // first column does not contain any RFU values
			
			for(int i = 1; i < columnsCount; i++) // Begin at one, first attribute contains "databaseId"
				if(attributes[i].contains("PD"))
					bitmap[i-1] = 1;
				else
					bitmap[i - 1] = 0;
			
			// <Message>
			System.out.println("Bitmap created.\nStart initializing header and data section...");
			// </Message>
			// #################################################################################
			// Step two - Creating arff header section and initializ data section
			// #################################################################################
			header = new StringBuffer(95000);
			data = new StringBuffer(10390760);
			
			header.append("@relation given_processed_data" + System.getProperty("line.separator"));
			for(int i = 0; i < columnsCount - 1; i++) // Add line breaks, each column will later be
				data.append(System.getProperty("line.separator")); // one row. Skip first column, though			

			// <Message>
			System.out.println("Header and data section initialized.\n" +
					"Start processing data. This may take some time...");
			// </Message>
			
			while((line = reader.readLine()) != null) {
				attributes = line.split("\t");
				// Add count_ to antibody id in order to get unique ids
				header.append("@ATTRIBUTE \"" + count++ + "_" + attributes[0] + "\" numeric" +
						System.getProperty("line.separator"));
				
				// #################################################################################
				// Step three - create data section
				// ################################################################################
				
				// It is important to use indexOf here since line break moves as attribute are inserted!
				posLineBreak = data.indexOf(System.getProperty("line.separator"));
				// the line breaks inserted at the beginning serve as orientation. The value for
				// each antibody is inserted at indexOfLineBreak - 1, nextIndexOfLineBreak - 1 and so
				// on. Thus the data is transformed row by row from a 9460 x 69 to a 69 x 9460 matrix
				for(int i = 1; i < columnsCount; i++) {
					// Insert string BEFORE the line break
					data.insert(posLineBreak, attributes[i] + "\t");
					// Because of insertion line break was moved about the length of the inserted
					// string + 1 character for the comma. Additional plus 1 in order to find the
					// next line break. else the same line break would be found again
					posLineBreak = data.indexOf(System.getProperty("line.separator")
							,posLineBreak + attributes[i].length() + 1 + 1);
				}
			}
			header.append("@ATTRIBUTE class {yes,no}");
			header.append(System.getProperty("line.separator") + "@data" + System.getProperty("line.separator"));
			
			// Insert nominal class
			posLineBreak = data.indexOf(System.getProperty("line.separator")); // set to one because of minus one below
			String insert;
			for(int i = 0; i < columnsCount - 1; i++) {
				if(bitmap[i] == 1)
					insert = "yes";
				else
					insert = "no";
				
				data.insert(posLineBreak, insert);
				posLineBreak = data.indexOf(System.getProperty("line.separator")
						,posLineBreak + insert.length() + 1);
			}
			// <Message>
			System.out.println("Data Processed. Writing to file...");
			// </Message>
			// ################################################################################
			// Step four - Write data to file
			// ################################################################################
			try {
				writer = new FileWriter(destination);
				writer.append(header);
				writer.append(data);
				// <Message>
				System.out.println("Data succesfully written");
				// </Message>
			}
			catch(IOException e) {
				System.err.println("Error during writing/opening of arff file from processed data export!");
				e.printStackTrace();
			}
			finally {try { writer.close();} catch(IOException e) {e.printStackTrace();}
			}			
		}
		catch(IOException e) {
			System.err.println("Error during opening/reading of processed data export!");
			e.printStackTrace();
		}
		finally {
			try {reader.close();} catch(IOException e){e.printStackTrace();}
		}
		
	}
	
	/**
	 * Removes leading lines from data in order to be able to import it via LOCAL INFILE and replaces
	 * tabs with semicolon
	 * @param file
	 * @return
	 */
	private File createTemporaryFileForImport(File file) {
		String line;
		String id;
		
		int offset = 63;
		BufferedReader reader = null;
		FileWriter writer = null;
		File tmp = new File("/tmp/tmp_db_import.csv");
		id = extractPatientId(file.getName());

		try {
			writer = new FileWriter(tmp);
			reader = new BufferedReader(new InputStreamReader(new DataInputStream(new FileInputStream(file))));
			
			while(offset-- > 0 && reader.readLine() != null);
			
			while((line = reader.readLine()) != null) {
				line = id +";" + line.replaceAll("\\t", ";");
				writer.append(line + System.getProperty("line.separator"));
			}			
		}
		catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				writer.close();
				reader.close();
			}
			catch(IOException e) {
				e.printStackTrace();
			}
		}
		
		return tmp;
	}
	
	private String extractPatientId(String value) {
		String ret = null;
		
		Pattern pattern = Pattern.compile("GSM\\d+");
		Matcher matcher = pattern.matcher(value);
		if(matcher.find())
			return matcher.group();
		else
			return null;
	}
	
	private boolean isNumerical(Object object) {
		if(object instanceof Integer || object instanceof Double)
			return true;
			
		return false;
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