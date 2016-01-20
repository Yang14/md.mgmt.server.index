package md.mgmt.service.impl;

import md.mgmt.base.md.ClusterNodeInfo;
import md.mgmt.base.md.ExactCode;
import md.mgmt.base.md.MdIndex;
import md.mgmt.common.CommonModule;
import md.mgmt.dao.CreateRdbDao;
import md.mgmt.dao.FindRdbDao;
import md.mgmt.dao.entity.DirMdIndex;
import md.mgmt.dao.entity.FileMdIndex;
import md.mgmt.facade.resp.index.MdAttrPos;
import md.mgmt.service.CreateMdIndexService;
import md.mgmt.utils.MdCacheUtils;
import md.mgmt.utils.MdUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mr-yang on 16-1-11.
 */
@Component
public class CreateMdIndexServiceImpl implements CreateMdIndexService {
    private Logger logger = LoggerFactory.getLogger(CreateMdIndexServiceImpl.class);

    @Autowired
    private CreateRdbDao createRdbDao;
    @Autowired
    private FindRdbDao findRdbDao;
    @Autowired
    private CommonModule commonModule;

    /**
     * 创建根目录节点
     * 指定根目录的编号，自身编码-1，分布编码都是0
     */
    @Override
    public boolean createRootDir() {
        String parentCode = "-1";
        String fileCode = "-1";
        long distrCode = 0;
        String name = "/";
        String key = MdUtils.genMdIndexKey(parentCode, name);
        List<Long> codes = new ArrayList<Long>();
        codes.add(distrCode);
        DirMdIndex dirMdIndex = new DirMdIndex(fileCode, true, codes);
        return createRdbDao.putNewDirIndex(key, dirMdIndex);
    }

    @Override
    public MdAttrPos createFileMdIndex(MdIndex mdIndex) {
        DirMdIndex parentDir = getParentDirMdIndex(mdIndex.getPath());
        if (parentDir == null) {
            return null;
        }
        String fileCode = commonModule.genFileCode();
        String parentFileCode = parentDir.getFileCode();
        String key = MdUtils.genMdIndexKey(parentFileCode, mdIndex.getName());
        createRdbDao.putFileMdIndex(key, new FileMdIndex(fileCode, false));
        return getMdAttrPos(parentDir, fileCode);
    }

    @Override
    public MdAttrPos createDirMdIndex(MdIndex mdIndex) {
        DirMdIndex parentDir = getParentDirMdIndex(mdIndex.getPath());
        if (parentDir == null) {
            return null;
        }
        List<Long> distrCodes = new ArrayList<Long>();
        distrCodes.add(commonModule.genDistrCode());
        String fileCode = commonModule.genFileCode();
        String key = MdUtils.genMdIndexKey(parentDir.getFileCode(), mdIndex.getName());
        createRdbDao.putNewDirIndex(key, new DirMdIndex(fileCode, true, distrCodes));
        return getMdAttrPos(parentDir, fileCode);
    }

    private DirMdIndex getParentDirMdIndex(String path) {
        DirMdIndex parentDir = MdCacheUtils.dirMdIndexMap.get(path);
        if (parentDir == null) {
            parentDir = findRdbDao.getParentDirMdIndexByPath(path);
            if (parentDir == null) {
                logger.error(String.format("createMdIndex:can't find DirMdIndex %s", parentDir));
                return null;
            }
            MdCacheUtils.dirMdIndexMap.put(path, parentDir);
            logger.info(String.format("%s cache failed...", path));
        }
        return parentDir;
    }

    private MdAttrPos getMdAttrPos(DirMdIndex parentDir, String fileCode) {
        List<Long> distrCodeList = parentDir.getDistrCodeList();
        long distrCode = distrCodeList.get(distrCodeList.size() - 1);
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

    //TODO
    //先要得到保存父目录的键，再更新节点信息
    private boolean updateDistrCodeListWithNewCode(DirMdIndex parentDir, long newCode) {
        List<Long> distrCodeList = parentDir.getDistrCodeList();
        distrCodeList.add(newCode);
        parentDir.setDistrCodeList(distrCodeList);
        return createRdbDao.putNewDirIndex("", parentDir);
    }

}
