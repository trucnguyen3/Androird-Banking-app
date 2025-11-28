package aka.digital.banking_v2;

public class NewsItem {
    private final String title;
    private final String body;

    public NewsItem(String title, String body) {
        this.title = title;
        this.body = body;
    }

    public String getTitle() { return title; }
    public String getBody() { return body; }
}