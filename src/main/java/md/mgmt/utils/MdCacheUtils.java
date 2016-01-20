package md.mgmt.utils;

import md.mgmt.dao.entity.NewDirMdIndex;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Mr-yang on 16-1-19.
 */
public class MdCacheUtils {

    public static Map<String,NewDirMdIndex> dirMdIndexMap = new ConcurrentHashMap<String, NewDirMdIndex>();

}
