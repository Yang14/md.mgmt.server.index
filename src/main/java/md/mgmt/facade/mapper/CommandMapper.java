package md.mgmt.facade.mapper;

import com.alibaba.fastjson.JSON;
import md.mgmt.base.constant.OpsTypeEnum;
import md.mgmt.base.md.MdIndex;
import md.mgmt.base.ops.ReqDto;
import md.mgmt.facade.resp.index.MdAttrPos;
import md.mgmt.service.CreateMdIndexService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Mr-yang on 15-12-22.
 */
@Component
public class CommandMapper {
    private Logger logger = LoggerFactory.getLogger(CommandMapper.class);

    @Autowired
    private CreateMdIndexService createMdIndexService;

    /**
     * 按客户端命令选择对应的服务
     */
    public String selectService(String cmd) {
        logger.info("client cmd:" + cmd);
        ReqDto reqDto = JSON.parseObject(cmd, ReqDto.class);
        if (reqDto.getOpsType() == OpsTypeEnum.CREATE_FILE.getCode()) {
            return createFileMdIndex(reqDto.getOpsContent());
        } else if (reqDto.getOpsType() == OpsTypeEnum.CREATE_DIR.getCode()) {

        }
        return "";
    }

    private String createFileMdIndex(String opsContent) {
        MdIndex mdIndex = JSON.parseObject(opsContent, MdIndex.class);
        MdAttrPos mdAttrPos = createMdIndexService.createFileMdIndex(mdIndex);
        return JSON.toJSONString(mdAttrPos);
    }

}
