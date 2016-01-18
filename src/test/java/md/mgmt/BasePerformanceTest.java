package md.mgmt;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by Mr-yang on 16-1-18.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring.xml")
public abstract class BasePerformanceTest {

    private Logger logger;
    private String methodDesc;

    private static int[] cycles = new int[]{10, 5, 3};
    private static int[] counts = new int[]{10000, 10000 * 10, 10000 * 100};
    private static int hotCount = 10000;



    public BasePerformanceTest(Logger logger, String methodDesc) {
        this.logger = logger;
        this.methodDesc = methodDesc;
    }

    public void moduleMethod() {
        logger.info("------------------start--------------------");
        logger.info(methodDesc);
        for (int i = 0; i < cycles.length; ++i) {
            calAvgTime(cycles[i], hotCount, counts[i]);
        }
        logger.info("-------------------end--------------------\n");
    }

    private void calAvgTime(int cycle, int hotCount, int count) {
        long totalTime = 0;
        long min = Integer.MAX_VALUE;
        long max = Integer.MIN_VALUE;
        for (int i = 0; i < cycle; ++i) {
            logger.info(String.format("round %s:", i + 1));
            long time = execMethod(hotCount, count);
            logger.info(String.format("hotCount: %s, count: %s, using time: %s", hotCount, count, time));
            if (time > max) {
                max = time;
            }
            if (time <= min) {
                min = time;
            }
            totalTime += time;
        }
        logger.info(String.format("min %s, max %s, total avg time: %s",
                min, max, totalTime / (count * cycle * 1.0)));
    }

    public abstract long execMethod(int hotCount, int count);
}
