package appleInsider;

import org.junit.Assert;
import org.junit.Test;

public class AppleTest extends BaseTest{
    private final static String BASE_URL = "https://appleinsider.ru/";
    private final static String SEARCH_STRING = "Чем iPhone 13 отличается от iPhone 12";
    private final static String EXPECTED_WORD = "iphone-13";

    @Test
    public void checkHref(){
        Assert.assertTrue(new MainPage(BASE_URL)
                .search(SEARCH_STRING)
                .getHrefFirstArticle()
                .contains(EXPECTED_WORD));
    }
}
