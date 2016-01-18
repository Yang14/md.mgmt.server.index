package md.mgmt.service;

import md.mgmt.BasePerformanceTest;
import md.mgmt.base.md.MdIndex;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by yang on 16-1-18.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring.xml")
public class ServicePerformancePerformanceTest extends BasePerformanceTest {
    private Logger logger = LoggerFactory.getLogger(ServicePerformancePerformanceTest.class);

    @Autowired
    private CreateMdIndexService createMdIndexService;

    @Autowired
    private FindMdIndexService findMdIndexService;

    @Autowired
    private RenameMdIndexService renameMdIndexService;

    @Before
    public void setUp() {
        if (createMdIndexService.createRootDir()) {
            logger.info("root dir create ok.");
        }
    }

    @Test
    public void testAutoCreateFileMdIndexPerformance() {
        logger.info("------------------------------------------");
        logger.info("start testing service create File MdIndex");
        for (int i=0;i<cycles.length;++i){
            testCreateFileMdIndex(cycles[i], hotCount, counts[i]);
        }
        logger.info("-------------------end--------------------");
    }

    private void testCreateFileMdIndex(int cycle,int hotCount, int count) {
        long totalTime = 0;
        long min = Integer.MAX_VALUE;
        long max = Integer.MIN_VALUE;
        for (int i = 0; i < cycle; ++i) {
            logger.info(String.format("testCreateFileMdIndex round %s:", i + 1));
            logger.info(String.format("hotCount: %s, count: %s", hotCount, count));
            long time = createFileMdIndex(hotCount, count);
            if (time > max) {
                max = time;
            }
            if (time <= min) {
                min = time;
            }
            totalTime += time;
        }
        logger.info(String.format("min %s, max %s, total avg time: %s\n\n",
                min, max, totalTime / (count * cycle * 1.0)));
    }

    private long createFileMdIndex(int hotCount, int count) {
        for (int i = 1; i < hotCount; i++) {
            createMdIndexService.createFileMdIndex(new MdIndex("/", "testFile" + i));
        }
        long start = System.currentTimeMillis();
        for (int i = 1; i < count; i++) {
            createMdIndexService.createFileMdIndex(new MdIndex("/", "testFile" + i));
        }
        long end = System.currentTimeMillis();
        logger.info(String.format("count: %s  use Total time: %s ms, avg time: %sms",
                count, (end - start), (end - start) / (count * 1.0)));
        return end - start;
    }


}
