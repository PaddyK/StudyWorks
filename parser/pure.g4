grammar pure;

document :
	loocv+;

loocv : 
	'<loocv>' ressources classifier '</loocv>';

ressources :
	'<ressources>' resdef+ '</ressources>';

resdef :
	'<ressource' 'name='  ATTRNAME  'value='  NUMBER  '/>';

classifier :
	(simpleclassifier | complexclassifier);

simpleclassifier :
	'<classifier name='  CLASSIFIERNAME  '/>';
	
complexclassifier :
	'<classifier name='  CLASSIFIERNAME  '>' attribute+ '</classifier>';

attribute :
	  '<attr type='  'switch'  'name='  ATTRNAME  '/>'
	| '<attr type='  'class'  'name='  ATTRNAME  '>' classifier '</attr>'
	| '<attr type='  'seq'  'name='  ATTRNAME  'value=' seqval  '/>'
	| '<attr type='  'static'  'name='  ATTRNAME  'value=' (NUMBER|ALPHANUMERIC) '/>'
        | '<attr type='  'exp'  'name='  ATTRNAME  'base='  NUMBER  'exp=' seqval  '/>'
        ;

attrval :
	NUMBER | ALPHANUMERIC;

seqval: start ',' next '..' end;

start: NUMBER;
next : NUMBER;
end  : NUMBER;

//RESNAME 	: (('a'..'z')|('A'..'Z'))+;
NUMBER          : ('+'|'-')?(('0'..'9')+ ('.'('0'..'9')+ )?);
HK 		: '"'                   { skip(); };
WS 		: (' ' | '\t' | '\r\n') { skip(); };
CLASSIFIERNAME 	: ('A'..'Z')(('A'..'Z')|('a'..'z')|('0'..'9'))+;
ATTRNAME	: (('A'..'Z')|('a'..'z'))+;
ALPHANUMERIC	: ('+'|'-')?(('a'..'z')|('A'..'Z')|('0'..'9'))+;