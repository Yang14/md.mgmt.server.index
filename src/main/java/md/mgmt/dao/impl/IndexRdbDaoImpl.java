package md.mgmt.dao.impl;

import com.alibaba.fastjson.JSON;
import md.mgmt.dao.IndexRdbDao;
import md.mgmt.dao.entity.DistrCodeList;
import md.mgmt.dao.entity.FileMdIndex;
import org.rocksdb.CompactionStyle;
import org.rocksdb.CompressionType;
import org.rocksdb.Options;
import org.rocksdb.RocksDB;
import org.rocksdb.util.SizeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by Mr-yang on 16-1-11.
 */
@Component
public class IndexRdbDaoImpl implements IndexRdbDao {
    private Logger logger = LoggerFactory.getLogger(IndexRdbDaoImpl.class);

    static {
        RocksDB.loadLibrary();
    }

    private static final String DB_PATH = "/data/rdb/mdIndex";
    private static final String RDB_DECODE = "UTF8";

    @Override
    public boolean putFileMdIndex(String key, FileMdIndex fileMdIndex) {
        return put(key, fileMdIndex);
    }

    @Override
    public boolean putDistrCodeList(String key, DistrCodeList distrCodeList) {
        return put(key, distrCodeList);
    }

    private boolean put(String key, Object obj) {
        Options options = new Options().setCreateIfMissing(true);
        RocksDB db = null;
        try {
            db = RocksDB.open(options, DB_PATH);
            db.put(key.getBytes(RDB_DECODE), JSON.toJSONString(obj).getBytes());
            return true;
        } catch (Exception e) {
            logger.error(String.format("[ERROR] caught the unexpceted exception -- %s\n", e));
        } finally {
            if (db != null) db.close();
            options.dispose();
        }
        return false;
    }
}
