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

    static {
        try {
            out = new FileWriter("projects_with_users.json", true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        GsonBuilder builder = new GsonBuilder();
        gson = builder.create();
    }

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(10);
        //Load first page
        ProjectsPage page = new ProjectsPage("http://devpost.com/software/popular" + (args.length > 0 ? "?page=" + Integer.parseInt(args[0]) : ""));

        do {
            //Process current page

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
        } while (page.hasNext());
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
