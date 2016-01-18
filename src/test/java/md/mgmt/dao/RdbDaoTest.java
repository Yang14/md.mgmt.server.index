package md.mgmt.dao;

import md.mgmt.dao.entity.DistrCodeList;
import md.mgmt.dao.entity.FileMdIndex;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mr-yang on 16-1-11.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring.xml")
public class RdbDaoTest {
    private Logger logger = LoggerFactory.getLogger(RdbDaoTest.class);

    @Autowired
    private IndexRdbDao indexRdbDao;

    @Autowired
    private IndexFindRdbDao indexFindRdbDao;


    @Test
    public void testPutFileMdIndex() {
        for (int i = 1; i < 100000; i++) {
            indexRdbDao.putFileMdIndex("key:" + i, new FileMdIndex(i + "", false));
        }
        int count = 10000;
        logger.info(String.valueOf(System.currentTimeMillis()));
        long start = System.currentTimeMillis();
        for (int i = 1; i < count; i++) {
            indexRdbDao.putFileMdIndex("key:" + i, new FileMdIndex(i + "", false));
        }
        long end = System.currentTimeMillis();
        logger.info(String.valueOf(System.currentTimeMillis()));
        logger.info(String.format("\ntestPutFileMdIndex %s  use Total time: %s ms\navg time: %ss",
                count, (end - start), (end - start) / (count * 1.0)));
    }

    @Test
    public void testGetFileMdIndex() {
        int count = 10000 * 10;
        for (int i = 1; i < 10000; i++) {
            indexFindRdbDao.getFileMdIndex("key:" + i);
        }
        logger.info(String.valueOf(System.currentTimeMillis()));
        long start = System.currentTimeMillis();
        for (int i = 1; i < count; i++) {
            logger.info(indexFindRdbDao.getFileMdIndex("key:" + i).toString());
        }
        long end = System.currentTimeMillis();
        logger.info(String.valueOf(System.currentTimeMillis()));
        logger.info(String.format("\ntestGetFileMdIndex %s  use Total time: %s ms\navg time: %ss",
                count, (end - start), (end - start) / (count * 1.0)));
    }

    @Test
    public void testPutDirMdIndex() {
        int cycle = 1;
        int hotCount = 10000 * 10;
        int count = 10000 * 100;
        for (int i = 0; i < cycle; ++i) {
            logger.info(String.format("testPutDirMdIndex round %s:", i + 1));
            logger.info(String.format("hotCount: %s, count: %s", hotCount, count));
            putDirMdIndex(hotCount, count);
        }
    }

    private void putDirMdIndex(int hotCount, int count) {
        for (int i = 1; i < hotCount; i++) {
            indexRdbDao.putFileMdIndex("key:" + i, new FileMdIndex(i + "", false));
        }
        long code = 1;
        long start = System.currentTimeMillis();
        for (int i = 1; i < count; i++) {
            indexRdbDao.putFileMdIndex("key:" + i, new FileMdIndex(i + "", false));
            List<Long> distrCodes = new ArrayList<Long>();
            distrCodes.add(code++);
            indexRdbDao.putDistrCodeList(i + "", new DistrCodeList(distrCodes));
        }
        long end = System.currentTimeMillis();
        logger.info(String.format("testPutFileMdIndex %s  use Total time: %s ms, avg time: %sms",
                count, (end - start), (end - start) / (count * 1.0)));
    }

    @Test
    public void testGetDirMdIndex() {
        int cycle = 5;
        int hotCount = 10000;
        int count = 10000 * 100;
        for (int i = 0; i < cycle; ++i) {
            logger.info(String.format("testPutDirMdIndex round %s:", i + 1));
            logger.info(String.format("hotCount: %s, count: %s", hotCount, count));
            getDirMdIndex(hotCount, count);
        }
    }

    private void getDirMdIndex(int hotCount, int count) {
        for (int i = 1; i < hotCount; i++) {
            indexFindRdbDao.getDirMdIndex("key:" + i);
        }
        long start = System.currentTimeMillis();
        for (int i = 1; i < count; i++) {
            indexFindRdbDao.getDirMdIndex("key:" + i);
        }
        long end = System.currentTimeMillis();
        logger.info(String.format("testPutFileMdIndex %s  use Total time: %s ms, avg time: %sms",
                count, (end - start), (end - start) / (count * 1.0)));
    }

}
