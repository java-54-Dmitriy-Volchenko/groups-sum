package telran.numbers.test;
import telran.performance.*;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import telran.numbers.*;
class GroupsSumTest {
private static final int N_GROUPS = 1000;
private static final int GROUP_LENGTH = 1000;
int[][] groups = {
		{1,2},
		{3,4},
		{5,6}
};
int[][] largeGroups = getLargeGroups(N_GROUPS, GROUP_LENGTH);
	@Test
	void goupsSumTaskThreadTest() {
		GroupsSum gs = new GroupsSumTaskThread(groups);
		assertEquals(21, gs.getSum());
	}
	private int[][] getLargeGroups(int nGroups, int groupLength) {
		 Random random = new Random();
	        return IntStream.range(0, N_GROUPS)
	                .mapToObj(i -> IntStream.generate(() -> random.nextInt(1000))
	                        .limit(GROUP_LENGTH) 
	                        .toArray()) 
	                .toArray(int[][]::new); 
	}
	@Test
	void goupsSumThreadPoolTest() {
		GroupsSum gs = new GroupsSumThreadPool(groups);
		assertEquals(21, gs.getSum());
	}
	@Test
	void groupsSumTaskThreadPerformance() {
		
		 PerformanceTest taskThreadTest = new PerformanceTest("Task Thread Performance", 10) {
		      @Override
		      protected void runTest() {
		      new GroupsSumTaskThread(largeGroups).getSum();
		            }
		        };
		        taskThreadTest.run();
	}
	
	@Test
	 void groupsSumTaskThreadPoolsPerformance() {
        PerformanceTest threadPoolTest = new PerformanceTest("Thread Pool Performance", 10) {
            @Override
            protected void runTest() {
                new GroupsSumThreadPool(largeGroups).getSum();
            }
        };
        threadPoolTest.run();
    }
	
}
