package md.mgmt.dao.entity;

import java.util.List;

/**
 * Created by Mr-yang on 16-1-12.
 */
public class RangeCode {
    private List<Integer> distrCodeList;
    private String fileCode;

    public RangeCode() {
    }

    public RangeCode(List<Integer> distrCodeList, String fileCode) {
        this.distrCodeList = distrCodeList;
        this.fileCode = fileCode;
    }

    public List<Integer> getDistrCodeList() {
        return distrCodeList;
    }

    public void setDistrCodeList(List<Integer> distrCodeList) {
        this.distrCodeList = distrCodeList;
    }

    public String getFileCode() {
        return fileCode;
    }

    public void setFileCode(String fileCode) {
        this.fileCode = fileCode;
    }

    @Override
    public String toString() {
        return "RangeCode{" +
                "distrCodeList=" + distrCodeList +
                ", fileCode='" + fileCode + '\'' +
                '}';
    }
}
