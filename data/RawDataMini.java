package data;

public class RawDataMini {
	/**
	 * Patient Id, identifies patient
	 */
	protected String patientId;
	/**
	 * Nominal feature, yes --> has parkinson, no --> no parkinson
	 */
	protected String hasParkinson;
	/**
	 * Median of pixel intensities for wavelength of 635nm
	 */	
	protected double f635median;
	/**
	 * Mean of pixel intensities for wavelength of 635nm
	 */
	protected double f635mean;
	/**
	 * Standard deviation of pixel intensities for wavelength of 635nm
	 */
	protected double f635sd;
	/**
	 * Background fluoroscence value of pixel intensities for wavelength of 635nm
	 */
	protected double b635;
	/**
	 * Background fluoroscence median of pixel intensities for wavelength of 635nm
	 */
	protected double b635median;
	/**
	 * Background fluoroscence mean of pixel intensities for wavelength of 635nm
	 */
	protected double b635mean;
	/**
	 * Background fluoroscence standard deviation of pixel intensities for wavelength of 635nm
	 */
	protected double b635sd;
	/**
	 * Median of pixel intensities for wavelength of 635nm
	 */
	protected double f532median;
	/**
	 * Mean of pixel intensities for wavelength of 635nm
	 */
	protected double f532mean;
	/**
	 * Standard Deviation of pixel intensities for wavelength of 635nm
	 */
	protected double f532sd;
	/**
	 * Background fluoroscence value of pixel intensities for wavelength of 635nm
	 */
	protected double b532;
	/**
	 * Background fluoroscence median of pixel intensities for wavelength of 635nm
	 */
	protected double b532median;
	/**
	 * Background fluoroscence mean of pixel intensities for wavelength of 635nm
	 */
	protected double b532mean;
	/**
	 * Background fluoroscence standard deviation of pixel intensities for wavelength of 635nm
	 */
	protected double b532sd;
	
	public String getPatientId() {
		return patientId;
	}
	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}
	
	public String getHasParkinson() {
		return hasParkinson;
	}
	public void setHasParkinson(String hasParkinson) {
		this.hasParkinson = hasParkinson;
	}
	public double getF635median() {
		return f635median;
	}
	public void setF635median(double f635median) {
		this.f635median = f635median;
	}
	public double getF635mean() {
		return f635mean;
	}
	public void setF635mean(double f635mean) {
		this.f635mean = f635mean;
	}
	public double getF635sd() {
		return f635sd;
	}
	public void setF635sd(double f635sd) {
		this.f635sd = f635sd;
	}
	public double getB635() {
		return b635;
	}
	public void setB635(double b635) {
		this.b635 = b635;
	}
	public double getB635median() {
		return b635median;
	}
	public void setB635median(double b635median) {
		this.b635median = b635median;
	}
	public double getB635mean() {
		return b635mean;
	}
	public void setB635mean(double b635mean) {
		this.b635mean = b635mean;
	}
	public double getB635sd() {
		return b635sd;
	}
	public void setB635sd(double b635sd) {
		this.b635sd = b635sd;
	}
	public double getF532median() {
		return f532median;
	}
	public void setF532median(double f532median) {
		this.f532median = f532median;
	}
	public double getF532mean() {
		return f532mean;
	}
	public void setF532mean(double f532mean) {
		this.f532mean = f532mean;
	}
	public double getF532sd() {
		return f532sd;
	}
	public void setF532sd(double f532sd) {
		this.f532sd = f532sd;
	}
	public double getB532() {
		return b532;
	}
	public void setB532(double b532) {
		this.b532 = b532;
	}
	public double getB532median() {
		return b532median;
	}
	public void setB532median(double b532median) {
		this.b532median = b532median;
	}
	public double getB532mean() {
		return b532mean;
	}
	public void setB532mean(double b532mean) {
		this.b532mean = b532mean;
	}
	public double getB532sd() {
		return b532sd;
	}
	public void setB532sd(double b532sd) {
		this.b532sd = b532sd;
	}
	
	/**
	 * Returns properties of object as tab separated string
	 * @return List of attributes separated by tabulator
	 */
	@Override
	public String toString() {
		return patientId + "\t" + f635mean + "\t" + f635median + "\t" + f635sd + "\t" + b635 + "\t" + b635mean + "\t" +
				b635median + "\t" + b635sd + "\t" + f532mean + "\t" + f532median + "\t" + f532sd + "\t" + b532 + "\t" +
				b532mean + "\t" + b532median + "\t" + b532sd + "\t" + hasParkinson;
	}
}