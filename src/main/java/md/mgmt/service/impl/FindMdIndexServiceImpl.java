package md.mgmt.service.impl;

import md.mgmt.base.md.ClusterNodeInfo;
import md.mgmt.base.md.MdIndex;
import md.mgmt.common.CommonModule;
import md.mgmt.dao.FindRdbDao;
import md.mgmt.dao.entity.FileMdIndex;
import md.mgmt.dao.entity.NewDirMdIndex;
import md.mgmt.facade.resp.find.DirMdAttrPosList;
import md.mgmt.facade.resp.find.FileMdAttrPosList;
import md.mgmt.service.FindMdIndexService;
import md.mgmt.utils.MdCacheUtils;
import md.mgmt.utils.MdUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Mr-yang on 16-1-12.
 */
@Component
public class FindMdIndexServiceImpl implements FindMdIndexService {
    private Logger logger = LoggerFactory.getLogger(FindMdIndexServiceImpl.class);

    @Autowired
    private FindRdbDao findRdbDao;
    @Autowired
    private CommonModule commonModule;

    @Override
    public FileMdAttrPosList findFileMdIndex(MdIndex mdIndex) {
        NewDirMdIndex parentDir = getParentDirMdIndex(mdIndex.getPath());
        if (parentDir == null) {
            return null;
        }
        List<Long> distrCodeList = parentDir.getDistrCodeList();
        List<ClusterNodeInfo> nodeInfoList = commonModule.getMdLocationList(distrCodeList);
        FileMdIndex fileMdIndex = findRdbDao.getFileMdIndex(
                MdUtils.genMdIndexKey(parentDir.getFileCode(), mdIndex.getName()));
        return new FileMdAttrPosList(nodeInfoList, fileMdIndex.getFileCode());
    }

    @Override
    public DirMdAttrPosList findDirMdIndex(MdIndex mdIndex) {
        NewDirMdIndex parentDir = getParentDirMdIndex(mdIndex.getPath());
        if (parentDir == null) {
            return null;
        }
        NewDirMdIndex dirMdIndex = findRdbDao.getNewDirMdIndex(
                MdUtils.genMdIndexKey(parentDir.getFileCode(), mdIndex.getName()));
        if (mdIndex.getPath().equals("/") && mdIndex.getName().equals("")) {
            dirMdIndex = parentDir;
        }
        List<Long> distrCodeList = dirMdIndex.getDistrCodeList();
        List<ClusterNodeInfo> nodeInfoList = commonModule.getMdLocationList(distrCodeList);
        return new DirMdAttrPosList(nodeInfoList);
    }

    private NewDirMdIndex getParentDirMdIndex(String path) {
        NewDirMdIndex parentDir = MdCacheUtils.dirMdIndexMap.get(path);
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
}
