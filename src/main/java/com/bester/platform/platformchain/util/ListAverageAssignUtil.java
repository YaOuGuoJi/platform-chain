package com.bester.platform.platformchain.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yanrui
 */
public class ListAverageAssignUtil {

    public static <T> List<List<T>> dealBySubList(List<T> source, int batchCount) {
        List<List<T>> result = new ArrayList<List<T>>();
        int sourListSize = source.size();
        int subCount = sourListSize % batchCount == 0 ? sourListSize / batchCount : sourListSize / batchCount + 1;
        int startIndext = 0;
        int stopIndext = 0;
        for (int i = 0; i < subCount; i++) {
            List<T> value = null;
            stopIndext = (i == subCount - 1) ? stopIndext + sourListSize % batchCount : stopIndext + batchCount;
            value = source.subList(startIndext, stopIndext);
            startIndext = stopIndext;
            result.add(value);
        }
        return result;
    }
}
