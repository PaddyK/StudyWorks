package data;

/**
 * This class represents one block of a microarray and holds 22 * 22 records
 * @author Patrick
 *
 */
public class Block {
	
	private int blockId;
	
	/**
	 * Number of columns of one block
	 */
	private int numCol;
	
	/**
	 * Number of rows of one block
	 */
	private int numRow;
	
	/**
	 * Represents the matrix of records of each block. Important to note: addreessing ist the same
	 * like in octave or R --> first index row, second index column. For example records[3][4]
	 * represents row 3 column 4
	 */
	private Record[][] records;
	
	public Block(int id, int rows, int columns) {
		blockId = id;
		numCol = columns;
		numRow = rows;
		records = new Record[numRow][numCol];
	}
		
	/**
	 * Fills this block with data
	 * @param block	Part of a gpr file representing on block
	 */
	public void fillWithData(String block) {
		int row = 0; // index for rows
		int column = 0; // index for columns
		int endIdx; // index marking end of cell
		int startIdx; // index marking beginning of cell
		String colSep = "\t";
		String rowSep = System.getProperty("line.separator");
		String[] rows = block.split(rowSep);
		
		for(String line : rows) {
			if(!line.trim().isEmpty()){ // in case line is empty or only contains /n
				startIdx = line.indexOf(colSep) + 1; // skip the first cell and go to second cell, tab excluded
				endIdx = line.indexOf(colSep, startIdx); // find end of second cell
				
				column = Integer.parseInt(line.substring(startIdx, endIdx)); // parse column index
				// extract row
				startIdx = endIdx + 1; // start index for third cell is end index of second cell, tab excluded
				endIdx = line.indexOf(colSep, startIdx); // find new end index for third cell
				row = Integer.parseInt(line.substring(startIdx, endIdx)); // parse row index
				
				records[row - 1][column - 1] = new Record();
				//Exclude tab char, exclude line break char
				records[row - 1][column - 1].fillWithData(line.substring(endIdx + 1, line.length()));
			}
		}
	}
	
	public void setRecord(Record record, int col, int row) {
		records[row][col] = record;
	}
	
	/**
	 * Goes through all records and adds normalized signals to a string if feature is not an control
	 * feature
	 * @return	String with comma separated normalized Signals of all non-control features of 
	 * 			this block
	 */
	public String[] getNormalizedSignalsAsLine() {
		String line1 = ""; // Each feature printed in duplicate --> two lines per block
		String line2 = "";
		for(int i = 0; i < records.length; i++)
			for(int j = 0; j < records[i].length; j+=2)
				if(!records[i][j].isControl() && !records[i][j].isEmpty()) {
					line1 += "," + records[i][j].getNormalizedSignal();
					line2 += "," + records[i][j + 1].getNormalizedSignal();
				}

		return new String[]{line1, line2};
	}
	
	/**
	 * Creates a line of all antibody ids not being controls of this block
	 * @return	tab separated line of non-control antibody ids
	 */
	public String getNonControlFeatureIdsAsLine() {
		String line = "";
		for(int i = 0; i < records.length; i++)
			for(int j = 0; j < records[i].length; j+=2)
				if(!records[i][j].isControl() && !records[i][j].isEmpty())
					line += records[i][j].getAntibodyId() + "_" + blockId + "-" +i + "-" +j + "\t";
		
		return line;
	}
	
	/**
	 * Returns non control feature ids of this block in form for Arff Header
	 * @return	String containing antibody ids as arff header
	 */
	public String getArffHeader() {
		String line = "";
				
		for(int i = 0; i < records.length; i++)
			for(int j = 0; j < records[i].length; j+=2)
				if(!records[i][j].isControl() && !records[i][j].isEmpty()) {
					line += System.getProperty("line.separator") + 
						"@ATTRIBUTE\t" + records[i][j].getAntibodyId() + "_" + blockId + "-" + i + 
						"-" +j + "\tNUMERIC";
				}
		
		return line;
	}
	
	public Record getRecord(int col, int row) {
		return records[row][col];
	}
	
	public int getNumColumns() {
		return numCol;
	}
	
	public int getNumRows() {
		return numRow;
	}
	
	public int getBlockId() {
		return blockId;
	}
}