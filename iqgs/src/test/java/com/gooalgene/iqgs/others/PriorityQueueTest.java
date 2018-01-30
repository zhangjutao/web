package com.gooalgene.iqgs.others;

import org.junit.Test;

import java.util.Comparator;
import java.util.PriorityQueue;

public class PriorityQueueTest {

    @Test
    public void testPriorityQueue(){
        PriorityQueue<Integer> queue = new PriorityQueue<>(10, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1 > o2 ? 1 : (o1.intValue() == o2.intValue() ? 0 : -1);
            }
        });
        queue.add(10);
        queue.add(-1);
        queue.add(8);
        System.out.println(queue.peek());
    }
}
