package md.mgmt.dao.entity;

import java.util.List;

/**
 * Created by Mr-yang on 16-1-11.
 */
public class BigDirMdIndex {

    private String fileCode;
    private boolean isDir;
    private List<Long> codeList;

    public BigDirMdIndex() {
    }

    public BigDirMdIndex(String fileCode, boolean isDir, List<Long> codeList) {
        this.fileCode = fileCode;
        this.isDir = isDir;
        this.codeList = codeList;
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

    public List<Long> getCodeList() {
        return codeList;
    }

    public void setCodeList(List<Long> codeList) {
        this.codeList = codeList;
    }

    @Override
    public String toString() {
        return "BigDirMdIndex{" +
                "fileCode='" + fileCode + '\'' +
                ", isDir=" + isDir +
                ", codeList=" + codeList +
                '}';
    }
}
