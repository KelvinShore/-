package com.atguigu.ct.analysis;

import com.atguigu.ct.analysis.tool.AnalysisTextTool;
import org.apache.hadoop.util.ToolRunner;

/**
 * Created by kelvin on 2019/5/31.
 */
public class AnalysisData {

    public static void main(String[] args) throws Exception {
        ToolRunner.run(new AnalysisTextTool(),args);
    }
}
