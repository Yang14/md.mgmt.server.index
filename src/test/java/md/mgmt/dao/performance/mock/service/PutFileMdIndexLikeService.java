package md.mgmt.dao.performance.mock.service;

import md.mgmt.BasePerformanceTest;
import md.mgmt.dao.FindRdbDao;
import md.mgmt.dao.CreateRdbDao;
import md.mgmt.dao.entity.DirMdIndex;
import md.mgmt.dao.entity.FileMdIndex;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Mr-yang on 16-1-18.
 */
public class PutFileMdIndexLikeService extends BasePerformanceTest {
    private static Logger logger = LoggerFactory.getLogger(PutFileMdIndexLikeService.class);

    private static String methodDesc = "testPutFileMdIndexLikeService";
    @Autowired
    private CreateRdbDao createRdbDao;

    @Autowired
    private FindRdbDao findRdbDao;

    public PutFileMdIndexLikeService() {
        super(logger, methodDesc);
    }

    @Test
    public void testPutFileMdIndexLikeService() {
        moduleMethod();
    }

    @Override
    public long execMethod(int hotCount, int count) {
        String key = -1 + ":" + "/";
        for (int i = 1; i < hotCount; i++) {
            findRdbDao.getDirMdIndex(key);
            createRdbDao.putFileMdIndex(0 + ":" + i, new FileMdIndex(i + "", false));
        }
        Map<String, DirMdIndex> hashMap = new ConcurrentHashMap<String, DirMdIndex>();

        long start = System.currentTimeMillis();
        for (int i = 1; i < count; i++) {
            DirMdIndex dirMdIndex = hashMap.get(key);
            if (dirMdIndex == null) {
                dirMdIndex = findRdbDao.getDirMdIndex(key);
                hashMap.put(key, dirMdIndex);
            }
            createRdbDao.putFileMdIndex(dirMdIndex.getMdIndex().getFileCode() + ":" + i, new FileMdIndex(i + "", false));
        }
        long end = System.currentTimeMillis();
        logger.info(String.format("count %s  use Total time: %s ms, avg time: %sms",
                count, (end - start), (end - start) / (count * 1.0)));
        return end - start;
    }


}
