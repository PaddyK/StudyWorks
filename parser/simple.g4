grammar simple;

@header {
package parser.common;

import java.util.List;
import java.util.ArrayList;
import org.antlr.v4.runtime.Parser;
import parser.*;
         }

document returns [List<Setup> setups]
@init {
       $setups = new ArrayList<Setup>();
       } :
	(s = loocv { $setups.add($s.setup); })+ 
        ;

loocv returns [Setup setup]: 
	'<loocv>' 
            rs = ressources 
            c = classifier 
        '</loocv>' { $setup = new Setup($rs.rssrcs, $c.clsfr); }
                           ;

ressources returns [List<ResDef> rssrcs]
@init {
       $rssrcs = new ArrayList<ResDef>(); } :
	'<ressources>' (rd = resdef { $rssrcs.add($rd.def); } )+
        '</ressources>';

resdef returns [ResDef def]:
	'<ressource' 'name='  n = TEXT   'value='  v = NUMBER  '/>'
    { $def = new ResDef($n.text, new MyNumber($v.text)); };

classifier returns [Classifier clsfr]:
	(sc = simpleclassifier { $clsfr = $sc.sc; }
    | cc = complexclassifier { $clsfr = $cc.cc; });

simpleclassifier returns [SimpleClassifier sc]:
	'<classifier name='  n = classifiername { $sc = new SimpleClassifier($n.name); }
     '/>';
	
complexclassifier returns [ComplexClassifier cc]
@init{ $cc = new ComplexClassifier(); }:
	'<classifier name='  n = classifiername { $cc.setName($n.name); }
     '>' (a = attribute { $cc.addAttribute($a.attr); })+ '</classifier>';

attribute returns [Attribute attr] :
	  '<attr type='  'switch'  'name='  n = TEXT  '/>'
            {$attr = new SwitchAttribute($n.text); }
	| '<attr type='  'class'  'name='  n = TEXT  '>' c = classifier '</attr>'
            {$attr = new ClassifierAttribute($n.text, $c.clsfr); }
	| '<attr type='  'seq'  'name='  n = TEXT  'value=' v = seqval  '/>'
            {$attr = new SequenceAttribute($n.text, $v.val); }
	| '<attr type='  'static'  'name='  n = TEXT  'value=' av = attrval '/>'
            {$attr = new StaticAttribute($n.text, $av.val); }
        | '<attr type='  'exp'  'name='  n = TEXT  'base='  b = NUMBER  'exp=' v = seqval  '/>'
            {$attr = new ExponentialAttribute($n.text, new MyNumber($b.text), $v.val); }
        ;

seqval returns [SequenceValue val]: 
           s = NUMBER ',' n = NUMBER '..' e = NUMBER 
           {$val = new SequenceValue(new MyNumber($s.text)
                    , new MyNumber($n.text)
                    , new MyNumber($e.text)); };

attrval returns [String val]
@init { $val = ""; } :
	(v = NUMBER { if(!$val.equals(""))$val += " "; $val += $v.text; } 
    |    t = TEXT   { if(!$val.equals(""))$val += " "; $val += $t.text; }
    |    s = string { if(!$val.equals(""))$val += " "; $val += $s.str; } )+;

classifiername returns [String name]
@init { $name = ""; } : 
       (t = TEXT { $name += $t.text; } 
       | n = NUMBER { $name += $n.text; })+;

string returns [String str]
@init { $str = ""; } :
    st1 = STRINGENCLOSE { $str += $st1.text; } 
    (t = TEXT { $str += $t.text; })+ 
    st2 = STRINGENCLOSE { $str += $st2.text; };

NUMBER          : ('+'|'-')?(('0'..'9')+ ('.'('0'..'9')+ )?);
TEXT            : (('A'..'Z') | ('a'..'z') | '.' | '-' )+;
STRINGENCLOSE   : '\\"';
HK 		: '"'                   { skip(); };
WS 		: (' ' | '\t' | '\r\n') { skip(); };
