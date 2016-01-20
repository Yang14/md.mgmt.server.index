package md.mgmt.dao.impl;

import com.alibaba.fastjson.JSON;
import md.mgmt.dao.FindRdbDao;
import md.mgmt.dao.entity.DirMdIndex;
import md.mgmt.dao.entity.FileMdIndex;
import md.mgmt.utils.MdUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by Mr-yang on 16-1-12.
 */
@Component
public class FindRdbDaoImpl extends BaseRdb implements FindRdbDao {
    private static Logger logger = LoggerFactory.getLogger(CreateRdbDaoImpl.class);

    @Override
    public DirMdIndex getParentDirMdIndexByPath(String path) {
        if (path == null || path.equals("") || path.charAt(0) != '/') {
            logger.error("findParentDirCodeByPath params err: " + path);
            return null;
        }
        if (path.equals("/")) {
            return getNewDirMdIndex(MdUtils.genMdIndexKey("-1", "/"));
        }
        String[] nodes = path.split("/");
        nodes[0] = "/";
        String code = "-1";
        for (int i = 0; i < nodes.length - 1 && code != null; ++i) {
            code = getFileMdIndex(MdUtils.genMdIndexKey(code, nodes[i])).getFileCode();
        }
        if (code == null) {   //路径不存在
            logger.error(String.format("path %s not exist.", path));
            return null;
        }
        return getNewDirMdIndex(MdUtils.genMdIndexKey(code, nodes[nodes.length - 1]));
    }

    @Override
    public FileMdIndex getFileMd(String path,String name) {
        if (path == null || path.equals("") || path.charAt(0) != '/') {
            logger.error("findParentDirCodeByPath params err: " + path);
            return null;
        }
        if (path.equals("/")) {
            return getFileMdIndex(MdUtils.genMdIndexKey("-1", name));
        }
        String[] nodes = path.split("/");
        nodes[0] = "/";
        String code = "-1";
        for (int i = 0; i < nodes.length && code != null; ++i) {
            code = getFileMdIndex(MdUtils.genMdIndexKey(code, nodes[i])).getFileCode();
        }
        if (code == null) {   //路径不存在
            logger.error(String.format("path %s not exist.", path));
            return null;
        }
        return getFileMdIndex(MdUtils.genMdIndexKey(code, name));
    }

    @Override
    public DirMdIndex getNewDirMdIndex(String key) {
        try {
            byte[] indexBytes = db.get(key.getBytes(RDB_DECODE));
            if (indexBytes != null) {
                return JSON.parseObject(new String(indexBytes, RDB_DECODE), DirMdIndex.class);
            }
        } catch (Exception e) {
            logger.error(String.format("[ERROR] caught the unexpected exception -- %s\n", e));
        }
        return null;
    }

    public FileMdIndex getFileMdIndex(String key) {
        try {
            byte[] indexBytes = db.get(key.getBytes(RDB_DECODE));
            if (indexBytes != null) {
                return JSON.parseObject(new String(indexBytes, RDB_DECODE), FileMdIndex.class);
            }
        } catch (Exception e) {
            logger.error(String.format("[ERROR] caught the unexpected exception -- %s\n", e));
        }
        return null;
    }

}
