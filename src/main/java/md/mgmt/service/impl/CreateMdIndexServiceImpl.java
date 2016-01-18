package md.mgmt.service.impl;

import md.mgmt.base.md.ClusterNodeInfo;
import md.mgmt.base.md.ExactCode;
import md.mgmt.base.md.MdIndex;
import md.mgmt.common.CommonModule;
import md.mgmt.dao.IndexFindRdbDao;
import md.mgmt.dao.IndexRdbDao;
import md.mgmt.dao.entity.DirMdIndex;
import md.mgmt.dao.entity.DistrCodeList;
import md.mgmt.dao.entity.FileMdIndex;
import md.mgmt.facade.resp.index.MdAttrPos;
import md.mgmt.service.CreateMdIndexService;
import md.mgmt.service.MdUtils;
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
    private IndexRdbDao indexRdbDao;
    @Autowired
    private IndexFindRdbDao indexFindRdbDao;

    @Autowired
    private CommonModule commonModule;

    /**
     * 创建根目录节点
     * 指定根目录的编号，自身编码和分布编码都是0
     */
    @Override
    public boolean createRootDir() {
        String parentCode = "-1";
        String fileCode = "-1";
        long distrCode = 0;
        String name = "/";
        String key = MdUtils.genMdIndexKey(parentCode, name);
        if (!indexRdbDao.putFileMdIndex(key, new FileMdIndex(fileCode, true))) {
            return false;
        }
        List<Long> codes = new ArrayList<Long>();
        codes.add(distrCode);
        DistrCodeList distrCodeList = new DistrCodeList();
        distrCodeList.setCodeList(codes);
        return indexRdbDao.putDistrCodeList(fileCode, distrCodeList);
    }

    @Override
    public MdAttrPos createFileMdIndex(MdIndex mdIndex) {
        return createMdIndex(mdIndex, false);
    }

    @Override
    public MdAttrPos createDirMdIndex(MdIndex mdIndex) {
        return createMdIndex(mdIndex, true);
    }

    private MdAttrPos createMdIndex(MdIndex mdIndex, boolean isDir) {
        DirMdIndex parentDir = indexFindRdbDao.getParentDirMdIndexByPath(mdIndex.getPath());
        if (parentDir == null) {
            return null;
        }
        String fileCode = commonModule.genFileCode();
        putFileMdIndex(parentDir, mdIndex, fileCode, isDir);
        if (isDir) {
            //是目录,添加分布列表信息
            putDistrCodeList(fileCode);
        }
        MdAttrPos mdAttrPos = getMdAttrPos(parentDir, fileCode);
        return mdAttrPos;
    }

    private boolean putDistrCodeList(String fileCode) {
        List<Long> codes = new ArrayList<Long>();
        codes.add(commonModule.genDistrCode());
        DistrCodeList distrCodeList = new DistrCodeList();
        distrCodeList.setCodeList(codes);
        return indexRdbDao.putDistrCodeList(fileCode, distrCodeList);
    }

    /**
     * 插入元数据索引节点
     * 目录和文件都需要这一步
     */
    private boolean putFileMdIndex(DirMdIndex parentDir, MdIndex mdIndex, String fileCode, boolean isDir) {
        String parentFileCode = parentDir.getMdIndex().getFileCode();
        String key = MdUtils.genMdIndexKey(parentFileCode, mdIndex.getName());
        return indexRdbDao.putFileMdIndex(key, new FileMdIndex(fileCode, isDir));
    }

    private MdAttrPos getMdAttrPos(DirMdIndex parentDir, String fileCode) {
        List<Long> distrCodeList = parentDir.getDistrCodeList().getCodeList();
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

    private boolean updateDistrCodeListWithNewCode(DirMdIndex parentDir, long newCode) {
        String parentFileCode = parentDir.getMdIndex().getFileCode();

        List<Long> distrCodeList = parentDir.getDistrCodeList().getCodeList();
        distrCodeList.add(newCode);
        DistrCodeList distrCodeList1 = parentDir.getDistrCodeList();
        distrCodeList1.setCodeList(distrCodeList);
        return indexRdbDao.putDistrCodeList(parentFileCode, distrCodeList1);
    }

}
