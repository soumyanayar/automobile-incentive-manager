package dataprovider;

import entities.*;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public class MsSqlDataProvider {
    private static final String URL
            = "jdbc:sqlserver://guiyu.database.windows.net:1433;databaseName=test2";
    private static final String USERNAME = "guiyu";
    private static final String PASSWORD = "2021test**";

    private Connection dbConnection;
    private static MsSqlDataProvider _instance;
    private Statement dbStatement;

    // Singleton Pattern
    static public MsSqlDataProvider getInstance() throws SQLException, ClassNotFoundException {
        if (_instance == null) {
            _instance = new MsSqlDataProvider();
            _instance.createRequiredTablesIfDoesNotExists();
        }
        return _instance;
    }

    private void createRequiredTablesIfDoesNotExists() throws SQLException {
        this.createIncentiveTableIfDoesnotExisit();
        this.createIncentiveVINsTableIfDoesnotExisit();
        this.createIncentiveRebatesTableIfDoesnotExisit();
    }

    private MsSqlDataProvider() throws SQLException, ClassNotFoundException {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        this.dbConnection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        this.dbStatement = dbConnection.createStatement();
    }

    private void createIncentiveTableIfDoesnotExisit() throws SQLException {
        this.dbStatement.execute("if not exists (select * from sysobjects where name='Incentive' and xtype='U')" +
                "CREATE TABLE dbo.Incentive (" +
                "id VARCHAR(40) PRIMARY KEY NOT NULL," +
                "incentiveType VARCHAR(20) NOT NULL," +
                "dealerId VARCHAR(40) NOT NULL," +
                "startDate DATE NOT NULL," +
                "endDate DATE NOT NULL," +
                "title VARCHAR(MAX) NOT NULL," +
                "description VARCHAR(MAX) NOT NULL," +
                "disclaimer VARCHAR(MAX) NOT NULL," +
                "carVinUUID VARCHAR(40) NOT NULL," +
                "cashDiscountType VARCHAR(20) DEFAULT(NULL)," +
                "discountValue DECIMAL DEFAULT(NULL)," +
                "apr DECIMAL DEFAULT(NULL)," +
                "loanmonths INT DEFAULT(NULL)," +
                "leasemonths INT DEFAULT(NULL)," +
                "signingPay DECIMAL DEFAULT(NULL)," +
                "monthlyPay DECIMAL DEFAULT(NULL)," +
                "rebateMapUUID VARCHAR(40) DEFAULT(NULL))");
    }

    private void createIncentiveVINsTableIfDoesnotExisit() throws SQLException {
        this.dbStatement.execute("if not exists (select * from sysobjects where name='IncentiveVINs' and xtype='U')" +
                "CREATE TABLE dbo.IncentiveVINs (" +
                "incentiveVinID VARCHAR(40) NOT NULL," +
                "carVIN VARCHAR(40) NOT NULL)");
    }

    private void createIncentiveRebatesTableIfDoesnotExisit() throws SQLException {
        this.dbStatement.execute("if not exists (select * from sysobjects where name='IncentiveRebates' and xtype='U')" +
                "CREATE TABLE dbo.IncentiveRebates (" +
                "rebateID VARCHAR(40) NOT NULL," +
                "rebateType VARCHAR(40) NOT NULL," +
                "rebateValue DECIMAL NOT NULL)");
    }

    public void persistIncentive(CashDiscountIncentive cashDiscountIncentive) throws SQLException {
        String carVinUUID = UUID.randomUUID().toString();
        String sql = "INSERT INTO Incentive(id, incentiveType, dealerId, startDate, endDate, title, description, disclaimer, carVinUUID, " +
                "cashDiscountType, discountValue) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement preparedStatement = this.dbConnection.prepareStatement(sql);
        preparedStatement.setString(1, cashDiscountIncentive.getId());
        preparedStatement.setString(2, cashDiscountIncentive.getIncentiveType().toString());
        preparedStatement.setString(3, cashDiscountIncentive.getDealerId());
        preparedStatement.setDate(4, new java.sql.Date(cashDiscountIncentive.getStartDate().getTime()));
        preparedStatement.setDate(5, new java.sql.Date(cashDiscountIncentive.getEndDate().getTime()));
        preparedStatement.setString(6, cashDiscountIncentive.getTitle());
        preparedStatement.setString(7, cashDiscountIncentive.getDescription());
        preparedStatement.setString(8, cashDiscountIncentive.getDisclaimer());
        preparedStatement.setString(9, carVinUUID);
        preparedStatement.setString(10, cashDiscountIncentive.getCashDiscountType().toString());
        preparedStatement.setDouble(11, cashDiscountIncentive.getValue());
        preparedStatement.execute();

        sql = "INSERT INTO IncentiveVINs(incentiveVinID, carVIN) VALUES (?, ?)";
        preparedStatement = this.dbConnection.prepareStatement(sql);
        for (String carVIN : cashDiscountIncentive.getCarVINList()) {
            preparedStatement.setString(1, carVinUUID);
            preparedStatement.setString(2, carVIN);
            preparedStatement.execute();
        }
    }

    public void persistIncentive(LoanIncentive loanDiscountIncentive) throws SQLException {
        String carVinUUID = UUID.randomUUID().toString();
        String sql = "INSERT INTO Incentive(id, incentiveType, dealerId, startDate, endDate, title, description, disclaimer, carVinUUID, " +
                "apr, loanmonths) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement preparedStatement = this.dbConnection.prepareStatement(sql);
        preparedStatement.setString(1, loanDiscountIncentive.getId());
        preparedStatement.setString(2, loanDiscountIncentive.getIncentiveType().toString());
        preparedStatement.setString(3, loanDiscountIncentive.getDealerId());
        preparedStatement.setDate(4, new java.sql.Date(loanDiscountIncentive.getStartDate().getTime()));
        preparedStatement.setDate(5, new java.sql.Date(loanDiscountIncentive.getEndDate().getTime()));
        preparedStatement.setString(6, loanDiscountIncentive.getTitle());
        preparedStatement.setString(7, loanDiscountIncentive.getDescription());
        preparedStatement.setString(8, loanDiscountIncentive.getDisclaimer());
        preparedStatement.setString(9, carVinUUID);
        preparedStatement.setDouble(10, loanDiscountIncentive.getApr());
        preparedStatement.setInt(11, loanDiscountIncentive.getMonths());
        preparedStatement.execute();

        sql = "INSERT INTO IncentiveVINs(incentiveVinID, carVIN) VALUES (?, ?)";
        preparedStatement = this.dbConnection.prepareStatement(sql);
        for (String carVIN : loanDiscountIncentive.getCarVINList()) {
            preparedStatement.setString(1, carVinUUID);
            preparedStatement.setString(2, carVIN);
            preparedStatement.execute();
        }
    }

    public void persistIncentive(LeasingIncentive leasingIncentive) throws SQLException {
        String carVinUUID = UUID.randomUUID().toString();
        String sql = "INSERT INTO Incentive(id, incentiveType, dealerId, startDate, endDate, title, description, disclaimer, carVinUUID, " +
                "leasemonths, signingPay, monthlyPay) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement preparedStatement = this.dbConnection.prepareStatement(sql);
        preparedStatement.setString(1, leasingIncentive.getId());
        preparedStatement.setString(2, leasingIncentive.getIncentiveType().toString());
        preparedStatement.setString(3, leasingIncentive.getDealerId());
        preparedStatement.setDate(4, new java.sql.Date(leasingIncentive.getStartDate().getTime()));
        preparedStatement.setDate(5, new java.sql.Date(leasingIncentive.getEndDate().getTime()));
        preparedStatement.setString(6, leasingIncentive.getTitle());
        preparedStatement.setString(7, leasingIncentive.getDescription());
        preparedStatement.setString(8, leasingIncentive.getDisclaimer());
        preparedStatement.setString(9, carVinUUID);
        preparedStatement.setInt(10, leasingIncentive.getMonths());
        preparedStatement.setDouble(11, leasingIncentive.getSigningPay());
        preparedStatement.setDouble(12, leasingIncentive.getMonthlyPay());
        preparedStatement.execute();

        sql = "INSERT INTO IncentiveVINs(incentiveVinID, carVIN) VALUES (?, ?)";
        preparedStatement = this.dbConnection.prepareStatement(sql);
        for (String carVIN : leasingIncentive.getCarVINList()) {
            preparedStatement.setString(1, carVinUUID);
            preparedStatement.setString(2, carVIN);
            preparedStatement.execute();
        }
    }

    public void persistIncentive(RebateIncentive rebateIncentive) throws SQLException {
        String carVinUUID = UUID.randomUUID().toString();
        String rebateMapUUID = UUID.randomUUID().toString();
        String sql = "INSERT INTO Incentive(id, incentiveType, dealerId, startDate, endDate, title, description, disclaimer, carVinUUID, " +
                "rebateMapUUID) VALUES (?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement preparedStatement = this.dbConnection.prepareStatement(sql);
        preparedStatement.setString(1, rebateIncentive.getId());
        preparedStatement.setString(2, rebateIncentive.getIncentiveType().toString());
        preparedStatement.setString(3, rebateIncentive.getDealerId());
        preparedStatement.setDate(4, new java.sql.Date(rebateIncentive.getStartDate().getTime()));
        preparedStatement.setDate(5, new java.sql.Date(rebateIncentive.getEndDate().getTime()));
        preparedStatement.setString(6, rebateIncentive.getTitle());
        preparedStatement.setString(7, rebateIncentive.getDescription());
        preparedStatement.setString(8, rebateIncentive.getDisclaimer());
        preparedStatement.setString(9, carVinUUID);
        preparedStatement.setString(10, rebateMapUUID);
        preparedStatement.execute();

        sql = "INSERT INTO IncentiveVINs(incentiveVinID, carVIN) VALUES (?, ?)";
        preparedStatement = this.dbConnection.prepareStatement(sql);
        for (String carVIN : rebateIncentive.getCarVINList()) {
            preparedStatement.setString(1, carVinUUID);
            preparedStatement.setString(2, carVIN);
            preparedStatement.execute();
        }

        sql = "INSERT INTO IncentiveRebates(rebateID, rebateType, rebateValue) VALUES (?, ?, ?)";
        preparedStatement = this.dbConnection.prepareStatement(sql);
        for (Map.Entry<String, Double> mapElement : rebateIncentive.getRebateMap().entrySet()) {
            preparedStatement.setString(1, rebateMapUUID);
            preparedStatement.setString(2, mapElement.getKey());
            preparedStatement.setDouble(3, mapElement.getValue());
            preparedStatement.execute();
        }
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        MsSqlDataProvider msSqlDataProvider = MsSqlDataProvider.getInstance();
        CashDiscountIncentive cashDiscountIncentive = new CashDiscountIncentive(
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                new Date(System.currentTimeMillis()),
                new Date(System.currentTimeMillis()),
                "title1",
                "description1",
                "disclaimer1",
                new HashSet<>(Arrays.asList(UUID.randomUUID().toString(), UUID.randomUUID().toString(), UUID.randomUUID().toString(), UUID.randomUUID().toString())),
                1000,
                CashDiscountType.FLATAMOUNT
        );

        LoanIncentive loanIncentive = new LoanIncentive(
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                new Date(System.currentTimeMillis()),
                new Date(System.currentTimeMillis()),
                "title1",
                "description1",
                "disclaimer1",
                new HashSet<>(Arrays.asList(UUID.randomUUID().toString(), UUID.randomUUID().toString(), UUID.randomUUID().toString(), UUID.randomUUID().toString())),
                3.4,
                72
        );

        LeasingIncentive leasingIncentive = new LeasingIncentive(
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                new Date(System.currentTimeMillis()),
                new Date(System.currentTimeMillis()),
                "title1",
                "description1",
                "disclaimer1",
                new HashSet<>(Arrays.asList(UUID.randomUUID().toString(), UUID.randomUUID().toString(), UUID.randomUUID().toString(), UUID.randomUUID().toString())),
                36,
                4000.0,
                500.0
        );

        RebateIncentive rebateIncentive = new RebateIncentive(
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                new Date(System.currentTimeMillis()),
                new Date(System.currentTimeMillis()),
                "title1",
                "description1",
                "disclaimer1",
                new HashSet<>(Arrays.asList(UUID.randomUUID().toString(), UUID.randomUUID().toString(), UUID.randomUUID().toString(), UUID.randomUUID().toString())),
                new HashMap<String, Double>() {
                    {
                        put("NewGradRebate", 2000.0);
                        put("MilitaryVeteranRebate", 3000.0);
                    }
                }
                );
        msSqlDataProvider.persistIncentive(cashDiscountIncentive);
        msSqlDataProvider.persistIncentive(loanIncentive);
        msSqlDataProvider.persistIncentive(leasingIncentive);
        msSqlDataProvider.persistIncentive(rebateIncentive);
    }
}
