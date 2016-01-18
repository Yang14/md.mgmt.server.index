package md.mgmt.dao.performance;

import md.mgmt.BasePerformanceTest;
import md.mgmt.dao.IndexFindRdbDao;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Mr-yang on 16-1-18.
 */
public class DaoPutDirMdIndex extends BasePerformanceTest {
    private static Logger logger = LoggerFactory.getLogger(DaoPutDirMdIndex.class);
    private static String methodDesc = "testGetDirMdIndex";
    @Autowired
    private IndexFindRdbDao indexFindRdbDao;

    protected DaoPutDirMdIndex() {
        super(logger, methodDesc);
    }

    @Override
    public long execMethod(int hotCount, int count) {
        for (int i = 1; i < hotCount; i++) {
            indexFindRdbDao.getDirMdIndex("key:" + i);
        }
        long start = System.currentTimeMillis();
        for (int i = 1; i < count; i++) {
            indexFindRdbDao.getDirMdIndex("key:" + i);
        }
        long end = System.currentTimeMillis();
        logger.info(String.format("count %s  use Total time: %s ms, avg time: %sms",
                count, (end - start), (end - start) / (count * 1.0)));
        return end - start;
    }

    @Test
    public void testGetDirMdIndex(){
        moduleMethod();
    }
}
