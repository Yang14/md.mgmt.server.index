package md.mgmt.service;

/**
 * Created by yang on 16-1-17.
 */
public class MdUtils {

    public static String genMdIndexKey(String parentCode, String fileName) {
        return parentCode + ":" + fileName;
    }
}
