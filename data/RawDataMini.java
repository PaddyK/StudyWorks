package data;

public class RawDataMini implements Comparable<RawDataMini>{
	private int set = 0;
	public int getSet(){return set;}
	
	protected Double dia;
	/**
	 * Patient Id, identifies patient
	 */
	protected String patientId;
	/**
	 * Median of pixel intensities for wavelength of 635nm
	 */	
	protected Double f635median;
	/**
	 * Mean of pixel intensities for wavelength of 635nm
	 */
	protected Double f635mean;
	/**
	 * Standard deviation of pixel intensities for wavelength of 635nm
	 */
	protected Double f635sd;
	/**
	 * Background fluoroscence value of pixel intensities for wavelength of 635nm
	 */
	protected Double b635;
	/**
	 * Background fluoroscence median of pixel intensities for wavelength of 635nm
	 */
	protected Double b635median;
	/**
	 * Background fluoroscence mean of pixel intensities for wavelength of 635nm
	 */
	protected Double b635mean;
	/**
	 * Background fluoroscence standard deviation of pixel intensities for wavelength of 635nm
	 */
	protected Double b635sd;
	/**
	 * the percentage of feature pixels with intensities more than one standard deviation above 
	 * the background pixel intensity, at wavelength 635nm
	 */
	protected Double percentGtB635AboutOneSD;
	/**
	 * the percentage of feature pixels with intensities more than two standard deviation above 
	 * the background pixel intensity, at wavelength 635nm
	 */
	protected Double percentGtB635AboutTwoSD;
	/**
	 * the percentage of feature pixels at wavelength 635nm that are saturated
	 */
	protected Double f635PercentageSaturation;
	/**
	 * Median of pixel intensities for wavelength of 635nm
	 */
	protected Double f532median;
	/**
	 * Mean of pixel intensities for wavelength of 635nm
	 */
	protected Double f532mean;
	/**
	 * Standard Deviation of pixel intensities for wavelength of 635nm
	 */
	protected Double f532sd;
	/**
	 * Background fluoroscence value of pixel intensities for wavelength of 635nm
	 */
	protected Double b532;
	/**
	 * Background fluoroscence median of pixel intensities for wavelength of 635nm
	 */
	protected Double b532median;
	/**
	 * Background fluoroscence mean of pixel intensities for wavelength of 635nm
	 */
	protected Double b532mean;
	/**
	 * Background fluoroscence standard deviation of pixel intensities for wavelength of 635nm
	 */
	protected Double b532sd;
	/**
	 * the percentage of feature pixels with intensities more than one standard deviation above 
	 * the background pixel intensity, at wavelength 532nm
	 */
	protected Double percentGtB532AboutOneSD;
	/**
	 * the percentage of feature pixels with intensities more than two standard deviation above 
	 * the background pixel intensity, at wavelength 532nm
	 */
	protected Double percentGtB532AboutTwoSD;
	/**
	 * the percentage of feature pixels at wavelength 635nm that are saturated
	 */
	protected Double f532PercentageSaturation;
	/**
	 * the ratio of the median intensities of each feature for each wavelength,
	 * with the median background subtracted
	 */
	protected Double ratioOfMedians;
	/**
	 * the ratio of the arithmetic mean intensities of each feature for each wavelength, 
	 * with the median background subtracted
	 */
	protected Double ratioOfMeans;
	/**
	 * the median of pixel-by-pixel ratios of pixel intensities, with the median background
	 * subtracted
	 */
	protected Double medianOfRatios;
	/**
	 * the geometric mean of the pixel-by-pixel ratios of pixel intensities, with the median
	 * background subtracted
	 */
	protected Double meanOfRatios;
	/**
	 * the geometric standard deviation of the pixel intensity ratios
	 */
	protected Double ratiosSD;
	/**
	 * the total number of feature pixels
	 */
	protected Double fPixel;
	/**
	 * the total number of background pixels
	 */
	protected Double bPixel;
	/**
	 * the sum of the median intensities for each wavelength, with the median background subtracted
	 */
	protected Double sumOfMedians;
	/**
	 * the sum of the arithmetic mean intensities for each wavelength, with the median
	 * background subtracted
	 */
	protected Double sumOfMeans;
	/**
	 * the median feature pixel intensity at wavelength 635nm with the median background subtracted
	 */
	protected Double f635MedianSubB635;
	/**
	 * the mean feature pixel intensity at wavelength 635nm with the median background subtracted
	 */
	protected Double f635MeanSubB635;
	/**
	 * the sum of feature pixel intensities at wavelength 635nm
	 */
	protected Double f635TotalIntensity;
	/**
	 * the signal-to-noise ratio at wavelength #1 - 635nm -, defined by 
	 * (Mean Foreground 1- Mean Background 1) / (Standard deviation of Background 1)
	 */
	protected Double f635SignalToNoiseRatio;
	/**
	 * Label for data, yes patient has parkinson, no patient does not have parkinson
	 */
	protected String label;
	/**
	 * Identifier of Antibody, database identifier NOT reporter ID
	 */
	protected String antibodyId;
	
