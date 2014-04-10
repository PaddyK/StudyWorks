package data;

/**
 * This class represents one spot on the microarray
 * @author Patrick
 *
 */
public class Record {
	/**
	 * The mean foreground signal from gpr file. Mean because not affected by outliers
	 */
	private int foreground;
	
	/**
	 * The mean backgournd intensity from gpr file
	 */
	private int background;
	
	/**
	 * Signal value, i.e. foreground - background
	 */
	private int signal;
	
	/**
	 * The normalized signal value
	 */
	private double normalizedSignal;
	
	/**
	 * Id for an antibody
	 */
	private String antibodyId;
	
	private String description;
	
	private boolean isControl;
	
	private boolean isEmpty;
	
	public Record(){
		isEmpty = false;
	};
	
	public Record(String id, int fg, int bg) {
		foreground = fg;
		background = bg;
		antibodyId = id;
		signal = foreground - background;
		
		isEmpty = false;
	}
	
	/**
	 * Fills this record with data from a gpr file
	 * @param record	One row from the gpr file
	 */
	public void fillWithData(String record) {
		String[] attributes = record.split("\t");
		
		background = Integer.parseInt(attributes[10].trim());
		foreground = Integer.parseInt(attributes[5].trim());
		signal = foreground - background;
		normalizedSignal = signal; // In order to allow no normalization
		description = attributes[53].trim();
		
		int colon = attributes[0].indexOf(':') + 1;
		if(description.contains("Control")) {
			isControl = true;
			isEmpty = false;
			int tilde = attributes[0].indexOf('~');
			antibodyId = attributes[0].trim().substring(0, (tilde == -1)? attributes[0].trim().length() : tilde);
		}
		else if(attributes[0].trim().contains("Empty")){
			isEmpty = true;
			isControl = false;
			antibodyId = attributes[0].trim();
		}
		else {
			isControl = false;
			isEmpty = false;
			int tilde = attributes[0].indexOf('~', colon);
			antibodyId = attributes[0].trim().substring(colon, tilde);
		}
	}

	public int getForeground() {
		return foreground;
	}

	public void setForeground(int foreground) {
		this.foreground = foreground;
	}

	public int getBackground() {
		return background;
	}

	public void setBackground(int background) {
		this.background = background;
	}

	public int getSignal() {
		return signal;
	}

	public void setSignal(int signal) {
		this.signal = signal;
	}

	public double getNormalizedSignal() {
		return normalizedSignal;
	}

	public void setNormalizedSignal(double normalizedSignal) {
		this.normalizedSignal = normalizedSignal;
	}

	public String getAntibodyId() {
		return antibodyId;
	}

	public void setAntibodyId(String antibodyId) {
		this.antibodyId = antibodyId;
	}
	
	public boolean isControl() {
		return isControl;
	}
	
	public boolean isEmpty() {
		return isEmpty;
	}
}