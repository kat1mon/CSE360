import javax.swing.JOptionPane;

public class buyingPopups {
    public void removeFromCart(Book b) {
        int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to remove " + b + " from cart?",
                "Remove from Cart", JOptionPane.YES_NO_CANCEL_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(null, b + " removed from cart!");
        } else if (choice == JOptionPane.NO_OPTION) {
            JOptionPane.showMessageDialog(null, b + " not removed from cart!");
        } else {
            // Nothing happens
        }
    }

    public void clearCart(Book b) {
        int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to clear your cart",
                "Clear Cart?", JOptionPane.YES_NO_CANCEL_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(null, "Cart cleared!");
        } else {
            // Nothing happens
        }
    }

    public void buyCart(Book b) {
        int choice = JOptionPane.showConfirmDialog(null,
                "Are you sure you want to make this purchase?\nBy selecting \"YES\" you agree to the terms and will be billed for the total below\nSelecting \"NO\" will close this popup and bring you back to your listings and cart",
                "Clear Cart?", JOptionPane.YES_NO_CANCEL_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(null, "Confirming Purchase");
        } else {
            // Nothing happens
        }
    }
}