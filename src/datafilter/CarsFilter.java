package datafilter;

import entities.Car;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

public class CarsFilter {
    public static Predicate<Car> isCarGreaterThanEqualTo(double price) {
        return car -> car.getMSRP() >= price;
    }

    public static Predicate<Car> isCarSmallerThanEqualTo(double price) {
        return car -> car.getMSRP() <= price;
    }

    public void ApplyFilters(List<Car> cars) {
        cars.stream()
                .filter(isCarGreaterThanEqualTo(10))
                .forEach((car) -> System.out.println(car.getModel() + " " + car.getMSRP()));
    }
}
