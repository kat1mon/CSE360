import java.util.ArrayList;

public class Order {
    private int orderNumber;
    private String orderDate;
    private String titles;
    private double totalCost;
    private double tax;
    
    public Order(int n, String d, double to, double ta, ArrayList<Book> b) {
        this.orderNumber = n;
        this.orderDate = d;
        this.totalCost = to;
        this.tax = ta;
        this.titles = "";
        for(Book book : b) {
        	titles += book.getTitle() + "\n";
        }
    }
    
    
    public Order(int n, String d, double to, double ta, ArrayList<String> t, boolean b) {
    	
    }
    
    public int getNumber() {
        return this.orderNumber;
    }
    
    public String getOrderDate() {
        return this.orderDate;
    }
    
    public double getTotalCost() {
        return this.totalCost;
    }
    
    public double getTax() {
        return this.tax;
    }
    
    public String getTitles() {
    	return this.titles;
    }
}