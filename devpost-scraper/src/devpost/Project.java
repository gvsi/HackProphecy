package devpost;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Project {
    private int likes = -1;
    private String hackathon = "";
    private boolean winner = false;
    private String name = "";
    private String blurp = "";
    private String description = "";
    private ArrayList<String> technologies = new ArrayList<>();
    private ArrayList<User> authors = new ArrayList<>();
    private String id ="";


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
        try {
            Document projectPage = Jsoup.connect(url).timeout(10000).get();
            name = projectPage.select("#app-title").first().text();
            try {
                id = url.split("software/")[1];
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                blurp = projectPage.select("#app-title ~ p.large").first().text();
            } catch (Exception ignored) {
            }
            likes = projectPage.select(".software-likes .side-count").isEmpty() ? 0 : Integer.parseInt(projectPage.select(".software-likes .side-count").first().text());
            try {
                description = projectPage.select("div#app-details-left").first().text();
            } catch (Exception ignored) {
            }
            technologies.addAll(projectPage.select(".cp-tag").stream().map(Element::text).collect(Collectors.toList()));
            try {
                hackathon = projectPage.select(".software-list-content > p").text();
            } catch (Exception ignored) {
            }
            winner = !projectPage.select(".winner").isEmpty();
            for (Element e : projectPage.select(".software-team-member :not(figure) > .user-profile-link")) {
                String link = e.attr("href");
                String[] tmp = link.split("/");
                String username = tmp[tmp.length - 1];
                if (UserSet.getUserSet().containsKey(username))
                    authors.add(UserSet.getUserSet().get(username));
                else {
                    authors.add(new User(link));
                }
            }
        } catch (SocketTimeoutException e) {
            Logger.getGlobal().log(Level.SEVERE, "Error fetching project " + url);
            Logger.getGlobal().log(Level.WARNING, e.getMessage());
            getProject(url); //Retry
        }
    }
}
