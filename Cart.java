import java.util.ArrayList;

public Class Cart{

double price;
double tax;
ArrayList<Book> books;

public double getCosts(){
    double  total =0;
    for(i =0; i < books.size(); i++)[
        total += books[i].price

    ]
    return total;

}

public void addBook(Book newBook){
    books.add(newBook)


}

public void remove_Book(Book newBook){
    books.remove(newBook)
    

}

public void clearCart(){
    books.clear()
}





}