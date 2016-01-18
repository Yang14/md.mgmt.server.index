package md.mgmt.dao.impl;

import org.rocksdb.Options;
import org.rocksdb.RocksDB;
import org.rocksdb.RocksDBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by yang on 16-1-16.
 */
public class BaseRdb {
    private static Logger logger = LoggerFactory.getLogger(IndexRdbDaoImpl.class);

    public static final String DB_PATH = "/data/index";
    public static Options options = new Options().setCreateIfMissing(true);
    public static RocksDB db = null;
    public static final String RDB_DECODE = "UTF8";

    static {
        RocksDB.loadLibrary();
        try {
            db = RocksDB.open(options, DB_PATH);
        } catch (RocksDBException e) {
            logger.error(e.getMessage());
        }
    }
}
