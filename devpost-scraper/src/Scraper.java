import devpost.ProjectsPage;

public class Scraper {
    public static void main(String[] args) {
        ProjectsPage page = new ProjectsPage("http://devpost.com/software/newest");
        do {
            //TODO: Process current page


            //Start loop for next page
            page = new ProjectsPage(page.getNextPage());
        } while (page.hasNext());

    }
}
