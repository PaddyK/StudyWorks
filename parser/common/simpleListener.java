// Generated from C:\Users\kalmbach\workspace\configFileCompiler\src\parser\simple.g4 by ANTLR 4.1

package parser.common;

import java.util.List;
import java.util.ArrayList;
import org.antlr.v4.runtime.Parser;
import parser.*;
         
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link simpleParser}.
 */
public interface simpleListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link simpleParser#seqval}.
	 * @param ctx the parse tree
	 */
	void enterSeqval(@NotNull simpleParser.SeqvalContext ctx);
	/**
	 * Exit a parse tree produced by {@link simpleParser#seqval}.
	 * @param ctx the parse tree
	 */
	void exitSeqval(@NotNull simpleParser.SeqvalContext ctx);

	/**
	 * Enter a parse tree produced by {@link simpleParser#document}.
	 * @param ctx the parse tree
	 */
	void enterDocument(@NotNull simpleParser.DocumentContext ctx);
	/**
	 * Exit a parse tree produced by {@link simpleParser#document}.
	 * @param ctx the parse tree
	 */
	void exitDocument(@NotNull simpleParser.DocumentContext ctx);

	/**
	 * Enter a parse tree produced by {@link simpleParser#resdef}.
	 * @param ctx the parse tree
	 */
	void enterResdef(@NotNull simpleParser.ResdefContext ctx);
	/**
	 * Exit a parse tree produced by {@link simpleParser#resdef}.
	 * @param ctx the parse tree
	 */
	void exitResdef(@NotNull simpleParser.ResdefContext ctx);

	/**
	 * Enter a parse tree produced by {@link simpleParser#classifier}.
	 * @param ctx the parse tree
	 */
	void enterClassifier(@NotNull simpleParser.ClassifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link simpleParser#classifier}.
	 * @param ctx the parse tree
	 */
	void exitClassifier(@NotNull simpleParser.ClassifierContext ctx);

	/**
	 * Enter a parse tree produced by {@link simpleParser#loocv}.
	 * @param ctx the parse tree
	 */
	void enterLoocv(@NotNull simpleParser.LoocvContext ctx);
	/**
	 * Exit a parse tree produced by {@link simpleParser#loocv}.
	 * @param ctx the parse tree
	 */
	void exitLoocv(@NotNull simpleParser.LoocvContext ctx);

	/**
	 * Enter a parse tree produced by {@link simpleParser#attribute}.
	 * @param ctx the parse tree
	 */
	void enterAttribute(@NotNull simpleParser.AttributeContext ctx);
	/**
	 * Exit a parse tree produced by {@link simpleParser#attribute}.
	 * @param ctx the parse tree
	 */
	void exitAttribute(@NotNull simpleParser.AttributeContext ctx);

	/**
	 * Enter a parse tree produced by {@link simpleParser#ressources}.
	 * @param ctx the parse tree
	 */
	void enterRessources(@NotNull simpleParser.RessourcesContext ctx);
	/**
	 * Exit a parse tree produced by {@link simpleParser#ressources}.
	 * @param ctx the parse tree
	 */
	void exitRessources(@NotNull simpleParser.RessourcesContext ctx);

	/**
	 * Enter a parse tree produced by {@link simpleParser#simpleclassifier}.
	 * @param ctx the parse tree
	 */
	void enterSimpleclassifier(@NotNull simpleParser.SimpleclassifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link simpleParser#simpleclassifier}.
	 * @param ctx the parse tree
	 */
	void exitSimpleclassifier(@NotNull simpleParser.SimpleclassifierContext ctx);

	/**
	 * Enter a parse tree produced by {@link simpleParser#complexclassifier}.
	 * @param ctx the parse tree
	 */
	void enterComplexclassifier(@NotNull simpleParser.ComplexclassifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link simpleParser#complexclassifier}.
	 * @param ctx the parse tree
	 */
	void exitComplexclassifier(@NotNull simpleParser.ComplexclassifierContext ctx);
}