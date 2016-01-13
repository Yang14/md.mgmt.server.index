package md.mgmt.facade.resp.find;

import md.mgmt.base.md.ClusterNodeInfo;

import java.util.List;

/**
 * Created by Mr-yang on 16-1-12.
 */
public class DirMdAttrPosList {

    private List<ClusterNodeInfo> clusterNodeInfos;

    public DirMdAttrPosList() {
    }

    public DirMdAttrPosList(List<ClusterNodeInfo> clusterNodeInfos) {
        this.clusterNodeInfos = clusterNodeInfos;
    }

    public List<ClusterNodeInfo> getClusterNodeInfos() {
        return clusterNodeInfos;
    }

    public void setClusterNodeInfos(List<ClusterNodeInfo> clusterNodeInfos) {
        this.clusterNodeInfos = clusterNodeInfos;
    }

    @Override
    public String toString() {
        return "DirMdAttrPosList{" +
                "clusterNodeInfos=" + clusterNodeInfos +
                '}';
    }
}
