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

    @Test
    public void testCreateDirMdIndex() {
        String path = "/bin";
        String name = "foo";
        MdAttrPos mdAttrPos = createMdIndexService.createDirMdIndex(new MdIndex(path, name));
        logger.info(mdAttrPos.toString());
    }

    @Test
    public void testCreateFileMdIndex() {
        String path = "/bin/foo";
        String name = "a";
        MdAttrPos mdAttrPos = createMdIndexService.createFileMdIndex(new MdIndex(path, name));
        if (mdAttrPos == null) {
            logger.info("创建失败！");
        } else {
            logger.info(mdAttrPos.toString());
        }
    }

    @Test
    public void testFindFileMdIndex() {
        String path = "/bin/foo";
        String name = "a";
        FileMdAttrPosList fileMdAttrPosList = findMdIndexService.findFileMdIndex(new MdIndex(path, name));
        logger.info(fileMdAttrPosList.toString());
    }

    @Test
    public void testFindDirMdIndex() {
        String path = "/bin";
        String name = "foo";
        DirMdAttrPosList dirMdAttrPosList = findMdIndexService.findDirMdIndex(new MdIndex(path, name));
        logger.info(dirMdAttrPosList.toString());
    }
}
