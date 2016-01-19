package md.mgmt.service.impl;

import md.mgmt.base.md.ClusterNodeInfo;
import md.mgmt.base.md.MdIndex;
import md.mgmt.common.CommonModule;
import md.mgmt.dao.IndexFindRdbDao;
import md.mgmt.dao.entity.DirMdIndex;
import md.mgmt.dao.entity.DistrCodeList;
import md.mgmt.dao.entity.FileMdIndex;
import md.mgmt.facade.resp.find.DirMdAttrPosList;
import md.mgmt.facade.resp.find.FileMdAttrPosList;
import md.mgmt.service.FindMdIndexService;
import md.mgmt.utils.MdCacheUtils;
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
    private IndexFindRdbDao indexFindRdbDao;

    @Autowired
    private CommonModule commonModule;

    @Override
    public FileMdAttrPosList findFileMdIndex(MdIndex mdIndex) {
        DirMdIndex dirMdIndex = MdCacheUtils.dirMdIndexMap.get(mdIndex.getPath());
        if (dirMdIndex == null) {
            dirMdIndex = indexFindRdbDao.getParentDirMdIndexByPath(mdIndex.getPath());
            if (dirMdIndex == null) {
                logger.error(String.format("createMdIndex:can't find DirMdIndex %s", mdIndex.getPath()));
                return null;
            }
            MdCacheUtils.dirMdIndexMap.put(mdIndex.getPath(), dirMdIndex);
        }
        DistrCodeList distrCodeList = dirMdIndex.getDistrCodeList();
        List<ClusterNodeInfo> clusterNodeInfos = commonModule.getMdLocationList(distrCodeList.getCodeList());
        FileMdIndex fileMdIndex = indexFindRdbDao.getFileMd(mdIndex.getPath(), mdIndex.getName());
        return new FileMdAttrPosList(clusterNodeInfos,fileMdIndex.getFileCode());
    }

    @Override
    public DirMdAttrPosList findDirMdIndex(MdIndex mdIndex) {
        DirMdIndex dirMdIndex = MdCacheUtils.dirMdIndexMap.get(mdIndex.getPath());
        if (dirMdIndex == null) {
            dirMdIndex = indexFindRdbDao.getDirMd(mdIndex);
            if (dirMdIndex == null) {
                logger.error(String.format("createMdIndex:can't find DirMdIndex %s", mdIndex.getPath()));
                return null;
            }
            MdCacheUtils.dirMdIndexMap.put(mdIndex.getPath(), dirMdIndex);
        }
        DistrCodeList distrCodeList = dirMdIndex.getDistrCodeList();
        List<ClusterNodeInfo> clusterNodeInfos = commonModule.getMdLocationList(distrCodeList.getCodeList());
        return new DirMdAttrPosList(clusterNodeInfos);
    }
}
