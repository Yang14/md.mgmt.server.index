package md.mgmt.service.impl;

import md.mgmt.base.md.ClusterNodeInfo;
import md.mgmt.base.ops.RenamedMd;
import md.mgmt.common.CommonModule;
import md.mgmt.dao.IndexFindRdbDao;
import md.mgmt.dao.IndexRdbDao;
import md.mgmt.dao.entity.DirMdIndex;
import md.mgmt.dao.entity.DistrCodeList;
import md.mgmt.dao.entity.FileMdIndex;
import md.mgmt.facade.resp.find.FileMdAttrPosList;
import md.mgmt.service.MdUtils;
import md.mgmt.service.RenameMdIndexService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Mr-yang on 16-1-16.
 */
@Component
public class RenameMdIndexServiceImpl implements RenameMdIndexService {
    private Logger logger = LoggerFactory.getLogger(RenameMdIndexServiceImpl.class);

    @Autowired
    private IndexFindRdbDao indexFindRdbDao;

    @Autowired
    private IndexRdbDao indexRdbDao;

    @Autowired
    private CommonModule commonModule;

    @Override
    public FileMdAttrPosList renameMdIndex(RenamedMd renamedMd) {
        FileMdAttrPosList fileMdAttrPosList = new FileMdAttrPosList();
        DirMdIndex dirMdIndex = indexFindRdbDao.getParentDirMdIndexByPath(renamedMd.getPath());
        if (dirMdIndex == null){
            return null;
        }
        DistrCodeList distrCodeList = dirMdIndex.getDistrCodeList();
        logger.info(distrCodeList.toString());
        List<ClusterNodeInfo> clusterNodeInfos = commonModule.getMdLocationList(distrCodeList.getCodeList());
        fileMdAttrPosList.setClusterNodeInfos(clusterNodeInfos);
        FileMdIndex fileMdIndex = indexFindRdbDao.getFileMd(renamedMd.getPath(),renamedMd.getName());
        fileMdAttrPosList.setFileCode(fileMdIndex.getFileCode());

        renameFileMdIndex(dirMdIndex.getMdIndex().getFileCode(), renamedMd, fileMdIndex);
        return fileMdAttrPosList;
    }

    private boolean renameFileMdIndex(String parentCode, RenamedMd renamedMd, FileMdIndex fileMdIndex) {
        removeFileMdIndex(parentCode,renamedMd.getName());
        String key = MdUtils.genMdIndexKey(parentCode, renamedMd.getNewName());
        return indexRdbDao.putFileMdIndex(key, fileMdIndex);
    }

    private void removeFileMdIndex(String parentCode, String name) {
        String key = MdUtils.genMdIndexKey(parentCode, name);
        indexRdbDao.removeFileMdIndex(key);
    }
}