	public RawDataMini() {
		dia = null;
		patientId = null;
		f635median = null;
		f635mean = null;
		f635sd = null;
		b635 = null;
		b635median = null;
		b635mean = null;
		b635sd = null;
		percentGtB635AboutOneSD = null;
		percentGtB635AboutTwoSD = null;
		f635PercentageSaturation = null;
		f532median = null;
		f532mean = null;
		f532sd = null;
		b532 = null;
		b532median = null;
		b532mean = null;
		b532sd = null;
		percentGtB532AboutOneSD = null;
		percentGtB532AboutTwoSD = null;
		f532PercentageSaturation = null;
		ratioOfMedians = null;
		ratioOfMeans = null;
		medianOfRatios = null;
		meanOfRatios = null;
		ratiosSD = null;
		fPixel = null;
		bPixel = null;
		sumOfMedians = null;
		sumOfMeans = null;
		f635MedianSubB635 = null;
		f635MeanSubB635 = null;
		f635TotalIntensity = null;
		f635SignalToNoiseRatio = null;
		label = null;
		antibodyId = null;
	}
	
	
	public String getAntibodyId() {
		return antibodyId;
	}
	public void setAntibodyId(String antibodyId) {
		this.antibodyId = antibodyId;
	}
	public String getPatientId() {
		return patientId;
	}
	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}
