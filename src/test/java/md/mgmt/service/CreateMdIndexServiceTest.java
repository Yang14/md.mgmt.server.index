package md.mgmt.service;

import md.mgmt.common.CommonModule;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.mockito.Mockito.when;

/**
 * Created by Mr-yang on 16-1-11.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring.xml")
public class CreateMdIndexServiceTest {

    @InjectMocks
    @Autowired
    private CreateMdIndexService createMdIndexService;

    @Mock
    private CommonModule commonModule;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateFileMdIndex(){
        when(commonModule.genDistrCode()).thenReturn("distrCode-1000");
        when(commonModule.genFileCode()).thenReturn("fileCode-9999");
        createMdIndexService.createFileMdIndex(null);
    }
}
