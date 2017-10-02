Part 1:
1. In this Activity we increase the number of documents on word co-occurence using lemmitization.
2. We test the Word co-occurence against varying number of files .
3. A graph is plot in Activity 4.docx to record the performance of MapReduce infrastructure by increasing the number of documents.
4. Input: The same latin files 
5. Output: BigramOutput has folders that contains outputs tested for different number of files.
			BigramFiles contains the time taken for when different number of files are given as input as well as the log.
6. Output Type: <Word1> <Word2> <Location>
				<Lemma1.1> <Lemma2.1> <Location>
				<Lemma1.1> <Lemma2.2> <Location>
				<Lemma1.2> <Lemma2.1> <Location>
				<Lemma1.2> <Lemma2.2> <Location>

Part 2:
1. In this activity we increase the co-occurence from bigram to trigram.
2. We test the Word co-occurence against varying number of files.
3. A graph is plot in Activity 4.docx to understand performance with scalability.
4. Input: The same latin files and a sample output that test against the TestInput Files given.
5. Output: TrigramTestOutput that has output for TestInput Files.
6. Output Type: <Word1> <Word2> <Word3> <Location>
				<Lemma1.1> <Lemma2.1> <Lemma3.1><Location>
				<Lemma1.1> <Lemma2.1> <Lemma3.2><Location>
				<Lemma1.1> <Lemma2.2> <Lemma3.1> <Location>
				<Lemma1.1> <Lemma2.2> <Lemma3.2> <Location>