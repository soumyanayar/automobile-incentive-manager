import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class IncentiveManagerUI extends JFrame {
    private JPanel mainPanel;
    private JTabbedPane tabbedPane;

    public IncentiveManagerUI() {
        this.setTitle("Create Incentive");


        mainPanel = new JPanel(new GridLayout());
        this.setContentPane(this.mainPanel);
        this.setBounds(100, 100, 897, 578);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //this.pack();

        tabbedPane = new JTabbedPane();
        tabbedPane.setBackground(Color.black);
        JComponent detailsPanel = new DetailsPanel();
        tabbedPane.addTab("Details", detailsPanel);
        JComponent inventoryPanel = new InventoryPanel();
        tabbedPane.addTab("Inventory", inventoryPanel);
        JComponent descriptionPanel = new DescriptionPanel();
        tabbedPane.addTab("Description", descriptionPanel);

        mainPanel.add(tabbedPane);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        IncentiveManagerUI frame = new IncentiveManagerUI();
    }
}