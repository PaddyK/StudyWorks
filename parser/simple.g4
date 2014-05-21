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
	'<ressource' 'name='  n = RESNAME   'value='  v = NUMBER  '/>'
    { $def = new ResDef($n.text, new MyNumber($v.text)); };

classifier returns [Classifier clsfr]:
	(sc = simpleclassifier { $clsfr = $sc.sc; }
    | cc = complexclassifier { $clsfr = $cc.cc; });

simpleclassifier returns [SimpleClassifier sc]:
	'<classifier name='  n = CLASSIFIERNAME { $sc = new SimpleClassifier($n.text); }
     '/>';
	
complexclassifier returns [ComplexClassifier cc]
@init{ $cc = new ComplexClassifier(); }:
	'<classifier name='  n = CLASSIFIERNAME { $cc.setName($n.text); }
     '>' (a = attribute { $cc.addAttribute($a.attr); })+ '</classifier>';

attribute returns [Attribute attr] :
	  '<attr type='  'switch'  'name='  n = ATTRNAME  '/>'
            {$attr = new SwitchAttribute($n.text); }
	| '<attr type='  'class'  'name='  n = ATTRNAME  '>' c = classifier '</attr>'
            {$attr = new ClassifierAttribute($n.text, $c.clsfr); }
	| '<attr type='  'seq'  'name='  n = ATTRNAME  'value=' v = seqval  '/>'
            {$attr = new SequenceAttribute($n.text, $v.val); }
        | '<attr type='  'exp'  'name='  n = ATTRNAME  'base='  b = NUMBER  'exp=' v = seqval  '/>'
            {$attr = new ExponentialAttribute($n.text, new MyNumber($b.text), $v.val); }
        ;

seqval returns [SequenceValue val]: 
           s = NUMBER ',' n = NUMBER '..' e = NUMBER 
           {$val = new SequenceValue(new MyNumber($s.text)
                    , new MyNumber($n.text)
                    , new MyNumber($e.text)); };

RESNAME 	: ('a'..'z')+;
NUMBER          : ('0'..'9')+ | ('0'..'9')+ '.' ('0'..'9')+;
HK 		: '"'                                             { skip(); };
WS              : (' ' | '\t' | '\r\n')                           { skip(); };
CLASSIFIERNAME 	: ('A'..'Z')(('A'..'Z')|('a'..'z')|('0'..'9'))+;
ATTRNAME	: (('a'..'z')|('A'..'Z'))+;
ALPHANUMERIC	: (' '|'\t'|'\v'|'\n'|'\r');