package md.mgmt.service;

import md.mgmt.base.ops.RenamedMd;
import md.mgmt.facade.resp.find.FileMdAttrPosList;

/**
 * Created by Mr-yang on 16-1-12.
 */
public interface RenameMdIndexService {

    /**
     * 重命名文件索引
     * 目录和文件不作区分
     * 1.获取文件索引
     * 2.更新文件索引的键
     * 3.为新键插入老值
     * 4.获取文件的父目录的分布编码
     * 5.返回文件元数据属性分布位置
     */
    public FileMdAttrPosList renameMdIndex(RenamedMd renamedMd);
}
