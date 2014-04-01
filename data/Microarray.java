package data;
import normalization.Normalization;

/**
 * This class represents a microarray. A microarray consists of 48 blocks. Each block of 22 * 22
 * records.
 * This class represents a gpr file and reconstructs the physical structure of the array
 * @author Patrick
 *
 */
public class Microarray {
	/** 12 x 4 Matrix of blocks of micorarray. Numbers taken from protoarray microarray picture
	 * http://tools.lifetechnologies.com/content/sfs/manuals/protoarray_applicationsguide_man.pdf
	 * page 4 - Array Specification
	 */
	private Block[][] blocks;
	private String disease;
	private String patientId;
	private int blockRows;
	private int blockColumns;
	private int featureRows;
	private int featureColumns;
	
	public Microarray(String disease, String id) {
		blockRows = 12;
		blockColumns = 4;
		featureRows = 22;
		featureColumns = 22;
		blocks = new Block[blockRows][blockColumns];
		this.disease = disease;
		patientId = id;
	}
	
	/**
	 * Takes the data from a previously read String Buffer and creates blocks
	 * @param gprFile
	 */
	public void fillWithData(StringBuffer gprFile) {
		int blockId;
		int blockIdNext;
		int startHeader;
		int endHeader;
		int beginFirstCell;
		int endFirstCell;
		int startBlock;
		int endBlock;
		int rowIdx, colIdx;
		
		String beginHeader = "Block";
		String colSep = "\t";
		String rowSep = System.getProperty("line.separator");
		
		startHeader = gprFile.indexOf(beginHeader); // Find begin of header line
		endHeader = gprFile.indexOf(rowSep, startHeader); // Find end of header line
		
		beginFirstCell = endHeader + 1; // begin of first data cell is end of header line + 1
		endFirstCell = gprFile.indexOf(colSep, beginFirstCell); // end of first cell is position of tab
		blockId = Integer.parseInt(gprFile.substring(beginFirstCell, endFirstCell).trim());
		
		startBlock = beginFirstCell; // the first block starts at this position
		
		// do while a new line is found and found place of new line is smaller than the length of
		// the string buffer - 2 (-1 because string indexing zero based, another -1 just to be sure ;))
		while(blockId < 48) {
			beginFirstCell = gprFile.indexOf(rowSep, endFirstCell) + 1; // find end of line
			endFirstCell = gprFile.indexOf(colSep, beginFirstCell); // end of Block cell
			blockIdNext = Integer.parseInt(gprFile.substring(beginFirstCell, endFirstCell).trim());
			
			// If the new blockId is different from the old one a new block starts --> create block
			if(blockIdNext > blockId) {
				rowIdx = (int)((blockId - 1) / 4); 	// calculate row, col, substract 1 because 
				colIdx = (blockId - 1) % 4;			// arrays zero based
				
				endBlock = beginFirstCell; // At this point we are in the first row of the next block
											// therefore end of block and start of new block are at
											// begin first cell
				
				blocks[rowIdx][colIdx] = new Block(blockId, featureRows, featureColumns);
				// extract the block
				blocks[rowIdx][colIdx].fillWithData(gprFile.substring(startBlock, endBlock));
				
				startBlock = endBlock; // start of new block is end of old block
				blockId = blockIdNext;
			}			
		}

		rowIdx = (int)((blockId - 1) / 4); 	// calculate row, col, substract 1 because 
		colIdx = (blockId - 1) % 4;			// arrays zero based
		
		blocks[rowIdx][colIdx] = new Block(blockId, featureRows, featureColumns);
		// extract the block 
		blocks[rowIdx][colIdx].fillWithData(gprFile.substring(startBlock, gprFile.length()));
	}

	/**
	 * Perform normalization on this microarray
	 * @param normalizationMethod	Normalization method of choice
	 */
	public void normalize(Normalization normalizationMethod) {
		normalizationMethod.normalize(this);
	}
	
	/**
	 * Creates a line of all normalized signals of non-control features
	 * @return	comma separated line of normalized of all non-control signals of this array
	 */
	public String getNormalizedSignalsAsLine() {
		String line1 = disease; // Each mircorarray has features printed in duplicate
		String line2 = disease; // therefore two lines of signals for each microarray
		String[] blockRet;
		for(int i = 0; i < blocks.length; i++)
			for(int j = 0; j < blocks[i].length; j++) {
				blockRet = blocks[i][j].getNormalizedSignalsAsLine();
				line1 += blockRet[0];
				line2 += blockRet[1];
			}
		return line1.trim() + System.getProperty("line.separator") + line2.trim() +
				System.getProperty("line.separator");
	}
	
	/**
	 * Creates a line of all antibody ids not being controls
	 * @return	tab separated line of non-control antibody ids
	 */
	public String getNonControlFeatureIdsAsLine() {
		String line = "";
		for(int i = 0; i < blocks.length; i++)
			for(int j = 0; j < blocks[i].length; j++)
				line += blocks[i][j].getNonControlFeatureIdsAsLine();
		return line.trim();
	}
	
	/**
	 * Returns an arff header where all attributes are declared (disease as nominal attribute and
	 * antibody ids as numeric attributes). @RELATION-attribute is not included.
	 * @return	arff header with all attributes declared
	 */
	public String getArffHeader() {
		String line = "@ATTRIBUTE disease {PD,BC,MS,AD,NDC}";
		for(int i = 0; i < blocks.length; i++)
			for(int j = 0; j < blocks[i].length; j++)
				line += blocks[i][j].getArffHeader();
		return line.trim();
	}
	
	public String getDisease() {
		return disease;
	}
	
	public String getPatientId() {
		return patientId;
	}
	
	public int getBlockRows() {
		return blockRows;
	}	

	public int getBlockColumns() {
		return blockColumns;
	}	

	public int getFeatureRows() {
		return featureRows;
	}	

	public int getFeatureColumns() {
		return featureColumns;
	}

	public Block getBlock(int row, int col) {
		return blocks[row][col];
	}
}