package lpnu.model;

public enum EnumTechnology {
    TwoD(0),
    ThreeD(50),
    FourDX(100);

    private final double price;

    EnumTechnology(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }
}
