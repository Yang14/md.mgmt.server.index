package md.mgmt.dao.entity;

import java.util.List;

/**
 * Created by Mr-yang on 16-1-11.
 */
public class DistrCodeList {
    private List<Long> codeList;

    public DistrCodeList() {
    }

    public DistrCodeList(List<Long> codeList) {
        this.codeList = codeList;
    }

    public List<Long> getCodeList() {
        return codeList;
    }

    public void setCodeList(List<Long> codeList) {
        this.codeList = codeList;
    }

    @Override
    public String toString() {
        return "DistrCodeList{" +
                "codeList=" + codeList +
                '}';
    }
}
