package md.mgmt.service;

import md.mgmt.base.md.MdIndex;
import md.mgmt.facade.resp.index.MdAttrPos;

/**
 * Created by Mr-yang on 16-1-11.
 */
public interface CreateMdIndexService {

    public MdAttrPos createFileMdIndex(MdIndex mdIndex);

    /**
     * 插入目录元数据索引过程
     * 1.根据key，插入DirMdIndex中的FileMdIndex
     * 2.以FileMdIndex中的fileCode为键，插入DistrCodeList
     */
    public MdAttrPos createDirMdIndex(MdIndex mdIndex);
}
