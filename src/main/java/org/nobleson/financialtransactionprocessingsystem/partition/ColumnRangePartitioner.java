package org.nobleson.financialtransactionprocessingsystem.partition;

import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;

import java.util.HashMap;
import java.util.Map;



public class ColumnRangePartitioner implements Partitioner {
    @Override
    public Map<String, ExecutionContext> partition(int gridSize) {
        int min = 1;
        int max = 1000;
        int targetSize = (max - min) / gridSize + 1;
        System.out.println("targetSize = " + targetSize);
        Map<String, ExecutionContext> partitions = new HashMap<String, ExecutionContext>();
        int number = 0;
        int start = min;
        int end = start + targetSize - 1;

        while (start <= max) {
            ExecutionContext context = new ExecutionContext();
            partitions.put("partition" +  number, context);

            if (end >= max){
                end = max;
            }
             context.putInt("minValue", start);
            context.putInt("maxValue", end);
            start += targetSize;
            end += targetSize;
            number++;
        }
        System.out.println("partition result :  " + partitions.toString());
        return partitions;
    }
}
