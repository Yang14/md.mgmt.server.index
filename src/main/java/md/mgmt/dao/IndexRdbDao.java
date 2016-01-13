package md.mgmt.dao;

import md.mgmt.dao.entity.DistrCodeList;
import md.mgmt.dao.entity.FileMdIndex;

/**
 * Created by Mr-yang on 16-1-11.
 */
public interface IndexRdbDao {

    /**
     * FileMdIndex转成JSONStr，再把str转成byte[]存储
     */
    public boolean putFileMdIndex(String key, FileMdIndex fileMdIndex);

    public boolean putDistrCodeList(String key, DistrCodeList distrCodeList);

}
