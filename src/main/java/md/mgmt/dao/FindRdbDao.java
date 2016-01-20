package md.mgmt.dao;

import md.mgmt.dao.entity.DirMdIndex;
import md.mgmt.dao.entity.FileMdIndex;
import md.mgmt.dao.entity.NewDirMdIndex;

/**
 * Created by Mr-yang on 16-1-12.
 */
public interface FindRdbDao {

    /**
     * 根据传入的路径找到最底层目录的目录索引
     */
    public NewDirMdIndex getParentDirMdIndexByPath(String path);

    public FileMdIndex getFileMd(String path, String name);

    //-----------------------------------------
    public FileMdIndex getFileMdIndex(String key);

    public DirMdIndex getDirMdIndex(String key);

    /**
     * 使用新的目录对象
     */
    public NewDirMdIndex getNewDirMdIndex(String key);
}
