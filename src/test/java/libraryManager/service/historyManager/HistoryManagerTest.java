package libraryManager.service.historyManager;

import libraryManager.entity.Account;
import libraryManager.model.LentBookInfo;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class HistoryManagerTest {

    @Test
    public void testDisplayHistory() {
        //given
        HistoryManager manager = new HistoryManager();
        LentBookInfo info = new LentBookInfo("bbb", 111L, LocalDate.now(), LocalDate.now().plusDays(30));
        Account account = new Account(111L, "Edmund Elefant", true);

        //when
        manager.add(account.getAccountID(), info);
        List<LentBookInfo> history = manager.displayHistory(111L);

        //then
        assertThat(history).isEqualTo(Arrays.asList(info));

    }

    @Test
    public void testDisplayEmptyHistory() {
        //given
        HistoryManager manager = new HistoryManager();

        //when
        List<LentBookInfo> history = manager.displayHistory(111L);

        //then
        assertThat(history).isEqualTo(new ArrayList<>());

    }
}