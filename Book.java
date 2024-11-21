public Class Book{
private String title;
private String author;
private String publication_date;
private String category;
private String condition;
private double price;
private int quantity;
private String seller;

public Book(String title,  String author, String publication_date, String category, String condition, double price, int quantity, String seller){
this.title = title;
this.author = author;
this.publication_date = publication_date;
this.category = category;
this.condition = condition;
this.price = price;
this.quantity = quantity;
this.seller = seller;
}


public String getTitle(){
    return title;

}
public String getAuthor(){
    return author;
}
 
public String getDate(){
    return publication_date;

} 

public String getCategory(){
return category;

}

public String getCondition(){
    return condition;

}
public double getPrice(){
    return price
}

public int getQuantity(){
    return quantity;

}
public void changeQuantity(int newQuantity){
    quantity = newQuantity;
}
public String getSeller(){
    return seller;
}



}