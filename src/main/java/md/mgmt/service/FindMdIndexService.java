package md.mgmt.service;

import md.mgmt.base.md.MdIndex;
import md.mgmt.facade.resp.find.DirMdAttrPosList;
import md.mgmt.facade.resp.find.FileMdAttrPosList;

/**
 * Created by Mr-yang on 16-1-12.
 */
public interface FindMdIndexService {

    /**
     * 1.获取父目录索引
     * 2.获取文件索引
     * 3.组合成文件存在的范围编码
     * 4.公共模块得到所有节点地址
     */
    public FileMdAttrPosList findFileMdIndex(MdIndex mdIndex);

    public DirMdAttrPosList findDirMdIndex(MdIndex mdIndex);
}
