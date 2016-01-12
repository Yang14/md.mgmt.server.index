package md.mgmt.dao.entity;

import java.util.ArrayList;

/**
 * Created by Mr-yang on 16-1-11.
 */
public class DistrCodeList {
    private ArrayList<Integer> codeList;

    public ArrayList<Integer> getCodeList() {
        return codeList;
    }

    public void setCodeList(ArrayList<Integer> codeList) {
        this.codeList = codeList;
    }

    @Override
    public String toString() {
        return "DistrCodeList{" +
                "codeList=" + codeList +
                '}';
    }
}
