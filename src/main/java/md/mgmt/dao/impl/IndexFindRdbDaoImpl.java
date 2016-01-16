package md.mgmt.dao.impl;

import com.alibaba.fastjson.JSON;
import md.mgmt.base.md.MdIndex;
import md.mgmt.dao.IndexFindRdbDao;
import md.mgmt.dao.entity.*;
import org.rocksdb.Options;
import org.rocksdb.RocksDB;
import org.rocksdb.RocksDBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by Mr-yang on 16-1-12.
 */
@Component
public class IndexFindRdbDaoImpl implements IndexFindRdbDao {
    private static Logger logger = LoggerFactory.getLogger(IndexRdbDaoImpl.class);

    private static final String DB_PATH = "/data/rdb/mdIndex";
    private static Options options = new Options().setCreateIfMissing(true);
    private static RocksDB db = null;
    private static final String RDB_DECODE = "UTF8";

    static {
        RocksDB.loadLibrary();
        try {
            db = RocksDB.open(options, DB_PATH);
        } catch (RocksDBException e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public DirMdIndex getParentDirMdIndexByPath(String path) {
        if (path == null || path.equals("") || path.charAt(0) != '/') {
            logger.error("findParentDirCodeByPath params err: " + path);
            return null;
        }
        if (path.equals("/")) {
            String key = JSON.toJSONString(new MdIndexKey("0", "/"));
            return getDirMdIndex(key);
        }
        String[] nodes = path.split("/");
        nodes[0] = "/";
        String code = "0";
        for (int i = 0; i < nodes.length - 1 && code != null; ++i) {
            String key = JSON.toJSONString(new MdIndexKey(code, nodes[i]));
            code = getFileMdIndex(key).getFileCode();
        }
        if (code == null) {   //路径不存在
            logger.error("路径不存在%s", path);
            return null;
        }
        return getDirMdIndex(JSON.toJSONString(new MdIndexKey(code, nodes[nodes.length - 1])));
    }

    @Override
    public FileMdIndex getFileMd(String path,String name) {
        if (path == null || path.equals("") || path.charAt(0) != '/') {
            logger.error("findParentDirCodeByPath params err: " + path);
            return null;
        }
        if (path.equals("/")) {
            String key = JSON.toJSONString(new MdIndexKey("0", name));
            return getFileMdIndex(key);
        }
        String[] nodes = path.split("/");
        nodes[0] = "/";
        String code = "0";
        for (int i = 0; i < nodes.length && code != null; ++i) {
            String key = JSON.toJSONString(new MdIndexKey(code, nodes[i]));
            code = getFileMdIndex(key).getFileCode();
        }
        if (code == null) {   //路径不存在
            logger.error("路径不存在%s", path);
            return null;
        }
        return getFileMdIndex(JSON.toJSONString(new MdIndexKey(code, name)));
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

    private DirMdIndex getDirMdIndex(String key) {
        try {
            byte[] indexBytes = db.get(key.getBytes(RDB_DECODE));
            if (indexBytes != null) {
                String indexValue = new String(indexBytes, RDB_DECODE);
                FileMdIndex fileMdIndex = JSON.parseObject(indexValue, FileMdIndex.class);
                String fileCode = fileMdIndex.getFileCode();
                byte[] distrCodeBytes = db.get(fileCode.getBytes(RDB_DECODE));
                if (distrCodeBytes != null) {
                    String distrCodeValue = new String(distrCodeBytes, RDB_DECODE);
                    DistrCodeList distrCodeList = JSON.parseObject(distrCodeValue, DistrCodeList.class);
                    return new DirMdIndex(fileMdIndex, distrCodeList);
                }
            }
        } catch (Exception e) {
            logger.error(String.format("[ERROR] caught the unexpected exception -- %s\n", e));
        }
        return null;
    }

    private FileMdIndex getFileMdIndex(String key) {
        try {
            byte[] indexBytes = db.get(key.getBytes(RDB_DECODE));
            if (indexBytes != null) {
                String indexValue = new String(indexBytes, RDB_DECODE);
                return JSON.parseObject(indexValue, FileMdIndex.class);
            }
        } catch (Exception e) {
            logger.error(String.format("[ERROR] caught the unexpected exception -- %s\n", e));
        }
        return null;
    }

}
