Bank account kata
=================

Think of your personal bank account experience
When in doubt, go for the simplest solution

Requirements
------------

Deposit and Withdrawal  
Transfer  
Account statement (date, amount, balance)  
Statement printing  
Statement filters (just deposits, withdrawal, date)

The Rules
---------

1. One level of indentation per method
2. Don’t use the ELSE keyword
3. Wrap all primitives and Strings
4. First class collections
5. One dot per line
6. Don’t abbreviate
7. Keep all entities small (50 lines)
8. No classes with more than two instance variables
9. No getters/setters/properties

#### For more information:

-  [Object Calisthenics pdf](http://www.cs.helsinki.fi/u/luontola/tdd-2009/ext/ObjectCalisthenics.pdf)
-  Object Calisthenics (full book), Jeff Bay in: The ThoughtWorks Anthology.
Pragmatic Bookshelf 2008
-  Original idea for the kata: How Object-Oriented Are You Feeling Today? - Krzysztof Jelski (Session on the Software Craftsmanship UK 2011 conference)


### My (*unfinished*) solution

Started from defining an acceptance test:

> Given a client makes a deposit of 1000 on 10-01-2012  
And a deposit of 2000 on 13-01-2012  
And a withdrawal of 500 on 14-01-2012  
When she prints her bank statement  
Then she would see  
date       || credit   || debit    || balance  
14/01/2012 ||          || 500.00   || 2500.00   
13/01/2012 || 2000.00  ||          || 3000.00  
10/01/2012 || 1000.00  ||          || 1000.00   

Do it yourself first and then compare the solutions. 

The files to look at:

[statement_printing.story](https://github.com/sandromancuso/Bank-kata/blob/master/src/test/resources/org/craftedsw/acceptancetests/stories/statement_printing.story)  
[StatementPrintingSteps.java](https://github.com/sandromancuso/Bank-kata/blob/master/src/test/java/org/craftedsw/acceptancetests/steps/StatementPrintingSteps.java)  
[Unit tests](https://github.com/sandromancuso/Bank-kata/tree/master/src/test/java/org/craftedsw/domain/test)  
[Domain classes](https://github.com/sandromancuso/Bank-kata/tree/master/src/main/java/org/craftedsw/domain)  