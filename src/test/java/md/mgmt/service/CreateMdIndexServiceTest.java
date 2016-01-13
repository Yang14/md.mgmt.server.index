package md.mgmt.service;

import md.mgmt.base.md.MdIndex;
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

import java.util.Random;

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

    @Before
    public void setUp() {
        if (createMdIndexService.createRootDir()) {
            if (createMdIndexService.createRootDir()) {
                logger.info("root dir create ok.");
            }
        }
    }

    private void insertDir(String path, String name) {
        createMdIndexService.createDirMdIndex(new MdIndex(path, name));
    }

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
        MdAttrPos mdAttrPos = createMdIndexService.createFileMdIndex(new MdIndex("/bin", "a"));
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

    //putFileMdIndex:{"fileName":"a1","parentDirCode":"b581ce3c0464435c923ad2e7ad785a38"}:
//fileCode-->fbc55ab206204a568fcdb83103343461
//MdAttrPos{exactCode=ExactCode{distrCode='-671204313', fileCode='fbc55ab206204a568fcdb83103343461'},
// clusterNodeInfo=ClusterNodeInfo{ip='node-03', port=8008, distrCode='-671204313'}}
    @Test
    public void testFindFileMdIndex() {
        String path = "/bin/foo";
        String name = "a1";
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
}
