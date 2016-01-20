package md.mgmt.dao;

import md.mgmt.dao.entity.BigDirMdIndex;
import md.mgmt.dao.entity.DistrCodeList;
import md.mgmt.dao.entity.FileMdIndex;

/**
 * Created by Mr-yang on 16-1-11.
 */
public interface CreateRdbDao {

    /**
     * FileMdIndex转成JSONStr，再把str转成byte[]存储
     */
    public boolean putFileMdIndex(String key, FileMdIndex fileMdIndex);

    public boolean putDistrCodeList(String key, DistrCodeList distrCodeList);

    public void  removeFileMdIndex(String key);

    //--------------------------------------------
    public boolean put(String key, Object obj);

    public boolean putNewDirIndex(String key,BigDirMdIndex dirMdIndex);

}
