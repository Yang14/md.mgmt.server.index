package md.mgmt.service.impl;

import com.alibaba.fastjson.JSON;
import md.mgmt.base.md.ClusterNodeInfo;
import md.mgmt.base.md.ExactCode;
import md.mgmt.base.md.MdIndex;
import md.mgmt.common.CommonModule;
import md.mgmt.dao.IndexRdbDao;
import md.mgmt.dao.entity.DirMdIndex;
import md.mgmt.dao.entity.DistrCodeList;
import md.mgmt.dao.entity.FileMdIndex;
import md.mgmt.dao.entity.MdIndexKey;
import md.mgmt.facade.resp.index.MdAttrPos;
import md.mgmt.service.CreateMdIndexService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * Created by Mr-yang on 16-1-11.
 */
@Component
public class CreateMdIndexServiceImpl implements CreateMdIndexService {
    private Logger logger = LoggerFactory.getLogger(CreateMdIndexServiceImpl.class);

    @Autowired
    private IndexRdbDao indexRdbDao;

    @Autowired
    private CommonModule commonModule;


    @Override
    public MdAttrPos createFileMdIndex(MdIndex mdIndex) {
        return createMdIndex(mdIndex, false);
    }

    @Override
    public MdAttrPos createDirMdIndex(MdIndex mdIndex) {
        return createMdIndex(mdIndex, true);
    }

    private MdAttrPos createMdIndex(MdIndex mdIndex, boolean isDir) {
        DirMdIndex parentDir = indexRdbDao.getParentDirMdIndexByPath(mdIndex.getPath());
        String fileCode = commonModule.genFileCode();
        putFileMdIndex(parentDir, mdIndex, fileCode, isDir);
        MdAttrPos mdAttrPos = getMdAttrPos(parentDir, fileCode);
        return mdAttrPos;
    }

    /**
     * 插入元数据索引节点
     * 目录和文件都需要这一步
     */
    private boolean putFileMdIndex(DirMdIndex parentDir, MdIndex mdIndex, String fileCode, boolean isDir) {
        String parentFileCode = parentDir.getMdIndex().getFileCode();
        String key = JSON.toJSONString(new MdIndexKey(parentFileCode, mdIndex.getName()));
        return indexRdbDao.putFileMdIndex(key, new FileMdIndex(fileCode, isDir));
    }

    private MdAttrPos getMdAttrPos(DirMdIndex parentDir, String fileCode) {
        ArrayList<Integer> distrCodeList = parentDir.getDistrCodeList().getCodeList();
        int distrCode = distrCodeList.get(distrCodeList.size() - 1);
        boolean isFit = commonModule.checkDistrCodeFit(distrCode);
        MdAttrPos mdAttrPos = new MdAttrPos();
        ClusterNodeInfo clusterNodeInfo;
        if (isFit) {
            clusterNodeInfo = commonModule.getMdLocation(distrCode);
        } else {
            clusterNodeInfo = commonModule.genMdLocation();
            //更新父目录的分布编码列表，传入父目录的编码
            updateDistrCodeListWithNewCode(parentDir, clusterNodeInfo.getDistrCode());
        }
        ExactCode exactCode = new ExactCode(distrCode, fileCode);
        mdAttrPos.setClusterNodeInfo(clusterNodeInfo);
        mdAttrPos.setExactCode(exactCode);
        return mdAttrPos;
    }

    private boolean updateDistrCodeListWithNewCode(DirMdIndex parentDir, Integer newCode) {
        String parentFileCode = parentDir.getMdIndex().getFileCode();
        ArrayList<Integer> distrCodeList = parentDir.getDistrCodeList().getCodeList();
        distrCodeList.add(newCode);
        DistrCodeList distrCodeList1 = parentDir.getDistrCodeList();
        distrCodeList1.setCodeList(distrCodeList);
        return indexRdbDao.putDistrCodeList(parentFileCode, distrCodeList1);
    }

}
