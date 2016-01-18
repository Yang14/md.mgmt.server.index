package md.mgmt.facade;

import md.mgmt.facade.mapper.CommandMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by yang on 16-1-17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring.xml")
public class CommandMapperTest {

    @Autowired
    private CommandMapper commandMapper;

    @Test
    public void testCreateFileIndex(){
        String cmd = "{\"opsContent\":\"{\\\"name\\\":\\\"a-file169\\\",\\\"path\\\":\\\"/home/a\\\"}\",\"opsType\":1}";
        commandMapper.selectService(cmd);
    }
}
