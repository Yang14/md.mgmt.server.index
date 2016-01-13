package md.mgmt.performance;

import com.alibaba.fastjson.JSON;
import md.mgmt.dao.entity.FileMdIndex;
import md.mgmt.dao.entity.MdIndexKey;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.rocksdb.*;
import org.rocksdb.util.SizeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Mr-yang on 16-1-13.
 */
public class Rdb {
    private Logger logger = LoggerFactory.getLogger(Rdb.class);

    private static final String DB_PATH = "/data/rocksdb/test3";
    private Options options = new Options().setCreateIfMissing(true);
    private RocksDB db_global = null;

    static {
        RocksDB.loadLibrary();

    }

    @Before
    public void setUp() throws RocksDBException {
        db_global = RocksDB.open(options, DB_PATH);
    }

    @After
    public void setOff() {
        if (db_global != null) {
            db_global.close();
        }
        options.dispose();
    }

    @Test
    public void testRdbWithOptionConfig() {
        RocksDB db = null;
        Options options = new Options();
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
            db = RocksDB.open(options, DB_PATH);
            int count = 10000;
            System.out.println("\n\n\n" + String.valueOf(System.currentTimeMillis()));
            long start = System.currentTimeMillis();
            for (int i = 0; i < count; i++) {
                db.put(("{hello}" + i).getBytes(),
                        ("{\"isDir\":\"true\",\"fileCode\":\"a47486fa7be74ee08b9a7adf04afb7af\"}").getBytes());
            }
            long end = System.currentTimeMillis();
            System.out.println(String.valueOf(System.currentTimeMillis()));
            System.out.println(
                    String.format("\nCreate %s dir use Total time: %s ms\navg time: %sms\n\n\n",
                            count, (end - start), (end - start) / (count * 1.0)));
        } catch (RocksDBException e) {
            System.out.format("[ERROR] caught the unexpceted exception -- %s\n", e);
        } finally {
            db.close();
            options.dispose();
        }
    }

    @Test
    public void testPutWithoutConfig() {
        Options options = new Options().setCreateIfMissing(true);
        RocksDB db = null;
        try {
            db = RocksDB.open(options, DB_PATH);
            int count = 10000;
            System.out.println("\n\n\n" + String.valueOf(System.currentTimeMillis()));
            long start = System.currentTimeMillis();
            for (int i = 0; i < count; i++) {
                db.put(JSON.toJSONString(new MdIndexKey("a47486fa7be74ee08b9a7adf04afb7af", "a" + i)).getBytes(),
                        (JSON.toJSONString(new FileMdIndex("a47486fa7be74ee08b9a7adf04afb7af", false))).getBytes());
            }
            long end = System.currentTimeMillis();
            System.out.println(String.valueOf(System.currentTimeMillis()));
            System.out.println(
                    String.format("\nCreate %s dir use Total time: %s ms\navg time: %sms\n\n\n",
                            count, (end - start), (end - start) / (count * 1.0)));
        } catch (Exception e) {
            logger.error(String.format("[ERROR] caught the unexpceted exception -- %s\n", e));
        } finally {
            if (db != null) db.close();
            options.dispose();
        }
    }

    /**
     * Every time try to open and close rdb, has spend lose of time
     */
    @Test
    public void testCycle() {

        int count = 100000;
        System.out.println("\n\n\n" + String.valueOf(System.currentTimeMillis()));
        long start = System.currentTimeMillis();
        String key = "a47486fa7be74ee08b9a7adf04afb7af";
        for (int i = 0; i < count; i++) {
            FileMdIndex fileMdIndex = new FileMdIndex("a47486fa7be74ee08b9a7adf04afb7af", false);
            put(key, fileMdIndex);
        }
        long end = System.currentTimeMillis();
        System.out.println(String.valueOf(System.currentTimeMillis()));
        System.out.println(
                String.format("\nCreate %s dir use Total time: %s ms\navg time: %sms\n\n\n",
                        count, (end - start), (end - start) / (count * 1.0)));
    }

    private boolean put(String key, Object obj) {
//        Options options = new Options().setCreateIfMissing(true);
//        RocksDB db = null;
        try {
            db_global.put(key.getBytes(), JSON.toJSONString(obj).getBytes());
            return true;
        } catch (Exception e) {
            logger.error(String.format("[ERROR] caught the unexpceted exception -- %s\n", e));
        } finally {
//            if (db != null) db.close();
//            options.dispose();
        }
        return false;
    }
}
