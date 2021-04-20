package dataprovider;

import entities.CashDiscountIncentive;
import entities.CashDiscountType;
import java.sql.*;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.UUID;

public class MsSqlDataProvider {
    private static final String URL
            = "jdbc:sqlserver://guiyu.database.windows.net:1433;databaseName=test2";
    private static final String USERNAME = "guiyu";
    private static final String PASSWORD = "2021test**";

    private Connection dbConnection;
    private static MsSqlDataProvider _instance;
    private Statement dbStatement;

    static public MsSqlDataProvider getInstance() throws SQLException, ClassNotFoundException {
        if (_instance == null) {
            _instance = new MsSqlDataProvider();
        }
        return _instance;
    }

    private MsSqlDataProvider() throws SQLException, ClassNotFoundException {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        this.dbConnection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        this.dbStatement = dbConnection.createStatement();
    }

    private void createIncentiveTable() throws SQLException {
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

    private void createIncentiveVINsTable() throws SQLException {
        this.dbStatement.execute("if not exists (select * from sysobjects where name='IncentiveVINs' and xtype='U')" +
                "CREATE TABLE dbo.IncentiveVINs (" +
                "incentiveVinID VARCHAR(40) NOT NULL," +
                "carVIN VARCHAR(40) NOT NULL)");
    }

    private void createIncentiveRebatesTable() throws SQLException {
        this.dbStatement.execute("if not exists (select * from sysobjects where name='IncentiveRebates' and xtype='U')" +
                "CREATE TABLE dbo.IncentiveRebates (" +
                "rebateID VARCHAR(40) NOT NULL," +
                "rebateType VARCHAR(40) NOT NULL," +
                "rebateValue DECIMAL NOT NULL)");
    }

    private void persistIncentive(CashDiscountIncentive cashDiscountIncentive) throws SQLException {
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

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        MsSqlDataProvider msSqlDataProvider = MsSqlDataProvider.getInstance();
        msSqlDataProvider.createIncentiveTable();
        msSqlDataProvider.createIncentiveVINsTable();
        msSqlDataProvider.createIncentiveRebatesTable();
        CashDiscountIncentive cashDiscountIncentive = new CashDiscountIncentive(
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                new Date(),
                new Date(),
                "title1",
                "description1",
                "disclaimer1",
                new HashSet<>(Arrays.asList(UUID.randomUUID().toString(), UUID.randomUUID().toString(), UUID.randomUUID().toString(), UUID.randomUUID().toString())),
                1000,
                CashDiscountType.FLATAMOUNT
        );
        msSqlDataProvider.persistIncentive(cashDiscountIncentive);
    }
}
