package md.mgmt.common.impl;

import md.mgmt.base.md.ClusterNodeInfo;
import md.mgmt.common.CommonModule;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * Created by Mr-yang on 16-1-11.
 */
@Component
public class CommonModuleImpl implements CommonModule {
    @Override
    public String genFileCode() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    @Override
    public Integer genDistrCode() {
        return new Random().nextInt();
    }

    @Override
    public boolean checkDistrCodeFit(Integer distrCode) {
        return true;
    }

    @Override
    public ClusterNodeInfo getMdLocation(Integer distrCode) {
        if (distrCode % 1 ==0)
        return null;
    }


    @Override
    public ClusterNodeInfo genMdLocation() {
        return null;
    }

    @Override
    public List<ClusterNodeInfo> getMdLocationList(List<Integer> distrCodeList) {
        return null;
    }
}
