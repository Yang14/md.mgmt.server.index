package md.mgmt.dao.performance;

import md.mgmt.BasePerformanceTest;
import md.mgmt.dao.IndexRdbDao;
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

    @Autowired
    private IndexRdbDao indexRdbDao;

    protected DaoPutFileMdIndex(String methodDesc) {
        super(logger, methodDesc);
    }

    @Override
    public long execMethod(int hotCount, int count) {
        for (int i = 1; i < hotCount; i++) {
            indexRdbDao.putFileMdIndex("key:" + i, new FileMdIndex(i + "", false));
        }
        logger.info(String.valueOf(System.currentTimeMillis()));
        long start = System.currentTimeMillis();
        for (int i = 1; i < count; i++) {
            indexRdbDao.putFileMdIndex("key:" + i, new FileMdIndex(i + "", false));
        }
        long end = System.currentTimeMillis();
        logger.info(String.valueOf(System.currentTimeMillis()));
        logger.info(String.format("\ncount: %s  use Total time: %s ms\navg time: %ss",
                count, (end - start), (end - start) / (count * 1.0)));
        return end - start;
    }

    @Test
    public void testPutFileMdIndex(){
        moduleMethod();
    }
}
