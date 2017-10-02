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

public class Trigram {
	static HashMap<String, ArrayList<String>> hm = new HashMap<String, ArrayList<String>>();

	public static class TrigramMapper extends Mapper<Object, Text, Text, Text> {

		public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
			if (value.toString().length() != 0 && value.toString().contains("<") && value.toString().contains(">")) {
				String value1 = value.toString().substring(value.toString().indexOf("<"),
						value.toString().indexOf(">") + 1);
				String value2 = value.toString().substring(value.toString().indexOf(">"));
				value2 = value2.replaceAll("[^A-Za-z0-9\\s]", "").trim().replaceAll(" +", " ").replaceAll("j", "i")
						.replaceAll("v", "u");
				String tokens[] = value2.split("\\s");
				for (int i = 0; i < tokens.length; i++) {
					for (int j = i + 1; j < tokens.length; j++) {
						for (int k = j + 1; k < tokens.length; k++) {
							if (hm.containsKey(tokens[i].toLowerCase()) && hm.containsKey(tokens[j].toLowerCase())
									&& hm.containsKey(tokens[k].toLowerCase())) {
								ArrayList<String> list1 = new ArrayList<String>();
								ArrayList<String> list2 = new ArrayList<String>();
								ArrayList<String> list3 = new ArrayList<String>();
								list1 = hm.get(tokens[i].toLowerCase());
								list2 = hm.get(tokens[j].toLowerCase());
								list3 = hm.get(tokens[k].toLowerCase());
								for (int l = 1; l < list1.size(); l++) {
									for (int m = 1; m < list2.size(); m++) {
										for (int n = 1; n < list3.size(); n++) {
											context.write(
													new Text(list1.get(l) + " " + list2.get(m) + " " + list3.get(n)),
													new Text(value1));
										}
									}
								}
							}
							context.write(new Text(tokens[i] + " " + tokens[j] + " " + tokens[k]), new Text(value1));

						}
					}
				}
			}
		}
	}

	public static class TrigramReducer extends Reducer<Text, Text, Text, Text> {

		public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
			Text temp = new Text();
			for (Text value : values) {
				temp.append(value.getBytes(), 0, value.getLength());
			}
			context.write(key, temp);

		}
	}

	public static void main(String[] args) throws Exception {
		Path pt = new Path("hdfs://localhost:9000//home//hadoop//lemmatizer");
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
		Job job = Job.getInstance(conf, "Trigram");
		job.setJarByClass(Trigram.class);
		job.setMapperClass(TrigramMapper.class);
		job.setCombinerClass(TrigramReducer.class);
		job.setReducerClass(TrigramReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);
		job.setOutputValueClass(Text.class);
		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}