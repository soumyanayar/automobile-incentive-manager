package ui;

import entities.*;
import validators.IncentiveDataValidator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.stream.Collectors;

public class IncentiveManagerUI extends JFrame {
    private JPanel mainPanel;
    private JTabbedPane tabbedPane;
    private JPanel detailsPanel;
    private JPanel inventoryPanel;
    private JPanel descriptionPanel;

    private IncentiveType incentiveTypeSelected;

    // Parameters related to Discount Type Incentive
    private CashDiscountType cashDiscountType;
    private double discountPercentage;
    private double discountFlatAmount;

    // Parameters related to Loan Type Incentive
    private double loanInterestRate;
    private int loanDurationInMonths;

    // Parameters related to Rebate Type Incentive
    private HashMap<String, Double> rebateMap;

    // Parameters related to Lease Type Incentive
    private int leaseDurationInMonths;
    private double leaseSigningAmount;
    private double leaseMonthlyPayment;

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

    public IncentiveManagerUI() {
        this.setTitle("Create Incentive");

        mainPanel = new JPanel(new GridLayout());
        this.setContentPane(this.mainPanel);
        this.setBounds(100, 100, 897, 578);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // this.pack();
        tabbedPane = new JTabbedPane();

        detailsPanel = new JPanel();
        detailsPanel.setLayout(null);
        createDetailsPanelComponents();
        tabbedPane.addTab("Details", detailsPanel);

        inventoryPanel = new JPanel();
        tabbedPane.addTab("Inventory", inventoryPanel);

        descriptionPanel = new JPanel();
        tabbedPane.addTab("Description", descriptionPanel);

        tabbedPane.setSelectedComponent(detailsPanel);

        mainPanel.add(tabbedPane);
        this.setVisible(true);
    }

    private void createDetailsPanelComponents() {
        // Create the components related to start date of the incentive.
        createStartDateComponents();

        // Create the components related to end date of the incentive.
        createEndDateComponents();

        // Create the components related to cash discount incentive.
        createCashDiscountIncentiveComponents();

        // Create the components related to loan incentive.
        createLoanIncentiveComponents();

        // Create the components related to the rebate incentive.
        createRebateIncentiveComponents();

        // Create the components related to the leasing incentive.
        createLeaseIncentiveComponents();

        // Create the components related to navigation of the pages.
        createNavigationComponentsFromDetailsPage();

        // Add Incentive groups to a radio button group
        createExclusiveIncentiveGroups();

        addButtonClickActionListenerToNextButton();

        addButtonClickActionListenerToCancelButton();

        // Default behaviour
        if (cashDicountSectionRadioButton.isSelected()) {
            enableCashDiscountGroup();

            // Default behavior in Cash Discount group
            flatRateDiscountRadioButton.setSelected(true);
            flatRateDiscountTextField.setEnabled(true);
            flatRateDiscountTextField.setText("");
            percentageRateDiscountTextField.setText("");
            percentageRateDiscountTextField.setEnabled(false);

            disableLoanGroup();
            disableLeaseGroup();
            disableRebateGroup();
        }
    }

