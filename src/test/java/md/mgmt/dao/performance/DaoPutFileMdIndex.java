package md.mgmt.dao.performance;

import md.mgmt.BasePerformanceTest;
import md.mgmt.dao.CreateRdbDao;
import md.mgmt.dao.entity.FileMdIndex;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Mr-yang on 16-1-18.
 */
public class DaoPutFileMdIndex extends BasePerformanceTest {
    private static Logger logger = LoggerFactory.getLogger(DaoPutFileMdIndex.class);

    private static String methodDesc = "testPutFileMdIndex";
    @Autowired
    private CreateRdbDao createRdbDao;

    public DaoPutFileMdIndex() {
        super(logger, methodDesc);
    }


    @Override
    public long execMethod(int hotCount, int count) {
        for (int i = 1; i < hotCount; i++) {
            createRdbDao.putFileMdIndex("key:" + i, new FileMdIndex(i + "", false));
        }
        long start = System.currentTimeMillis();
        for (int i = 1; i < count; i++) {
            createRdbDao.putFileMdIndex("key:" + i, new FileMdIndex(i + "", false));
        }
        long end = System.currentTimeMillis();
        logger.info(String.format("count %s  use Total time: %s ms, avg time: %sms",
                count, (end - start), (end - start) / (count * 1.0)));
        return end - start;
    }

    @Test
    public void testPutFileMdIndex(){
        moduleMethod();
    }
}
