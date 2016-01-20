package md.mgmt.dao.performance;

import md.mgmt.BasePerformanceTest;
import md.mgmt.dao.IndexFindRdbDao;
import md.mgmt.dao.IndexRdbDao;
import md.mgmt.dao.entity.DistrCodeList;
import md.mgmt.dao.entity.FileMdIndex;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mr-yang on 16-1-18.
 */
public class DaoPutDirMdIndex extends BasePerformanceTest {
    private static Logger logger = LoggerFactory.getLogger(DaoPutDirMdIndex.class);
    private static String methodDesc = "testPutDirMdIndex";
    @Autowired
    private IndexRdbDao indexRdbDao;

    public DaoPutDirMdIndex() {
        super(logger, methodDesc);
    }

    @Override
    public long execMethod(int hotCount, int count) {
        for (int i = 1; i < hotCount; i++) {
            indexRdbDao.putFileMdIndex("key:" + i, new FileMdIndex(i + "", false));
        }
        long code = 10;
        long start = System.currentTimeMillis();
        for (int i = 1; i < count; i++) {
            indexRdbDao.putFileMdIndex("key:" + i, new FileMdIndex(i + "", false));
            List<Long> distrCodes = new ArrayList<Long>();
            distrCodes.add(code++);
            indexRdbDao.putDistrCodeList(i + "", new DistrCodeList(distrCodes));
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
