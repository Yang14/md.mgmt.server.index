package md.mgmt.facade.resp.find;

import md.mgmt.base.md.ClusterNodeInfo;

import java.util.List;

/**
 * Created by Mr-yang on 16-1-12.
 */
public class FileMdAttrPosList {

    private List<ClusterNodeInfo> clusterNodeInfos;
    private String fileCode;

    public FileMdAttrPosList() {
    }

    public FileMdAttrPosList(List<ClusterNodeInfo> clusterNodeInfos, String fileCode) {
        this.clusterNodeInfos = clusterNodeInfos;
        this.fileCode = fileCode;
    }

    public List<ClusterNodeInfo> getClusterNodeInfos() {
        return clusterNodeInfos;
    }

    public void setClusterNodeInfos(List<ClusterNodeInfo> clusterNodeInfos) {
        this.clusterNodeInfos = clusterNodeInfos;
    }

    public String getFileCode() {
        return fileCode;
    }

    public void setFileCode(String fileCode) {
        this.fileCode = fileCode;
    }

    @Override
    public String toString() {
        return "FileMdAttrPosList{" +
                "clusterNodeInfos=" + clusterNodeInfos +
                ", fileCode='" + fileCode + '\'' +
                '}';
    }
}
