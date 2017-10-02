import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class Lemmatization{
	static HashMap<String, ArrayList<String>> hm = new HashMap<String, ArrayList<String>>();

	public static class LatinMapper extends Mapper<Object, Text, Text, Text> {

		public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
			if (value.toString().length() != 0) {
				String value1 = value.toString().substring(value.toString().indexOf("<"),
						value.toString().indexOf(">")+1);
				//String position[] = value1.split("\\.");
				String value2 = value.toString().substring(value.toString().indexOf(">"));
				value2 = value2.replaceAll("[^A-Za-z0-9\\s]", "").trim().replaceAll(" +", " ").replaceAll("j", "i").replaceAll("v", "u");
				String tokens[] = value2.split("\\s");
				for (int i = 0; i < tokens.length; i++) {
					
					if (hm.containsKey(tokens[i].toLowerCase())) {
						ArrayList<String> list = new ArrayList<String>();
						list = hm.get(tokens[i].toLowerCase());
						for (int j = 0; j < list.size(); j++) {
							context.write(new Text(list.get(j)),new Text(value1));
						}
					} else {
						context.write(new Text(tokens[i]), new Text(value1));
					}
				}
			}
		}
	}


	public static class LatinReducer extends Reducer<Text, Text, Text, Text> {

		public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
			// String temp = "";
			Text temp = new Text();
			for (Text value : values) {
				// temp += value.toString() + " ";
				temp.append(value.getBytes(), 0, value.getLength());
			}

			context.write(key,temp);
		}
	}

	public static void main(String[] args) throws Exception {
		Path pt = new Path("hdfs://localhost:9000//home//hadoop//new_lemmatizer.csv");
		FileSystem fs = FileSystem.get(new Configuration());
		BufferedReader br = new BufferedReader(new InputStreamReader(fs.open(pt)));
		String line;
		while ((line = br.readLine()) != null) {
			String tokens[] = line.split(",");
			if (!hm.containsKey(tokens[0])) {
				ArrayList<String> list = new ArrayList<String>();
				for (int i = 0; i < tokens.length; i++) {
					if (tokens[i] != "") {
						list.add(tokens[i]);
					}
				}
				hm.put(tokens[0], list);
			}
		}
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf, "LatinWordCount");
		job.setJarByClass(Lemmatization.class);
		job.setMapperClass(LatinMapper.class);
		job.setCombinerClass(LatinReducer.class);
		job.setReducerClass(LatinReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);
		job.setOutputValueClass(Text.class);
		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}