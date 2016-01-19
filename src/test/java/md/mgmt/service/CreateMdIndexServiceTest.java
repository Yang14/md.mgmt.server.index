package md.mgmt.service;

import md.mgmt.base.md.MdIndex;
import md.mgmt.base.ops.RenamedMd;
import md.mgmt.facade.IndexServer;
import md.mgmt.facade.resp.find.DirMdAttrPosList;
import md.mgmt.facade.resp.find.FileMdAttrPosList;
import md.mgmt.facade.resp.index.MdAttrPos;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by Mr-yang on 16-1-11.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring.xml")
public class CreateMdIndexServiceTest {
    private Logger logger = LoggerFactory.getLogger(CreateMdIndexServiceTest.class);

    @Autowired
    private CreateMdIndexService createMdIndexService;

    @Autowired
    private FindMdIndexService findMdIndexService;

    @Autowired
    private RenameMdIndexService renameMdIndexService;

    @Before
    public void setUp() {
        if (createMdIndexService.createRootDir()) {
            logger.info("root dir create ok.");
        }
    }

    @Test
    public void startServer() throws InterruptedException {
        new IndexServer().run();
    }

    private void insertDir(String path, String name) {
        createMdIndexService.createDirMdIndex(new MdIndex(path, name));
    }

    /**
     * 每次都打开、关闭rdb消耗大量时间，测试使用全局或者缓存的方式能加速创建效率，每秒能有10w才800ms
     */
    @Test
    public void testBatchCreateDirTime() {
        int count = 10000;
        logger.info(String.valueOf(System.currentTimeMillis()));
        long start = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            createMdIndexService.createDirMdIndex(new MdIndex("/home/a", "tstDir" + i));
        }
        long end = System.currentTimeMillis();
        logger.info(String.valueOf(System.currentTimeMillis()));
        logger.info(String.format("\nCreate %s  use Total time: %s ms\navg time: %ss",
                count, (end - start), (end - start) / (count * 1.0)));
    }

    @Test
    public void testBatchFindDirTime() {
        int count = 10000;
        logger.info(String.valueOf(System.currentTimeMillis()));
        long start = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            findMdIndexService.findDirMdIndex(new MdIndex("/home/a", "tstDir" + i));
        }
        long end = System.currentTimeMillis();
        logger.info(String.valueOf(System.currentTimeMillis()));
        logger.info(String.format("\ntestBatchFindDirTime %s  use Total time: %s ms\navg time: %ss",
                count, (end - start), (end - start) / (count * 1.0)));
    }

    @Test
    public void testCreateDirMdIndex() {
        createMdIndexService.createDirMdIndex(new MdIndex("/", "home"));
        createMdIndexService.createDirMdIndex(new MdIndex("/home","a"));
        createMdIndexService.createDirMdIndex(new MdIndex("/home/a","b"));
        createMdIndexService.createDirMdIndex(new MdIndex("/home/a/b","c"));
        createMdIndexService.createDirMdIndex(new MdIndex("/home/a/b/c","d"));
        createMdIndexService.createDirMdIndex(new MdIndex("/home/a/b/c/d","e"));
    }


    @Test
    public void testCreateFileMdIndex() {
        MdAttrPos mdAttrPos = createMdIndexService.createFileMdIndex(new MdIndex("/", "root1"));
        if (mdAttrPos == null) {
            logger.info("创建失败！");
        } else {
            logger.info(mdAttrPos.toString());
        }
        mdAttrPos = createMdIndexService.createFileMdIndex(new MdIndex("/bin/foo", "a1"));
        if (mdAttrPos == null) {
            logger.info("创建失败！");
        } else {
            logger.info(mdAttrPos.toString());
        }
    }

    @Test
    public void testFindFileMdIndex() {
        String path = "/home7";
        String name = "tstFile-100000";
        FileMdAttrPosList fileMdAttrPosList = findMdIndexService.findFileMdIndex(new MdIndex(path, name));
        logger.info(fileMdAttrPosList.toString());
    }

    @Test
    public void testFindDirMdIndex() {
        String path = "/";
        String name = "home4";
        DirMdAttrPosList dirMdAttrPosList = findMdIndexService.findDirMdIndex(new MdIndex(path, name));
        logger.info(dirMdAttrPosList.toString());
    }

    @Test
    public void testRenameMdIndex() {
        createMdIndexService.createDirMdIndex(new MdIndex("/", "etc"));
        logger.info(findMdIndexService.findDirMdIndex(new MdIndex("/", "etc")).toString());
        createMdIndexService.createFileMdIndex(new MdIndex("/etc", "a.t"));
        logger.info(findMdIndexService.findFileMdIndex(new MdIndex("/etc", "a.t")).toString());

        logger.info(renameMdIndexService.renameMdIndex(new RenamedMd("/", "etc", "etc2")).toString());
        logger.info(renameMdIndexService.renameMdIndex(new RenamedMd("/etc2", "a.t", "a2.txt")).toString());

        logger.info(findMdIndexService.findFileMdIndex(new MdIndex("/", "etc2")).toString());
    }
}
