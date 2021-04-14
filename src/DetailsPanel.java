import javax.swing.*;
import java.awt.*;

public class DetailsPanel extends JPanel {
    JTextField startDateTextBox;
    CalendarPanel startDateCalendarPanel;
    JLabel startDateLabel;

    JTextField endDateTextBox;
    CalendarPanel endDateCalendarPanel;
    JLabel endDateLabel;

    public DetailsPanel() {
        this.setLayout(null);
        this.createDetailsPanelComponents();
    }

    private void createDetailsPanelComponents()
    {
        // Create the components related to start date of the incentive.
        this.createStartDateComponents();

        // Create the components related to end date of the incentive.
        this.createEndDateComponents();
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

    private void createEndDateComponents() {
        endDateTextBox = new JTextField();
        endDateTextBox.setBounds(551, 26, 89, 30);

        endDateCalendarPanel = new CalendarPanel(endDateTextBox, "yyyy/MM/dd");
        endDateCalendarPanel.initCalendarPanel();
        this.add(endDateCalendarPanel);
        this.add(endDateTextBox);

        endDateLabel = new JLabel("End Date");
        endDateLabel.setFont(new Font("Lucida Grande", Font.BOLD, 15));
        endDateLabel.setBounds(432, 26, 89, 30);
        this.add(endDateLabel);
    }
}
