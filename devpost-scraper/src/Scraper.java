import devpost.Project;
import devpost.ProjectsPage;

public class Scraper {
    public static void main(String[] args) {
        ProjectsPage page = new ProjectsPage("http://devpost.com/software/newest");
        do {
            //TODO: Process current page
            //Start loop for next page
            System.out.println(page.getNextPage());
            page = new ProjectsPage(page.getNextPage());
            for (String p : page.getProjectURLs()) {
                Project project = new Project(p);
                System.out.println(project.getLikes());
            }

        } while (page.hasNext());

    }
}
