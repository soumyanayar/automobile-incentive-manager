package dataprovider;

import entities.Car;
import entities.Incentive;

import java.util.List;

public interface DataProvider {
    List<Car> getAllCarsByDealerId(String dealerId);
    void persistIncentive(Incentive incentive);
}
