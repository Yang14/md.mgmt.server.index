package md.mgmt.performance;

import org.rocksdb.*;
import org.rocksdb.util.SizeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Mr-yang on 16-1-13.
 */
public class Rdb {
    private Logger logger = LoggerFactory.getLogger(Rdb.class);

    private static final String DB_PATH = "/data/rdb/mdIndex";

    static {
        RocksDB.loadLibrary();
    }

    static {
        RocksDB.loadLibrary();
    }

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("usage: RocksDBSample db_path");
            return;
        }
        String db_path = args[0];
        String db_path_not_found = db_path + "_not_found";

        RocksDB db = null;
        Options options = new Options();
        try {
            db = RocksDB.open(options, db_path_not_found);
            assert (false);
        } catch (RocksDBException e) {
            System.out.format("caught the expceted exception -- %s\n", e);
            assert (db == null);
        }

        try {
            options.setCreateIfMissing(true)
                    .createStatistics()
                    .setWriteBufferSize(8 * SizeUnit.KB)
                    .setMaxWriteBufferNumber(3)
                    .setMaxBackgroundCompactions(10)
                    .setCompressionType(CompressionType.SNAPPY_COMPRESSION)
                    .setCompactionStyle(CompactionStyle.UNIVERSAL);
        } catch (IllegalArgumentException e) {
            assert (false);
        }
        options.setMemTableConfig(
                new HashSkipListMemTableConfig()
                        .setHeight(4)
                        .setBranchingFactor(4)
                        .setBucketCount(2000000));

        options.setTableFormatConfig(new PlainTableConfig());
        options.setAllowMmapReads(true);
        options.setRateLimiterConfig(new GenericRateLimiterConfig(10000000,
                10000, 10));

        try {
            db = RocksDB.open(options, db_path);
            db.put("hello".getBytes(), "world".getBytes());
            byte[] value = db.get("hello".getBytes());
            assert ("world".equals(new String(value)));
            String str = db.getProperty("rocksdb.stats");
            assert (str != null && !str.equals(""));
        } catch (RocksDBException e) {
            System.out.format("[ERROR] caught the unexpceted exception -- %s\n", e);
            assert (db == null);
            assert (false);
        }
        // be sure to release the c++ pointer
        db.close();

        ReadOptions readOptions = new ReadOptions();
        readOptions.setFillCache(false);

        options.dispose();
        readOptions.dispose();
    }
}