    private void addButtonClickActionListenerToCancelButton() {
        detailsPageCancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Change later
                System.exit(0);
            }
        });
    }

    private void addButtonClickActionListenerToNextButton() {
        detailsPageNextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                validateIncentiveDetailsAndCreateIncentiveInstance();
            }
        });
    }

    private void validateIncentiveDetailsAndCreateIncentiveInstance() {
        Date startDate = validateAndParseDate(startDateTextBox.getText(), "Start Date");
        Date endDate = validateAndParseDate(endDateTextBox.getText(), "End Date");

        if (IncentiveDataValidator.isNull(startDate) || IncentiveDataValidator.isNull(endDate)) {
            return;
        }

        if (startDate.after(endDate)) {
            JOptionPane.showMessageDialog(null, "Start Date: " + startDate + " cannot be after End Date: " + endDate, "Start Date greater than End Date", JOptionPane.ERROR_MESSAGE);
            return;
        }

        this.incentiveTypeSelected = IncentiveType.fromString(incentiveGroups.getSelection().getActionCommand());

        assert this.incentiveTypeSelected != null;

        switch (this.incentiveTypeSelected) {
            case DISCOUNT:
                boolean isCashCountParametersValid = validateAndParseCashDiscountIncentiveParameters();
                if (isCashCountParametersValid) {
                    String message = this.incentiveTypeSelected + "\n" + this.cashDiscountType + "\n" + this.discountFlatAmount + "\n" + this.discountPercentage;
                    JOptionPane.showMessageDialog(null, message);
                    tabbedPane.setSelectedComponent(inventoryPanel);
                }
                break;
            case LOAN:
                boolean isLoanIncentiveParametersValid = validateAndParseLoanIncentiveParameters();
                if (isLoanIncentiveParametersValid) {
                    String message = this.incentiveTypeSelected + "\n" + this.loanInterestRate + "\n" + this.loanDurationInMonths;
                    JOptionPane.showMessageDialog(null, message);
                    tabbedPane.setSelectedComponent(inventoryPanel);
                }
                break;
            case REBATE:
                boolean isRebateIncentiveParametersValid = validateAndParseRebateIncentiveParameters();
                if (isRebateIncentiveParametersValid) {
                    String message = this.incentiveTypeSelected + rebateMap.entrySet().stream()
                            .map(e -> e.getKey() + "=" + e.getValue())
                            .collect(Collectors.joining("&"));
                    JOptionPane.showMessageDialog(null, message);
                    tabbedPane.setSelectedComponent(inventoryPanel);
                }
            case LEASE:
                boolean isLeaseIncentiveParametersValid = validateAndParseLeaseIncentiveParameters();
                if (isLeaseIncentiveParametersValid) {
                    String message = this.incentiveTypeSelected + "\n" + this.leaseDurationInMonths + "\n" + this.leaseSigningAmount + "\n" + this.leaseMonthlyPayment;
                    JOptionPane.showMessageDialog(null, message);
                    tabbedPane.setSelectedComponent(inventoryPanel);
                }
                break;

            default:
                JOptionPane.showMessageDialog(null, "Please select valid incentive Type", "Invalid Incentive type", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean validateAndParseLeaseIncentiveParameters() {
        try {
            this.leaseDurationInMonths = Integer.parseInt(leaseDurationInMonthsTextBox.getText());
            if (this.leaseDurationInMonths <= 0 || this.leaseDurationInMonths > 72) {
                JOptionPane.showMessageDialog(null, "Please enter valid number of months for the lease duration (1-72)", "Invalid lease duration", JOptionPane.ERROR_MESSAGE);
                this.leaseSigningAmount = 0.0;
                this.leaseMonthlyPayment = 0.0;
                return false;
            }
        } catch (NumberFormatException ne) {
            JOptionPane.showMessageDialog(null, "Please enter valid number of months for the lease duration", "Invalid lease duration", JOptionPane.ERROR_MESSAGE);
            this.leaseSigningAmount = 0.0;
            this.leaseMonthlyPayment = 0.0;
            return false;
        }

        try {
            this.leaseSigningAmount = Double.parseDouble(leaseSigningAmountTextBox.getText());
        } catch (NumberFormatException ne) {
            JOptionPane.showMessageDialog(null, "Please enter valid number for lease signing amount", "Invalid lease signing amount", JOptionPane.ERROR_MESSAGE);
            this.leaseDurationInMonths = 0;
            this.leaseMonthlyPayment = 0.0;
            return false;
        }

        try {
            this.leaseMonthlyPayment = Double.parseDouble(leaseMonthlyPaymentInDollarsTextBox.getText());
        } catch (NumberFormatException ne) {
            JOptionPane.showMessageDialog(null, "Please enter valid number of for monthly lease payment", "Invalid monthly lease payment", JOptionPane.ERROR_MESSAGE);
            this.leaseDurationInMonths = 0;
            this.leaseSigningAmount = 0.0;
            return false;
        }

        return true;
    }

    private boolean validateAndParseRebateIncentiveParameters() {
        rebateMap = new HashMap<>();
        if (newGradRebateCheckBox.isSelected()) {
            try {
                double newGradRebateAmount = Double.parseDouble(newGradRebateTextBox.getText());
                rebateMap.put("NewGradRebate", newGradRebateAmount);
            } catch (NumberFormatException ne) {
                JOptionPane.showMessageDialog(null, "Please enter valid number for the new grad incentive amount", "Invalid Rebate Incentive Amount", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }

        if (militaryRebateCheckBox.isSelected()) {
            try {
                double militaryRebateAmount = Double.parseDouble(militaryRebateTextBox.getText());
                rebateMap.put("MilitaryVeteranRebate", militaryRebateAmount);
            } catch (NumberFormatException ne) {
                JOptionPane.showMessageDialog(null, "Please enter valid number for the military veteran incentive amount", "Invalid Rebate Incentive Amount", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }

        if (rebateMap.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please select atleast one type of Rebate incentive", "Invalid Rebate Incentive selection", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    private boolean validateAndParseLoanIncentiveParameters() {
        try {
            this.loanInterestRate = Double.parseDouble(interestRateTextField.getText());
            if (this.loanInterestRate < 0.0 || this.loanInterestRate > 100.0) {
                JOptionPane.showMessageDialog(null, "Please enter valid interest rate value between 0.0 to 100.0 in the loan interest field", "Invalid Loan Interest Rate", JOptionPane.ERROR_MESSAGE);
                this.loanInterestRate = 0.0;
                this.loanDurationInMonths = 0;
                return false;
            }
        } catch (NumberFormatException ne) {
            JOptionPane.showMessageDialog(null, "Please enter correct loan interest rate", "Invalid Loan Interest Rate", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        try {
            this.loanDurationInMonths = Integer.parseInt(loanDurationInMonthsTextField.getText());
            if (this.loanDurationInMonths <= 0 || this.loanDurationInMonths > 72) {
                JOptionPane.showMessageDialog(null, "Please enter valid loan duration in months between 1-72 months in the loan duration field", "Invalid Loan Duration in months", JOptionPane.ERROR_MESSAGE);
                this.loanInterestRate = 0.0;
                this.loanDurationInMonths = 0;
                return false;
            }
        } catch (NumberFormatException ne) {
            JOptionPane.showMessageDialog(null, "Please enter correct loan duration months", "Invalid Loan Interest Rate", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    private boolean validateAndParseCashDiscountIncentiveParameters() {
        this.cashDiscountType = CashDiscountType.fromString(cashDiscountSelectionRadioButtonGroup.getSelection().getActionCommand());
        if (cashDiscountType == null) {
            JOptionPane.showMessageDialog(null, "Please select correct discount type", "Invalid Discount Type", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        switch (cashDiscountType) {
            case PERCENTAGE:
                try {
                    this.discountPercentage = Double.parseDouble(percentageRateDiscountTextField.getText());
                    if (discountPercentage < 0.0 || discountPercentage > 100.0) {
                        this.discountPercentage = 0.0;
                        JOptionPane.showMessageDialog(null, "Please enter valid percentage value between 0.0 to 100.0 in the discount percentage field", "Invalid Discount Percentage", JOptionPane.ERROR_MESSAGE);
                        return false;
                    }
                    this.discountFlatAmount = 0.0;
                } catch (NumberFormatException ne) {
                    JOptionPane.showMessageDialog(null, "Please enter valid percentage value between 0.0 to 100.0 in the discount percentage field", "Invalid Discount Percentage", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
                break;

            case FLATAMOUNT:
                try {
                    this.discountFlatAmount = Double.parseDouble(flatRateDiscountTextField.getText());
                    this.discountPercentage = 0.0;
                } catch (NumberFormatException ne) {
                    JOptionPane.showMessageDialog(null, "Please enter valid number for flat amount field", "Invalid Flat Amount", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
                break;

            default:
                JOptionPane.showMessageDialog(null, "Please select valid discount type", "Invalid Discount Type", JOptionPane.ERROR_MESSAGE);
                return false;
        }

        return true;
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
        detailsPanel.add(detailsPageCancelButton);

        detailsPageNextButton = new JButton("Next");
        detailsPageNextButton.setBounds(450, 448, 117, 29);
        detailsPanel.add(detailsPageNextButton);
    }

    public void enableLeaseGroup() {
        leaseMonthlyPaymentInDollarsTextBox.setText("");
        leaseMonthlyPaymentInDollarsTextBox.setEnabled(true);
        leaseDurationInMonthsTextBox.setText("");
        leaseDurationInMonthsTextBox.setEnabled(true);
        leaseSigningAmountTextBox.setText("");
        leaseSigningAmountTextBox.setEnabled(true);
    }

    public void disableLeaseGroup() {
        leaseMonthlyPaymentInDollarsTextBox.setText("");
        leaseMonthlyPaymentInDollarsTextBox.setEnabled(false);
        leaseDurationInMonthsTextBox.setText("");
        leaseDurationInMonthsTextBox.setEnabled(false);
        leaseSigningAmountTextBox.setText("");
        leaseSigningAmountTextBox.setEnabled(false);
    }

    private void createLeaseIncentiveComponents() {
        leaseSectionRadioButton = new JRadioButton("Lease Incentive");
        leaseSectionRadioButton.setFont(new Font("Lucida Grande", Font.BOLD, 15));
        leaseSectionRadioButton.setBounds(432, 271, 204, 23);
        leaseSectionRadioButton.setActionCommand(String.valueOf(IncentiveType.LEASE));
        detailsPanel.add(leaseSectionRadioButton);

        leaseMonthlyPaymentInDollarsLabel = new JLabel("Monthly Payment of ($)");
        leaseMonthlyPaymentInDollarsLabel.setBounds(456, 304, 153, 16);
        detailsPanel.add(leaseMonthlyPaymentInDollarsLabel);

        leaseDurationInMonthsLabel = new JLabel("Number of months");
        leaseDurationInMonthsLabel.setBounds(456, 332, 153, 16);
        detailsPanel.add(leaseDurationInMonthsLabel);

        leaseSigningAmountLabel = new JLabel("Signing Amount of ($)");
        leaseSigningAmountLabel.setBounds(456, 360, 153, 16);
        detailsPanel.add(leaseSigningAmountLabel);

        leaseMonthlyPaymentInDollarsTextBox = new JTextField();
        leaseMonthlyPaymentInDollarsTextBox.setBounds(621, 299, 56, 26);
        detailsPanel.add(leaseMonthlyPaymentInDollarsTextBox);
        leaseMonthlyPaymentInDollarsTextBox.setColumns(10);

        leaseDurationInMonthsTextBox = new JTextField();
        leaseDurationInMonthsTextBox.setBounds(621, 327, 56, 26);
        detailsPanel.add(leaseDurationInMonthsTextBox);
        leaseDurationInMonthsTextBox.setColumns(10);

        leaseSigningAmountTextBox = new JTextField();
        leaseSigningAmountTextBox.setColumns(10);
        leaseSigningAmountTextBox.setBounds(621, 355, 56, 26);
        detailsPanel.add(leaseSigningAmountTextBox);

        leaseSectionRadioButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (leaseSectionRadioButton.isSelected()) {
                    enableLeaseGroup();
                    disableCashDiscountGroup();
                    disableLoanGroup();
                    disableRebateGroup();
                }
            }
        });
    }

    public void enableRebateGroup() {
        newGradRebateCheckBox.setSelected(false);
        newGradRebateCheckBox.setEnabled(true);
        militaryRebateCheckBox.setSelected(false);
        militaryRebateCheckBox.setEnabled(true);
        newGradRebateTextBox.setEnabled(true);
        newGradRebateTextBox.setText("");
        militaryRebateTextBox.setEnabled(true);
        militaryRebateTextBox.setText("");
    }

    public void disableRebateGroup() {
        newGradRebateCheckBox.setSelected(false);
        newGradRebateCheckBox.setEnabled(false);
        militaryRebateCheckBox.setSelected(false);
        militaryRebateCheckBox.setEnabled(false);
        newGradRebateTextBox.setEnabled(false);
        newGradRebateTextBox.setText("");
        militaryRebateTextBox.setEnabled(false);
        militaryRebateTextBox.setText("");
    }

    private void createRebateIncentiveComponents() {
        rebateSectionRadioButton = new JRadioButton("Rebate Incentive");
        rebateSectionRadioButton.setFont(new Font("Lucida Grande", Font.BOLD, 15));
        rebateSectionRadioButton.setBounds(427, 121, 204, 23);
        rebateSectionRadioButton.setActionCommand(String.valueOf(IncentiveType.REBATE));
        detailsPanel.add(rebateSectionRadioButton);

        newGradRebateCheckBox = new JCheckBox("Rebate for new Grads");
        newGradRebateCheckBox.setBounds(456, 156, 165, 23);
        detailsPanel.add(newGradRebateCheckBox);

        militaryRebateCheckBox = new JCheckBox("Rebate for Millitary Veterans");
        militaryRebateCheckBox.setBounds(456, 191, 221, 23);
        detailsPanel.add(militaryRebateCheckBox);

        newGradRebateTextBox = new JTextField();
        newGradRebateTextBox.setBounds(688, 155, 63, 26);
        detailsPanel.add(newGradRebateTextBox);
        newGradRebateTextBox.setColumns(10);

        militaryRebateTextBox = new JTextField();
        militaryRebateTextBox.setColumns(10);
        militaryRebateTextBox.setBounds(688, 190, 63, 26);
        detailsPanel.add(militaryRebateTextBox);

        rebateSectionRadioButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (rebateSectionRadioButton.isSelected()) {
                    enableRebateGroup();
                    disableCashDiscountGroup();
                    disableLoanGroup();
                    disableLeaseGroup();
                }
            }
        });
    }

    public void disableLoanGroup() {
        interestRateTextField.setText("");
        interestRateTextField.setEnabled(false);
        loanDurationInMonthsTextField.setText("");
        loanDurationInMonthsTextField.setEnabled(false);
    }

    public void enableLoanGroup() {
        interestRateTextField.setText("");
        interestRateTextField.setEnabled(true);
        loanDurationInMonthsTextField.setText("");
        loanDurationInMonthsTextField.setEnabled(true);
    }

    private void createLoanIncentiveComponents() {
        loanSectionRadioButton = new JRadioButton("Loan Incentive");
        loanSectionRadioButton.setFont(new Font("Lucida Grande", Font.BOLD, 15));
        loanSectionRadioButton.setBounds(68, 270, 204, 23);
        loanSectionRadioButton.setActionCommand(String.valueOf(IncentiveType.LOAN));
        detailsPanel.add(loanSectionRadioButton);

        interestRateLabel = new JLabel("Interest Rate (%)");
        interestRateLabel.setBounds(107, 304, 102, 16);
        detailsPanel.add(interestRateLabel);

        loanDurationInMonthsLabel = new JLabel("Number of months");
        loanDurationInMonthsLabel.setBounds(107, 332, 119, 16);
        detailsPanel.add(loanDurationInMonthsLabel);

        interestRateTextField = new JTextField();
        interestRateTextField.setBounds(238, 299, 50, 26);
        detailsPanel.add(interestRateTextField);
        interestRateTextField.setColumns(10);

        loanDurationInMonthsTextField = new JTextField();
        loanDurationInMonthsTextField.setColumns(10);
        loanDurationInMonthsTextField.setBounds(238, 327, 50, 26);
        detailsPanel.add(loanDurationInMonthsTextField);

        loanSectionRadioButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (loanSectionRadioButton.isSelected()) {
                    enableLoanGroup();
                    disableCashDiscountGroup();
                    disableLeaseGroup();
                    disableRebateGroup();
                }
            }
        });
    }

    public void enableCashDiscountGroup() {
        flatRateDiscountRadioButton.setSelected(true);
        flatRateDiscountRadioButton.setEnabled(true);
        percentageRateDiscountRadioButton.setEnabled(true);
        flatRateDiscountTextField.setText("");
        flatRateDiscountTextField.setEnabled(true);
        percentageRateDiscountTextField.setText("");
        percentageRateDiscountTextField.setEnabled(true);
    }

    public void disableCashDiscountGroup() {
        flatRateDiscountRadioButton.setSelected(false);
        flatRateDiscountRadioButton.setEnabled(false);
        percentageRateDiscountRadioButton.setSelected(false);
        percentageRateDiscountRadioButton.setEnabled(false);
        flatRateDiscountTextField.setText("");
        flatRateDiscountTextField.setEnabled(false);
        percentageRateDiscountTextField.setText("");
        percentageRateDiscountTextField.setEnabled(false);
    }

    private void createCashDiscountIncentiveComponents() {
        cashDicountSectionRadioButton = new JRadioButton("Discount Incentive");
        cashDicountSectionRadioButton.setFont(new Font("Lucida Grande", Font.BOLD, 15));
        cashDicountSectionRadioButton.setBounds(68, 121, 204, 23);
        cashDicountSectionRadioButton.setActionCommand(String.valueOf(IncentiveType.DISCOUNT));
        cashDicountSectionRadioButton.setSelected(true);
        detailsPanel.add(cashDicountSectionRadioButton);

        flatRateDiscountRadioButton = new JRadioButton("$");
        flatRateDiscountRadioButton.setBounds(107, 156, 50, 23);
        flatRateDiscountRadioButton.setActionCommand(String.valueOf(CashDiscountType.FLATAMOUNT));
        detailsPanel.add(flatRateDiscountRadioButton);

        percentageRateDiscountRadioButton = new JRadioButton("%");
        percentageRateDiscountRadioButton.setBounds(107, 191, 42, 23);
        percentageRateDiscountRadioButton.setActionCommand(String.valueOf(CashDiscountType.PERCENTAGE));
        detailsPanel.add(percentageRateDiscountRadioButton);

        cashDiscountSelectionRadioButtonGroup = new ButtonGroup();
        cashDiscountSelectionRadioButtonGroup.add(flatRateDiscountRadioButton);
        cashDiscountSelectionRadioButtonGroup.add(percentageRateDiscountRadioButton);


        flatRateDiscountTextField = new JTextField();
        flatRateDiscountTextField.setBounds(161, 156, 75, 26);
        detailsPanel.add(flatRateDiscountTextField);
        flatRateDiscountTextField.setColumns(10);

        percentageRateDiscountTextField = new JTextField();
        percentageRateDiscountTextField.setColumns(10);
        percentageRateDiscountTextField.setBounds(161, 188, 75, 26);
        detailsPanel.add(percentageRateDiscountTextField);

        cashDicountSectionRadioButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (cashDicountSectionRadioButton.isSelected()) {
                    enableCashDiscountGroup();
                    disableLoanGroup();
                    disableLeaseGroup();
                    disableRebateGroup();
                }
            }
        });

        flatRateDiscountRadioButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (flatRateDiscountRadioButton.isSelected()) {
                    flatRateDiscountTextField.setEnabled(true);
                    flatRateDiscountTextField.setText("");
                    percentageRateDiscountTextField.setText("");
                    percentageRateDiscountTextField.setEnabled(false);
                }
            }
        });

        percentageRateDiscountRadioButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (percentageRateDiscountRadioButton.isSelected()) {
                    percentageRateDiscountTextField.setText("");
                    percentageRateDiscountTextField.setEnabled(true);
                    flatRateDiscountTextField.setText("");
                    flatRateDiscountTextField.setEnabled(false);
                }
            }
        });

    }

    private void createStartDateComponents() {
        startDateTextBox = new JTextField();
        startDateTextBox.setBounds(184, 26, 89, 30);

        startDateCalendarPanel = new CalendarPanel(startDateTextBox, "yyyy/MM/dd");
        startDateCalendarPanel.initCalendarPanel();
        detailsPanel.add(startDateCalendarPanel);
        detailsPanel.add(startDateTextBox);

        startDateLabel = new JLabel("Start Date");
        startDateLabel.setFont(new Font("Lucida Grande", Font.BOLD, 15));
        startDateLabel.setBounds(68, 26, 89, 30);
        detailsPanel.add(startDateLabel);
    }

    private void createEndDateComponents() {
        endDateTextBox = new JTextField();
        endDateTextBox.setBounds(551, 26, 89, 30);

        endDateCalendarPanel = new CalendarPanel(endDateTextBox, "yyyy/MM/dd");
        endDateCalendarPanel.initCalendarPanel();
        detailsPanel.add(endDateCalendarPanel);
        detailsPanel.add(endDateTextBox);

        endDateLabel = new JLabel("End Date");
        endDateLabel.setFont(new Font("Lucida Grande", Font.BOLD, 15));
        endDateLabel.setBounds(432, 26, 89, 30);
        detailsPanel.add(endDateLabel);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                new IncentiveManagerUI();
            }
        });
    }
}