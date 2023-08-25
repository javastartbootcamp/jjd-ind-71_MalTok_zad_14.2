package pl.javastart.task;

import java.io.*;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

class ControlStation {
    private static final int EXIT = 0;
    private static final int READ_AND_ADD_VEHICLE = 1;
    private static final int SERVE_AND_PRINT_VEHICLE = 2;
    private final Queue<Vehicle> vehicleQueue;
    private final FileManipulator fileManipulator;

    public ControlStation() {
        this.vehicleQueue = new LinkedList<>();
        this.fileManipulator = new FileManipulator();
    }

    public void mainLoop() {
        try (Scanner scanner = new Scanner(System.in)) {
            fileManipulator.getPreviousSession(vehicleQueue);
            int userOption;
            do {
                printOption();
                System.out.println("Wybierz opcję: ");
                userOption = scanner.nextInt();
                scanner.nextLine();
                evaluateOption(userOption);
            } while (userOption != EXIT);
        } catch (IOException e) {
            System.err.println("Błąd odczytu/zapisu pliku " + fileManipulator.getFile().getName());
        } catch (InputMismatchException e) {
            System.err.println("Wpisano niepoprawną wartość");
        }
    }

    private void printOption() {
        System.out.println(EXIT + " - wyjście z programu");
        System.out.println(READ_AND_ADD_VEHICLE + " - wczytanie informacji i dodanie pojazdu do kolejki");
        System.out.println(SERVE_AND_PRINT_VEHICLE + " - obsługa i informacje o pojeździe");
    }

    private void evaluateOption(int option) throws IOException {
        switch (option) {
            case EXIT -> exit();
            case READ_AND_ADD_VEHICLE -> readAndAddVehicle();
            case SERVE_AND_PRINT_VEHICLE -> serveAndPrintVehicle();
            default -> System.out.println("Błędna opcja, wybierz jeszcze raz");
        }
    }

    private void exit() throws IOException {
        if (vehicleQueue.isEmpty()) {
            System.out.println("Gratulacje! Koniec pracy.");
        } else {
            fileManipulator.writeQueueToFile(vehicleQueue);
            System.out.println("Zapisano stan kolejki");
        }
    }

    private void readAndAddVehicle() {
        Vehicle vehicle = getVehicleFromUser();
        vehicleQueue.offer(vehicle);
        System.out.println("Pomyślnie dodano pojazd do kolejki");
    }

    private void serveAndPrintVehicle() {
        Vehicle vehicleUnderService = vehicleQueue.poll();
        if (vehicleUnderService == null) {
            System.out.println("Brak pojazdów w kolejce");
        } else {
            System.out.println(vehicleUnderService);
            System.out.println("Pojazdów w kolejce: " + vehicleQueue.size());
        }
    }

    private Vehicle getVehicleFromUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Podaj typ pojazdu:");
        String type = scanner.nextLine();
        System.out.println("Podaj markę:");
        String brand = scanner.nextLine();
        System.out.println("Podaj model:");
        String model = scanner.nextLine();
        System.out.println("Podaj rok produkcji:");
        int year = scanner.nextInt();
        System.out.println("Podaj przebieg w kilometrach:");
        int mileage = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Podaj numer VIN:");
        String vin = scanner.nextLine();
        return new Vehicle(type, brand, model, year, mileage, vin);
    }
}
