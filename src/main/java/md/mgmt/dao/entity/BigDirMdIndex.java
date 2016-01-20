package md.mgmt.dao.entity;

/**
 * Created by Mr-yang on 16-1-11.
 */
public class BigDirMdIndex {

    private FileMdIndex mdIndex;
    private DistrCodeList distrCodeList;

    public BigDirMdIndex() {
    }

    public BigDirMdIndex(FileMdIndex mdIndex, DistrCodeList distrCodeList) {
        this.mdIndex = mdIndex;
        this.distrCodeList = distrCodeList;
    }

    public FileMdIndex getMdIndex() {
        return mdIndex;
    }

    public void setMdIndex(FileMdIndex mdIndex) {
        this.mdIndex = mdIndex;
    }

    public DistrCodeList getDistrCodeList() {
        return distrCodeList;
    }

    public void setDistrCodeList(DistrCodeList distrCodeList) {
        this.distrCodeList = distrCodeList;
    }

    @Override
    public String toString() {
        return "DirMdIndex{" +
                "mdIndex=" + mdIndex +
                ", distrCodeList=" + distrCodeList +
                '}';
    }
}
