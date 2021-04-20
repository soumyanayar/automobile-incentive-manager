package dataprovider;

import entities.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.sql.Date;
import java.util.*;

public class MsSqlDataProvider implements DataProvider{
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

    private List<String> getRecordFromLine(String line) {
        List<String> values = new ArrayList<>();
        try (Scanner rowScanner = new Scanner(line)) {
            rowScanner.useDelimiter(",");
            while (rowScanner.hasNext()) {
                values.add(rowScanner.next());
            }
        }
        return values;
    }

    public List<LoanIncentive> getAllLoanIncentivesByCarVIN(String carVIN) throws SQLException{
        String sql = "SELECT * FROM \n" +
                "\t(SELECT * FROM dbo.Incentive\n" +
                "\tWHERE incentiveType = 'Loan Incentive')\n" +
                "AS Incentive\n" +
                "JOIN dbo.IncentiveVINs AS IncentiveVINs\n" +
                "ON Incentive.carVinUUID = IncentiveVINs.incentiveVinID\n" +
                "AND GETDATE() BETWEEN Incentive.startDate AND Incentive.endDate\n" +
                "WHERE IncentiveVINs.carVIN = '"+ carVIN +"'";

        List<LoanIncentive> loanIncentiveList = new ArrayList<>();

        ResultSet rs = this.dbStatement.executeQuery(sql);
        while(rs.next()){
            // Retrieve by column name
            String id  = rs.getString("id");
            String incentiveType = rs.getString("incentiveType");
            String dealerId = rs.getString("dealerId");
            Date startDate = rs.getDate("startDate");
            Date endDate = rs.getDate("endDate");
            String title = rs.getString("title");
            String description = rs.getString("description");
            String disclaimer = rs.getString("disclaimer");
            HashSet carVINs = new HashSet<>(Arrays.asList(rs.getString("carVIN")));
            double apr = rs.getDouble("apr");
            int months = rs.getInt("loanmonths");
            loanIncentiveList.add(new LoanIncentive(id, dealerId, startDate, endDate, title, description, disclaimer, carVINs, apr, months));
        }

        return loanIncentiveList;
    }

    public List<CashDiscountIncentive> getAllCashDiscountIncentivesByCarVIN(String carVIN) throws SQLException {
        String sql = "SELECT * FROM \n" +
                "\t(SELECT * FROM dbo.Incentive\n" +
                "\tWHERE incentiveType = 'Discount Incentive')\n" +
                "AS Incentive\n" +
                "JOIN dbo.IncentiveVINs AS IncentiveVINs\n" +
                "ON Incentive.carVinUUID = IncentiveVINs.incentiveVinID\n" +
                "AND GETDATE() BETWEEN Incentive.startDate AND Incentive.endDate\n" +
                "WHERE IncentiveVINs.carVIN = '"+ carVIN +"'";

        List<CashDiscountIncentive> cashDiscountIncentiveList = new ArrayList<>();

        ResultSet rs = this.dbStatement.executeQuery(sql);
        while(rs.next()){
            // Retrieve by column name
            String id  = rs.getString("id");
            String incentiveType = rs.getString("incentiveType");
            String dealerId = rs.getString("dealerId");
            Date startDate = rs.getDate("startDate");
            Date endDate = rs.getDate("endDate");
            String title = rs.getString("title");
            String description = rs.getString("description");
            String disclaimer = rs.getString("disclaimer");
            HashSet carVINs = new HashSet<>(Arrays.asList(rs.getString("carVIN")));
            CashDiscountType cashDiscountType = CashDiscountType.fromString(rs.getString("cashDiscountType"));
            double discountValue = rs.getInt("discountValue");
            cashDiscountIncentiveList.add(new CashDiscountIncentive(id, dealerId, startDate, endDate, title, description, disclaimer, carVINs, discountValue, cashDiscountType));
        }

        return cashDiscountIncentiveList;
    }

