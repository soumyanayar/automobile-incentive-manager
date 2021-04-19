package dataprovider;

import entities.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class CsvDataProvider implements DataProvider {
    private final static String COMMA_DELIMITER = ",";
    private final static String PIPE_DELIMITTER = "|";

    private List<String> getRecordFromLine(String line) {
        List<String> values = new ArrayList<String>();
        try (Scanner rowScanner = new Scanner(line)) {
            rowScanner.useDelimiter(COMMA_DELIMITER);
            while (rowScanner.hasNext()) {
                values.add(rowScanner.next());
            }
        }
        return values;
    }

    @Override
    public List<Car> getAllCarsByDealerId(String dealerId) {
        String inputDataPath = System.getProperty("user.dir") + "\\src\\dataprovider\\mockedcsvdatabase\\Cars.csv";
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

    public void persistIncentive(CashDiscountIncentive cashDiscountIncentive) {
        String incentiveCSVFilePath = System.getProperty("user.dir") + "\\src\\dataprovider\\mockedcsvdatabase\\Incentive.csv";
        String incentiveVINsCSVFilePath = System.getProperty("user.dir") + "\\src\\dataprovider\\mockedcsvdatabase\\IncentiveVINs.csv";
        try {
            BufferedWriter incentiveBW = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(incentiveCSVFilePath, true), StandardCharsets.UTF_8));
            BufferedWriter incentiveVINsBW = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(incentiveVINsCSVFilePath, true), StandardCharsets.UTF_8));
            String incentiveVinUuid = UUID.randomUUID().toString();
            String incentiveLine =
                    cashDiscountIncentive.getIncentiveType() +
                            PIPE_DELIMITTER +
                            cashDiscountIncentive.getId() +
                            PIPE_DELIMITTER +
                            cashDiscountIncentive.getDealerId() +
                            PIPE_DELIMITTER +
                            cashDiscountIncentive.getStartDate() +
                            PIPE_DELIMITTER +
                            cashDiscountIncentive.getEndDate() +
                            PIPE_DELIMITTER +
                            cashDiscountIncentive.getTitle() +
                            PIPE_DELIMITTER +
                            cashDiscountIncentive.getDescription() +
                            PIPE_DELIMITTER +
                            cashDiscountIncentive.getDisclaimer() +
                            PIPE_DELIMITTER +
                            cashDiscountIncentive.getCashDiscountType() +
                            PIPE_DELIMITTER +
                            cashDiscountIncentive.getValue() +
                            PIPE_DELIMITTER +
                            incentiveVinUuid;

            incentiveBW.write(incentiveLine);
            incentiveBW.newLine();
            incentiveBW.flush();
            incentiveBW.close();

            for (String vin : cashDiscountIncentive.getCarVINList()) {
                String incentiveVinLine = incentiveVinUuid +
                        PIPE_DELIMITTER +
                        vin;

                incentiveVINsBW.write(incentiveVinLine);
                incentiveVINsBW.newLine();
            }

            incentiveVINsBW.flush();
            incentiveVINsBW.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void persistIncentive(LoanIncentive loanIncentive) {
        String incentiveCSVFilePath = System.getProperty("user.dir") + "\\src\\dataprovider\\mockedcsvdatabase\\Incentive.csv";
        String incentiveVINsCSVFilePath = System.getProperty("user.dir") + "\\src\\dataprovider\\mockedcsvdatabase\\IncentiveVINs.csv";
        try {
            BufferedWriter incentiveBW = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(incentiveCSVFilePath, true), StandardCharsets.UTF_8));
            BufferedWriter incentiveVINsBW = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(incentiveVINsCSVFilePath, true), StandardCharsets.UTF_8));
            String incentiveVinUuid = UUID.randomUUID().toString();
            String incentiveLine =
                    loanIncentive.getIncentiveType() +
                            PIPE_DELIMITTER +
                            loanIncentive.getId() +
                            PIPE_DELIMITTER +
                            loanIncentive.getDealerId() +
                            PIPE_DELIMITTER +
                            loanIncentive.getStartDate() +
                            PIPE_DELIMITTER +
                            loanIncentive.getEndDate() +
                            PIPE_DELIMITTER +
                            loanIncentive.getTitle() +
                            PIPE_DELIMITTER +
                            loanIncentive.getDescription() +
                            PIPE_DELIMITTER +
                            loanIncentive.getDisclaimer() +
                            PIPE_DELIMITTER +
                            loanIncentive.getApr() +
                            PIPE_DELIMITTER +
                            loanIncentive.getMonths() +
                            PIPE_DELIMITTER +
                            incentiveVinUuid;

            incentiveBW.write(incentiveLine);
            incentiveBW.newLine();
            incentiveBW.flush();
            incentiveBW.close();

            for (String vin : loanIncentive.getCarVINList()) {
                String incentiveVinLine = incentiveVinUuid +
                        PIPE_DELIMITTER +
                        vin;

                incentiveVINsBW.write(incentiveVinLine);
                incentiveVINsBW.newLine();
            }

            incentiveVINsBW.flush();
            incentiveVINsBW.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void persistIncentive(LeasingIncentive leasingIncentive) {
        String incentiveCSVFilePath = System.getProperty("user.dir") + "\\src\\dataprovider\\mockedcsvdatabase\\Incentive.csv";
        String incentiveVINsCSVFilePath = System.getProperty("user.dir") + "\\src\\dataprovider\\mockedcsvdatabase\\IncentiveVINs.csv";
        try {
            BufferedWriter incentiveBW = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(incentiveCSVFilePath, true), StandardCharsets.UTF_8));
            BufferedWriter incentiveVINsBW = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(incentiveVINsCSVFilePath, true), StandardCharsets.UTF_8));
            String incentiveVinUuid = UUID.randomUUID().toString();
            String incentiveLine =
                    leasingIncentive.getIncentiveType() +
                            PIPE_DELIMITTER +
                            leasingIncentive.getId() +
                            PIPE_DELIMITTER +
                            leasingIncentive.getDealerId() +
                            PIPE_DELIMITTER +
                            leasingIncentive.getStartDate() +
                            PIPE_DELIMITTER +
                            leasingIncentive.getEndDate() +
                            PIPE_DELIMITTER +
                            leasingIncentive.getTitle() +
                            PIPE_DELIMITTER +
                            leasingIncentive.getDescription() +
                            PIPE_DELIMITTER +
                            leasingIncentive.getDisclaimer() +
                            PIPE_DELIMITTER +
                            leasingIncentive.getMonths() +
                            PIPE_DELIMITTER +
                            leasingIncentive.getSigningPay() +
                            PIPE_DELIMITTER +
                            leasingIncentive.getMonthlyPay() +
                            PIPE_DELIMITTER +
                            incentiveVinUuid;

            incentiveBW.write(incentiveLine);
            incentiveBW.newLine();
            incentiveBW.flush();
            incentiveBW.close();

            for (String vin : leasingIncentive.getCarVINList()) {
                String incentiveVinLine = incentiveVinUuid +
                        PIPE_DELIMITTER +
                        vin;

                incentiveVINsBW.write(incentiveVinLine);
                incentiveVINsBW.newLine();
            }

            incentiveVINsBW.flush();
            incentiveVINsBW.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void persistIncentive(RebateIncentive rebateIncentive) {
        String incentiveCSVFilePath = System.getProperty("user.dir") + "\\src\\dataprovider\\mockedcsvdatabase\\Incentive.csv";
        String incentiveVINsCSVFilePath = System.getProperty("user.dir") + "\\src\\dataprovider\\mockedcsvdatabase\\IncentiveVINs.csv";
        String incentiveRebateMapFilePath = System.getProperty("user.dir") + "\\src\\dataprovider\\mockedcsvdatabase\\IncentiveRebates.csv";
        try {
            BufferedWriter incentiveBW = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(incentiveCSVFilePath, true), StandardCharsets.UTF_8));
            BufferedWriter incentiveVINsBW = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(incentiveVINsCSVFilePath, true), StandardCharsets.UTF_8));
            BufferedWriter incentiveRebatesBW = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(incentiveRebateMapFilePath, true), StandardCharsets.UTF_8));
            String incentiveVinUuid = UUID.randomUUID().toString();
            String incentiveRebateUuid = UUID.randomUUID().toString();
            String incentiveLine =
                    rebateIncentive.getIncentiveType() +
                            PIPE_DELIMITTER +
                            rebateIncentive.getId() +
                            PIPE_DELIMITTER +
                            rebateIncentive.getDealerId() +
                            PIPE_DELIMITTER +
                            rebateIncentive.getStartDate() +
                            PIPE_DELIMITTER +
                            rebateIncentive.getEndDate() +
                            PIPE_DELIMITTER +
                            rebateIncentive.getTitle() +
                            PIPE_DELIMITTER +
                            rebateIncentive.getDescription() +
                            PIPE_DELIMITTER +
                            rebateIncentive.getDisclaimer() +
                            PIPE_DELIMITTER +
                            incentiveVinUuid +
                            PIPE_DELIMITTER +
                            incentiveRebateUuid;

            incentiveBW.write(incentiveLine);
            incentiveBW.newLine();
            incentiveBW.flush();
            incentiveBW.close();

            for (String vin : rebateIncentive.getCarVINList()) {
                String incentiveVinLine = incentiveVinUuid +
                        PIPE_DELIMITTER +
                        vin;

                incentiveVINsBW.write(incentiveVinLine);
                incentiveVINsBW.newLine();
            }

            incentiveVINsBW.flush();
            incentiveVINsBW.close();

            for (Map.Entry<String, Double> mapElement : rebateIncentive.getRebateMap().entrySet()) {
                String incentiveRebateLine = incentiveRebateUuid +
                        PIPE_DELIMITTER +
                        mapElement.getKey() +
                        PIPE_DELIMITTER +
                        mapElement.getValue();

                incentiveRebatesBW.write(incentiveRebateLine);
                incentiveRebatesBW.newLine();
            }

            incentiveRebatesBW.flush();
            incentiveRebatesBW.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // For Test Purpose
    public static void main(String[] args) {
        new CsvDataProvider().getAllCarsByDealerId("E5301FBD-D4E1-4595-AC90-260228D681A1");
    }
}
