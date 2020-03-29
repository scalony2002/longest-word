
### This application implements following requirements:

1. Write a method in the major programming language of your choice that returns 
the longest word in a sentence and its length. For example, "The cow jumped over the moon."
should return jumped and 6
2. Write unit test, reworking code as needed
3. Add method that returns the shortest word and length with unit test.
4. Create README documenting any assumptions you made and including instruction on how 
to build and execute your tests.
5. Share your code using GitHub or similar.


### Building application
This application requires: 
* java 11+
* maven 3.6+
to build the project do following command in the project main folder
```
mvn clean package
```

### Running tests
To run the tests execute following command
```
mvn test
```

### Executing application
Application reads data from standard input, meaning that after application starts
then user can start typing its data. After completing data input terminate input
by pressing ^D or ^Z. To execute the application do following command shown below. 
```
mvn exec:java -D"exec.mainClass"="com.pf.TextScanner"
```

### Assumptions
Indeed as suggested in the requirements some assumptions are needed as the requirements
are not entirely clear. 

##### length of sentence vs length of word?
The first problem I see is the pronoun `its` in the first requirement.
It may refer to both **word** or **sentence** unfortunately the example given is not sufficient to 
disambiguate it as both the chosen word `jumped` and the sentence 
`The cow jumped over the moon.` have the length of 6.
The program assumes that the intention was to return the length of sentence. The length 
of longest word can be calculated from the word itself so to return the length of the 
word would be duplication. 

##### single sentence vs multiple sentences
The requirement assumes that there will be only one sentence in the input data. But this might
not be the case. So the question occurs what should happen if multiple sentences are entered.
The simple answer is to return result for each of the entered sentences. In the same 
order as those sentences appear in the input data.

##### sentences separation
To separate one sentence from another the program will use a certain characters which will be 
treated as sentence septators those characters are `.`, `?`, `!`. The sentence separators
can be configured via system properties.

##### quotations
The other problem to consider is to how to deal with quotations. Lets consider following 
sentence: `Gandalf shouted "You shall not pass!" while striking ground with his staff.`
In this example `!` is not sentence separator. However for simplicity sake the program
assumes that there is not such things as quotes hence every sentence termination counts.

##### word separations
The program will assume that given character group is a word if it is not separated either 
by whitespace or by sentence separator. 
 
##### UTF-8
All data input and output in utf-8