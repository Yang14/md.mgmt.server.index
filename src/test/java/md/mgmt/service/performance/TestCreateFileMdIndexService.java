package md.mgmt.service.performance;

import md.mgmt.BasePerformanceTest;
import md.mgmt.base.md.MdIndex;
import md.mgmt.service.CreateMdIndexService;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Mr-yang on 16-1-18.
 */
public class TestCreateFileMdIndexService extends BasePerformanceTest{
    private static Logger logger = LoggerFactory.getLogger(TestCreateFileMdIndexService.class);
    private static String methodDesc = "testCreateFileMdIndex";

    @Autowired
    private CreateMdIndexService createMdIndexService;

    public TestCreateFileMdIndexService() {
        super(logger, methodDesc);
    }

    @Override
    public long execMethod(int hotCount, int count) {
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

    @Test
    public void testCreateFileMdIndex(){
        moduleMethod();
    }
}
