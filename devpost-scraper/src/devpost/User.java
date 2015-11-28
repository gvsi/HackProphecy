package devpost;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

//Unused class, for future expansions
public class User {
    private String username = "";
    private int wins = 0;
    private int projects = 0;
    private int hackathons = 0;
    private String name = "";
    private String description = "";
    private ArrayList<String> technologies = new ArrayList<>();

    public User(String url) {
        try {
            getUser(url);
            UserSet.getUserSet().put(username, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getUsername() {

        return username;
    }

    public int getWins() {
        return wins;
    }

    public int getProjects() {
        return projects;
    }

    public int getHackathons() {
        return hackathons;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<String> getTechnologies() {
        return technologies;
    }

    public void getUser(String url) throws IOException {
        String[] tmp = url.split("/");
        username = tmp[tmp.length - 1];
        Document user = Jsoup.connect(url).timeout(10000).get();
        wins = user.select(".winner").size();
        try {
            projects = Integer.parseInt(user.select("li:contains(Project) .totals").text());
        } catch (Exception ignored) {
        }
        try {
            hackathons = Integer.parseInt(user.select("li:contains(Hackathon) .totals").text());
        } catch (Exception ignored) {
        }
        name = user.select("#portfolio-user-name").first().ownText();
        description = user.select("#portfolio-user-bio").first().text();
        technologies.addAll(user.select(".cp-tag").stream().map(Element::text).collect(Collectors.toList()));
    }
}
