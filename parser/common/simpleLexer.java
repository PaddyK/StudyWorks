// Generated from parser/simple.g4 by ANTLR 4.2.2

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
		T__21=1, T__20=2, T__19=3, T__18=4, T__17=5, T__16=6, T__15=7, T__14=8, 
		T__13=9, T__12=10, T__11=11, T__10=12, T__9=13, T__8=14, T__7=15, T__6=16, 
		T__5=17, T__4=18, T__3=19, T__2=20, T__1=21, T__0=22, NUMBER=23, HK=24, 
		WS=25, CLASSIFIERNAME=26, ATTRNAME=27, ALPHANUMERIC=28;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] tokenNames = {
		"<INVALID>",
		"'value='", "'<ressource'", "','", "'<classifier name='", "'<loocv>'", 
		"'<attr type='", "'<ressources>'", "'</attr>'", "'name='", "'>'", "'/>'", 
		"'</classifier>'", "'exp='", "'</loocv>'", "'switch'", "'exp'", "'class'", 
		"'seq'", "'base='", "'static'", "'</ressources>'", "'..'", "NUMBER", "'\"'", 
		"WS", "CLASSIFIERNAME", "ATTRNAME", "ALPHANUMERIC"
	};
	public static final String[] ruleNames = {
		"T__21", "T__20", "T__19", "T__18", "T__17", "T__16", "T__15", "T__14", 
		"T__13", "T__12", "T__11", "T__10", "T__9", "T__8", "T__7", "T__6", "T__5", 
		"T__4", "T__3", "T__2", "T__1", "T__0", "NUMBER", "HK", "WS", "CLASSIFIERNAME", 
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
	public String getSerializedATN() { return _serializedATN; }

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
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\36\u0111\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\3\2\3\2\3\2\3\2\3\2\3\2"+
		"\3\2\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\4\3\4\3\5\3\5\3\5\3"+
		"\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3\6"+
		"\3\6\3\6\3\6\3\6\3\6\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3"+
		"\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t"+
		"\3\t\3\t\3\t\3\n\3\n\3\n\3\n\3\n\3\n\3\13\3\13\3\f\3\f\3\f\3\r\3\r\3\r"+
		"\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\16\3\16\3\16\3\16\3\16"+
		"\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\20\3\20\3\20\3\20\3\20"+
		"\3\20\3\20\3\21\3\21\3\21\3\21\3\22\3\22\3\22\3\22\3\22\3\22\3\23\3\23"+
		"\3\23\3\23\3\24\3\24\3\24\3\24\3\24\3\24\3\25\3\25\3\25\3\25\3\25\3\25"+
		"\3\25\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26"+
		"\3\26\3\27\3\27\3\27\3\30\5\30\u00e6\n\30\3\30\6\30\u00e9\n\30\r\30\16"+
		"\30\u00ea\3\30\3\30\6\30\u00ef\n\30\r\30\16\30\u00f0\5\30\u00f3\n\30\3"+
		"\31\3\31\3\31\3\32\3\32\3\32\5\32\u00fb\n\32\3\32\3\32\3\33\3\33\6\33"+
		"\u0101\n\33\r\33\16\33\u0102\3\34\6\34\u0106\n\34\r\34\16\34\u0107\3\35"+
		"\5\35\u010b\n\35\3\35\6\35\u010e\n\35\r\35\16\35\u010f\2\2\36\3\3\5\4"+
		"\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37\21!\22"+
		"#\23%\24\'\25)\26+\27-\30/\31\61\32\63\33\65\34\67\359\36\3\2\6\4\2--"+
		"//\4\2\13\13\"\"\5\2\62;C\\c|\4\2C\\c|\u0119\2\3\3\2\2\2\2\5\3\2\2\2\2"+
		"\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2"+
		"\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2"+
		"\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2"+
		"\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2"+
		"\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\3;\3\2\2\2\5B\3\2\2\2\7M\3\2\2"+
		"\2\tO\3\2\2\2\13a\3\2\2\2\ri\3\2\2\2\17u\3\2\2\2\21\u0082\3\2\2\2\23\u008a"+
		"\3\2\2\2\25\u0090\3\2\2\2\27\u0092\3\2\2\2\31\u0095\3\2\2\2\33\u00a3\3"+
		"\2\2\2\35\u00a8\3\2\2\2\37\u00b1\3\2\2\2!\u00b8\3\2\2\2#\u00bc\3\2\2\2"+
		"%\u00c2\3\2\2\2\'\u00c6\3\2\2\2)\u00cc\3\2\2\2+\u00d3\3\2\2\2-\u00e1\3"+
		"\2\2\2/\u00e5\3\2\2\2\61\u00f4\3\2\2\2\63\u00fa\3\2\2\2\65\u00fe\3\2\2"+
		"\2\67\u0105\3\2\2\29\u010a\3\2\2\2;<\7x\2\2<=\7c\2\2=>\7n\2\2>?\7w\2\2"+
		"?@\7g\2\2@A\7?\2\2A\4\3\2\2\2BC\7>\2\2CD\7t\2\2DE\7g\2\2EF\7u\2\2FG\7"+
		"u\2\2GH\7q\2\2HI\7w\2\2IJ\7t\2\2JK\7e\2\2KL\7g\2\2L\6\3\2\2\2MN\7.\2\2"+
		"N\b\3\2\2\2OP\7>\2\2PQ\7e\2\2QR\7n\2\2RS\7c\2\2ST\7u\2\2TU\7u\2\2UV\7"+
		"k\2\2VW\7h\2\2WX\7k\2\2XY\7g\2\2YZ\7t\2\2Z[\7\"\2\2[\\\7p\2\2\\]\7c\2"+
		"\2]^\7o\2\2^_\7g\2\2_`\7?\2\2`\n\3\2\2\2ab\7>\2\2bc\7n\2\2cd\7q\2\2de"+
		"\7q\2\2ef\7e\2\2fg\7x\2\2gh\7@\2\2h\f\3\2\2\2ij\7>\2\2jk\7c\2\2kl\7v\2"+
		"\2lm\7v\2\2mn\7t\2\2no\7\"\2\2op\7v\2\2pq\7{\2\2qr\7r\2\2rs\7g\2\2st\7"+
		"?\2\2t\16\3\2\2\2uv\7>\2\2vw\7t\2\2wx\7g\2\2xy\7u\2\2yz\7u\2\2z{\7q\2"+
		"\2{|\7w\2\2|}\7t\2\2}~\7e\2\2~\177\7g\2\2\177\u0080\7u\2\2\u0080\u0081"+
		"\7@\2\2\u0081\20\3\2\2\2\u0082\u0083\7>\2\2\u0083\u0084\7\61\2\2\u0084"+
		"\u0085\7c\2\2\u0085\u0086\7v\2\2\u0086\u0087\7v\2\2\u0087\u0088\7t\2\2"+
		"\u0088\u0089\7@\2\2\u0089\22\3\2\2\2\u008a\u008b\7p\2\2\u008b\u008c\7"+
		"c\2\2\u008c\u008d\7o\2\2\u008d\u008e\7g\2\2\u008e\u008f\7?\2\2\u008f\24"+
		"\3\2\2\2\u0090\u0091\7@\2\2\u0091\26\3\2\2\2\u0092\u0093\7\61\2\2\u0093"+
		"\u0094\7@\2\2\u0094\30\3\2\2\2\u0095\u0096\7>\2\2\u0096\u0097\7\61\2\2"+
		"\u0097\u0098\7e\2\2\u0098\u0099\7n\2\2\u0099\u009a\7c\2\2\u009a\u009b"+
		"\7u\2\2\u009b\u009c\7u\2\2\u009c\u009d\7k\2\2\u009d\u009e\7h\2\2\u009e"+
		"\u009f\7k\2\2\u009f\u00a0\7g\2\2\u00a0\u00a1\7t\2\2\u00a1\u00a2\7@\2\2"+
		"\u00a2\32\3\2\2\2\u00a3\u00a4\7g\2\2\u00a4\u00a5\7z\2\2\u00a5\u00a6\7"+
		"r\2\2\u00a6\u00a7\7?\2\2\u00a7\34\3\2\2\2\u00a8\u00a9\7>\2\2\u00a9\u00aa"+
		"\7\61\2\2\u00aa\u00ab\7n\2\2\u00ab\u00ac\7q\2\2\u00ac\u00ad\7q\2\2\u00ad"+
		"\u00ae\7e\2\2\u00ae\u00af\7x\2\2\u00af\u00b0\7@\2\2\u00b0\36\3\2\2\2\u00b1"+
		"\u00b2\7u\2\2\u00b2\u00b3\7y\2\2\u00b3\u00b4\7k\2\2\u00b4\u00b5\7v\2\2"+
		"\u00b5\u00b6\7e\2\2\u00b6\u00b7\7j\2\2\u00b7 \3\2\2\2\u00b8\u00b9\7g\2"+
		"\2\u00b9\u00ba\7z\2\2\u00ba\u00bb\7r\2\2\u00bb\"\3\2\2\2\u00bc\u00bd\7"+
		"e\2\2\u00bd\u00be\7n\2\2\u00be\u00bf\7c\2\2\u00bf\u00c0\7u\2\2\u00c0\u00c1"+
		"\7u\2\2\u00c1$\3\2\2\2\u00c2\u00c3\7u\2\2\u00c3\u00c4\7g\2\2\u00c4\u00c5"+
		"\7s\2\2\u00c5&\3\2\2\2\u00c6\u00c7\7d\2\2\u00c7\u00c8\7c\2\2\u00c8\u00c9"+
		"\7u\2\2\u00c9\u00ca\7g\2\2\u00ca\u00cb\7?\2\2\u00cb(\3\2\2\2\u00cc\u00cd"+
		"\7u\2\2\u00cd\u00ce\7v\2\2\u00ce\u00cf\7c\2\2\u00cf\u00d0\7v\2\2\u00d0"+
		"\u00d1\7k\2\2\u00d1\u00d2\7e\2\2\u00d2*\3\2\2\2\u00d3\u00d4\7>\2\2\u00d4"+
		"\u00d5\7\61\2\2\u00d5\u00d6\7t\2\2\u00d6\u00d7\7g\2\2\u00d7\u00d8\7u\2"+
		"\2\u00d8\u00d9\7u\2\2\u00d9\u00da\7q\2\2\u00da\u00db\7w\2\2\u00db\u00dc"+
		"\7t\2\2\u00dc\u00dd\7e\2\2\u00dd\u00de\7g\2\2\u00de\u00df\7u\2\2\u00df"+
		"\u00e0\7@\2\2\u00e0,\3\2\2\2\u00e1\u00e2\7\60\2\2\u00e2\u00e3\7\60\2\2"+
		"\u00e3.\3\2\2\2\u00e4\u00e6\t\2\2\2\u00e5\u00e4\3\2\2\2\u00e5\u00e6\3"+
		"\2\2\2\u00e6\u00e8\3\2\2\2\u00e7\u00e9\4\62;\2\u00e8\u00e7\3\2\2\2\u00e9"+
		"\u00ea\3\2\2\2\u00ea\u00e8\3\2\2\2\u00ea\u00eb\3\2\2\2\u00eb\u00f2\3\2"+
		"\2\2\u00ec\u00ee\7\60\2\2\u00ed\u00ef\4\62;\2\u00ee\u00ed\3\2\2\2\u00ef"+
		"\u00f0\3\2\2\2\u00f0\u00ee\3\2\2\2\u00f0\u00f1\3\2\2\2\u00f1\u00f3\3\2"+
		"\2\2\u00f2\u00ec\3\2\2\2\u00f2\u00f3\3\2\2\2\u00f3\60\3\2\2\2\u00f4\u00f5"+
		"\7$\2\2\u00f5\u00f6\b\31\2\2\u00f6\62\3\2\2\2\u00f7\u00fb\t\3\2\2\u00f8"+
		"\u00f9\7\17\2\2\u00f9\u00fb\7\f\2\2\u00fa\u00f7\3\2\2\2\u00fa\u00f8\3"+
		"\2\2\2\u00fb\u00fc\3\2\2\2\u00fc\u00fd\b\32\3\2\u00fd\64\3\2\2\2\u00fe"+
		"\u0100\4C\\\2\u00ff\u0101\t\4\2\2\u0100\u00ff\3\2\2\2\u0101\u0102\3\2"+
		"\2\2\u0102\u0100\3\2\2\2\u0102\u0103\3\2\2\2\u0103\66\3\2\2\2\u0104\u0106"+
		"\t\5\2\2\u0105\u0104\3\2\2\2\u0106\u0107\3\2\2\2\u0107\u0105\3\2\2\2\u0107"+
		"\u0108\3\2\2\2\u01088\3\2\2\2\u0109\u010b\t\2\2\2\u010a\u0109\3\2\2\2"+
		"\u010a\u010b\3\2\2\2\u010b\u010d\3\2\2\2\u010c\u010e\t\4\2\2\u010d\u010c"+
		"\3\2\2\2\u010e\u010f\3\2\2\2\u010f\u010d\3\2\2\2\u010f\u0110\3\2\2\2\u0110"+
		":\3\2\2\2\17\2\u00e5\u00ea\u00f0\u00f2\u00fa\u0100\u0102\u0105\u0107\u010a"+
		"\u010d\u010f\4\3\31\2\3\32\3";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}