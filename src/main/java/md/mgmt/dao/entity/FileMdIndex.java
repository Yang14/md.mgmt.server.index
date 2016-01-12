package md.mgmt.dao.entity;

/**
 * Created by Mr-yang on 16-1-11.
 */
public class FileMdIndex {
    private String fileCode;
    private boolean isDir;

    public FileMdIndex(String fileCode, boolean isDir) {
        this.fileCode = fileCode;
        this.isDir = isDir;
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

    @Override
    public String toString() {
        return "FileMdIndex{" +
                "fileCode='" + fileCode + '\'' +
                ", isDir=" + isDir +
                '}';
    }
}
