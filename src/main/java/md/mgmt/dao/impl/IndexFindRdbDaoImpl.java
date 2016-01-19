package md.mgmt.dao.impl;

import com.alibaba.fastjson.JSON;
import md.mgmt.base.md.MdIndex;
import md.mgmt.dao.IndexFindRdbDao;
import md.mgmt.dao.entity.*;
import md.mgmt.utils.MdUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by Mr-yang on 16-1-12.
 */
@Component
public class IndexFindRdbDaoImpl extends BaseRdb implements IndexFindRdbDao {
    private static Logger logger = LoggerFactory.getLogger(IndexRdbDaoImpl.class);


    @Override
    public DirMdIndex getParentDirMdIndexByPath(String path) {
        if (path == null || path.equals("") || path.charAt(0) != '/') {
            logger.error("findParentDirCodeByPath params err: " + path);
            return null;
        }
        if (path.equals("/")) {
            return getDirMdIndex(MdUtils.genMdIndexKey("-1", "/"));
        }
        String[] nodes = path.split("/");
        nodes[0] = "/";
        String code = "-1";
        for (int i = 0; i < nodes.length - 1 && code != null; ++i) {
            code = getFileMdIndex(MdUtils.genMdIndexKey(code, nodes[i])).getFileCode();
        }
        if (code == null) {   //路径不存在
            logger.error("路径不存在%s", path);
            return null;
        }
        return getDirMdIndex(MdUtils.genMdIndexKey(code, nodes[nodes.length - 1]));
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
            logger.error("路径不存在%s", path);
            return null;
        }
        return getFileMdIndex(MdUtils.genMdIndexKey(code, name));
    }

    @Override
    public DirMdIndex getDirMd(MdIndex mdIndex) {
        String newPath;
        if (mdIndex.getPath().equals("/")) {
            newPath = mdIndex.getPath() + mdIndex.getName();
        } else {
            newPath = mdIndex.getPath() + "/" + mdIndex.getName();
        }
        return getParentDirMdIndexByPath(newPath);
    }

    public DirMdIndex getDirMdIndex(String key) {
        try {
            byte[] indexBytes = db.get(key.getBytes(RDB_DECODE));
            if (indexBytes != null) {
                FileMdIndex fileMdIndex = JSON.parseObject(new String(indexBytes, RDB_DECODE), FileMdIndex.class);
                String fileCode = fileMdIndex.getFileCode();
                byte[] distrCodeBytes = db.get(fileCode.getBytes(RDB_DECODE));
                if (distrCodeBytes != null) {
                    DistrCodeList distrCodeList = JSON.parseObject(
                            new String(distrCodeBytes, RDB_DECODE), DistrCodeList.class);
                    return new DirMdIndex(fileMdIndex, distrCodeList);
                }
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
