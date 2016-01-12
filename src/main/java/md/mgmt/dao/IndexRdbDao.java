package md.mgmt.dao;

import md.mgmt.dao.entity.DirMdIndex;
import md.mgmt.dao.entity.DistrCodeList;
import md.mgmt.dao.entity.FileMdIndex;

/**
 * Created by Mr-yang on 16-1-11.
 */
public interface IndexRdbDao {

    /**
     * 根据传入的路径找到最底层目录的目录索引
     */
    public DirMdIndex getParentDirMdIndexByPath(String path);

    /**
     * FileMdIndex转成JSONStr，再把str转成byte[]存储
     */
    public boolean putFileMdIndex(String key, FileMdIndex fileMdIndex);

    public DirMdIndex getDirMdIndex(String key);

    public boolean putDistrCodeList(String key, DistrCodeList distrCodeList);

}
