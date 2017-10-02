1. We perform Pairs and Stripes for Word Co-occurence on tweets.Tweets are cleaned using the same PreprocessingTweets.java used in Activity 1.
2. To run the worcount on HDFS perform the following steps:
	2.1 Make sure that you have added path to JAVA_HOME and JAVA_CLASSPATH.If not perform the following commands:
		export JAVA_HOME=/usr/lib/jvm/java-6-openjdk-i386
		export HADOOP_CLASSPATH=$JAVA_HOME/lib/tools.jar
	2.2 Apply the changes by running: source ~/.bashrc
	2.3 Start hadoop using: start-hadoop.sh
	2.4 Make a new folder and put the input files in the new folder i.e copy the input files from local machine to hadoop:
		hdfs dfs –mkdir –p ~/input/
		hdfs dfs –put ~/media/ProcessedTweets ~/input
	2.5 Compile the java code: hadoop com.sun.tools.javac.Main Pairs<Stripes>.java
	2.6	Create a jar file : jar cf Pairs<Stripes>.jar Pairs<Stripes>*.class
	2.7 Run : hadoop jar Pairs<Stripes>.jar Pairs<Stripes> ~/input/ProcessedTweets ~/Pairs<Stripes>Output
	2.8 Get the Output on local machine using the following command:
		 hdfs dfs -get ~/Pairs<Stripes>Output/ /media/ProcessedTweets/
3. Input Folder:ProcessedTweets - It contains 3 files [tweet1,tweet2,tweet3]
4. Output Folder:PairsOutput:A Single file that contains count of co-occuring words using the Pairs approach
   Output Type: <Word1> <Word2> <count> , where count is the number of times Word1 and Word2 occur together in all the files.