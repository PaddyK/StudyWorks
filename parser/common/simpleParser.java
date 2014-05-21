// Generated from C:\Users\kalmbach\workspace\configFileCompiler\src\parser\simple.g4 by ANTLR 4.1

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
		T__20=1, T__19=2, T__18=3, T__17=4, T__16=5, T__15=6, T__14=7, T__13=8, 
		T__12=9, T__11=10, T__10=11, T__9=12, T__8=13, T__7=14, T__6=15, T__5=16, 
		T__4=17, T__3=18, T__2=19, T__1=20, T__0=21, RESNAME=22, NUMBER=23, HK=24, 
		WS=25, CLASSIFIERNAME=26, ATTRNAME=27, ALPHANUMERIC=28;
	public static final String[] tokenNames = {
		"<INVALID>", "'<loocv>'", "'<attr type='", "'base='", "'<classifier name='", 
		"'</classifier>'", "'exp='", "'seq'", "'class'", "'</ressources>'", "'..'", 
		"'>'", "'value='", "'name='", "'</loocv>'", "'switch'", "'exp'", "'</attr>'", 
		"'/>'", "'<ressource'", "','", "'<ressources>'", "RESNAME", "NUMBER", 
		"'\"'", "WS", "CLASSIFIERNAME", "ATTRNAME", "ALPHANUMERIC"
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
			} while ( _la==1 );
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
			setState(25); match(1);
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
		public List<ResdefContext> resdef() {
			return getRuleContexts(ResdefContext.class);
		}
		public ResdefContext resdef(int i) {
			return getRuleContext(ResdefContext.class,i);
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
			setState(31); match(21);
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
			} while ( _la==19 );
			setState(39); match(9);
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
		public TerminalNode RESNAME() { return getToken(simpleParser.RESNAME, 0); }
		public TerminalNode NUMBER() { return getToken(simpleParser.NUMBER, 0); }
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
			setState(41); match(19);
			setState(42); match(13);
			setState(43); ((ResdefContext)_localctx).n = match(RESNAME);
			setState(44); match(12);
			setState(45); ((ResdefContext)_localctx).v = match(NUMBER);
			setState(46); match(18);
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
		public ComplexclassifierContext complexclassifier() {
			return getRuleContext(ComplexclassifierContext.class,0);
		}
		public SimpleclassifierContext simpleclassifier() {
			return getRuleContext(SimpleclassifierContext.class,0);
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
			setState(60); match(18);
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
			setState(65); match(11);
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
			} while ( _la==2 );
			setState(73); match(5);
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
		public Token b;
		public ClassifierContext classifier() {
			return getRuleContext(ClassifierContext.class,0);
		}
		public TerminalNode ATTRNAME() { return getToken(simpleParser.ATTRNAME, 0); }
		public TerminalNode NUMBER() { return getToken(simpleParser.NUMBER, 0); }
		public SeqvalContext seqval() {
			return getRuleContext(SeqvalContext.class,0);
		}
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
			setState(110);
			switch ( getInterpreter().adaptivePredict(_input,4,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(75); match(2);
				setState(76); match(15);
				setState(77); match(13);
				setState(78); ((AttributeContext)_localctx).n = match(ATTRNAME);
				setState(79); match(18);
				((AttributeContext)_localctx).attr =  new SwitchAttribute((((AttributeContext)_localctx).n!=null?((AttributeContext)_localctx).n.getText():null)); 
				}
				break;

			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(81); match(2);
				setState(82); match(8);
				setState(83); match(13);
				setState(84); ((AttributeContext)_localctx).n = match(ATTRNAME);
				setState(85); match(11);
				setState(86); ((AttributeContext)_localctx).c = classifier();
				setState(87); match(17);
				((AttributeContext)_localctx).attr =  new ClassifierAttribute((((AttributeContext)_localctx).n!=null?((AttributeContext)_localctx).n.getText():null), ((AttributeContext)_localctx).c.clsfr); 
				}
				break;

			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(90); match(2);
				setState(91); match(7);
				setState(92); match(13);
				setState(93); ((AttributeContext)_localctx).n = match(ATTRNAME);
				setState(94); match(12);
				setState(95); ((AttributeContext)_localctx).v = seqval();
				setState(96); match(18);
				((AttributeContext)_localctx).attr =  new SequenceAttribute((((AttributeContext)_localctx).n!=null?((AttributeContext)_localctx).n.getText():null), ((AttributeContext)_localctx).v.val); 
				}
				break;

			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(99); match(2);
				setState(100); match(16);
				setState(101); match(13);
				setState(102); ((AttributeContext)_localctx).n = match(ATTRNAME);
				setState(103); match(3);
				setState(104); ((AttributeContext)_localctx).b = match(NUMBER);
				setState(105); match(6);
				setState(106); ((AttributeContext)_localctx).v = seqval();
				setState(107); match(18);
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
			setState(112); ((SeqvalContext)_localctx).s = match(NUMBER);
			setState(113); match(20);
			setState(114); ((SeqvalContext)_localctx).n = match(NUMBER);
			setState(115); match(10);
			setState(116); ((SeqvalContext)_localctx).e = match(NUMBER);
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
		"\3\uacf5\uee8c\u4f5d\u8b0d\u4a45\u78bd\u1b2f\u3378\3\36z\4\2\t\2\4\3\t"+
		"\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\3\2\3\2\3\2"+
		"\6\2\30\n\2\r\2\16\2\31\3\3\3\3\3\3\3\3\3\3\3\3\3\4\3\4\3\4\3\4\6\4&\n"+
		"\4\r\4\16\4\'\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\6"+
		"\3\6\3\6\5\6:\n\6\3\7\3\7\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\b\3\b\6\b"+
		"H\n\b\r\b\16\bI\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3"+
		"\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t"+
		"\3\t\3\t\3\t\3\t\3\t\3\t\5\tq\n\t\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\2\13"+
		"\2\4\6\b\n\f\16\20\22\2\2w\2\27\3\2\2\2\4\33\3\2\2\2\6!\3\2\2\2\b+\3\2"+
		"\2\2\n9\3\2\2\2\f;\3\2\2\2\16@\3\2\2\2\20p\3\2\2\2\22r\3\2\2\2\24\25\5"+
		"\4\3\2\25\26\b\2\1\2\26\30\3\2\2\2\27\24\3\2\2\2\30\31\3\2\2\2\31\27\3"+
		"\2\2\2\31\32\3\2\2\2\32\3\3\2\2\2\33\34\7\3\2\2\34\35\5\6\4\2\35\36\5"+
		"\n\6\2\36\37\7\20\2\2\37 \b\3\1\2 \5\3\2\2\2!%\7\27\2\2\"#\5\b\5\2#$\b"+
		"\4\1\2$&\3\2\2\2%\"\3\2\2\2&\'\3\2\2\2\'%\3\2\2\2\'(\3\2\2\2()\3\2\2\2"+
		")*\7\13\2\2*\7\3\2\2\2+,\7\25\2\2,-\7\17\2\2-.\7\30\2\2./\7\16\2\2/\60"+
		"\7\31\2\2\60\61\7\24\2\2\61\62\b\5\1\2\62\t\3\2\2\2\63\64\5\f\7\2\64\65"+
		"\b\6\1\2\65:\3\2\2\2\66\67\5\16\b\2\678\b\6\1\28:\3\2\2\29\63\3\2\2\2"+
		"9\66\3\2\2\2:\13\3\2\2\2;<\7\6\2\2<=\7\34\2\2=>\b\7\1\2>?\7\24\2\2?\r"+
		"\3\2\2\2@A\7\6\2\2AB\7\34\2\2BC\b\b\1\2CG\7\r\2\2DE\5\20\t\2EF\b\b\1\2"+
		"FH\3\2\2\2GD\3\2\2\2HI\3\2\2\2IG\3\2\2\2IJ\3\2\2\2JK\3\2\2\2KL\7\7\2\2"+
		"L\17\3\2\2\2MN\7\4\2\2NO\7\21\2\2OP\7\17\2\2PQ\7\35\2\2QR\7\24\2\2Rq\b"+
		"\t\1\2ST\7\4\2\2TU\7\n\2\2UV\7\17\2\2VW\7\35\2\2WX\7\r\2\2XY\5\n\6\2Y"+
		"Z\7\23\2\2Z[\b\t\1\2[q\3\2\2\2\\]\7\4\2\2]^\7\t\2\2^_\7\17\2\2_`\7\35"+
		"\2\2`a\7\16\2\2ab\5\22\n\2bc\7\24\2\2cd\b\t\1\2dq\3\2\2\2ef\7\4\2\2fg"+
		"\7\22\2\2gh\7\17\2\2hi\7\35\2\2ij\7\5\2\2jk\7\31\2\2kl\7\b\2\2lm\5\22"+
		"\n\2mn\7\24\2\2no\b\t\1\2oq\3\2\2\2pM\3\2\2\2pS\3\2\2\2p\\\3\2\2\2pe\3"+
		"\2\2\2q\21\3\2\2\2rs\7\31\2\2st\7\26\2\2tu\7\31\2\2uv\7\f\2\2vw\7\31\2"+
		"\2wx\b\n\1\2x\23\3\2\2\2\7\31\'9Ip";
	public static final ATN _ATN =
		ATNSimulator.deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}