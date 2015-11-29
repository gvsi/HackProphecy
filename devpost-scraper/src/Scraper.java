import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import devpost.Project;
import devpost.ProjectsPage;

import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Scraper {
    static FileWriter out;
    static Gson gson;
    static int maxPage = 9999;
    static {
        try {
            out = new FileWriter("projects_with_users.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
        GsonBuilder builder = new GsonBuilder();
        gson = builder.create();
    }

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(10);
        int startPage = (args.length > 0 ? Integer.parseInt(args[0]) : 0);
        //maxPage = startPage + 376;
        //Load first page
        ProjectsPage page = new ProjectsPage("http://devpost.com/software/popular" + (startPage>0 ? "?page=" +startPage : ""));
        do {
            //Process current page
            Logger.getGlobal().log(Level.INFO, "Parsing page " + page.getPageNumber());
            for (String p : page.getProjectURLs()) {
                try {
                    executor.execute(new ProjectRunner(p));
                    //Project project = new Project(p);
                    //System.out.println(project.getLikes());
                    //System.out.println(gson.toJson(project)); //debug purposes only #TODO remove
                    //out.write(gson.toJson(project) + ",\n");
                } catch (Exception e) {
                    Logger.getGlobal().log(Level.SEVERE, "Unable to fetch " + p);
                }
            }

            //Start loop for next page
            page = new ProjectsPage(page.getNextPage());
        } while (page.hasNext() && Integer.parseInt(page.getNextPage().split("\\?page=")[1])<maxPage);
    }

    public static synchronized void writeFile(String s) {
        try {
            out.write(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class ProjectRunner implements Runnable {
        String url = "";

        public ProjectRunner(String url) {
            this.url = url;
        }

        @Override
        public void run() {
            try {
                Project project = new Project(this.url);
                out.write(gson.toJson(project) + ",\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
