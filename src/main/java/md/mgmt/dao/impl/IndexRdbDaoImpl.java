package md.mgmt.dao.impl;

import com.alibaba.fastjson.JSON;
import md.mgmt.dao.IndexRdbDao;
import md.mgmt.dao.entity.BigDirMdIndex;
import md.mgmt.dao.entity.DistrCodeList;
import md.mgmt.dao.entity.FileMdIndex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by Mr-yang on 16-1-11.
 */
@Component
public class IndexRdbDaoImpl extends BaseRdb implements IndexRdbDao {
    private static Logger logger = LoggerFactory.getLogger(IndexRdbDaoImpl.class);

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

    public boolean put(String key, Object obj) {
        try {
            db.put(key.getBytes(RDB_DECODE), JSON.toJSONString(obj).getBytes());
            return true;
        } catch (Exception e) {
            logger.error(String.format("[ERROR] caught the unexpected exception -- %s\n", e));
        }
        return false;
    }

    @Override
    public boolean putNewDirIndex(String key, BigDirMdIndex dirMdIndex) {
        return put(key,dirMdIndex);
    }
}
