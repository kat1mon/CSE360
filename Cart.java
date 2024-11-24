import java.util.ArrayList;

public class Cart{

	double price;
	double tax;
	ArrayList<Book> books;
	
	public Cart() {
		price = 0;
		tax = 0.081;
		books = new ArrayList<Book>();
		for(Book b : books) {
			price += b.getPrice();
		}
	}
	
	public double getTotal() {
		double total = price + (tax * price);
		return total;
	}
	
	public double getTax() {
		return tax * price;
	}

	/*public double getCosts(){
		double total = 0;
		for(int i = 0; i < books.size(); i++) {
			total += books.get(i).getPrice();
		}
    return total;
	}*/
	
	public ArrayList<Book> getBooks(){
		return books;
	}

	public void addBook(Book newBook){
		books.add(newBook);
	}

	public void remove_Book(Book newBook){
		books.remove(newBook);
	}

	public void clearCart(){
		books.clear();
	}

}