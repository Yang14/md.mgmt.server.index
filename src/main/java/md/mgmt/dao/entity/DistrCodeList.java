package md.mgmt.dao.entity;

import java.util.List;

/**
 * Created by Mr-yang on 16-1-11.
 */
public class DistrCodeList {
    private List<Integer> codeList;

    public DistrCodeList() {
    }

    public DistrCodeList(List<Integer> codeList) {
        this.codeList = codeList;
    }

    public List<Integer> getCodeList() {
        return codeList;
    }

    public void setCodeList(List<Integer> codeList) {
        this.codeList = codeList;
    }

    @Override
    public String toString() {
        return "DistrCodeList{" +
                "codeList=" + codeList +
                '}';
    }
}
