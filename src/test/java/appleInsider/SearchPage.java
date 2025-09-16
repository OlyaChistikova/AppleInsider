package appleInsider;

import com.codeborne.selenide.ElementsCollection;

import static com.codeborne.selenide.Selenide.$$x;

public class SearchPage {
    private final ElementsCollection articleTitles = $$x("//h2//a[1]");

    /**
     * Возвращает href из первой статьи
     */
    public String getHrefFirstArticle(){
        return articleTitles.first().getAttribute("href");
    }
}