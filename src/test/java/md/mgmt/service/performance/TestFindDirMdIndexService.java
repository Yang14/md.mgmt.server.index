package md.mgmt.service.performance;

import md.mgmt.BasePerformanceTest;
import md.mgmt.base.md.MdIndex;
import md.mgmt.service.FindMdIndexService;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Mr-yang on 16-1-18.
 */
public class TestFindDirMdIndexService extends BasePerformanceTest{
    private static Logger logger = LoggerFactory.getLogger(TestFindDirMdIndexService.class);
    private static String methodDesc = "testFindDirMdIndex";

    @Autowired
    private FindMdIndexService findMdIndexService;

    public TestFindDirMdIndexService() {
        super(logger, methodDesc);
    }

    @Override
    public long execMethod(int hotCount, int count) {
        String path = "/";
        String dirName = "tstDir-";
        for (int i = 1; i < hotCount; i++) {
            findMdIndexService.findDirMdIndex(new MdIndex(path, dirName + i));
        }
        long start = System.currentTimeMillis();
        for (int i = 1; i < count; i++) {
            findMdIndexService.findDirMdIndex(new MdIndex(path, dirName + i));
        }
        long end = System.currentTimeMillis();
        logger.info(String.format("count: %s  use Total time: %s ms, avg time: %sms",
                count, (end - start), (end - start) / (count * 1.0)));
        return end - start;
    }

    @Test
    public void testFindDirMdIndex(){
        moduleMethod();
    }
}
