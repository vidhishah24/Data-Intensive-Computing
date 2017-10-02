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

public class Bigram {
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
					 for (int j = i+1; j < tokens.length; j++) {
//					if (position.length==3){
//						context.write(new Text(tokens[i]+" "+tokens[j]),
//								new Text(position[0] + ",[" + position[1] + "," + position[2] + "]>"));
//					}else{
//					context.write(new Text(tokens[i]+" "+tokens[j]),
//							new Text(position[0] + position[1] + ",[" + position[2] + "," + position[3] + "]>"));
//					}
						 if (hm.containsKey(tokens[i].toLowerCase())&& hm.containsKey(tokens[j].toLowerCase())) {
								ArrayList<String> list1 = new ArrayList<String>();
								ArrayList<String> list2 =new ArrayList<String>();
								list1 = hm.get(tokens[i].toLowerCase());
								list2 = hm.get(tokens[j].toLowerCase());
								for (int l = 1; l < list1.size(); l++) {
									for (int k = 1; k < list2.size(); k++) {
									context.write(new Text(list1.get(l)+" "+list2.get(k)), new Text(value1));
									}
								}
							} 
								context.write(new Text(tokens[i]+" "+tokens[j]),new Text(value1));
							
						}
					 }
				}
			}
		}

//	public static class LatinCombiner extends Reducer<Text, Text, Text, Text> {
//
//		public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
//			// String temp = "";
//			Text temp = new Text();
//			for (Text value : values) {
//				// temp += value.toString() + " ";
//				temp.append(value.getBytes(), 0, value.getLength());
//			}
//			context.write(key, temp);
//		}
//	}

	public static class LatinReducer extends Reducer<Text, Text, Text, Text> {

		public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
			// String temp = "";
			Text temp = new Text();
			for (Text value : values) {
				// temp += value.toString() + " ";
				temp.append(value.getBytes(), 0, value.getLength());
			}
			//String []keys=key.toString().split("\\s");
			// int count = temp.length() - temp.replace("<", "").length();
//			if (hm.containsKey(keys[0].toLowerCase())&& hm.containsKey(keys[1].toLowerCase())) {
//				ArrayList<String> list1 = new ArrayList<String>();
//				ArrayList<String> list2 =new ArrayList<String>();
//				list1 = hm.get(keys[0].toLowerCase());
//				list2 = hm.get(keys[1].toLowerCase());
//				for (int i = 1; i < list1.size(); i++) {
//					for (int j = 1; j < list2.size(); j++) {
//					context.write(new Text(list1.get(i)+" "+list2.get(j)), temp);
//					}
//				}
//			} 
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
		Job job = Job.getInstance(conf, "LatinWordCount");
		job.setJarByClass(Bigram.class);
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