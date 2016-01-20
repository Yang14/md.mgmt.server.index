package md.mgmt.dao.performance.mock.service;

import md.mgmt.BasePerformanceTest;
import md.mgmt.dao.FindRdbDao;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Mr-yang on 16-1-18.
 */
public class GetFileMdIndexLikeService extends BasePerformanceTest {
    private static Logger logger = LoggerFactory.getLogger(GetFileMdIndexLikeService.class);

    private static String methodDesc = "testGetFileMdIndexLikeService";

    @Autowired
    private FindRdbDao findRdbDao;

    public GetFileMdIndexLikeService() {
        super(logger, methodDesc);
    }

    @Test
    public void testGetFileMdIndexLikeService() {
        moduleMethod();
    }

    @Override
    public long execMethod(int hotCount, int count) {
        for (int i = 1; i < hotCount; i++) {
            findRdbDao.getFileMd("/",i+"");
        }
        long start = System.currentTimeMillis();
        for (int i = 1; i < count; i++) {
            findRdbDao.getFileMd("/",i+"");
        }
        long end = System.currentTimeMillis();
        logger.info(String.format("count %s  use Total time: %s ms, avg time: %sms",
                count, (end - start), (end - start) / (count * 1.0)));
        return end - start;
    }


}
