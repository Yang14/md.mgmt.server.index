package md.mgmt.service;

import md.mgmt.base.md.ClusterNodeInfo;
import md.mgmt.base.md.MdIndex;
import md.mgmt.common.CommonModule;
import md.mgmt.facade.resp.index.MdAttrPos;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;

/**
 * Created by Mr-yang on 16-1-11.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring.xml")
public class CreateMdIndexServiceTest {
    private Logger logger = LoggerFactory.getLogger(CreateMdIndexServiceTest.class);

    @InjectMocks
    @Autowired
    private CreateMdIndexService createMdIndexService;

    @Mock
    private CommonModule commonModule;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
        when(commonModule.genDistrCode()).thenReturn(1000);
        when(commonModule.genFileCode()).thenReturn("fileCode-9999");
        when(commonModule.checkDistrCodeFit(anyInt())).thenReturn(true);
        when(commonModule.getMdLocation(anyInt())).thenReturn(new ClusterNodeInfo("be-01", 8008, 1000));
        when(commonModule.genMdLocation()).thenReturn(new ClusterNodeInfo("be-02", 8008, 1001));
        if (createMdIndexService.createRootDir()) {
            logger.info("root dir create ok.");
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
}
