package md.mgmt.dao;

import md.mgmt.base.md.MdIndex;
import md.mgmt.dao.entity.DirMdIndex;
import md.mgmt.dao.entity.FileMdIndex;

/**
 * Created by Mr-yang on 16-1-12.
 */
public interface IndexFindRdbDao {

    /**
     * 根据传入的路径找到最底层目录的目录索引
     */
    public DirMdIndex getParentDirMdIndexByPath(String path);

    public FileMdIndex getFileMd(String path,String name);

    public DirMdIndex getDirMd(MdIndex mdIndex);
//-----------------------------------------
    public FileMdIndex getFileMdIndex(String key);

    public DirMdIndex getDirMdIndex(String key);
}
