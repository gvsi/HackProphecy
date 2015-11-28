package devpost;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class ProjectsPage {
    private String nextPage = "";
    private ArrayList<String> projectURLs = new ArrayList<>();

    public ProjectsPage(String nextPage, ArrayList<String> projectURLs) {
        this.nextPage = nextPage;
        this.projectURLs = projectURLs;
    }

    public ProjectsPage(String url) {
        getPage(url);
    }

    public boolean hasNext() {
        return !nextPage.isEmpty();
    }

    public String getNextPage() {
        return nextPage;
    }

    public ArrayList<String> getProjectURLs() {
        return projectURLs;
    }


    public void getPage(String url) {
        try {
            Document directoryPage = Jsoup.connect(url).timeout(10000).get();
            projectURLs.addAll(directoryPage.select(".link-to-software").stream().map(projectLinks -> projectLinks.attr("href")).collect(Collectors.toList()));
            nextPage = "http://devpost.com/" + directoryPage.select(".next.next_page").first().child(0).attr("href");
        } catch (IOException e) {
            //e.printStackTrace();
            Logger.getGlobal().log(Level.SEVERE, "Unable to fetch " + url);
            Logger.getGlobal().log(Level.WARNING, e.getMessage());
            getPage(url); //Retry damn it!
        }
    }
}
