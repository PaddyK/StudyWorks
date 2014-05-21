// Generated from C:\Users\kalmbach\workspace\configFileCompiler\src\parser\simple.g4 by ANTLR 4.1

package parser.common;

import java.util.List;
import java.util.ArrayList;
import org.antlr.v4.runtime.Parser;
import parser.*;
         
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class simpleLexer extends Lexer {
	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__20=1, T__19=2, T__18=3, T__17=4, T__16=5, T__15=6, T__14=7, T__13=8, 
		T__12=9, T__11=10, T__10=11, T__9=12, T__8=13, T__7=14, T__6=15, T__5=16, 
		T__4=17, T__3=18, T__2=19, T__1=20, T__0=21, RESNAME=22, NUMBER=23, HK=24, 
		WS=25, CLASSIFIERNAME=26, ATTRNAME=27, ALPHANUMERIC=28;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] tokenNames = {
		"<INVALID>",
		"'<loocv>'", "'<attr type='", "'base='", "'<classifier name='", "'</classifier>'", 
		"'exp='", "'seq'", "'class'", "'</ressources>'", "'..'", "'>'", "'value='", 
		"'name='", "'</loocv>'", "'switch'", "'exp'", "'</attr>'", "'/>'", "'<ressource'", 
		"','", "'<ressources>'", "RESNAME", "NUMBER", "'\"'", "WS", "CLASSIFIERNAME", 
		"ATTRNAME", "ALPHANUMERIC"
	};
	public static final String[] ruleNames = {
		"T__20", "T__19", "T__18", "T__17", "T__16", "T__15", "T__14", "T__13", 
		"T__12", "T__11", "T__10", "T__9", "T__8", "T__7", "T__6", "T__5", "T__4", 
		"T__3", "T__2", "T__1", "T__0", "RESNAME", "NUMBER", "HK", "WS", "CLASSIFIERNAME", 
		"ATTRNAME", "ALPHANUMERIC"
	};


	public simpleLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "simple.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	@Override
	public void action(RuleContext _localctx, int ruleIndex, int actionIndex) {
		switch (ruleIndex) {
		case 23: HK_action((RuleContext)_localctx, actionIndex); break;

		case 24: WS_action((RuleContext)_localctx, actionIndex); break;
		}
	}
	private void HK_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 0:  skip();  break;
		}
	}
	private void WS_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 1:  skip();  break;
		}
	}

	public static final String _serializedATN =
		"\3\uacf5\uee8c\u4f5d\u8b0d\u4a45\u78bd\u1b2f\u3378\2\36\u010f\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\3\2\3\2\3\2\3\2\3\2\3\2"+
		"\3\2\3\2\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\4\3\4\3\4\3"+
		"\4\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5"+
		"\3\5\3\5\3\5\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3"+
		"\7\3\7\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\t\3\n\3\n\3\n"+
		"\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\13\3\13\3\13\3\f\3\f\3"+
		"\r\3\r\3\r\3\r\3\r\3\r\3\r\3\16\3\16\3\16\3\16\3\16\3\16\3\17\3\17\3\17"+
		"\3\17\3\17\3\17\3\17\3\17\3\17\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\21"+
		"\3\21\3\21\3\21\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\23\3\23\3\23"+
		"\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\25\3\25\3\26"+
		"\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\27\6\27"+
		"\u00df\n\27\r\27\16\27\u00e0\3\30\6\30\u00e4\n\30\r\30\16\30\u00e5\3\30"+
		"\6\30\u00e9\n\30\r\30\16\30\u00ea\3\30\3\30\6\30\u00ef\n\30\r\30\16\30"+
		"\u00f0\5\30\u00f3\n\30\3\31\3\31\3\31\3\32\3\32\3\32\5\32\u00fb\n\32\3"+
		"\32\3\32\3\33\3\33\6\33\u0101\n\33\r\33\16\33\u0102\3\34\6\34\u0106\n"+
		"\34\r\34\16\34\u0107\3\35\3\35\3\35\3\35\5\35\u010e\n\35\2\36\3\3\1\5"+
		"\4\1\7\5\1\t\6\1\13\7\1\r\b\1\17\t\1\21\n\1\23\13\1\25\f\1\27\r\1\31\16"+
		"\1\33\17\1\35\20\1\37\21\1!\22\1#\23\1%\24\1\'\25\1)\26\1+\27\1-\30\1"+
		"/\31\1\61\32\2\63\33\3\65\34\1\67\35\19\36\1\3\2\6\4\2\13\13\"\"\5\2\62"+
		";C\\c|\4\2C\\c|\4\2\f\f\17\17\u0118\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2"+
		"\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23"+
		"\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2"+
		"\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2"+
		"\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3"+
		"\2\2\2\2\67\3\2\2\2\29\3\2\2\2\3;\3\2\2\2\5C\3\2\2\2\7O\3\2\2\2\tU\3\2"+
		"\2\2\13g\3\2\2\2\ru\3\2\2\2\17z\3\2\2\2\21~\3\2\2\2\23\u0084\3\2\2\2\25"+
		"\u0092\3\2\2\2\27\u0095\3\2\2\2\31\u0097\3\2\2\2\33\u009e\3\2\2\2\35\u00a4"+
		"\3\2\2\2\37\u00ad\3\2\2\2!\u00b4\3\2\2\2#\u00b8\3\2\2\2%\u00c0\3\2\2\2"+
		"\'\u00c3\3\2\2\2)\u00ce\3\2\2\2+\u00d0\3\2\2\2-\u00de\3\2\2\2/\u00f2\3"+
		"\2\2\2\61\u00f4\3\2\2\2\63\u00fa\3\2\2\2\65\u00fe\3\2\2\2\67\u0105\3\2"+
		"\2\29\u010d\3\2\2\2;<\7>\2\2<=\7n\2\2=>\7q\2\2>?\7q\2\2?@\7e\2\2@A\7x"+
		"\2\2AB\7@\2\2B\4\3\2\2\2CD\7>\2\2DE\7c\2\2EF\7v\2\2FG\7v\2\2GH\7t\2\2"+
		"HI\7\"\2\2IJ\7v\2\2JK\7{\2\2KL\7r\2\2LM\7g\2\2MN\7?\2\2N\6\3\2\2\2OP\7"+
		"d\2\2PQ\7c\2\2QR\7u\2\2RS\7g\2\2ST\7?\2\2T\b\3\2\2\2UV\7>\2\2VW\7e\2\2"+
		"WX\7n\2\2XY\7c\2\2YZ\7u\2\2Z[\7u\2\2[\\\7k\2\2\\]\7h\2\2]^\7k\2\2^_\7"+
		"g\2\2_`\7t\2\2`a\7\"\2\2ab\7p\2\2bc\7c\2\2cd\7o\2\2de\7g\2\2ef\7?\2\2"+
		"f\n\3\2\2\2gh\7>\2\2hi\7\61\2\2ij\7e\2\2jk\7n\2\2kl\7c\2\2lm\7u\2\2mn"+
		"\7u\2\2no\7k\2\2op\7h\2\2pq\7k\2\2qr\7g\2\2rs\7t\2\2st\7@\2\2t\f\3\2\2"+
		"\2uv\7g\2\2vw\7z\2\2wx\7r\2\2xy\7?\2\2y\16\3\2\2\2z{\7u\2\2{|\7g\2\2|"+
		"}\7s\2\2}\20\3\2\2\2~\177\7e\2\2\177\u0080\7n\2\2\u0080\u0081\7c\2\2\u0081"+
		"\u0082\7u\2\2\u0082\u0083\7u\2\2\u0083\22\3\2\2\2\u0084\u0085\7>\2\2\u0085"+
		"\u0086\7\61\2\2\u0086\u0087\7t\2\2\u0087\u0088\7g\2\2\u0088\u0089\7u\2"+
		"\2\u0089\u008a\7u\2\2\u008a\u008b\7q\2\2\u008b\u008c\7w\2\2\u008c\u008d"+
		"\7t\2\2\u008d\u008e\7e\2\2\u008e\u008f\7g\2\2\u008f\u0090\7u\2\2\u0090"+
		"\u0091\7@\2\2\u0091\24\3\2\2\2\u0092\u0093\7\60\2\2\u0093\u0094\7\60\2"+
		"\2\u0094\26\3\2\2\2\u0095\u0096\7@\2\2\u0096\30\3\2\2\2\u0097\u0098\7"+
		"x\2\2\u0098\u0099\7c\2\2\u0099\u009a\7n\2\2\u009a\u009b\7w\2\2\u009b\u009c"+
		"\7g\2\2\u009c\u009d\7?\2\2\u009d\32\3\2\2\2\u009e\u009f\7p\2\2\u009f\u00a0"+
		"\7c\2\2\u00a0\u00a1\7o\2\2\u00a1\u00a2\7g\2\2\u00a2\u00a3\7?\2\2\u00a3"+
		"\34\3\2\2\2\u00a4\u00a5\7>\2\2\u00a5\u00a6\7\61\2\2\u00a6\u00a7\7n\2\2"+
		"\u00a7\u00a8\7q\2\2\u00a8\u00a9\7q\2\2\u00a9\u00aa\7e\2\2\u00aa\u00ab"+
		"\7x\2\2\u00ab\u00ac\7@\2\2\u00ac\36\3\2\2\2\u00ad\u00ae\7u\2\2\u00ae\u00af"+
		"\7y\2\2\u00af\u00b0\7k\2\2\u00b0\u00b1\7v\2\2\u00b1\u00b2\7e\2\2\u00b2"+
		"\u00b3\7j\2\2\u00b3 \3\2\2\2\u00b4\u00b5\7g\2\2\u00b5\u00b6\7z\2\2\u00b6"+
		"\u00b7\7r\2\2\u00b7\"\3\2\2\2\u00b8\u00b9\7>\2\2\u00b9\u00ba\7\61\2\2"+
		"\u00ba\u00bb\7c\2\2\u00bb\u00bc\7v\2\2\u00bc\u00bd\7v\2\2\u00bd\u00be"+
		"\7t\2\2\u00be\u00bf\7@\2\2\u00bf$\3\2\2\2\u00c0\u00c1\7\61\2\2\u00c1\u00c2"+
		"\7@\2\2\u00c2&\3\2\2\2\u00c3\u00c4\7>\2\2\u00c4\u00c5\7t\2\2\u00c5\u00c6"+
		"\7g\2\2\u00c6\u00c7\7u\2\2\u00c7\u00c8\7u\2\2\u00c8\u00c9\7q\2\2\u00c9"+
		"\u00ca\7w\2\2\u00ca\u00cb\7t\2\2\u00cb\u00cc\7e\2\2\u00cc\u00cd\7g\2\2"+
		"\u00cd(\3\2\2\2\u00ce\u00cf\7.\2\2\u00cf*\3\2\2\2\u00d0\u00d1\7>\2\2\u00d1"+
		"\u00d2\7t\2\2\u00d2\u00d3\7g\2\2\u00d3\u00d4\7u\2\2\u00d4\u00d5\7u\2\2"+
		"\u00d5\u00d6\7q\2\2\u00d6\u00d7\7w\2\2\u00d7\u00d8\7t\2\2\u00d8\u00d9"+
		"\7e\2\2\u00d9\u00da\7g\2\2\u00da\u00db\7u\2\2\u00db\u00dc\7@\2\2\u00dc"+
		",\3\2\2\2\u00dd\u00df\4c|\2\u00de\u00dd\3\2\2\2\u00df\u00e0\3\2\2\2\u00e0"+
		"\u00de\3\2\2\2\u00e0\u00e1\3\2\2\2\u00e1.\3\2\2\2\u00e2\u00e4\4\62;\2"+
		"\u00e3\u00e2\3\2\2\2\u00e4\u00e5\3\2\2\2\u00e5\u00e3\3\2\2\2\u00e5\u00e6"+
		"\3\2\2\2\u00e6\u00f3\3\2\2\2\u00e7\u00e9\4\62;\2\u00e8\u00e7\3\2\2\2\u00e9"+
		"\u00ea\3\2\2\2\u00ea\u00e8\3\2\2\2\u00ea\u00eb\3\2\2\2\u00eb\u00ec\3\2"+
		"\2\2\u00ec\u00ee\7\60\2\2\u00ed\u00ef\4\62;\2\u00ee\u00ed\3\2\2\2\u00ef"+
		"\u00f0\3\2\2\2\u00f0\u00ee\3\2\2\2\u00f0\u00f1\3\2\2\2\u00f1\u00f3\3\2"+
		"\2\2\u00f2\u00e3\3\2\2\2\u00f2\u00e8\3\2\2\2\u00f3\60\3\2\2\2\u00f4\u00f5"+
		"\7$\2\2\u00f5\u00f6\b\31\2\2\u00f6\62\3\2\2\2\u00f7\u00fb\t\2\2\2\u00f8"+
		"\u00f9\7\17\2\2\u00f9\u00fb\7\f\2\2\u00fa\u00f7\3\2\2\2\u00fa\u00f8\3"+
		"\2\2\2\u00fb\u00fc\3\2\2\2\u00fc\u00fd\b\32\3\2\u00fd\64\3\2\2\2\u00fe"+
		"\u0100\4C\\\2\u00ff\u0101\t\3\2\2\u0100\u00ff\3\2\2\2\u0101\u0102\3\2"+
		"\2\2\u0102\u0100\3\2\2\2\u0102\u0103\3\2\2\2\u0103\66\3\2\2\2\u0104\u0106"+
		"\t\4\2\2\u0105\u0104\3\2\2\2\u0106\u0107\3\2\2\2\u0107\u0105\3\2\2\2\u0107"+
		"\u0108\3\2\2\2\u01088\3\2\2\2\u0109\u010e\t\2\2\2\u010a\u010b\7^\2\2\u010b"+
		"\u010e\7x\2\2\u010c\u010e\t\5\2\2\u010d\u0109\3\2\2\2\u010d\u010a\3\2"+
		"\2\2\u010d\u010c\3\2\2\2\u010e:\3\2\2\2\16\2\u00e0\u00e5\u00ea\u00f0\u00f2"+
		"\u00fa\u0100\u0102\u0105\u0107\u010d";
	public static final ATN _ATN =
		ATNSimulator.deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}