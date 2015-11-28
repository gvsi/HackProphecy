package devpost;

public class Project {
    int likes = 0;
    Hackathon hackathon = null;
    String description = "";

    public Project(int likes, Hackathon hackathon, String description) {
        this.likes = likes;
        this.hackathon = hackathon;
        this.description = description;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public Hackathon getHackathon() {
        return hackathon;
    }

    public void setHackathon(Hackathon hackathon) {
        this.hackathon = hackathon;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
