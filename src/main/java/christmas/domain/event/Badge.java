package christmas.domain.event;

public enum Badge {

    NON(0, "없음"),
    STAR(5_000, "별"),
    TREE(10_000, "트리"),
    SANTA(20_000, "산타");

    private final Integer threshold;
    private final String name;

    public static Badge of(int benefitAmount) {
        if (benefitAmount >= SANTA.threshold) {
            return SANTA;
        }
        if (benefitAmount >= TREE.threshold) {
            return TREE;
        }
        if (benefitAmount >= STAR.threshold) {
            return STAR;
        }
        return NON;
    }

    public String getName() {
        return name;
    }

    private Badge(int threshold, String name) {
        this.threshold = threshold;
        this.name = name;
    }
}
