/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

public class Distinct {
  
  private static final String tempDir = "/tmp";
  public static class TokenizerMapper1 
       extends Mapper<Object, Text, Text, IntWritable>{
    
    private final static IntWritable one = new IntWritable(1);
    private Text word = new Text();
      
    public void map(Object key, Text value, Context context
                    ) throws IOException, InterruptedException {
        word.set(value.toString());
        context.write(word, one);
    }
  }
  
  public static class Reducer1 
       extends Reducer<Text,IntWritable,Text,NullWritable> {

    public void reduce(Text key, Iterable<IntWritable> values, 
                       Context context
                       ) throws IOException, InterruptedException {
      
      context.write(key, NullWritable.get());
    }
  }
  
  public static class TokenizerMapper2 
       extends Mapper<Object, Text, Text, IntWritable>{
    
    private final static IntWritable one = new IntWritable(1);
    private Text word = new Text("1");
      
    public void map(Object key, Text value, Context context
                    ) throws IOException, InterruptedException {
        context.write(word,one);
    }
  }
  
  public static class Reducer2
       extends Reducer<Text,IntWritable,IntWritable,NullWritable> {

    public void reduce(Text key, Iterable<IntWritable> values,
                       Context context
                       ) throws IOException, InterruptedException {
      int sum = 0;
      for (IntWritable val : values) {
        sum += 1;
      }
      context.write(new IntWritable(sum),NullWritable.get());
    }
  }

  public static void main(String[] args) throws Exception {
    Configuration conf = new Configuration();
    String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
    if (otherArgs.length != 2) {
      System.err.println("Usage: Distinct <in> <out>");
      System.exit(2);
    }
    Job job = Job.getInstance(conf, "distinct1");
    job.setJarByClass(Distinct.class);
    job.setMapperClass(TokenizerMapper1.class);
    job.setReducerClass(Reducer1.class);
    FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
    FileOutputFormat.setOutputPath(job, new Path(tempDir));
    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(IntWritable.class);
    job.waitForCompletion(true);
    
    Configuration conf2 = new Configuration();
    Job job2 = Job.getInstance(conf2, "distinct2");
    job2.setJarByClass(Distinct.class);
    job2.setMapperClass(TokenizerMapper2.class);
    job2.setReducerClass(Reducer2.class);
    FileInputFormat.addInputPath(job2, new Path(tempDir));
    FileOutputFormat.setOutputPath(job2, new Path(otherArgs[1]));
    job2.setOutputKeyClass(Text.class);
    job2.setOutputValueClass(IntWritable.class);
    System.exit(job2.waitForCompletion(true) ? 0 : 1);
  }
}
