// Generated from parser/simple.g4 by ANTLR 4.2.2

package parser.common;

import java.util.List;
import java.util.ArrayList;
import org.antlr.v4.runtime.Parser;
import parser.*;
         
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class simpleParser extends Parser {
	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__21=1, T__20=2, T__19=3, T__18=4, T__17=5, T__16=6, T__15=7, T__14=8, 
		T__13=9, T__12=10, T__11=11, T__10=12, T__9=13, T__8=14, T__7=15, T__6=16, 
		T__5=17, T__4=18, T__3=19, T__2=20, T__1=21, T__0=22, NUMBER=23, HK=24, 
		WS=25, CLASSIFIERNAME=26, ATTRNAME=27, ALPHANUMERIC=28;
	public static final String[] tokenNames = {
		"<INVALID>", "'value='", "'<ressource'", "','", "'<classifier name='", 
		"'<loocv>'", "'<attr type='", "'<ressources>'", "'</attr>'", "'name='", 
		"'>'", "'/>'", "'</classifier>'", "'exp='", "'</loocv>'", "'switch'", 
		"'exp'", "'class'", "'seq'", "'base='", "'static'", "'</ressources>'", 
		"'..'", "NUMBER", "'\"'", "WS", "CLASSIFIERNAME", "ATTRNAME", "ALPHANUMERIC"
	};
	public static final int
		RULE_document = 0, RULE_loocv = 1, RULE_ressources = 2, RULE_resdef = 3, 
		RULE_classifier = 4, RULE_simpleclassifier = 5, RULE_complexclassifier = 6, 
		RULE_attribute = 7, RULE_seqval = 8;
	public static final String[] ruleNames = {
		"document", "loocv", "ressources", "resdef", "classifier", "simpleclassifier", 
		"complexclassifier", "attribute", "seqval"
	};

	@Override
	public String getGrammarFileName() { return "simple.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public simpleParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class DocumentContext extends ParserRuleContext {
		public List<Setup> setups;
		public LoocvContext s;
		public LoocvContext loocv(int i) {
			return getRuleContext(LoocvContext.class,i);
		}
		public List<LoocvContext> loocv() {
			return getRuleContexts(LoocvContext.class);
		}
		public DocumentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_document; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).enterDocument(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).exitDocument(this);
		}
	}

	public final DocumentContext document() throws RecognitionException {
		DocumentContext _localctx = new DocumentContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_document);

		       ((DocumentContext)_localctx).setups =  new ArrayList<Setup>();
		       
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(21); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(18); ((DocumentContext)_localctx).s = loocv();
				 _localctx.setups.add(((DocumentContext)_localctx).s.setup); 
				}
				}
				setState(23); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==5 );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LoocvContext extends ParserRuleContext {
		public Setup setup;
		public RessourcesContext rs;
		public ClassifierContext c;
		public RessourcesContext ressources() {
			return getRuleContext(RessourcesContext.class,0);
		}
		public ClassifierContext classifier() {
			return getRuleContext(ClassifierContext.class,0);
		}
		public LoocvContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_loocv; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).enterLoocv(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).exitLoocv(this);
		}
	}

	public final LoocvContext loocv() throws RecognitionException {
		LoocvContext _localctx = new LoocvContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_loocv);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(25); match(5);
			setState(26); ((LoocvContext)_localctx).rs = ressources();
			setState(27); ((LoocvContext)_localctx).c = classifier();
			setState(28); match(14);
			 ((LoocvContext)_localctx).setup =  new Setup(((LoocvContext)_localctx).rs.rssrcs, ((LoocvContext)_localctx).c.clsfr); 
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class RessourcesContext extends ParserRuleContext {
		public List<ResDef> rssrcs;
		public ResdefContext rd;
		public ResdefContext resdef(int i) {
			return getRuleContext(ResdefContext.class,i);
		}
		public List<ResdefContext> resdef() {
			return getRuleContexts(ResdefContext.class);
		}
		public RessourcesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ressources; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).enterRessources(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).exitRessources(this);
		}
	}

	public final RessourcesContext ressources() throws RecognitionException {
		RessourcesContext _localctx = new RessourcesContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_ressources);

		       ((RessourcesContext)_localctx).rssrcs =  new ArrayList<ResDef>(); 
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(31); match(7);
			setState(35); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(32); ((RessourcesContext)_localctx).rd = resdef();
				 _localctx.rssrcs.add(((RessourcesContext)_localctx).rd.def); 
				}
				}
				setState(37); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==2 );
			setState(39); match(21);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ResdefContext extends ParserRuleContext {
		public ResDef def;
		public Token n;
		public Token v;
		public TerminalNode NUMBER() { return getToken(simpleParser.NUMBER, 0); }
		public TerminalNode ATTRNAME() { return getToken(simpleParser.ATTRNAME, 0); }
		public ResdefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_resdef; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).enterResdef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).exitResdef(this);
		}
	}

	public final ResdefContext resdef() throws RecognitionException {
		ResdefContext _localctx = new ResdefContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_resdef);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(41); match(2);
			setState(42); match(9);
			setState(43); ((ResdefContext)_localctx).n = match(ATTRNAME);
			setState(44); match(1);
			setState(45); ((ResdefContext)_localctx).v = match(NUMBER);
			setState(46); match(11);
			 ((ResdefContext)_localctx).def =  new ResDef((((ResdefContext)_localctx).n!=null?((ResdefContext)_localctx).n.getText():null), new MyNumber((((ResdefContext)_localctx).v!=null?((ResdefContext)_localctx).v.getText():null))); 
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ClassifierContext extends ParserRuleContext {
		public Classifier clsfr;
		public SimpleclassifierContext sc;
		public ComplexclassifierContext cc;
		public SimpleclassifierContext simpleclassifier() {
			return getRuleContext(SimpleclassifierContext.class,0);
		}
		public ComplexclassifierContext complexclassifier() {
			return getRuleContext(ComplexclassifierContext.class,0);
		}
		public ClassifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classifier; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).enterClassifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).exitClassifier(this);
		}
	}

	public final ClassifierContext classifier() throws RecognitionException {
		ClassifierContext _localctx = new ClassifierContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_classifier);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(55);
			switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
			case 1:
				{
				setState(49); ((ClassifierContext)_localctx).sc = simpleclassifier();
				 ((ClassifierContext)_localctx).clsfr =  ((ClassifierContext)_localctx).sc.sc; 
				}
				break;

			case 2:
				{
				setState(52); ((ClassifierContext)_localctx).cc = complexclassifier();
				 ((ClassifierContext)_localctx).clsfr =  ((ClassifierContext)_localctx).cc.cc; 
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SimpleclassifierContext extends ParserRuleContext {
		public SimpleClassifier sc;
		public Token n;
		public TerminalNode CLASSIFIERNAME() { return getToken(simpleParser.CLASSIFIERNAME, 0); }
		public SimpleclassifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_simpleclassifier; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).enterSimpleclassifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).exitSimpleclassifier(this);
		}
	}

	public final SimpleclassifierContext simpleclassifier() throws RecognitionException {
		SimpleclassifierContext _localctx = new SimpleclassifierContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_simpleclassifier);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(57); match(4);
			setState(58); ((SimpleclassifierContext)_localctx).n = match(CLASSIFIERNAME);
			 ((SimpleclassifierContext)_localctx).sc =  new SimpleClassifier((((SimpleclassifierContext)_localctx).n!=null?((SimpleclassifierContext)_localctx).n.getText():null)); 
			setState(60); match(11);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ComplexclassifierContext extends ParserRuleContext {
		public ComplexClassifier cc;
		public Token n;
		public AttributeContext a;
		public List<AttributeContext> attribute() {
			return getRuleContexts(AttributeContext.class);
		}
		public AttributeContext attribute(int i) {
			return getRuleContext(AttributeContext.class,i);
		}
		public TerminalNode CLASSIFIERNAME() { return getToken(simpleParser.CLASSIFIERNAME, 0); }
		public ComplexclassifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_complexclassifier; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).enterComplexclassifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).exitComplexclassifier(this);
		}
	}

	public final ComplexclassifierContext complexclassifier() throws RecognitionException {
		ComplexclassifierContext _localctx = new ComplexclassifierContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_complexclassifier);
		 ((ComplexclassifierContext)_localctx).cc =  new ComplexClassifier(); 
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(62); match(4);
			setState(63); ((ComplexclassifierContext)_localctx).n = match(CLASSIFIERNAME);
			 _localctx.cc.setName((((ComplexclassifierContext)_localctx).n!=null?((ComplexclassifierContext)_localctx).n.getText():null)); 
			setState(65); match(10);
			setState(69); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(66); ((ComplexclassifierContext)_localctx).a = attribute();
				 _localctx.cc.addAttribute(((ComplexclassifierContext)_localctx).a.attr); 
				}
				}
				setState(71); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==6 );
			setState(73); match(12);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AttributeContext extends ParserRuleContext {
		public Attribute attr;
		public Token n;
		public ClassifierContext c;
		public SeqvalContext v;
		public Token an;
		public Token b;
		public TerminalNode ALPHANUMERIC() { return getToken(simpleParser.ALPHANUMERIC, 0); }
		public TerminalNode NUMBER() { return getToken(simpleParser.NUMBER, 0); }
		public SeqvalContext seqval() {
			return getRuleContext(SeqvalContext.class,0);
		}
		public ClassifierContext classifier() {
			return getRuleContext(ClassifierContext.class,0);
		}
		public TerminalNode ATTRNAME() { return getToken(simpleParser.ATTRNAME, 0); }
		public AttributeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_attribute; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).enterAttribute(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).exitAttribute(this);
		}
	}

	public final AttributeContext attribute() throws RecognitionException {
		AttributeContext _localctx = new AttributeContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_attribute);
		try {
			setState(121);
			switch ( getInterpreter().adaptivePredict(_input,5,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(75); match(6);
				setState(76); match(15);
				setState(77); match(9);
				setState(78); ((AttributeContext)_localctx).n = match(ATTRNAME);
				setState(79); match(11);
				((AttributeContext)_localctx).attr =  new SwitchAttribute((((AttributeContext)_localctx).n!=null?((AttributeContext)_localctx).n.getText():null)); 
				}
				break;

			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(81); match(6);
				setState(82); match(17);
				setState(83); match(9);
				setState(84); ((AttributeContext)_localctx).n = match(ATTRNAME);
				setState(85); match(10);
				setState(86); ((AttributeContext)_localctx).c = classifier();
				setState(87); match(8);
				((AttributeContext)_localctx).attr =  new ClassifierAttribute((((AttributeContext)_localctx).n!=null?((AttributeContext)_localctx).n.getText():null), ((AttributeContext)_localctx).c.clsfr); 
				}
				break;

			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(90); match(6);
				setState(91); match(18);
				setState(92); match(9);
				setState(93); ((AttributeContext)_localctx).n = match(ATTRNAME);
				setState(94); match(1);
				setState(95); ((AttributeContext)_localctx).v = seqval();
				setState(96); match(11);
				((AttributeContext)_localctx).attr =  new SequenceAttribute((((AttributeContext)_localctx).n!=null?((AttributeContext)_localctx).n.getText():null), ((AttributeContext)_localctx).v.val); 
				}
				break;

			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(99); match(6);
				setState(100); match(20);
				setState(101); match(9);
				setState(102); ((AttributeContext)_localctx).n = match(ATTRNAME);
				setState(103); match(1);
				setState(106);
				switch (_input.LA(1)) {
				case NUMBER:
					{
					setState(104); ((AttributeContext)_localctx).an = match(NUMBER);
					}
					break;
				case ALPHANUMERIC:
					{
					setState(105); ((AttributeContext)_localctx).an = match(ALPHANUMERIC);
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(108); match(11);
				((AttributeContext)_localctx).attr =  new StaticAttribute((((AttributeContext)_localctx).n!=null?((AttributeContext)_localctx).n.getText():null), (((AttributeContext)_localctx).an!=null?((AttributeContext)_localctx).an.getText():null)); 
				}
				break;

			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(110); match(6);
				setState(111); match(16);
				setState(112); match(9);
				setState(113); ((AttributeContext)_localctx).n = match(ATTRNAME);
				setState(114); match(19);
				setState(115); ((AttributeContext)_localctx).b = match(NUMBER);
				setState(116); match(13);
				setState(117); ((AttributeContext)_localctx).v = seqval();
				setState(118); match(11);
				((AttributeContext)_localctx).attr =  new ExponentialAttribute((((AttributeContext)_localctx).n!=null?((AttributeContext)_localctx).n.getText():null), new MyNumber((((AttributeContext)_localctx).b!=null?((AttributeContext)_localctx).b.getText():null)), ((AttributeContext)_localctx).v.val); 
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SeqvalContext extends ParserRuleContext {
		public SequenceValue val;
		public Token s;
		public Token n;
		public Token e;
		public TerminalNode NUMBER(int i) {
			return getToken(simpleParser.NUMBER, i);
		}
		public List<TerminalNode> NUMBER() { return getTokens(simpleParser.NUMBER); }
		public SeqvalContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_seqval; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).enterSeqval(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).exitSeqval(this);
		}
	}

	public final SeqvalContext seqval() throws RecognitionException {
		SeqvalContext _localctx = new SeqvalContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_seqval);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(123); ((SeqvalContext)_localctx).s = match(NUMBER);
			setState(124); match(3);
			setState(125); ((SeqvalContext)_localctx).n = match(NUMBER);
			setState(126); match(22);
			setState(127); ((SeqvalContext)_localctx).e = match(NUMBER);
			((SeqvalContext)_localctx).val =  new SequenceValue(new MyNumber((((SeqvalContext)_localctx).s!=null?((SeqvalContext)_localctx).s.getText():null))
			                    , new MyNumber((((SeqvalContext)_localctx).n!=null?((SeqvalContext)_localctx).n.getText():null))
			                    , new MyNumber((((SeqvalContext)_localctx).e!=null?((SeqvalContext)_localctx).e.getText():null))); 
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\36\u0085\4\2\t\2"+
		"\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\3\2\3"+
		"\2\3\2\6\2\30\n\2\r\2\16\2\31\3\3\3\3\3\3\3\3\3\3\3\3\3\4\3\4\3\4\3\4"+
		"\6\4&\n\4\r\4\16\4\'\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3"+
		"\6\3\6\3\6\3\6\5\6:\n\6\3\7\3\7\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\b\3"+
		"\b\6\bH\n\b\r\b\16\bI\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t"+
		"\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3"+
		"\t\3\t\3\t\3\t\5\tm\n\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3"+
		"\t\3\t\5\t|\n\t\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\2\2\13\2\4\6\b\n\f\16"+
		"\20\22\2\2\u0084\2\27\3\2\2\2\4\33\3\2\2\2\6!\3\2\2\2\b+\3\2\2\2\n9\3"+
		"\2\2\2\f;\3\2\2\2\16@\3\2\2\2\20{\3\2\2\2\22}\3\2\2\2\24\25\5\4\3\2\25"+
		"\26\b\2\1\2\26\30\3\2\2\2\27\24\3\2\2\2\30\31\3\2\2\2\31\27\3\2\2\2\31"+
		"\32\3\2\2\2\32\3\3\2\2\2\33\34\7\7\2\2\34\35\5\6\4\2\35\36\5\n\6\2\36"+
		"\37\7\20\2\2\37 \b\3\1\2 \5\3\2\2\2!%\7\t\2\2\"#\5\b\5\2#$\b\4\1\2$&\3"+
		"\2\2\2%\"\3\2\2\2&\'\3\2\2\2\'%\3\2\2\2\'(\3\2\2\2()\3\2\2\2)*\7\27\2"+
		"\2*\7\3\2\2\2+,\7\4\2\2,-\7\13\2\2-.\7\35\2\2./\7\3\2\2/\60\7\31\2\2\60"+
		"\61\7\r\2\2\61\62\b\5\1\2\62\t\3\2\2\2\63\64\5\f\7\2\64\65\b\6\1\2\65"+
		":\3\2\2\2\66\67\5\16\b\2\678\b\6\1\28:\3\2\2\29\63\3\2\2\29\66\3\2\2\2"+
		":\13\3\2\2\2;<\7\6\2\2<=\7\34\2\2=>\b\7\1\2>?\7\r\2\2?\r\3\2\2\2@A\7\6"+
		"\2\2AB\7\34\2\2BC\b\b\1\2CG\7\f\2\2DE\5\20\t\2EF\b\b\1\2FH\3\2\2\2GD\3"+
		"\2\2\2HI\3\2\2\2IG\3\2\2\2IJ\3\2\2\2JK\3\2\2\2KL\7\16\2\2L\17\3\2\2\2"+
		"MN\7\b\2\2NO\7\21\2\2OP\7\13\2\2PQ\7\35\2\2QR\7\r\2\2R|\b\t\1\2ST\7\b"+
		"\2\2TU\7\23\2\2UV\7\13\2\2VW\7\35\2\2WX\7\f\2\2XY\5\n\6\2YZ\7\n\2\2Z["+
		"\b\t\1\2[|\3\2\2\2\\]\7\b\2\2]^\7\24\2\2^_\7\13\2\2_`\7\35\2\2`a\7\3\2"+
		"\2ab\5\22\n\2bc\7\r\2\2cd\b\t\1\2d|\3\2\2\2ef\7\b\2\2fg\7\26\2\2gh\7\13"+
		"\2\2hi\7\35\2\2il\7\3\2\2jm\7\31\2\2km\7\36\2\2lj\3\2\2\2lk\3\2\2\2mn"+
		"\3\2\2\2no\7\r\2\2o|\b\t\1\2pq\7\b\2\2qr\7\22\2\2rs\7\13\2\2st\7\35\2"+
		"\2tu\7\25\2\2uv\7\31\2\2vw\7\17\2\2wx\5\22\n\2xy\7\r\2\2yz\b\t\1\2z|\3"+
		"\2\2\2{M\3\2\2\2{S\3\2\2\2{\\\3\2\2\2{e\3\2\2\2{p\3\2\2\2|\21\3\2\2\2"+
		"}~\7\31\2\2~\177\7\5\2\2\177\u0080\7\31\2\2\u0080\u0081\7\30\2\2\u0081"+
		"\u0082\7\31\2\2\u0082\u0083\b\n\1\2\u0083\23\3\2\2\2\b\31\'9Il{";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}