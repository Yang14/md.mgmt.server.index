package md.mgmt.dao.entity;

/**
 * Created by Mr-yang on 16-1-11.
 */
public class MdIndexKey {

    private String parentDirCode;
    private String fileName;

    public MdIndexKey() {
    }

    public MdIndexKey(String parentDirCode, String fileName) {
        this.parentDirCode = parentDirCode;
        this.fileName = fileName;
    }

    public String getParentDirCode() {
        return parentDirCode;
    }

    public void setParentDirCode(String parentDirCode) {
        this.parentDirCode = parentDirCode;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String toString() {
        return "MdIndexKey{" +
                "parentDirCode='" + parentDirCode + '\'' +
                ", fileName='" + fileName + '\'' +
                '}';
    }
}
