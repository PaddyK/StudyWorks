package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import classification.WekaController;

import data.DataController;
import data.RawDataMini;

import weka.core.Instances;

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
	
	Hashtable<String, ArrayList<RawDataMini>> data;
	
	public Program() {
		this.rawDataHeader = new ArrayList<String>();
		//this.rawDataHeader.add("F635_Median");
		this.rawDataHeader.add("F635_Mean");
		//this.rawDataHeader.add("F635_SD");
		//this.rawDataHeader.add("B635");
//		this.rawDataHeader.add("B635_Median");
//		this.rawDataHeader.add("B635_Mean");
//		this.rawDataHeader.add("B635_SD");
//		this.rawDataHeader.add("F532_Median");
//		this.rawDataHeader.add("F532_Mean");
//		this.rawDataHeader.add("F532_SD");
//		this.rawDataHeader.add("B532");
//		this.rawDataHeader.add("B532_Median");
//		this.rawDataHeader.add("B532_Mean");
//		this.rawDataHeader.add("B532_SD ");
		
		reporterIds = new ArrayList<Integer>();
		reporterIds.add(8327);
//		reporterIds.add(1890);
//		reporterIds.add(2158);
//		reporterIds.add(2591);
//		reporterIds.add(3240);
//		reporterIds.add(3921);
//		reporterIds.add(4079);
//		reporterIds.add(4834);
//		reporterIds.add(6083);
		reporterIds.add(7841);
		
		DataController datactrl = new DataController("patrick", "");
		//data = datactrl.readMinimalRawDataFromDb(reporterIds, rawDataHeader);
		
		data = datactrl.convertDatabaseExportToFeatureVector(rawDataHeader, "/mnt/data/Mean_of_10_most_significant.csv");
		
		datactrl.writeRawDataMiniToFile("/mnt/data/Mean_of_10_most_significant_vector.csv", data, rawDataHeader, reporterIds);
//		Object[] tmp = data.keySet().toArray();
//		String[] keys = new String[tmp.length];
//		
//		for(int i = 0; i < keys.length; i++)
//			keys[i] = tmp[i].toString();
//		
//		Arrays.sort(keys);
//		for(String s : keys)
//			for(RawDataMini m : data.get(s))
//				System.out.println(m);
		
		//datactrl.writeRawDataMiniToFile("C:\\DATATEST.csv", datactrl.convertDatabaseExportToFeatureVector(rawDataHeader, "G:\\10MOST2.csv"),
				//rawDataHeader, reporterIds);
		// WekaController wc = new WekaController();
		// wc.readInstancesFromCSV("/home/patrick/Documents/DHBW/5Semester/Study_Works/antibodies/" +
				// "10_most_prevelant.csv");
		// wc.runTenFoldCrossValidation("weka.classifiers.trees.J48", new String[]{"-U"});
		datactrl.closeConnection();
	}
	
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Program();
		System.out.println("Finish");
	}

}
