import java.io.IOException;
import java.util.StringTokenizer;
import java.util.Set;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class Stripes{


public static class MyHashMap extends MapWritable{
	@Override
	public String toString(){
	StringBuilder result = new StringBuilder();
        Set<Writable> keySet = this.keySet();

        for (Object key : keySet) {
            result.append("/n" + key.toString() + "/t" + this.get(key));
        }
        return result.toString();
}
}

public static class TokenizerMapper
       extends Mapper<LongWritable, Text, Text, MyHashMap>{

	private MyHashMap myhashmap = new MyHashMap();
	private final static IntWritable one = new IntWritable(1);
  	private Text word = new Text();

	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
    	String[] tokens = value.toString().replaceAll(" +"," ").trim().split("\\s");
     	
	if (tokens.length > 1) {

	for (int i = 0; i < tokens.length-1; i++) {
		word.set(tokens[i]);
          	myhashmap.clear();
             for (int j = i+1; j < tokens.length; j++) {
		if (j == i) continue;
		Text neighbor = new Text(tokens[j]);
		if(myhashmap.containsKey(neighbor)){
                   IntWritable writer = (IntWritable)myhashmap.get(neighbor);
                   writer.set(writer.get()+1);
                }else{
                   myhashmap.put(neighbor,one);
                }
		
                 context.write(word, myhashmap);
              }
          }


	}
	}
}

public static class IntSumReducer
       extends Reducer<Text,MapWritable,Text,Text> {
	private MyHashMap hashmap = new MyHashMap();
	
	public void reduce(Text key, Iterable<MapWritable> values,
                       Context context) throws IOException, InterruptedException {
	hashmap.clear();
        for (MapWritable value : values) {

        Set<Writable> set = value.keySet();
        for (Writable keys : set) {
            IntWritable count = (IntWritable) value.get(keys);
            if (hashmap.containsKey(keys)) {
                IntWritable writer = (IntWritable) hashmap.get(keys);
                writer.set(writer.get() + count.get());
            } else {
                hashmap.put(keys, count);
            }
        }

        }
	for(Object obj : hashmap.keySet()){
        context.write(key,new Text(obj+" " + hashmap.get(obj)));
	}
    }
}



public static void main(String[] args) throws Exception {
    Configuration conf = new Configuration();
    Job job = Job.getInstance(conf, "Stripes");
    job.setJarByClass(Stripes.class);
    job.setMapperClass(TokenizerMapper.class);
    job.setReducerClass(IntSumReducer.class);
    job.setOutputKeyClass(Text.class);
    job.setMapOutputKeyClass(Text.class);
    job.setMapOutputValueClass(MyHashMap.class);
    job.setOutputValueClass(Text.class);
    FileInputFormat.addInputPath(job, new Path(args[0]));
    FileOutputFormat.setOutputPath(job, new Path(args[1]));
    System.exit(job.waitForCompletion(true) ? 0 : 1);
  }


}