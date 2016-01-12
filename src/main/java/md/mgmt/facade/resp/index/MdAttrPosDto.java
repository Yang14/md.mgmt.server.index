package md.mgmt.facade.resp.index;

/**
 * Created by Mr-yang on 16-1-9.
 */
public class MdAttrPosDto {
    private Boolean success;
    private String msg;
    private MdAttrPos mdAttrPos;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public MdAttrPos getMdAttrPos() {
        return mdAttrPos;
    }

    public void setMdAttrPos(MdAttrPos mdAttrPos) {
        this.mdAttrPos = mdAttrPos;
    }


    @Override
    public String toString() {
        return "MdAttrPosDto{" +
                "success=" + success +
                ", msg='" + msg + '\'' +
                ", mdAttrPos=" + mdAttrPos +
                '}';
    }
}
