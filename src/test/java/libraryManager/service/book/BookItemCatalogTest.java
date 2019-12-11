package libraryManager.service.book;

import libraryManager.model.BookItem;
import libraryManager.model.Language;
import org.testng.annotations.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class BookItemCatalogTest {

    BookItem book1 = new BookItem(1L, "Krzyżacy", "Sienkiewicz", "Zysk i Ska", 350, Language.POLISH, "aaa");
    BookItem book2 = new BookItem(1L, "Krzyżacy", "Sienkiewicz", "Zysk i Ska", 350, Language.POLISH, "bbb");
    BookItem book3 = new BookItem(2L, "Pan Tadeusz", "Mickiewicz", "Zysk i Ska", 200, Language.POLISH, "ccc");

    @Test
    public void findBookItemByTitleTest() {

        //given
        BookItemCatalog bookItemCatalog = new BookItemCatalog();

        //when
        bookItemCatalog.add(book1);
        bookItemCatalog.add(book2);

        //then
        assertThat(bookItemCatalog.findByTitle("Krzyżacy")).isEqualTo(Arrays.asList(book1, book2));
    }

    @Test
    public void findBookItemByAuthorTest() {
        //given
        BookItemCatalog bookItemCatalog = new BookItemCatalog();

        //when
        bookItemCatalog.add(book1);
        bookItemCatalog.add(book2);

        //then
        assertThat(bookItemCatalog.findByAuthor("Sienkiewicz")).isEqualTo(Arrays.asList(book1, book2));
    }

    @Test
    public void findBookItemByIsbnTest() {
        //given
        BookItemCatalog bookItemCatalog = new BookItemCatalog();

        //when
        bookItemCatalog.add(book1);
        bookItemCatalog.add(book1);
        bookItemCatalog.add(book2);

        //then
        assertThat(bookItemCatalog.findByIsbn(1L)).isEqualTo(Arrays.asList(book1, book2));
    }

    @Test
    public void findBookItemByRfidTagTest() {
        //given
        BookItemCatalog bookItemCatalog = new BookItemCatalog();

        //when
        bookItemCatalog.add(book1);
        bookItemCatalog.add(book2);

        //then
        assertThat(bookItemCatalog.findByRfidTag("aaa")).isEqualTo(book1);
    }

    @Test
    public void findBookByIsbnTest() {

        //given
        BookItemCatalog bookItemCatalog = new BookItemCatalog();

        //when
        bookItemCatalog.add(book1);
        bookItemCatalog.add(book2);


        //then
        assertThat(bookItemCatalog.findBookByIsbn(1L)).isEqualTo(book1);
    }

    @Test
    public void findBookByIsbnWhenNoSuchIsbnTest() {

        //given
        BookItemCatalog bookItemCatalog = new BookItemCatalog();

        //when
        bookItemCatalog.add(book1);
        bookItemCatalog.add(book2);


        //then
        assertThat(bookItemCatalog.findBookByIsbn(5L)).isEqualTo(null);
    }
    @Test
    public void removeBookItemTest() {

        //given
        BookItemCatalog bookItemCatalog = new BookItemCatalog();

        //when
        bookItemCatalog.add(book1);
        bookItemCatalog.add(book2);
        bookItemCatalog.remove("aaa");

        //then
        assertThat(bookItemCatalog.findBookByIsbn(1L)).isEqualTo(book2);
    }

    @Test
    public void findBookByAuthorTest(){

        //given
        BookItemCatalog bookItemCatalog = new BookItemCatalog();

        //when
        bookItemCatalog.add(book1);
        bookItemCatalog.add(book2);
        bookItemCatalog.add(book3);

        //then
        assertThat(bookItemCatalog.findBookByAuthor("Sienkiewicz")).isEqualTo(Arrays.asList(book1));
    }

    @Test
    public void findBookByAuthorWhenNoSuchAuthorInLibraryTest(){

        //given
        BookItemCatalog bookItemCatalog = new BookItemCatalog();

        //when
        bookItemCatalog.add(book1);
        bookItemCatalog.add(book2);
        bookItemCatalog.add(book3);

        //then

        assertThat(bookItemCatalog.findBookByAuthor("Słowacki")).isEmpty();
    }
    @Test
    public void findBookByTitleTest(){

        //given
        BookItemCatalog bookItemCatalog = new BookItemCatalog();

        //when
        bookItemCatalog.add(book1);
        bookItemCatalog.add(book2);
        bookItemCatalog.add(book3);

        //then
        assertThat(bookItemCatalog.findBookByTitle("Krzyżacy")).isEqualTo(Arrays.asList(book1));
    }

}