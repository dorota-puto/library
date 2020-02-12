package libraryManager.service.book;

import libraryManager.model.BookItemDTO;

public interface IManageBookItemCatalog {
    Boolean remove(String rfidTag);
    Boolean add(BookItemDTO bookItem);
}
