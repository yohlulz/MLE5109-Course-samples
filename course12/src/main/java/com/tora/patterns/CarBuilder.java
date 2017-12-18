package com.tora.patterns;

/**
 * @author Ovidiu Maja<ovi.dan89@gmail.com>
 * @version Dec 06, 2017
 */
public class CarBuilder {
    private int wheels;
    private String color;

    /* force construction using a builder */
    private CarBuilder() {
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getColor() {
        return color;
    }

    public void setColor(final String color) {
        this.color = color;
    }

    public int getWheels() {
        return wheels;
    }

    public void setWheels(final int wheels) {
        this.wheels = wheels;
    }

    @Override
    public String toString() {
        return "Car [wheels = " + wheels + ", color = " + color + "]";
    }

    public static class Builder {
        private CarBuilder car;

        private Builder() {
            car = new CarBuilder();
        }

        public Builder color(final String color) {
            car.setColor(color);
            return this;
        }

        public Builder wheels(final int wheels) {
            car.setWheels(wheels);
            return this;
        }

        public CarBuilder build() {
            return car;
        }
    }

    public static void main(String[] args) {
        System.out.println(CarBuilder.builder()
                .color("red")
                .wheels(4)
                .build());
    }
}
