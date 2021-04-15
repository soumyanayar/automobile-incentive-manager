package validators;

import java.util.regex.Pattern;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;

public class IncentiveDataValidator {
    // check null or empty data
    public static boolean isNullOrEmpty(String content) {
        return (content == null || content.trim().isEmpty());
    }

    public static boolean isNull(Object content) {
        return (content == null);
    }

    // check if the string contains only digits and a dot.
    private boolean isOnlyDigitsAndDot(String arr) {

        return Pattern.matches("^\\d+(?:\\.\\d+)?$", arr);
    }

    // check if the string contains only digits, letters, -, and space.
    private boolean containsSpecialCharacters(String text) {
        return !Pattern.matches("[a-zA-Z0-9 \\-]*", text);
    }

    // check date
    private static boolean isValidDate(String strDate) throws ParseException {
        String[] time = strDate.split("/");

        if (time.length != 3)
            return false;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Date date = sdf.parse(strDate);
        String str = sdf.format(date);
        if (str.equals(strDate))
            return true;
        else
            return false;
    }

    public static Date parseDateFromString(String strDate) throws ParseException {
        String[] time = strDate.split("/");

        if (time.length != 3)
            return null;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Date date = sdf.parse(strDate);
        String str = sdf.format(date);
        if (str.equals(strDate))
            return date;
        else
            return null;
    }

    public boolean validateStartDate(String startDate) throws ParseException {
        return isValidDate(startDate);
    }

    // start date should be earlier than end date
    public String validateEndDate(String startDate, String endDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

        if (sdf.parse(startDate).getTime() <= sdf.parse(endDate).getTime())
            return "True";
        else
            return "End date should not be earlier than start date.";
    }

    // discount
    // cash > 0
    public String validateCashDiscount(String cashDiscount) {
        if (isNullOrEmpty(cashDiscount)) {

            return "Discount is empty.";
        }

        try {
            double discount = Double.parseDouble(cashDiscount);

            if (discount < 0) {
                return "Cash value should be positive.";
            }
            return "True";
        } catch (Exception e) {
            return "Discount should only contain numbers.";
        }
    }

    // 0 < percentage < 100
    public String validatePercentageDiscount(String percentageDiscount) {
        if (isNullOrEmpty(percentageDiscount)) {
            return "Discount is empty ";
        }

        try {
            double discount = Double.parseDouble(percentageDiscount);

            if (discount < 0 || discount > 100) {
                return "Percentage value should be greater than 0 and smaller than 100";
            }
            return "true";
        } catch (Exception e) {
            return "Discount should only contain numbers.";
        }
    }

    // should not be empty
    public String validateTitle(String title) {
        if (isNullOrEmpty(title))
            return "Title is empty.";
        else
            return "true";
    }

    public String validateDescription(String description) {
        if (isNullOrEmpty(description))
            return "Description is empty ";
        else
            return "true";
    }

    public String validateDisclaimer(String disclaimer) {
        if (isNullOrEmpty(disclaimer))
            return "disclaimer is empty ";
        else
            return "true";
    }

    public String validateMakeName(String makeName) {
        if (containsSpecialCharacters(makeName))
            return "Make name contains only digits, letters, -, and space.";
        else if (isNullOrEmpty(makeName))
            return "Make name is null or empty.";
        else
            return "true";
    }

    public String validateModelName(String modelName) {
        if (containsSpecialCharacters(modelName))
            return "Model name contains only digits, letters, -, and space.";
        else if (isNullOrEmpty(modelName))
            return "Model name is null or empty.";
        else
            return "true";
    }

    public String validatePrice(String price) {
        if (Double.parseDouble(price) < 0)
            return "Price should be positive.";
        else if (!isOnlyDigitsAndDot(price))
            return "Price should be a valid number.";
        else if (isNullOrEmpty(price))
            return "Price is null or empty.";
        else
            return "true";
    }

    public String validateMillage(String millage) {
        if (Double.parseDouble(millage) < 0)
            return "Millage should be positive.";
        else if (!isOnlyDigitsAndDot(millage))
            return "Millage should be a valid number.";
        else if (isNullOrEmpty(millage))
            return "Millage is null or empty.";
        else
            return "true";
    }
}
