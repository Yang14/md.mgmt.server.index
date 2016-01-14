package md.mgmt.facade.mapper;

import com.alibaba.fastjson.JSON;
import md.mgmt.base.constant.OpsTypeEnum;
import md.mgmt.base.md.MdIndex;
import md.mgmt.base.ops.ReqDto;
import md.mgmt.base.ops.RespDto;
import md.mgmt.facade.resp.find.DirMdAttrPosList;
import md.mgmt.facade.resp.find.FileMdAttrPosList;
import md.mgmt.facade.resp.index.MdAttrPos;
import md.mgmt.service.CreateMdIndexService;
import md.mgmt.service.FindMdIndexService;
import md.mgmt.service.impl.CreateMdIndexServiceImpl;
import md.mgmt.service.impl.FindMdIndexServiceImpl;
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
    private CreateMdIndexService createMdIndexService = new CreateMdIndexServiceImpl();

    @Autowired
    private FindMdIndexService findMdIndexService = new FindMdIndexServiceImpl();

    /**
     * 按客户端命令选择对应的服务
     */
    public String selectService(String cmd) {
        ReqDto reqDto = JSON.parseObject(cmd, ReqDto.class);
        String opsContent = reqDto.getOpsContent();
        if (opsContent == null) {
            return getRespStr(false, "参数错误", null);
        }
        if (reqDto.getOpsType() == OpsTypeEnum.CREATE_FILE.getCode()) {
            return createFileMdIndex(opsContent);
        } else if (reqDto.getOpsType() == OpsTypeEnum.CREATE_DIR.getCode()) {
            return createDirMdIndex(opsContent);
        } else if (reqDto.getOpsType() == OpsTypeEnum.FIND_FILE.getCode()) {
            return findFileMdIndex(opsContent);
        } else if (reqDto.getOpsType() == OpsTypeEnum.LIST_DIR.getCode()) {
            return findDirMdIndex(opsContent);
        }
        return getRespStr(false, "参数错误", null);
    }

    private String createFileMdIndex(String opsContent) {
        MdIndex mdIndex = JSON.parseObject(opsContent, MdIndex.class);
        MdAttrPos mdAttrPos = createMdIndexService.createFileMdIndex(mdIndex);
        logger.info(mdAttrPos.toString());
        if (mdAttrPos == null) {
            return getRespStr(false, "创建文件索引失败", null);
        }
        return getRespStr(true, "创建文件索引成功", mdAttrPos);
    }

    private String createDirMdIndex(String opsContent) {
        MdIndex mdIndex = JSON.parseObject(opsContent, MdIndex.class);
        MdAttrPos mdAttrPos = createMdIndexService.createDirMdIndex(mdIndex);
        if (mdAttrPos == null) {
            return getRespStr(false, "创建目录索引失败", null);
        }
        return getRespStr(true, "创建目录索引成功", mdAttrPos);
    }

    private String findFileMdIndex(String opsContent) {
        MdIndex mdIndex = JSON.parseObject(opsContent, MdIndex.class);
        FileMdAttrPosList fileMdIndex = findMdIndexService.findFileMdIndex(mdIndex);
        if (fileMdIndex == null) {
            return getRespStr(false, "获取文件索引失败", null);
        }
        return getRespStr(true, "获取文件索引成功", fileMdIndex);
    }

    private String findDirMdIndex(String opsContent) {
        MdIndex mdIndex = JSON.parseObject(opsContent, MdIndex.class);
        DirMdAttrPosList dirMdIndex = findMdIndexService.findDirMdIndex(mdIndex);
        if (dirMdIndex == null) {
            return getRespStr(false, "获取目录索引失败", null);
        }
        return getRespStr(true, "获取目录索引成功", dirMdIndex);
    }

    private String getRespStr(boolean success, String msg, Object objStr) {
        RespDto respDto = new RespDto();
        respDto.setSuccess(success);
        respDto.setMsg(msg);
        respDto.setObj(JSON.toJSONString(objStr));
        return JSON.toJSONString(respDto);
    }

}
