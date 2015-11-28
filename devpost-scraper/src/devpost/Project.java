package devpost;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Project {
    private int likes = -1;
    private String hackathon = "";
    private boolean winner = false;
    private String name = "";
    private String blurp = "";
    private String description = "";
    private ArrayList<String> technologies = new ArrayList<>();
    private ArrayList<String> authors = new ArrayList<>();


    public Project(String url) throws IOException {
        getProject(url);
    }


    public String getBlurp() {
        return blurp;
    }

    public String getName() {
        return name;
    }

    public int getLikes() {
        return likes;
    }

    public String getHackathon() {
        return hackathon;
    }


    public String getDescription() {
        return description;
    }

    public void getProject(String url) throws IOException {
        Document projectPage = Jsoup.connect(url).timeout(10000).get();
        name = projectPage.select("#app-title").first().text();
        blurp = projectPage.select("#app-title ~ p.large").first().text();
        likes = projectPage.select(".software-likes .side-count").isEmpty() ? 0 : Integer.parseInt(projectPage.select(".software-likes .side-count").first().text());
        description = projectPage.select("div#app-details-left").first().text();
        technologies.addAll(projectPage.select(".cp-tag.recognized-tag").stream().map(Element::text).collect(Collectors.toList()));
        try {
            hackathon = projectPage.select(".software-list-content > p").text();
        } catch (Exception e) {
            //NullPointerException is expected when not entered in hackathon;
        }
        winner = !projectPage.select(".winner").isEmpty();
        authors.addAll(projectPage.select(".software-team-member :not(figure) > .user-profile-link").stream().map(Element::text).collect(Collectors.toList()));
    }
}
