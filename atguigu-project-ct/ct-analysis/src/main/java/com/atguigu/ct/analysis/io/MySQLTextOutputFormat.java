package com.atguigu.ct.analysis.io;

import com.atguigu.ct.common.util.JDBCUtil;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.output.FileOutputCommitter;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/** MySQL数据格式化输入对象
 * Created by kelvin on 2019/5/31.
 */
public class MySQLTextOutputFormat extends OutputFormat<Text,Text> {

    protected static class MySQLRecordWriter extends RecordWriter<Text,Text>{
        private Connection connection=null;

        public MySQLRecordWriter(){
            //获取资源
             connection = JDBCUtil.getConnection();
        }

        /**
         * 输出数据
         * @param key
         * @param value
         * @throws IOException
         * @throws InterruptedException
         */
        public void write(Text key, Text value) throws IOException, InterruptedException {
            String[] values = value.toString().split("_");
            String sumCall = values[0];
            String sumDuration = values[1];

            PreparedStatement pstat=null;

            try {
                String insertSQL="insert into ct_call ( telid,dateid,sumcall,sumduration)values(?,?,?,?)";
                pstat=connection.prepareStatement(insertSQL);
                pstat.setInt(1,2);
                pstat.setInt(2,3);
                pstat.setInt(3,Integer.parseInt(sumCall));
                pstat.setInt(4,Integer.parseInt(sumDuration));

                pstat.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (pstat != null){
                    try {
                        pstat.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        /**
         * 释放资源
         * @param context
         * @throws IOException
         * @throws InterruptedException
         */
        @Override
        public void close(TaskAttemptContext context) throws IOException, InterruptedException {
            if(connection != null){
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }


    }

    @Override
    public RecordWriter<Text, Text> getRecordWriter(TaskAttemptContext context) throws IOException, InterruptedException {
        return new MySQLRecordWriter();
    }

    @Override
    public void checkOutputSpecs(JobContext context) throws IOException, InterruptedException {

    }

    private FileOutputCommitter committer=null;
    public static Path getOutputPath(JobContext job){
        String name = job.getConfiguration().get(FileOutputFormat.OUTDIR);
        return name==null?null:new Path(name);
    }

    @Override
    public OutputCommitter getOutputCommitter(TaskAttemptContext context) throws IOException, InterruptedException {
        if(committer == null){
            Path output = getOutputPath(context);
            committer = new FileOutputCommitter(output, context);
        }

        return committer;
    }
}
