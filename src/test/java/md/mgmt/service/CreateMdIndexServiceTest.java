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
            if (createMdIndexService.createRootDir()) {
                logger.info("root dir create ok.");
            }
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
        String path1 = "/bin/foo";
        String path2 = "/bin/foo2";
        String namePrefix = "tst-";
        int count = 4;
        logger.info(String.valueOf(System.currentTimeMillis()));
        long start = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            createMdIndexService.createFileMdIndex(new MdIndex(path1, namePrefix));
        }
        long end = System.currentTimeMillis();
        logger.info(String.valueOf(System.currentTimeMillis()));
        logger.info(String.format("\nCreate 10000 dir use Total time: %s ms\navg time: %ss",
                (end - start), (end - start) / (count * 1000.0)));
    }

    @Test
    public void testCreateDirMdIndex() {
        long start = System.currentTimeMillis();
        MdAttrPos mdAttrPos = createMdIndexService.createDirMdIndex(new MdIndex("/", "bin"));
        logger.info(mdAttrPos.toString());
        mdAttrPos = createMdIndexService.createDirMdIndex(new MdIndex("/bin", "foo"));
        logger.info(mdAttrPos.toString());
        mdAttrPos = createMdIndexService.createDirMdIndex(new MdIndex("/bin", "foo2"));
        logger.info(mdAttrPos.toString());
        mdAttrPos = createMdIndexService.createDirMdIndex(new MdIndex("/bin", "foo3"));
        logger.info(mdAttrPos.toString());
        mdAttrPos = createMdIndexService.createDirMdIndex(new MdIndex("/", "etc"));
        logger.info(mdAttrPos.toString());
        long end = System.currentTimeMillis();
        logger.info(String.format("\nCreate 10000 dir use Total time: %s\navg time: %s",
                (end - start), (end - start) / (5 * 1.0)));

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
        String path = "/";
        String name = "root1";
        FileMdAttrPosList fileMdAttrPosList = findMdIndexService.findFileMdIndex(new MdIndex(path, name));
        logger.info(fileMdAttrPosList.toString());
    }

    @Test
    public void testFindDirMdIndex() {
        String path = "/bin";
        String name = "foo2";
        DirMdAttrPosList dirMdAttrPosList = findMdIndexService.findDirMdIndex(new MdIndex(path, name));
        logger.info(dirMdAttrPosList.toString());
    }

    @Test
    public void testRenameMdIndex(){
        createMdIndexService.createDirMdIndex(new MdIndex("/","etc"));
        logger.info(findMdIndexService.findDirMdIndex(new MdIndex("/", "etc")).toString());
        createMdIndexService.createFileMdIndex(new MdIndex("/etc", "a.t"));
        logger.info(findMdIndexService.findFileMdIndex(new MdIndex("/etc", "a.t")).toString());

        renameMdIndexService.renameMdIndex(new RenamedMd("/", "etc", "etc2"));
        logger.info(findMdIndexService.findDirMdIndex(new MdIndex("/", "etc2")).toString());
        renameMdIndexService.renameMdIndex(new RenamedMd("/etc2", "a.t","a2.txt"));
        logger.info(findMdIndexService.findFileMdIndex(new MdIndex("/etc2", "a2.txt")).toString());
    }
}
