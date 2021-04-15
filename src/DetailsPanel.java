import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

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

    private JRadioButton rebateSectionRadioButton;
    private JCheckBox newGradRebateCheckBox;
    private JCheckBox militaryRebateCheckBox;
    private JTextField newGradRebateTextBox;
    private JTextField militaryRebateTextBox;

    private JRadioButton leaseSectionRadioButton;
    private JLabel leaseMonthlyPaymentInDollarsLabel;
    private JLabel leaseDurationInMonthsLabel;
    private JLabel leaseSigningAmountLabel;
    private JTextField leaseMonthlyPaymentInDollarsTextBox;
    private JTextField leaseDurationInMonthsTextBox;
    private JTextField leaseSigningAmountTextBox;
    private JButton detailsPageCancelButton;
    private JButton detailsPageNextButton;

    private ButtonGroup incentiveGroups;

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

        // Create the components related to the rebate incentive.
        this.createRebateIncentiveComponents();

        // Create the components related to the leasing incentive.
        this.createLeaseIncentiveComponents();

        // Create the components related to navigation of the pages.
        this.createNavigationComponentsFromDetailsPage();

        // Add Incentive groups to a radio button group
        this.createExclusiveIncentiveGroups();

        addButtonClickActionListenerToNextButton();
    }

    private void addButtonClickActionListenerToNextButton() {
        detailsPageNextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                validateIncentiveDetailsAndCreateIncentiveInstance();
            }
        });
    }

    private void validateIncentiveDetailsAndCreateIncentiveInstance() {
        /*Date startDate = validateAndParseDate(startDateTextBox.getText(), "Start Date");
        Date endDate = validateAndParseDate(endDateTextBox.getText(), "End Date");
        if (IncentiveDataValidator.isNull(startDate) || IncentiveDataValidator.isNull(endDate)) {
            return;
        }

        if (startDate.after(endDate)) {
            JOptionPane.showMessageDialog(null, "Start Date: "+ startDate + " cannot be after End Date: " + endDate, "Start Date greater than End Date", JOptionPane.ERROR_MESSAGE);
            return;
        }*/

        JOptionPane.showMessageDialog(null, incentiveGroups.getSelection().getActionCommand());
    }

    private Date validateAndParseDate(String date, String title) {
        if (IncentiveDataValidator.isNullOrEmpty(date)) {
            JOptionPane.showMessageDialog(null, title + " field cannot be empty", "Required Field Missing", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        Date startDate = null;
        try {
            startDate = IncentiveDataValidator.parseDateFromString(date);
        } catch (ParseException parseException) {
            parseException.printStackTrace();
        }

        if (startDate == null) {
            JOptionPane.showMessageDialog(null, title + " needs to be a valid date in the format yyyy/mm/dd", "Invalid StartDate", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        if (startDate.before(new Date())) {
            JOptionPane.showMessageDialog(null, title + " cannot be a past date and needs to be a valid date in the format yyyy/mm/dd", "Invalid StartDate", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        return startDate;
    }

    private void createExclusiveIncentiveGroups() {
        incentiveGroups = new ButtonGroup();
        incentiveGroups.add(cashDicountSectionRadioButton);
        incentiveGroups.add(loanSectionRadioButton);
        incentiveGroups.add(rebateSectionRadioButton);
        incentiveGroups.add(leaseSectionRadioButton);
    }

    private void createNavigationComponentsFromDetailsPage() {
        detailsPageCancelButton = new JButton("Cancel");
        detailsPageCancelButton.setBounds(250, 448, 117, 29);
        this.add(detailsPageCancelButton);

        detailsPageNextButton = new JButton("Next");
        detailsPageNextButton.setBounds(450, 448, 117, 29);
        this.add(detailsPageNextButton);
    }

    private void createLeaseIncentiveComponents() {
        leaseSectionRadioButton = new JRadioButton("Lease Incentive");
        leaseSectionRadioButton.setFont(new Font("Lucida Grande", Font.BOLD, 15));
        leaseSectionRadioButton.setBounds(432, 271, 204, 23);
        leaseSectionRadioButton.setActionCommand(String.valueOf(IncentiveType.LEASE));
        this.add(leaseSectionRadioButton);

        leaseMonthlyPaymentInDollarsLabel = new JLabel("Monthly Payment of ($)");
        leaseMonthlyPaymentInDollarsLabel.setBounds(456, 304, 153, 16);
        this.add(leaseMonthlyPaymentInDollarsLabel);

        leaseDurationInMonthsLabel = new JLabel("Number of months");
        leaseDurationInMonthsLabel.setBounds(456, 332, 153, 16);
        this.add(leaseDurationInMonthsLabel);

        leaseSigningAmountLabel = new JLabel("Signing Amount of ($)");
        leaseSigningAmountLabel.setBounds(456, 360, 153, 16);
        this.add(leaseSigningAmountLabel);

        leaseMonthlyPaymentInDollarsTextBox = new JTextField();
        leaseMonthlyPaymentInDollarsTextBox.setBounds(621, 299, 56, 26);
        this.add(leaseMonthlyPaymentInDollarsTextBox);
        leaseMonthlyPaymentInDollarsTextBox.setColumns(10);

        leaseDurationInMonthsTextBox = new JTextField();
        leaseDurationInMonthsTextBox.setBounds(621, 327, 56, 26);
        this.add(leaseDurationInMonthsTextBox);
        leaseDurationInMonthsTextBox.setColumns(10);

        leaseSigningAmountTextBox = new JTextField();
        leaseSigningAmountTextBox.setColumns(10);
        leaseSigningAmountTextBox.setBounds(621, 355, 56, 26);
        this.add(leaseSigningAmountTextBox);
    }

    private void createRebateIncentiveComponents() {
        rebateSectionRadioButton = new JRadioButton("Rebate Incentive");
        rebateSectionRadioButton.setFont(new Font("Lucida Grande", Font.BOLD, 15));
        rebateSectionRadioButton.setBounds(427, 121, 204, 23);
        rebateSectionRadioButton.setActionCommand(String.valueOf(IncentiveType.REBATE));
        this.add(rebateSectionRadioButton);

        newGradRebateCheckBox = new JCheckBox("Rebate for new Grads");
        newGradRebateCheckBox.setBounds(456, 156, 165, 23);
        this.add(newGradRebateCheckBox);

        militaryRebateCheckBox = new JCheckBox("Rebate for Millitary Veterans");
        militaryRebateCheckBox.setBounds(456, 191, 221, 23);
        this.add(militaryRebateCheckBox);

        newGradRebateTextBox = new JTextField();
        newGradRebateTextBox.setBounds(688, 155, 63, 26);
        this.add(newGradRebateTextBox);
        newGradRebateTextBox.setColumns(10);

        militaryRebateTextBox = new JTextField();
        militaryRebateTextBox.setColumns(10);
        militaryRebateTextBox.setBounds(688, 190, 63, 26);
        this.add(militaryRebateTextBox);
    }

    private void createLoanIncentiveComponents() {
        loanSectionRadioButton = new JRadioButton("Loan Incentive");
        loanSectionRadioButton.setFont(new Font("Lucida Grande", Font.BOLD, 15));
        loanSectionRadioButton.setBounds(68, 270, 204, 23);
        loanSectionRadioButton.setActionCommand(String.valueOf(IncentiveType.LOAN));
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
        cashDicountSectionRadioButton.setActionCommand(String.valueOf(IncentiveType.DISCOUNT));
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
