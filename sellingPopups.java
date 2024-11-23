import javax.swing.JOptionPane;

public class sellingPopups {
    public void publishListing(Book b) {
        int choice = JOptionPane.showConfirmDialog(null,
                "Are you sure that you want to publish the following listing?\nNOTE: If you publish improper listings, you may lose access to selling books on BookNook\nBy clicking yes, you agree that 20% of your profit will go to the systems administrators.",
                "Publish Listing", JOptionPane.YES_NO_CANCEL_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(null, "Your listing has been published");
        } else if (choice == JOptionPane.NO_OPTION) {
            // popup closes
        }
    }

    public void removeListing(Book b) {
        int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to remove the following listing",
                "Remove Listing", JOptionPane.YES_NO_CANCEL_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(null, "Your listing has been removed");
        } else if (choice == JOptionPane.NO_OPTION) {
            // popup closes
        }
    }
}
