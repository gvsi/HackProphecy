import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import devpost.Project;
import devpost.ProjectsPage;

import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Scraper {
    public static void main(String[] args) {
        try {
            FileWriter out = new FileWriter("projects.json");
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();

            //Load first page
            ProjectsPage page = new ProjectsPage("http://devpost.com/software/popular");

            do {
                //Process current page
                for (String p : page.getProjectURLs()) {
                    try {
                        Project project = new Project(p);
                        //System.out.println(project.getLikes());
                        System.out.println(gson.toJson(project)); //debug purposes only #TODO remove
                        out.write(gson.toJson(project) + ",\n");
                    } catch (Exception e) {
                        Logger.getGlobal().log(Level.SEVERE, "Unable to fetch " + p);
                    }
                }

                //Start loop for next page
                page = new ProjectsPage(page.getNextPage());
            } while (page.hasNext());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
