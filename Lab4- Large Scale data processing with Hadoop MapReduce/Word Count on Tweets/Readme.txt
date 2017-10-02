1. We first collect tweets in R.
2. Java code is written for preprocessing the tweets wherein the special characters,stopwords and all the unwanted data are removed.Preprcoessing is done so that word count is performed only on valid and relevant data.
3. The preprocessed tweets are in the folder "ProcessedTweets" .
4. We then run the wordcount on "ProcessedTweets"[Input] using MapReduce on HDFS.
5. To run the worcount on HDFS perform the following steps:
	5.1 Make sure that you have added path to JAVA_HOME and JAVA_CLASSPATH.If not perform the following commands:
		export JAVA_HOME=/usr/lib/jvm/java-6-openjdk-i386
		export HADOOP_CLASSPATH=$JAVA_HOME/lib/tools.jar
	5.2 Apply the changes by running: source ~/.bashrc
	5.3 Start hadoop using: start-hadoop.sh
	5.4 Make a new folder for all the in put files and put the input files in the new folder:
		hdfs dfs –mkdir –p ~/input/
		hdfs dfs –put ~/media/InputFiles ~/input
	5.5 Compile the java code: hadoop com.sun.tools.javac.Main WordCount.java
	5.6	Create a jar file : jar cf wc.jar WordCount*.class
	5.7 Run : hadoop jar wc.jar WordCount ~/input/InputFiles ~/WordCountOutput
	5.8 Get the Output on local machine using the following command:
		 hdfs dfs -get ~/WordCountOutput/ /media/InputFiles/
6. The output of WordCount is in the folder WordCountOutput.	
7. Once we get the WordCount we go back to R and visualize the output using word cloud.
