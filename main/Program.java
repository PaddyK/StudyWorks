package main;

import java.io.File;
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
		
		// When adding new features make sure to check war data mini if everything is set correctly !!!
		this.rawDataHeader = new ArrayList<String>();
		this.rawDataHeader.add("F635_Median");
		this.rawDataHeader.add("F635_Mean");
//		this.rawDataHeader.add("F635_SD");
//		this.rawDataHeader.add("B635");
//		this.rawDataHeader.add("B635_Median");
//		this.rawDataHeader.add("B635_Mean");
//		this.rawDataHeader.add("B635_SD");
//		this.rawDataHeader.add("F532_Median");
//		this.rawDataHeader.add("F532_Mean");
//		this.rawDataHeader.add("F532_SD");
//		this.rawDataHeader.add("B532");
//		this.rawDataHeader.add("B532_Median");
//		this.rawDataHeader.add("B532_Mean");
//		this.rawDataHeader.add("B532_SD");
//		this.rawDataHeader.add("Dia");
//		this.rawDataHeader.add("percent_gt_B635_1SD");
//		this.rawDataHeader.add("percent_gt_B635_2SD");
//		this.rawDataHeader.add("F635_percent_Sat");
//		this.rawDataHeader.add("percent_gt_B532_1SD");
//		this.rawDataHeader.add("percentage_gt_B532_2SD");
//		this.rawDataHeader.add("F532_percentage_Sat");
//		this.rawDataHeader.add("Ratio_of_Medians_635_532");
//		this.rawDataHeader.add("Ratio_of_Means_635_532");
//		this.rawDataHeader.add("Median_of_Ratios_635_532");
//		this.rawDataHeader.add("Mean_of_Ratios_635_532");
//		this.rawDataHeader.add("Ratios_SD_635_532");
//		this.rawDataHeader.add("F_Pixels");
//		this.rawDataHeader.add("B_Pixels");
//		this.rawDataHeader.add("Sum_of_Medians_635_532");
//		this.rawDataHeader.add("Sum_of_Means_635_532");
		this.rawDataHeader.add("F635_Median_B635");
		this.rawDataHeader.add("F635_Mean_B635");
		this.rawDataHeader.add("F635_Total_Intensity");
//		this.rawDataHeader.add("SNR_635");
		
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
//		data = datactrl.readMinimalRawDataFromDb(reporterIds, rawDataHeader);
		
//		data = datactrl.convertDatabaseExportToFeatureVector("/home/patrick/Documents/DHBW/5Semester/Study_Works/antibodies/Data Analysis/AllFeaturesDbExport.csv");
//		data = datactrl.createFeatureVectorMean("/home/patrick/Documents/DHBW/5Semester/Study_Works/antibodies/Data Analysis/AllFeaturesDbExport.csv");
//		datactrl.writeRawDataMiniToFile("/home/patrick/Documents/DHBW/5Semester/Study_Works/antibodies/Data Analysis/test.csv",data, rawDataHeader, reporterIds);
//		for(String s : rawDataHeader){
//			ArrayList<String> tmp = new ArrayList<String>();
//			tmp.add(s);
//			datactrl.writeRawDataMiniToFile("/home/patrick/Documents/DHBW/5Semester/Study_Works/antibodies/Data Analysis/"+ s + "_Vector_mean.csv"
//				,data, tmp, reporterIds);
//		}
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
		
//		datactrl.importProtoArrayProcessedDataInDatabase("/mnt/data/Study_Works_blob/antibody/ProtoArrayAnalyzedData/PD vs NPC/");
		
//		datactrl.writeGivenProcessedDataFromCSVToArff("/home/patrick/Documents/DHBW/5Semester/Study_Works/antibodies/Data Analysis/TestingSet.csv"
//				,"/home/patrick/Documents/DHBW/5Semester/Study_Works/antibodies/Data Analysis/TestingSet.arff");
		datactrl.writeGivenProcessedDataFromCSVToArff("/home/patrick/Documents/DHBW/5Semester/Study_Works/antibodies/Data Analysis/TrainingSet.csv"
				,"/home/patrick/Documents/DHBW/5Semester/Study_Works/antibodies/Data Analysis/TrainingSet.arff");
		datactrl.closeConnection();
	}
	
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Program();
		System.out.println("Finish");
	}

}
