package pl.javastart.task;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

class FileManipulator {
    private static final String FILE_PATH = "queue.csv";
    private final File file;

    public FileManipulator() {
        this.file = new File(FILE_PATH);
    }

    public File getFile() {
        return file;
    }

    public void getPreviousSession(Queue<Vehicle> queue) throws IOException {
        if (file.exists()) {
            List<Vehicle> vehicleList = createVehiclesListFromFile();
            if (!vehicleList.isEmpty()) {
                queue.addAll(vehicleList);
            }
            Files.deleteIfExists(Path.of(FILE_PATH));
        }
    }

    public List<Vehicle> createVehiclesListFromFile() throws FileNotFoundException {
        LinkedList<Vehicle> vehiclesList;
        try (
                Scanner scanner = new Scanner(file)
        ) {
            vehiclesList = new LinkedList<>();
            while (scanner.hasNextLine()) {
                String nextLine = scanner.nextLine();
                String[] split = nextLine.split(";");
                String type = split[0];
                String brand = split[1];
                String model = split[2];
                int year = Integer.parseInt(split[3]);
                int mileage = Integer.parseInt(split[4]);
                String vin = split[5];
                vehiclesList.add(new Vehicle(type, brand, model, year, mileage, vin));
            }
        }
        return vehiclesList;
    }

    public void writeQueueToFile(Queue<Vehicle> queue) throws IOException {
        try (
                var bufferedWriter = new BufferedWriter(new FileWriter(file, false))
        ) {
            for (Vehicle vehicle : queue) {
                bufferedWriter.write(vehicle.getOutputFormat());
            }
        }
    }

}
