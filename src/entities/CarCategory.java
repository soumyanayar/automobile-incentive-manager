package entities;

public enum CarCategory {
    USED ("Used"),
    NEW ("New"),
    CERTIFIEDPREOWNED ("Certified Pre-Owned");

    private String category;

    CarCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString(){
        return this.category;
    }

    public static CarCategory fromString(String text) {
        for (CarCategory carCategory : CarCategory.values()) {
            if (carCategory.category.equalsIgnoreCase(text)) {
                return carCategory;
            }
        }
        return null;
    }
}
