package parser;
import org.antlr.v4.runtime.*;

import parser.common.*;

import java.io.*;
import java.util.List;

public class Parser {

	public static void main(String[] args) {
		try {
			parseFile("C:\\Users\\kalmbach\\workspace\\configFileCompiler\\src\\example5.xml");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void parseFile(String filename) throws Exception {
		try {
			ANTLRFileStream stream = new ANTLRFileStream(filename);
            simpleLexer       lexer  = new simpleLexer(stream);
            CommonTokenStream ts     = new CommonTokenStream(lexer);
            simpleParser      parser = new simpleParser(ts);
            List<Setup> setups = parser.document().setups;
            for(Setup p : setups) {
            	for(String s : p.getClassifier().getOptionsAsString()){
                	System.out.print("\n" + p.getClassifier().getPath() + " ");
            		System.out.print("" + s);
            	}
            }
		}
		catch(Exception e) {
			throw e;
		}
	}

}
