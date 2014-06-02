/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

grammar pure2;

document:
            element +;

element:
           elementbeginning (emptyelementending | fullelementending);

elementbeginning:
                    STAG S? name (S attribute)* S?;

emptyelementending:
                      SLASH CTAG ws;

fullelementending:
                     CTAG element+ STAG SLASH S? name S? CTAG ws;

attribute :
              name EQ HK value HK;

value :
          number | textual | sequence;

textual:
           (LETTER | DIGIT | SLASH | EQ | BSLASH HK | BSLASH BSLASH | DOT  | S | SIGN)+;

sequence:
            number COMMA number DOT DOT number;

name :
         LETTER+;

ws :
       (S | OWS) *;

number:
          simplenumber | power;

simplenumber:
          SIGN? DIGIT+ (DOT DIGIT+)?;

power:
       simplenumber POW simplenumber;
           
STAG                : '<';
CTAG                : '>';
SLASH               :'/';
BSLASH              : '\\';
DASH                : '-';
EQ                  : '=';
HK                  : '"';
DOT                 : '.';
COMMA               : ',';
S                   : ' ';
POW                 : '^';
OWS                 : ('\n' | '\r\n' | '\t') { skip(); };
LETTER              : ('a'..'z')|('A'..'Z');
SIGN                : '+' | '-';
DIGIT               : ('0'..'9');