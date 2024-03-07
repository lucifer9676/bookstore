import java.util.Date;

public class Book {
    private int id;
    private String title;
    private String author;
    private double price;
    private int quantity;
    private Date published_date;
    private String genre;

    
    public Book() {
    }

    public Book(int id,String title, String author, double price, int quantity, Date published_date, String genre) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.price = price;
        this.quantity = quantity;
        this.published_date = published_date;
        this.genre = genre;
    }

    // Getters and setters
    public int getId(){
        return id;

    }
    public void setId(int id){
        this.id = id;

    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Date getPublishedDate() {
        return published_date;
    }

    public void setPublishedDate(Date published_date) {
        this.published_date = published_date;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