    public List<RebateIncentive> getAllRebateIncentivesByCarVIN(String carVIN) throws SQLException{
        String sql = "SELECT * FROM \n" +
                "\t(SELECT * FROM dbo.Incentive\n" +
                "\tWHERE incentiveType = 'Rebate Incentive') \n" +
                "AS Incentive\n" +
                "JOIN dbo.IncentiveVINs AS IncentiveVINs\n" +
                "ON Incentive.carVinUUID = IncentiveVINs.incentiveVinID\n" +
                "JOIN dbo.IncentiveRebates AS IncentiveRebates\n" +
                "ON Incentive.rebateMapUUID = IncentiveRebates.rebateID\n" +
                "AND GETDATE() BETWEEN Incentive.startDate AND Incentive.endDate\n" +
                "WHERE IncentiveVINs.carVIN = '" + carVIN + "'";

        List<RebateIncentive> rebateIncentiveList = new ArrayList<>();

        ResultSet rs = this.dbStatement.executeQuery(sql);
        while(rs.next()){
            // Retrieve by column name
            String id  = rs.getString("id");
            String incentiveType = rs.getString("incentiveType");
            String dealerId = rs.getString("dealerId");
            Date startDate = rs.getDate("startDate");
            Date endDate = rs.getDate("endDate");
            String title = rs.getString("title");
            String description = rs.getString("description");
            String disclaimer = rs.getString("disclaimer");
            HashSet carVINs = new HashSet<>(Arrays.asList(rs.getString("carVIN")));
            HashMap rebateMap = new HashMap<String, Double>() {
                {
                    put(rs.getString("rebateType"), rs.getDouble("rebateValue"));
                }
            };
            rebateIncentiveList.add(new RebateIncentive(id, dealerId, startDate, endDate, title, description, disclaimer, carVINs, rebateMap));
        }

        return rebateIncentiveList;
    }

    public List<LeasingIncentive> getAllLeasingIncentivesByCarVIN(String carVIN) throws SQLException{
        String sql = "SELECT * FROM \n" +
                "\t(SELECT * FROM dbo.Incentive\n" +
                "\tWHERE incentiveType = 'Lease Incentive')\n" +
                "AS Incentive\n" +
                "JOIN dbo.IncentiveVINs AS IncentiveVINs\n" +
                "ON Incentive.carVinUUID = IncentiveVINs.incentiveVinID\n" +
                "AND GETDATE() BETWEEN Incentive.startDate AND Incentive.endDate\n" +
                "WHERE IncentiveVINs.carVIN = '"+ carVIN +"'";

        List<LeasingIncentive> leasingIncentiveList = new ArrayList<>();

        ResultSet rs = this.dbStatement.executeQuery(sql);
        while(rs.next()){
            // Retrieve by column name
            String id  = rs.getString("id");
            String incentiveType = rs.getString("incentiveType");
            String dealerId = rs.getString("dealerId");
            Date startDate = rs.getDate("startDate");
            Date endDate = rs.getDate("endDate");
            String title = rs.getString("title");
            String description = rs.getString("description");
            String disclaimer = rs.getString("disclaimer");
            HashSet carVINs = new HashSet<>(Arrays.asList(rs.getString("carVIN")));
            int leasemonths = rs.getInt("leasemonths");
            double signingPay = rs.getDouble("signingPay");
            double monthlyPay = rs.getDouble("monthlyPay");
            leasingIncentiveList.add(new LeasingIncentive(id, dealerId, startDate, endDate, title, description, disclaimer, carVINs, leasemonths, signingPay, monthlyPay));
        }

        return leasingIncentiveList;
    }

