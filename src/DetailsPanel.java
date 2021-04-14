import javax.swing.*;
import java.awt.*;

public class DetailsPanel extends JPanel {
    JTextField startDateTextBox;
    CalendarPanel startDateCalendarPanel;
    JLabel startDateLabel;

    public DetailsPanel() {
        this.setLayout(null);
        this.createDetailsPanelComponents();
    }

    private void createDetailsPanelComponents()
    {
        // Create the components related to start date of the incentive.:q
        this.createStartDateComponents();
    }

    private void createStartDateComponents() {
        startDateTextBox = new JTextField();
        startDateTextBox.setBounds(184, 26, 89, 30);

        startDateCalendarPanel = new CalendarPanel(startDateTextBox, "yyyy/MM/dd");
        startDateCalendarPanel.initCalendarPanel();
        this.add(startDateCalendarPanel);
        this.add(startDateTextBox);

        startDateLabel = new JLabel("Start Date");
        startDateLabel.setFont(new Font("Lucida Grande", Font.BOLD, 15));
        startDateLabel.setBounds(68, 26, 89, 30);
        this.add(startDateLabel);
    }
}
