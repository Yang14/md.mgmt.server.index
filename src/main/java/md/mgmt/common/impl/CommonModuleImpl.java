package md.mgmt.common.impl;

import md.mgmt.base.md.ClusterNodeInfo;
import md.mgmt.common.CommonModule;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Mr-yang on 16-1-11.
 */
@Component
public class CommonModuleImpl implements CommonModule {

    private static long fileCode = 100000;

    @Override
    public String genFileCode() {
        return "f-" + fileCode++;
    }

    @Override
    public Long genDistrCode() {
        return Long.valueOf(new Random().nextInt() & 0x0FFFFFFFF);
    }

    @Override
    public boolean checkDistrCodeFit(Long distrCode) {
        return true;
    }

    @Override
    public ClusterNodeInfo getMdLocation(Long distrCode) {
        if (distrCode == null) {
            return null;
        }
        ClusterNodeInfo clusterNodeInfo = new ClusterNodeInfo();
        if (distrCode % 3 == 0) {
            clusterNodeInfo.setIp("node-03");
        } else if (distrCode % 2 == 0) {
            clusterNodeInfo.setIp("node-02");
        } else {
            clusterNodeInfo.setIp("node-01");
        }
        clusterNodeInfo.setIp("192.168.0.13");
        clusterNodeInfo.setDistrCode(distrCode);
        clusterNodeInfo.setPort(8008);
        return clusterNodeInfo;
    }


    @Override
    public ClusterNodeInfo genMdLocation() {
        return getMdLocation(genDistrCode());
    }

    @Override
    public List<ClusterNodeInfo> getMdLocationList(List<Long> distrCodeList) {
        if (distrCodeList == null) {
            return null;
        }
        List<ClusterNodeInfo> nodeInfos = new ArrayList<ClusterNodeInfo>();
        for (long code : distrCodeList) {
            nodeInfos.add(getMdLocation(code));
        }
        return nodeInfos;
    }
}
