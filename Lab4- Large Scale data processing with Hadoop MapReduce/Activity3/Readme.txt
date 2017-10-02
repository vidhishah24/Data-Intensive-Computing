1. Lemmatization is the technique of converting words to its standard form.Lemmatization is performed on two Classical Latin Files.
2. A java code is written for the same where we read the tess files line by line and split individual words.
3. In the mapper the word is normalized by replacing j with i and v with u throughout.
3. The Mapper checks if the word appears in the new_lemmatization.csv file and emits the word and its lemmas along with the location of the word.
4. In the reducer all the occurence of the same word are merged together along with its respective location.
5. 	5.1 Make sure that you have added path to JAVA_HOME and JAVA_CLASSPATH.If not perform the following commands:
		export JAVA_HOME=/usr/lib/jvm/java-6-openjdk-i386
		export HADOOP_CLASSPATH=$JAVA_HOME/lib/tools.jar
	5.2 Apply the changes by running: source ~/.bashrc
	5.3 Start hadoop using: start-hadoop.sh
	5.4 Make a new folder and put the input files in the new folder i.e copy the input files from local machine to hadoop:
		hdfs dfs –mkdir –p ~/input/
		hdfs dfs –put ~/media/Activity1/TestInput ~/input
	5.5 Compile the java code: hadoop com.sun.tools.javac.Main Lemmatization.java
	5.6	Create a jar file : jar cf Lemmatization.jar Lemmatization*.class
	5.7 Run : hadoop jar Lemmatization.jar Lemmatization ~/input/TestInput ~/TestOutput
	5.8 Get the Output on local machine using the following command:
		 hdfs dfs -get ~/TestOutput/ /media/TestOutput/
6. Input : TestInput contains 2 files [lucan.bellum_civile.part.1.tess and vergil.aeneid.tess] and a new_lemmatizer file that contains the lemmas.
7. Output: TestOutput has part-r-00000 file that has words and lemmas with its location.
8. Output Type: <Word> <Location> , where location is <Doc Id ChapterNo.LineNo>
9. Lemmatization is performed on multiple documents for files in latin folders for <1....10> and the Output for each is provided in the Output<NoofFiles> Folder.
10. To visualize the peformance of Lemmatization with MapRreduce by increasing the number of files, a graph is plotted for NumberofFiles VS Time and is included in the Activity 3 folder. 