import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


class Toy {
    private int id;
    private String name;
    private int quantity;
    private int weight;

    public Toy(int id, String name, int quantity, int weight) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.weight = weight;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}

class ToyStore {
    private List<Toy> toys = new ArrayList<>();

    public void addNewToy(Toy toy) {
        toys.add(toy);
    }

    public Toy drawToy() {
        Random rand = new Random();
        int totalWeight = toys.stream().mapToInt(Toy::getWeight).sum();
        int randomWeight = rand.nextInt(totalWeight);

        int cumulativeWeight = 0;
        for (Toy toy : toys) {
            cumulativeWeight += toy.getWeight();
            if (randomWeight < cumulativeWeight) {
                toy.setWeight(0);
                return toy;
            }
        }

        return null;
    }

    public void writeToFile(Toy toy) {
        try {
            FileWriter writer = new FileWriter("prizes.txt", true);
            writer.write(toy.getName() + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

public class ToyStoreApp {
    public static void main(String[] args) {
        ToyStore toyStore = new ToyStore();
        toyStore.addNewToy(new Toy(1, "Кукла", 10, 30));
        toyStore.addNewToy(new Toy(2, "Машинка", 20, 40));
        toyStore.addNewToy(new Toy(3, "Мячик", 15, 30));

        Toy winningToy = toyStore.drawToy();
        if (winningToy != null) {
            toyStore.writeToFile(winningToy);
            System.out.println("Вы выиграли игрушку: " + winningToy.getName());
        } else {
            System.out.println("Игрушки закончились.");
        }
    }
}