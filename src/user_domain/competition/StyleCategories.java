package user_domain.competition;

public enum StyleCategories {
    BACKSTROKE("Rygcrawl"),
    BREASTSTROKE("BrystsvÝmning"),
    BUTTERFLY("Butterfly"),
    CRAWL("Crawl");
    final String styleName;

    StyleCategories(String styleName) {
        this.styleName = styleName;
    }
}
