package valera_bot.model;

public class Anekdote {

    private int id;
    private int cat;
    private String text;

    public Anekdote() {
    }

    public Anekdote(Anekdote anekdote) {
        this(anekdote.getId(), anekdote.getCat(), anekdote.getText());
    }

    public Anekdote(int id, int cat, String text) {
        this.id = id;
        this.cat = cat;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCat() {
        return cat;
    }

    public void setCat(int cat) {
        this.cat = cat;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "AnekdoteModel{" +
                "id=" + id +
                ", cat=" + cat +
                ", text='" + text + '\'' +
                '}';
    }
}
