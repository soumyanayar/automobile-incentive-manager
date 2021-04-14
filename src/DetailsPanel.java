import javax.swing.*;
import java.awt.*;

public class DetailsPanel extends JPanel {
    private JTextField startDateTextBox;
    private CalendarPanel startDateCalendarPanel;
    private JLabel startDateLabel;
    private JTextField endDateTextBox;
    private CalendarPanel endDateCalendarPanel;
    private JLabel endDateLabel;
    private JRadioButton cashDicountSectionRadioButton;
    private JRadioButton flatRateDiscountRadioButton;
    private JRadioButton percentageRateDiscountRadioButton;
    private ButtonGroup cashDiscountSelectionRadioButtonGroup;
    private JTextField flatRateDiscountTextField;
    private JTextField percentageRateDiscountTextField;
    private JRadioButton loanSectionRadioButton;
    private JLabel interestRateLabel;
    private JLabel loanDurationInMonthsLabel;
    private JTextField interestRateTextField;
    private JTextField loanDurationInMonthsTextField;

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

        // Create the components related to cash discount incentive.
        this.createCashDiscountIncentiveComponents();

        // Create the components related to loan incentive.
        this.createLoanIncentiveComponents();
    }

    private void createLoanIncentiveComponents() {
        loanSectionRadioButton = new JRadioButton("Loan Incentive");
        loanSectionRadioButton.setFont(new Font("Lucida Grande", Font.BOLD, 15));
        loanSectionRadioButton.setBounds(68, 270, 204, 23);
        this.add(loanSectionRadioButton);

        interestRateLabel = new JLabel("Interest Rate (%)");
        interestRateLabel.setBounds(107, 304, 102, 16);
        this.add(interestRateLabel);

        loanDurationInMonthsLabel = new JLabel("Number of months");
        loanDurationInMonthsLabel.setBounds(107, 332, 119, 16);
        this.add(loanDurationInMonthsLabel);

        interestRateTextField = new JTextField();
        interestRateTextField.setBounds(238, 299, 50, 26);
        this.add(interestRateTextField);
        interestRateTextField.setColumns(10);

        loanDurationInMonthsTextField = new JTextField();
        loanDurationInMonthsTextField.setColumns(10);
        loanDurationInMonthsTextField.setBounds(238, 327, 50, 26);
        this.add(loanDurationInMonthsTextField);
    }

    private void createCashDiscountIncentiveComponents() {
        cashDicountSectionRadioButton = new JRadioButton("Discount Incentive");
        cashDicountSectionRadioButton.setFont(new Font("Lucida Grande", Font.BOLD, 15));
        cashDicountSectionRadioButton.setBounds(68, 121, 204, 23);
        this.add(cashDicountSectionRadioButton);

        flatRateDiscountRadioButton = new JRadioButton("$");
        flatRateDiscountRadioButton.setBounds(107, 156, 50, 23);
        this.add(flatRateDiscountRadioButton);

        percentageRateDiscountRadioButton = new JRadioButton("%");
        percentageRateDiscountRadioButton.setBounds(107, 191, 42, 23);
        this.add(percentageRateDiscountRadioButton);

        cashDiscountSelectionRadioButtonGroup = new ButtonGroup();
        cashDiscountSelectionRadioButtonGroup.add(flatRateDiscountRadioButton);
        cashDiscountSelectionRadioButtonGroup.add(percentageRateDiscountRadioButton);

        flatRateDiscountTextField = new JTextField();
        flatRateDiscountTextField.setBounds(161, 156, 75, 26);
        this.add(flatRateDiscountTextField);
        flatRateDiscountTextField.setColumns(10);

        percentageRateDiscountTextField = new JTextField();
        percentageRateDiscountTextField.setColumns(10);
        percentageRateDiscountTextField.setBounds(161, 188, 75, 26);
        this.add(percentageRateDiscountTextField);
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
