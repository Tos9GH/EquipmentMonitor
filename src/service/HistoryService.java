package service;

import model.CheckRecord;

import java.util.ArrayList;
import java.util.List;

public class HistoryService {
    private final List<CheckRecord> historyList;
    public HistoryService (List<CheckRecord> historyList) {
        this.historyList = historyList;
    }
    public List<CheckRecord> getHistory() {
        return historyList;
    }

    public void addRecord(CheckRecord record) {
        historyList.add(record);
    }

    public void setHistory (List<CheckRecord> loadedHistory){
        historyList.clear();
        historyList.addAll(loadedHistory);
    }
}
