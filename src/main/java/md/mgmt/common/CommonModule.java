package md.mgmt.common;

import md.mgmt.base.md.ClusterNodeInfo;

import java.util.List;

/**
 * Created by Mr-yang on 16-1-11.
 */
public interface CommonModule {
    /**
     * 生成文件编码
     */
    public String genFileCode();

    /**
     * 生成分布编码
     */
    public Long genDistrCode();

    /**
     * 检验分布编码对应的节点能否继续保持新的元数据
     */
    public boolean checkDistrCodeFit(Long distrCode);

    /**
     * 获取分布编码对应的元数据节点信息
     */
    public ClusterNodeInfo getMdLocation(Long distrCode);

    /**
     * 生成分布编码对应的元数据节点信息
     */
    public ClusterNodeInfo genMdLocation();

    public List<ClusterNodeInfo> getMdLocationList(List<Long> distrCodeList);
}
