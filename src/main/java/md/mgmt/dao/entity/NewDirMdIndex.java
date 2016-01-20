package md.mgmt.dao.entity;

import java.util.List;

/**
 * Created by Mr-yang on 16-1-11.
 */
public class NewDirMdIndex {

    private String fileCode;
    private boolean isDir;
    private List<Long> distrCodeList;

    public NewDirMdIndex() {
    }

    public NewDirMdIndex(String fileCode, boolean isDir, List<Long> distrCodeList) {
        this.fileCode = fileCode;
        this.isDir = isDir;
        this.distrCodeList = distrCodeList;
    }

    public String getFileCode() {
        return fileCode;
    }

    public void setFileCode(String fileCode) {
        this.fileCode = fileCode;
    }

    public boolean isDir() {
        return isDir;
    }

    public void setDir(boolean isDir) {
        this.isDir = isDir;
    }

    public List<Long> getDistrCodeList() {
        return distrCodeList;
    }

    public void setDistrCodeList(List<Long> distrCodeList) {
        this.distrCodeList = distrCodeList;
    }

    @Override
    public String toString() {
        return "NewDirMdIndex{" +
                "fileCode='" + fileCode + '\'' +
                ", isDir=" + isDir +
                ", distrCodeList=" + distrCodeList +
                '}';
    }
}