    // This method is same as CSV data provider's getAllCarsByDealerId
    // This whole Data Provider was created to demonstrate how to persist incentive into MsSql Database
    // Since Yangzi Xin team has already implemented getAllCarsByDealerId, I have kept this same as CSV data provider's getAllCarsByDealerId
    // TODO @soumya if needed in future, update this method to actually read cars by dealer id from the MsSql database.
    @Override
    public List<Car> getAllCarsByDealerId(String dealerId) {
        String inputDataPath = System.getProperty("user.dir") + "/src/dataprovider/mockedcsvdatabase/Cars.csv";
        List<List<String>> records = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(inputDataPath));) {
            while (scanner.hasNextLine()) {
                records.add(getRecordFromLine(scanner.nextLine()));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        List<Car> carsByDealerId = new ArrayList<>();
        for (int i = 1; i < records.size(); ++i) {
            List<String> carRecord = records.get(i);
            if (carRecord.get(1).equalsIgnoreCase(dealerId)) {
                Car car = new Car();
                car.setVIN(carRecord.get(0));
                car.setDealerId(carRecord.get(1));
                car.setCarCategory(CarCategory.fromString(carRecord.get(2)));
                car.setMake(carRecord.get(3));
                car.setModel(carRecord.get(4));
                car.setYear(Integer.parseInt(carRecord.get(5)));
                car.setMSRP(Double.parseDouble(carRecord.get(6)));
                car.setColor(carRecord.get(7));
                car.setLocation(carRecord.get(8));
                car.setMileage(Integer.parseInt(carRecord.get(9)));
                carsByDealerId.add(car);
            }
        }

        return carsByDealerId;
    }

    @Override
    public void persistIncentive(CashDiscountIncentive cashDiscountIncentive) {
        String carVinUUID = UUID.randomUUID().toString();
        String sql = "INSERT INTO Incentive(id, incentiveType, dealerId, startDate, endDate, title, description, disclaimer, carVinUUID, " +
                "cashDiscountType, discountValue) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
        try {
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
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    @Override
    public void persistIncentive(LoanIncentive loanDiscountIncentive) {
        String carVinUUID = UUID.randomUUID().toString();
        String sql = "INSERT INTO Incentive(id, incentiveType, dealerId, startDate, endDate, title, description, disclaimer, carVinUUID, " +
                "apr, loanmonths) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
        try {
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
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    @Override
    public void persistIncentive(LeasingIncentive leasingIncentive) {
        String carVinUUID = UUID.randomUUID().toString();
        String sql = "INSERT INTO Incentive(id, incentiveType, dealerId, startDate, endDate, title, description, disclaimer, carVinUUID, " +
                "leasemonths, signingPay, monthlyPay) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
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
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    @Override
    public void persistIncentive(RebateIncentive rebateIncentive) {
        String carVinUUID = UUID.randomUUID().toString();
        String rebateMapUUID = UUID.randomUUID().toString();
        String sql = "INSERT INTO Incentive(id, incentiveType, dealerId, startDate, endDate, title, description, disclaimer, carVinUUID, " +
                "rebateMapUUID) VALUES (?,?,?,?,?,?,?,?,?,?)";
        try {
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
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    // For Testing Purpose.
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        MsSqlDataProvider msSqlDataProvider = MsSqlDataProvider.getInstance();

        CashDiscountIncentive cashDiscountIncentive = createMockedCashDiscountIncentiveInstance();
        LoanIncentive loanIncentive = createMockedLoanIncentiveInstance();
        LeasingIncentive leasingIncentive = createMockedLeasingIncentiveInstance();
        RebateIncentive rebateIncentive = createMockedRebateIncentiveInstance();

        msSqlDataProvider.persistIncentive(cashDiscountIncentive);
        msSqlDataProvider.persistIncentive(loanIncentive);
        msSqlDataProvider.persistIncentive(leasingIncentive);
        msSqlDataProvider.persistIncentive(rebateIncentive);

        List<LoanIncentive> loanIncentiveList = msSqlDataProvider.getAllLoanIncentivesByCarVIN("05d391f1-d622-4dd0-bf9d-c1e3ed45a7a5");
        for (LoanIncentive incentive : loanIncentiveList) {
            System.out.println(incentive.getId());
        }

        List<RebateIncentive> rebateIncentiveList = msSqlDataProvider.getAllRebateIncentivesByCarVIN("2ebfdfce-514f-4a75-b10d-f76417611df2");
        for (RebateIncentive incentive : rebateIncentiveList) {
            System.out.println(incentive.getId());
        }
    }

    private static RebateIncentive createMockedRebateIncentiveInstance() {
        return new RebateIncentive(
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
    }

    private static LeasingIncentive createMockedLeasingIncentiveInstance() {
        return new LeasingIncentive(
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
    }

    private static LoanIncentive createMockedLoanIncentiveInstance() {
        return new LoanIncentive(
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
    }

    private static CashDiscountIncentive createMockedCashDiscountIncentiveInstance() {
        return new CashDiscountIncentive(
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
    }
}
