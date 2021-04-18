package dataprovider;

import entities.Car;
import entities.CarCategory;
import entities.Incentive;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CsvDataProvider implements DataProvider {
    private final static String COMMA_DELIMITER = ",";

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
        String inputDataPath = System.getProperty("user.dir") + "\\src\\dataprovider\\Cars.csv";
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
    public void persistIncentive(Incentive incentive) {

    }

    public static void main(String[] args) {
        new CsvDataProvider().getAllCarsByDealerId("E5301FBD-D4E1-4595-AC90-260228D681A1");
    }
}