//	public double getF635median() {
//		return f635median;
//	}
//	public double getF635mean() {
//		return f635mean;
//	}
//	public void setF635mean(double f635mean) {
//		if(this.f635mean == null)
//			this.f635mean = f635mean;
//		else
//			this.f635mean = (this.f635mean + f635mean) / 2;
//	}
//	public double getF635sd() {
//		return f635sd;
//	}
//	public void setF635sd(double f635sd) {
//		if(this.f635sd == null)
//			this.f635sd = f635sd;
//		else
//			this.f635sd = (this.f635sd + f635sd) / 2;
//	}
//	public double getB635() {
//		return b635;
//	}
//	public void setB635(double b635) {
//		if(this.b635 == null)
//			this.b635 = b635;
//		else
//			this.b635 = (this.b635 + b635) / 2;
//	}
//	public double getB635median() {
//		return b635median;
//	}
//	public void setB635median(double b635median) {
//		if(this.b635median == null)
//			this.b635median = b635median;
//		else
//			this.b635median = (this.b635median + b635median) / 2;
//	}
//	public double getB635mean() {
//		return b635mean;
//	}
//	public void setB635mean(double b635mean) {
//		if(this.b635mean == null)
//			this.b635mean = b635mean;
//		else
//			this.b635mean = (this.b635mean + b635mean) / 2;
//	}
//	public double getB635sd() {
//		return b635sd;
//	}
//	public void setB635sd(double b635sd) {
//		if(this.b635sd == null)
//			this.b635sd = b635sd;
//		else
//			this.b635sd = (this.b635sd + b635sd) / 2;
//	}
//	public double getF532median() {
//		return f532median;
//	}
//	public void setF532median(double f532median) {
//		if(this.f532median == null)
//			this.f532median = f532median;
//		else
//			this.f532median = (this.f532median + f532median) / 2;
//	}
//	public double getF532mean() {
//		return f532mean;
//	}
//	public void setF532mean(double f532mean) {
//		if(this.f532mean == null)
//			this.f532mean = f532mean;
//		else
//			this.f532mean = (this.f532mean + f532mean) / 2;
//	}
//	public double getF532sd() {
//		return f532sd;
//	}
//	public void setF532sd(double f532sd) {
//		if(this.f532sd == null)
//			this.f532sd = f532sd;
//		else
//			this.f532sd = (this.f532sd + f532sd) / 2;
//	}
//	public double getB532() {
//		return b532;
//	}
//	public void setB532(double b532) {
//		if(this.b532 == null)
//			this.b532 = b532;
//		else
//			this.b532 = (this.b532 + b532) / 2;
//	}
//	public double getB532median() {
//		return b532median;
//	}
//	public void setB532median(double b532median) {
//		if(this.b532median == null)
//			this.b532median = b532median;
//		else
//			this.b532median = (this.b532median + b532median) / 2;
//	}
//	public double getB532mean() {
//		return b532mean;
//	}
//	public void setB532mean(double b532mean) {
//		if(this.b532mean == null)
//			this.b532mean = b532mean;
//		else
//			this.b532mean = (this.b532mean + b532mean) / 2;
//	}
//	public double getB532sd() {
//		return b532sd;
//	}
//	public void setB532sd(double b532sd) {
//		if(this.b532sd == null)
//			this.b532sd = b532sd;
//		else
//			this.b532sd = (this.b532sd + b532sd) / 2;
//	}
	public String getLabel() {
		return this.label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	
	
	public void setDia(Double dia) {
		if(this.dia == null)
			this.dia = dia;
		else
			this.dia = (this.dia + dia) / 2; 
	}
	public void setF635median(Double f635median) {
		if(this.f635median == null)
			this.f635median = f635median;
		else
			this.f635median = (this.f635median + f635median) / 2;
	}
	public void setF635mean(Double f635mean) {
		if(this.f635mean == null)
			this.f635mean = f635mean;
		else
			this.f635mean = (this.f635mean + f635mean) / 2;
	}
	public void setF635sd(Double f635sd) {
		if(this.f635sd == null)
			this.f635sd = f635sd;
		else
			this.f635sd = (this.f635sd + f635sd) / 2;
	}
	public void setB635(Double b635) {
		if(this.b635 == null)
			this.b635 = b635;
		else
			this.b635 = (this.b635 + b635) / 2;
	}
	public void setB635median(Double b635median) {
		if(this.b635median == null)
			this.b635median = b635median;
		else
			this.b635median = (this.b635median + b635median) / 2;
	}
	public void setB635mean(Double b635mean) {
		if(this.b635mean == null)
			this.b635mean = b635mean;
		else
			this.b635mean = (this.b635mean + b635mean) / 2;
	}
	public void setB635sd(Double b635sd) {
		if(this.b635sd == null)
			this.b635sd = b635sd;
		else
			this.b635sd = (this.b635sd + b635sd) / 2;
	}
	public void setPercentGtB635AboutOneSD(Double percentGtB635AboutOneSD) {
		if(this.percentGtB635AboutOneSD == null)
			this.percentGtB635AboutOneSD = percentGtB635AboutOneSD;
		else
			this.percentGtB635AboutOneSD = (this.percentGtB635AboutOneSD + percentGtB635AboutOneSD) / 2;
	}
	public void setPercentGtB635AboutTwoSD(Double percentGtB635AboutTwoSD) {
		if(this.percentGtB635AboutTwoSD == null)
			this.percentGtB635AboutTwoSD = percentGtB635AboutTwoSD;
		else
			this.percentGtB635AboutTwoSD = (this.percentGtB635AboutTwoSD + percentGtB635AboutTwoSD) / 2;
	}
	public void setF635PercentageSaturation(Double f635PercentageSaturation) {
		if(this.f635PercentageSaturation == null)
			this.f635PercentageSaturation = f635PercentageSaturation;
		else
			this.f635PercentageSaturation = (this.f635PercentageSaturation + f635PercentageSaturation) / 2;
	}
	public void setF532median(Double f532median) {
		if(this.f532median == null)
			this.f532median = f532median;
		else
			this.f532median = (this.f532median + f532median) / 2;
	}
	public void setF532mean(Double f532mean) {
		if(this.f532mean == null)
			this.f532mean = f532mean;
		else
			this.f532mean = (this.f532mean + f532mean) / 2;
	}
	public void setF532sd(Double f532sd) {
		if(this.f532sd == null)
			this.f532sd = f532sd;
		else
			this.f532sd = (this.f532sd + f532sd) / 2;
	}
	public void setB532(Double b532) {
		if(this.b532 == null)
			this.b532 = b532;
		else
			this.b532 = (this.b532 + b532) / 2;
	}
	public void setB532median(Double b532median) {
		if(this.b532median == null)
			this.b532median = b532median;
		else
			this.b532median = (this.b532median + b532median) / 2;
	}
	public void setB532mean(Double b532mean) {
		if(this.b532mean == null)
			this.b532mean = b532mean;
		else
			this.b532mean = (this.b532mean + b532mean) / 2;
	}
	public void setB532sd(Double b532sd) {
		if(this.b532sd == null)
			this.b532sd = b532sd;
		else
			this.b532sd = (this.b532sd + b532sd) / 2;
	}
	public void setPercentGtB532AboutOneSD(Double percentGtB532AboutOneSD) {
		if(this.percentGtB532AboutOneSD == null)
			this.percentGtB532AboutOneSD = percentGtB532AboutOneSD;
		else
			this.percentGtB532AboutOneSD = (this.percentGtB532AboutOneSD + percentGtB532AboutOneSD) / 2;
	}
	public void setPercentGtB532AboutTwoSD(Double percentGtB532AboutTwoSD) {
		if(this.percentGtB532AboutTwoSD == null)
			this.percentGtB532AboutTwoSD = percentGtB532AboutTwoSD;
		else
			this.percentGtB532AboutTwoSD = (this.percentGtB532AboutTwoSD + percentGtB532AboutTwoSD) / 2;
	}
	public void setF532PercentageSaturation(Double f532PercentageSaturation) {
		if(this.f532PercentageSaturation == null)
			this.f532PercentageSaturation = f532PercentageSaturation;
		else
			this.f532PercentageSaturation = (this.f532PercentageSaturation + f532PercentageSaturation) / 2;
	}
	public void setRatioOfMedians(Double ratioOfMedians) {
		if(this.ratioOfMedians == null)
			this.ratioOfMedians = ratioOfMedians;
		else
			this.ratioOfMedians = (this.ratioOfMedians + ratioOfMedians) / 2;
	}
	public void setRatioOfMeans(Double ratioOfMeans) {
		if(this.ratioOfMeans == null)
			this.ratioOfMeans = ratioOfMeans;
		else
			this.ratioOfMeans = (this.ratioOfMeans + ratioOfMeans) / 2;
	}
	public void setMedianOfRatios(Double medianOfRatios) {
		if(this.medianOfRatios == null)
			this.medianOfRatios = medianOfRatios;
		else
			this.medianOfRatios = (this.medianOfRatios + medianOfRatios) / 2;
	}
	public void setMeanOfRatios(Double meanOfRatios) {
		if(this.meanOfRatios == null)
			this.meanOfRatios = meanOfRatios;
		else
			this.meanOfRatios = (this.meanOfRatios + meanOfRatios) / 2;
	}
	public void setRatiosSD(Double ratiosSD) {
		if(this.ratiosSD == null)
			this.ratiosSD = ratiosSD;
		else
			this.ratiosSD = (this.ratiosSD + ratiosSD) / 2;
	}
	public void setfPixel(Double fPixel) {
		if(this.fPixel == null)
			this.fPixel = fPixel;
		else
			this.fPixel = (this.fPixel + fPixel) / 2;
	}
	public void setbPixel(Double bPixel) {
		if(this.bPixel == null)
			this.bPixel = bPixel;
		else
			this.bPixel = (this.bPixel + bPixel) / 2;
	}
	public void setSumOfMedians(Double sumOfMedians) {
		if(this.sumOfMedians == null)
			this.sumOfMedians = sumOfMedians;
		else
			this.sumOfMedians = (this.sumOfMedians + sumOfMedians) / 2;
	}
	public void setSumOfMeans(Double sumOfMeans) {
		if(this.sumOfMeans == null)
			this.sumOfMeans = sumOfMeans;
		else
			this.sumOfMeans = (this.sumOfMeans + sumOfMeans) / 2;
	}
	public void setF635MedianSubB635(Double f635MedianSubB635) {
		if(this.f635MedianSubB635 == null)
			this.f635MedianSubB635 = f635MedianSubB635;
		else
			this.f635MedianSubB635 = (this.f635MedianSubB635 + f635MedianSubB635) / 2;
	}
	public void setF635MeanSubB635(Double f635MeanSubB635) {
		if(this.f635MeanSubB635 == null)
			this.f635MeanSubB635 = f635MeanSubB635;
		else
			this.f635MeanSubB635 = (this.f635MeanSubB635 + f635MeanSubB635) / 2;
	}
	public void setF635TotalIntensity(Double f635TotalIntensity) {
		if(this.f635TotalIntensity == null)
			this.f635TotalIntensity = f635TotalIntensity;
		else
			this.f635TotalIntensity = (this.f635TotalIntensity + f635TotalIntensity) / 2;
	}
	public void setF635SignalToNoiseRatio(Double f635SignalToNoiseRatio) {
		if(this.f635SignalToNoiseRatio == null)
			this.f635SignalToNoiseRatio = f635SignalToNoiseRatio;
		else
			this.f635SignalToNoiseRatio = (this.f635SignalToNoiseRatio + f635SignalToNoiseRatio) / 2;
	}
	/**
	 * Returns properties of object as tab separated string
	 * @return List of attributes separated by tabulator
	 */
	@Override
	public String toString() {
		return patientId + "\t" + f635mean + "\t" + f635median + "\t" + f635sd + "\t" + b635 + "\t" + b635mean + "\t" +
				b635median + "\t" + b635sd + "\t" + f532mean + "\t" + f532median + "\t" + f532sd + "\t" + b532 + "\t" +
				b532mean + "\t" + b532median + "\t" + b532sd + "\t" + label + "\t" + antibodyId;
	}
	
	public String getAttributeFromHeader(String header) {
		String ret = null;
		switch(header) {
		// Those two need only be added once for each feature vector
		case "Patient_id" : ret = this.patientId;
			break;
		case "Label" : ret = this.label;
			break;
		case "F635_Median" : ret = "" + this.f635median;
			break;
		case "F635_Mean" : ret = "" + this.f635mean;
			break;
		case "F635_SD" : ret = "" + this.f635sd;
			break;
		case "B635" : ret = "" + this.b635;
			break;
		case "B635_Median" : ret = "" + this.b635median;
			break;
		case "B635_Mean" : ret = "" + this.b635mean;
			break;
		case "B635_SD" : ret = "" + this.b635sd;
			break;
		case "F532_Median" : ret = "" + this.f532median;
			break;
		case "F532_Mean" : ret = "" + this.f532mean;
			break;
		case "F532_SD" : ret = "" + this.f532sd;
			break;
		case "B532" : ret = "" + this.b532;
			break;
		case "B532_Median" : ret = "" + this.b532median;
			break;
		case "B532_Mean" : ret = "" + this.b532mean;
			break;
		case "B532_SD" : ret = "" + this.b532sd;
			break;
		case "Dia" : ret = "" + this.dia;
			break;
		case "percent_gt_B635_1SD" : ret = "" + this.percentGtB635AboutOneSD;
			break;
		case "percent_gt_B635_2SD" : ret = "" + this.percentGtB635AboutTwoSD;
			break;
		case "F635_percent_Sat" : ret = "" + this.f635PercentageSaturation;
			break;
		case "percent_gt_B532_1SD" : ret = "" + this.percentGtB532AboutOneSD;
			break;
		case "percentage_gt_B532_2SD" : ret = "" + this.percentGtB532AboutTwoSD;
			break;
		case "F532_percentage_Sat" : ret = "" + this.f532PercentageSaturation;
			break;
		case "Ratio_of_Medians_635_532" : ret = "" + this.ratioOfMedians;
			break;
		case "Ratio_of_Means_635_532" : ret = "" + this.ratioOfMeans;
			break;
		case "Median_of_Ratios_635_532" : ret = "" + this.medianOfRatios;
			break;
		case "Mean_of_Ratios_635_532" : ret = "" + this.meanOfRatios;
			break;
		case "Ratios_SD_635_532" : ret = "" + this.ratiosSD;
			break;
		case "F_Pixels" : ret = "" + this.fPixel;
			break;
		case "B_Pixels" : ret = "" + this.bPixel;
			break;
		case "Sum_of_Medians_635_532" : ret = "" + this.sumOfMedians;
			break;
		case "Sum_of_Means_635_532" : ret = "" + this.sumOfMeans;
			break;
		case "F635_Median_B635" : ret = "" + this.f635median;
			break;
		case "F635_Mean_B635" : ret = "" + this.f635mean;
			break;
		case "F635_Total_Intensity" : ret = "" + this.f635TotalIntensity;
			break;
		case "SNR_635" : ret = "" + this.f635SignalToNoiseRatio;
			break;
		case "DatabaseId" : ret = this.antibodyId;
	
		}
		return ret;
	}
	
	public void setAttributeFromHeader(String header, Object value) {
		set++;
		if(value.getClass().getName().equals("java.lang.String") && !header.equals("Patient_id") && !header.equals("Label") && !header.equals("DatabaseId"))
			value = Double.parseDouble((String)value);
		
		switch(header) {
		// Those two need only be added once for each feature vector
		case "Patient_id" : setPatientId((String)value);
			break;
		case "Label" : setLabel((String)value);
			break;
		case "F635_Median" : setF635median((double)value);
			break;
		case "DatabaseId" : setAntibodyId((String)value);
			break;
		case "F635_Mean" : setF635mean((double)value);
			break;
		case "F635_SD" : setF635sd((double)value);
			break;
		case "B635" : setB635((double)value);
			break;
		case "B635_Median" : setB635median((double)value);
			break;
		case "B635_Mean" : setB635mean((double)value);
			break;
		case "B635_SD" : setB635sd((double)value);
			break;
		case "F532_Median" : setF532median((double)value);
			break;
		case "F532_Mean" : setF532mean((double)value);
			break;
		case "F532_SD" : setF532sd((double)value);
			break;
		case "B532" : setB532((double)value);
			break;
		case "B532_Median" : setB532median((double)value);
			break;
		case "B532_Mean" : setB532mean((double)value);
			break;
		case "B532_SD" : setB532sd((double)value);
			break;
		case "Dia" : setDia((double)value);
			break;
		case "percent_gt_B635_1SD" : setPercentGtB635AboutOneSD((double)value);
			break;
		case "percent_gt_B635_2SD" : setPercentGtB635AboutTwoSD((double)value);
			break;
		case "F635_percent_Sat" : setF635PercentageSaturation((double)value);
			break;
		case "percent_gt_B532_1SD" : setPercentGtB532AboutOneSD((double)value);
			break;
		case "percentage_gt_B532_2SD" : setPercentGtB532AboutTwoSD((double)value);
			break;
		case "F532_percentage_Sat" : setF532PercentageSaturation((double)value);
			break;
		case "Ratio_of_Medians_635_532" : setRatioOfMedians((double)value);
			break;
		case "Ratio_of_Means_635_532" : setRatioOfMeans((double)value);
			break;
		case "Median_of_Ratios_635_532" : setMedianOfRatios((double)value);
			break;
		case "Mean_of_Ratios_635_532" : setMeanOfRatios((double)value);
			break;
		case "Ratios_SD_635_532" : setRatiosSD((double)value);
			break;
		case "F_Pixels" : setfPixel((double)value);
			break;
		case "B_Pixels" : setbPixel((double)value);
			break;
		case "Sum_of_Medians_635_532" : setSumOfMedians((double)value);
			break;
		case "Sum_of_Means_635_532" : setSumOfMeans((double)value);
			break;
		case "F635_Median_B635" : setF635MedianSubB635((double)value);
			break;
		case "F635_Mean_B635" : setF635MeanSubB635((double)value);
			break;
		case "F635_Total_Intensity" : setF635TotalIntensity((double)value);
			break;
		case "SNR_635" : setF635SignalToNoiseRatio((double)value);
			break;
		}
	}
	
	@Override	
	public int compareTo(RawDataMini arg0) {
		int own = 0,
			other = 0;
		for(int i = 0; i < this.antibodyId.length(); i++)
			own += (int)this.antibodyId.charAt(i);
		for(int i = 0; i < arg0.antibodyId.length(); i++)
			other = (int)arg0.antibodyId.charAt(i);
		
		if(own > other)
			return 1;
		else if(own < other)
			return -1;
		else
			return 0;
	}
}