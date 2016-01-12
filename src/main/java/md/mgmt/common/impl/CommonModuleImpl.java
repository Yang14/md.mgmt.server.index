package md.mgmt.common.impl;

import md.mgmt.base.md.ClusterNodeInfo;
import md.mgmt.common.CommonModule;
import org.springframework.stereotype.Component;

/**
 * Created by Mr-yang on 16-1-11.
 */
@Component
public class CommonModuleImpl implements CommonModule {
    @Override
    public String genFileCode() {
        return null;
    }

    @Override
    public Integer genDistrCode() {
        return null;
    }

    @Override
    public boolean checkDistrCodeFit(Integer distrCode) {
        return false;
    }

    @Override
    public ClusterNodeInfo getMdLocation(Integer distrCode) {
        return null;
    }


    @Override
    public ClusterNodeInfo genMdLocation() {
        return null;
    }
}
