# An extention to Weka for leave-one-(patient)-out CC validation

This repositories contains an extension to the machine learning program weka. In the context of my Study Works a 5-class problem between Alzheimers Disease, Parkinsons Disease, Breast Cancer, Multiple Sclerosis and Healthy Controls was tackled. The necessary data comes from functional protein microarrays printed in duplicate.
In the accompanying study we used both spots on the array for classification to increase our available data. Usually only one spot would be chosen.
This project contains the means to parse a .gpr file to .arff files for weka. Furthermore, a variant of leave-one-out cross-validation was implemented. This is necessary as no sample of a patient should be in the test and data set. Weka does not come with the possibility to specificly split a corpus by a special attribute (This would be the patient id in our case).

In addition to the previous features, multithreading at different stages has been implemented. In the study a lot of classifiers were evaluated. Some of them terminated quickly, others took longer. To adapt to this, the user can specify the number of threads classifying, reading from file, or writing to file or database (for how to do this please see further below)

## Dependencies
In order for this contribution the following additional libraries are required:
* [ANTLR 4](http://www.antlr.org/download.html)
* [ConfigFileCompiler](https://github.com/PaddyK/configFileCompiler)
* [Weka](http://www.cs.waikato.ac.nz/ml/weka/downloading.html)
* [ANT](http://ant.apache.org/srcdownload.cgi), [ANT Installation Guide](http://ant.apache.org/manual/index.html#installing)
* [MySQL JDBC driver](http://dev.mysql.com/downloads/connector/j/)

ANTLR4 is actually needed for the ConfigFileCompiler

## Installation
1. Clone this repositories
2. Get the weka jar
3. Clone and build ConfigFileCompiler
4. Place the jars within the folder StudyWorks/lib/ you need to create it yourself
5. Build the jar by invoking `ant package`
6. Execute the jar by calling `ant execute -Dconfig /path/to/config/file` where `/path/to/config/file` is a configuration file as according to StudyWorksCompiler (See example below)

## Configuration File
The configuration file us used to set up your experiment (again the facilities available in weka do not matcht our usecase). In the configuration file you can specify the number of classifiers, file readers and result writers. In addition you can specify the file results should be appended to and whether or not to consist results in a database or solely in a file (writing to a database takes very long, so just go for the text file :)). Also you must specify the folder where the .arff files reside.
In addition, the config file gives you the opportunity to easily describe parameter tuning for a classifier. Values for an parameter can be specified either specific like `1,5,8,9,10,16` or if it follows a sequence like this `1,3,..,11`. This expression will be resolved to the numbers `1,3,5,7,9,11`.
The in `<resources>...</ressources>`specified values will be applied to all following defined classifiers.
Available resources are (required ones are bold):
* reader *default=1*
* writer *default=1*
* classifier *default=1*
* bag *default=1*
* infogain *default=-1, no info gain*
* numattributes *default=-1, all attributes if a number n is specified when top n features are selected for classification*
* **arffFolder**
* resultfile *default=C:\\weka_experiment_results.csv on windows and ~/weka_experiment_results.csv on weka
* **sqlOut** *Folder to store derived sql statements*

### Basic configuration file
````xml
<experiment>
	<resources>
		<resource name="arffFolder" value="/g/Documents/DHBW/5Semester/Study_Works/antibodies/DataAnalysis/Arff/loocv/" />
		<resource name="resultfile" value="/g/Documents/GitHub/StudyWorks/results/results.csv" />
		<resource name="sqlOut" value="/g/Documents/GitHub/results/" />
	</resources>
	<classifier name="REPTree" />
</experiment>
````

### Investigating impact of information gain for multiple classifiers
This config file will call weka for all the specified classifiers with information gain from `0.1,02.,...,1` with four classification threads, one writer thread and, since not specified, one reader thread.

````xml
<experiment>
	<resources>
		<resource name="arffFolder" value="/g/Documents/DHBW/5Semester/Study_Works/antibodies/DataAnalysis/Arff/loocv/" />
		<resource name="resultfile" value="/g/Documents/GitHub/StudyWorks/results/results.csv" />
		<resource name="sqlOut" value="/g/Documents/GitHub/results/" />
	</resources>
	<classifier name="REPTree" />
	<classifier name="Ridor" />	
	<classifier name="KStar" />	
	<classifier name="PART" />	
	<classifier name="IBk" />	
	<classifier name="IB1" />	
	<classifier name="SMO" />	
	<classifier name="NaiveBayes" />	
	<classifier name="BayesNet" />	
	<classifier name="DMNBtext" />	
	<classifier name="RBFNetwork" />	
	<classifier name="DecisionTable" />
</experiment>
````

### Parameter tuning on a complex classifier
This example runs a grid search on the paramters `I={1,5,10,..,100` and `P={10,20,30,...,100}`. Classification will be executed for each combination, that is `runs={{1,10},{1,20},..,{1,100},{5,10},..,{100,100}}`.

````xml
<experiment>
	<resources>
		<resource name="reader" value="1" />
		<resource name="writer" value="1" />
		<resource name="bag" value="1" />
		<resource name="infogain" value="-1" />
		<resource name="numattributes" value="-1" />
	</resources>
	<classifier name="AdaBoostM1">
		<attribute type="class" name="W">
			<classifier name="J48">
				<attribute name="U" />
				<attribute name="M" value="2" />
			</classifier>
		</attribute>
		<attribute name="P" value="10,20..100" />
		<attribute name="S" value="1" />
		<attribute name="I" value="1,5..100" />
	</classifier>
</experiment>
````
