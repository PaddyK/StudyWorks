package main;

import java.util.ArrayList;
import java.util.List;

import data.DataController;

public class Program {

	/**
	 * Stores (so far) used header names of sql table
	 * @TODO Implement this as sql query to dynamically get header information
	 */
	ArrayList<String> rawDataHeader;
	/**
	 * List of integers representing antibodies
	 * @TODO should be dynamic
	 */
	List<Integer> reporterIds;
	
	public Program() {
		this.rawDataHeader = new ArrayList<String>();
		this.rawDataHeader.add("F635_Mean");
		//this.rawDataHeader.add("Patient_id");
		

		reporterIds = new ArrayList<Integer>();
		reporterIds.add(8327);
		reporterIds.add(1890);
		reporterIds.add(2158);
		reporterIds.add(2591);
		reporterIds.add(3240);
		reporterIds.add(3921);
		reporterIds.add(4079);
		reporterIds.add(4834);
		reporterIds.add(6083);
		reporterIds.add(7841);
		
		DataController datactrl = new DataController("patrick", "");
		datactrl.writeRawDataMiniToFile("C:\\DATATEST.csv", datactrl.convertDatabaseExportToFeatureVector(rawDataHeader, "G:\\10MOST2.csv"),
				rawDataHeader, reporterIds);
	}
	
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Program();
		System.out.println("Finish");
	}

}
