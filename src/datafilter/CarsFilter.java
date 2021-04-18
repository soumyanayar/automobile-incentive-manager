package datafilter;

import entities.Car;
import entities.CarCategory;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CarsFilter {
    public static Predicate<Car> isCarPriceGreaterThanEqualTo(double price) {
        return car -> car.getMSRP() >= price;
    }

    public static Predicate<Car> isCarPriceLesserThanEqualTo(double price) {
        return car -> car.getMSRP() <= price;
    }

    public static Predicate<Car> isCarCategoryEqualTo(CarCategory carCategory) {
        return car -> car.getCarCategory().equals(carCategory);
    }

    public static Predicate<Car> isCarModelEqual(String model) {
        return car -> car.getModel().equalsIgnoreCase(model);
    }

    public static Predicate<Car> isCarMakeEqual(String make) {
        return car -> car.getMake().equalsIgnoreCase(make);
    }

    public static Predicate<Car> isCarYearGreaterThanOrEqualTo(int year) {
        return car -> car.getYear() >= year;
    }

    public static Predicate<Car> isCarYearLesserThanOrEqualTo(int year) {
        return car -> car.getYear() <= year;
    }

    public static Predicate<Car> isCarVINEqualTo(String VIN) {
        return car -> car.getVIN().equalsIgnoreCase(VIN);
    }

    public static Predicate<Car> isCarMileageGreaterThanOrEqualTo(int mileage) {
        return car -> car.getMileage() >= mileage;
    }

    public static Predicate<Car> isCarMileageLesserThanOrEqualTo(int mileage) {
        return car -> car.getMileage() <= mileage;
    }

    public static List<Car> ApplyFilters(List<Car> cars, List<Predicate<Car>> filterList) {
        return cars.stream()
                .filter(filterList.stream().reduce(x->true, Predicate::and))
                .collect(Collectors.toList());
    }
}
