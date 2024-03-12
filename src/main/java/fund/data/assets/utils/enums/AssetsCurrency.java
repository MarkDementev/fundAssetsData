package fund.data.assets.utils.enums;

public enum AssetsCurrency {
    RUSRUB("Russian ruble");

    private final String title;

    AssetsCurrency(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}