package md.mgmt.utils;

import md.mgmt.dao.entity.DirMdIndex;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Mr-yang on 16-1-19.
 */
public class MdCacheUtils {

    public static Map<String,DirMdIndex> dirMdIndexMap = new ConcurrentHashMap<String, DirMdIndex>();

}
