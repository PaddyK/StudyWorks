grammar pure;

document :
	loocv+;

loocv : 
	'<loocv>' ressources classifier '</loocv>';

ressources :
	'<ressources>' resdef+ '</ressources>';

resdef :
	'<ressource' 'name='  TEXT  'value='  NUMBER  '/>';

classifier :
	(simpleclassifier | complexclassifier);

simpleclassifier :
	'<classifier name='  classifiername  '/>';
	
complexclassifier :
	'<classifier name='  classifiername  '>' attribute+ '</classifier>';

attribute :
	  '<attr type='  'switch'  'name=' TEXT '/>'
	| '<attr type='  'class'  'name='  TEXT  '>' classifier '</attr>'
	| '<attr type='  'seq'  'name='  TEXT  'value=' seqval  '/>'
	| '<attr type='  'static'  'name='  TEXT  'value=' attrval '/>'
        | '<attr type='  'exp'  'name='  TEXT  'base='  NUMBER  'exp=' seqval  '/>'
        ;

attrval :
	(NUMBER | TEXT | string)+;

seqval: NUMBER ',' NUMBER '..' NUMBER;

classifiername 	: (TEXT | NUMBER)+;

string : STRINGENCLOSE TEXT+ STRINGENCLOSE;

NUMBER          : ('+'|'-')?(('0'..'9')+ ('.'('0'..'9')+ )?);
TEXT            : (('A'..'Z') | ('a'..'z') | '.' | '-' )+;
STRINGENCLOSE   : '\\"';
HK 		: '"'                   { skip(); };
WS 		: (' ' | '\t' | '\r\n') { skip(); };