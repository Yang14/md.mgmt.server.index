package md.mgmt.dao;

import md.mgmt.dao.entity.DirMdIndex;
import md.mgmt.dao.entity.FileMdIndex;

/**
 * Created by Mr-yang on 16-1-12.
 */
public interface FindRdbDao {

    /**
     * 根据传入的路径找到最底层目录的目录索引
     */
    public DirMdIndex getParentDirMdIndexByPath(String path);

    public FileMdIndex getFileMd(String path, String name);

    /**
     * 使用新的目录对象
     */
    public DirMdIndex getNewDirMdIndex(String key);

    //-----------------------------------------
    /**测试使用*/
    public FileMdIndex getFileMdIndex(String key);


}
