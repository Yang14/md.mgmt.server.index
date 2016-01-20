package md.mgmt.dao.performance.bigDir;

import md.mgmt.BasePerformanceTest;
import md.mgmt.dao.IndexRdbDao;
import md.mgmt.dao.entity.BigDirMdIndex;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mr-yang on 16-1-20.
 */
public class PutBigDirTest extends BasePerformanceTest {
    private static Logger logger = LoggerFactory.getLogger(PutBigDirTest.class);
    private static String methodDesc = "PutBigDirTest";

    @Autowired
    private IndexRdbDao indexRdbDao;

    public PutBigDirTest() {
        super(logger, methodDesc);
    }

    @Test
    public void testPutBigDir() {
        moduleMethod();
    }

    @Override
    public long execMethod(int hotCount, int count) {
        long code = 9999999;
        for (int i = 1; i < hotCount; i++) {
            List<Long> codes = new ArrayList<Long>();
            codes.add(code++);
            BigDirMdIndex dirMdIndex = new BigDirMdIndex(i + "", true, codes);
            indexRdbDao.putNewDirIndex("key:" + i, dirMdIndex);
        }
        long start = System.currentTimeMillis();
        for (int i = 1; i < count; i++) {
            List<Long> codes = new ArrayList<Long>();
            codes.add(code++);
            BigDirMdIndex dirMdIndex = new BigDirMdIndex(i + "", true, codes);
            indexRdbDao.putNewDirIndex("key:" + i, dirMdIndex);
        }
        long end = System.currentTimeMillis();
        logger.info(String.format("count %s  use Total time: %s ms, avg time: %sms",
                count, (end - start), (end - start) / (count * 1.0)));
        return end - start;
    }
}
