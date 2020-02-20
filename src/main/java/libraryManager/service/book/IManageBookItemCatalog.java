package libraryManager.service.book;

import libraryManager.entity.full.FullBookItem;

// TODO: remove?
public interface IManageBookItemCatalog {
    Boolean remove(String rfidTag);
    Boolean add(FullBookItem bookItem);
}
