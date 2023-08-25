package pl.javastart.task;

class Vehicle {
    private final String type;
    private final String brand;
    private final String model;
    private final int year;
    private final int mileage;
    private final String vin;

    public Vehicle(String type, String brand, String model, int year, int mileage, String vin) {
        this.type = type;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.mileage = mileage;
        this.vin = vin;
    }

    @Override
    public String toString() {
        return type + " " + brand + " " + model + ", rocznik: " + year + ", przebieg: " + mileage + ", VIN: " + vin;
    }

    public String getOutputFormat() {
        return String.format("%s;%s;%s;%d;%d;%s\n", type, brand, model, year, mileage, vin);
    }
}
