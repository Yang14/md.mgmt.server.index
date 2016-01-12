package md.mgmt.facade.resp.index;

import md.mgmt.base.md.ClusterNodeInfo;
import md.mgmt.base.md.ExactCode;

/**
 * Created by Mr-yang on 16-1-9.
 */
public class MdAttrPos {

    private ExactCode exactCode;
    private ClusterNodeInfo clusterNodeInfo;

    public ExactCode getExactCode() {
        return exactCode;
    }

    public void setExactCode(ExactCode exactCode) {
        this.exactCode = exactCode;
    }

    public ClusterNodeInfo getClusterNodeInfo() {
        return clusterNodeInfo;
    }

    public void setClusterNodeInfo(ClusterNodeInfo clusterNodeInfo) {
        this.clusterNodeInfo = clusterNodeInfo;
    }

    @Override
    public String toString() {
        return "MdAttrPos{" +
                "exactCode=" + exactCode +
                ", clusterNodeInfo=" + clusterNodeInfo +
                '}';
    }
}
