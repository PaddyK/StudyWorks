package data;

public abstract interface Normalization {
	
	/**
	 * Performs normalization of an microarray. To be implemented in subclass
	 * @param microarray	Microarray on which normalization will be performed
	 */
	public abstract void normalize(Microarray microarray);
}