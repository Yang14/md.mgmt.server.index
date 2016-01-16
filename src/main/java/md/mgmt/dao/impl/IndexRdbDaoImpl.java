package md.mgmt.dao.impl;

import com.alibaba.fastjson.JSON;
import md.mgmt.dao.IndexRdbDao;
import md.mgmt.dao.entity.DistrCodeList;
import md.mgmt.dao.entity.FileMdIndex;
import org.rocksdb.Options;
import org.rocksdb.RocksDB;
import org.rocksdb.RocksDBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by Mr-yang on 16-1-11.
 */
@Component
public class IndexRdbDaoImpl implements IndexRdbDao {
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
    public boolean putFileMdIndex(String key, FileMdIndex fileMdIndex) {
        return put(key, fileMdIndex);
    }

    @Override
    public boolean putDistrCodeList(String key, DistrCodeList distrCodeList) {
        return put(key, distrCodeList);
    }

    @Override
    public void removeFileMdIndex(String key) {
        try {
            db.remove(key.getBytes());
        } catch (Exception e) {
            logger.error(String.format("[ERROR] caught the unexpected exception -- %s\n", e));
        }
    }

    private boolean put(String key, Object obj) {
        try {
            db.put(key.getBytes(RDB_DECODE), JSON.toJSONString(obj).getBytes());
            return true;
        } catch (Exception e) {
            logger.error(String.format("[ERROR] caught the unexpected exception -- %s\n", e));
        }
        return false;
    }
}
