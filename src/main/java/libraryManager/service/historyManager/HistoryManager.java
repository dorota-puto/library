package libraryManager.service.historyManager;

import libraryManager.model.LentBookInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HistoryManager {

    private Map<Long, List<LentBookInfo>> lentReturnedBookInfoByAccountId = new HashMap<>();


    public List<LentBookInfo> displayHistory(Long accountId) {
        return lentReturnedBookInfoByAccountId.getOrDefault(accountId, new ArrayList<>());
    }

    public void add(Long accountId, LentBookInfo info) {
        List<LentBookInfo> list = lentReturnedBookInfoByAccountId.computeIfAbsent(accountId, k -> new ArrayList<>());
        list.add(info);
    }
}
