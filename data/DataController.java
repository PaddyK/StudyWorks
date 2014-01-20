package data;
import java.util.ArrayList;
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
	 * @return				List of RawDataMinimal elements
	 */
	public List<RawDataMini> readMinimalRawData(java.util.List<Integer> antibodyIds) {
		ResultSet data;
		RawDataMini tmp;
		List<RawDataMini> rawData = null;
		PreparedStatement statement = null;
		String stmnt = "SELECT Patient_id" +
				",sdr.Sample_source_name" +
				",rd.F635_Median" +
				",rd.F635_Mean" +
				",F635_SD" +
				",rd.B635" +
				",rd.B635_Median" +
				",rd.B635_Mean" +
				",rd.B635_SD" +
				",rd.F532_Median" +
				",rd.F532_Mean" +
				",rd.F532_SD " +
				",rd.B532" +
				",rd.B532_Median" +
				",rd.B532_Mean" +
				",rd.B532_SD FROM RawData AS rd INNER JOIN SamleAndDataRelationship as sdr " +
				"	ON rd.Patient_id LIKE CONCAT(sdr.PatientId, '%')" +
				"WHERE rd.ID IN (SELECT ad.DatabaseId FROM ArrayDesign WHERE idArrayDesign IN (";
		
		for(int i : antibodyIds)
			stmnt += "?,";
		// Remove trailing comma
		stmnt = stmnt.substring(0, stmnt.length() - 2) + "));";
		
		try {
			statement = (PreparedStatement)this.databaseConnection.prepareStatement(stmnt);
			for(int i = 0; i < antibodyIds.size(); i++)
				statement.setInt(i, antibodyIds.get(i));
			data = statement.executeQuery();
			rawData = new ArrayList<RawDataMini>();
			while(data.next()) {
				tmp = new RawDataMini();
				tmp.setPatientId(data.getString("Patient_id"));
				tmp.setHasParkinson((data.getString("").toLowerCase().contains("pd"))? "yes" : "no");
				tmp.setF635median(data.getInt("F635_Median"));
				tmp.setF635mean(data.getInt("F635_Mean"));
				tmp.setF635sd(data.getInt("F635_SD"));
				tmp.setB635(data.getInt("B635"));
				tmp.setB635median(data.getInt("B635_Median"));
				tmp.setB635mean(data.getInt("B635_Mean"));
				tmp.setB635sd(data.getInt("B635_SD"));
				tmp.setF532median(data.getInt("F532_Median"));
				tmp.setF532median(data.getInt("F532_Mean"));
				tmp.setF532median(data.getInt("F532_SD"));
				tmp.setB532(data.getInt("B532"));
				tmp.setB532median(data.getInt("B532_Median"));
				tmp.setB532mean(data.getInt("B532_Mean"));
				tmp.setB532sd(data.getInt("B532_SD"));
				
				rawData.add(tmp);
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
	 * Writes the data of a given set of raw data items to a csv file
	 * @param destination	Path to destination file
	 * @param data			Set of raw data lines from database
	 */
	public void writeRawDataMiniToFile(String destination, List<RawDataMini> data) {
		File dest = new File(destination);
		FileWriter writer = null;
		
		try {
			writer = new FileWriter(dest.getAbsolutePath());
			for(RawDataMini d : data)
				writer.append(d.toString() + System.getProperty("line.separator"));
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
}