import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileWorker {

    public static void write(String toyName) {
        try (FileWriter writer = new FileWriter("Prize.txt", true)) {
            writer.write(toyName);
            writer.append('\n');

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Считываем тестовые данные из файла и вносим их в массив Toy.listToy.
     * @return Если успех возвращается 1, неуспех -1.
     */
    public static int readT() {
        String[] array;
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("listToys.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                array = line.split(";");
                if (controlString(array) == 1){
                    Toy r = new Toy(array[0],Integer.parseInt(array[1]),Integer.parseInt(array[2])); //Создаем экземпляр игрушки
                    Toy.listToy.add(r);                                                              //Добавляем экземпляр в список экземпляров игрушки

                }

            }
            if (Toy.listToy.size() > 0){
                System.out.println("Загружен тестовый набор игрушек из файла listToys.txt");
                System.out.println();
                System.out.println();
            }
        } catch (IOException e) {
            System.out.println("Нет возможности загрузить файл с примерами");
            return -1;
//            e.printStackTrace();
        }
        if (Toy.listToy.size() > 0){
            return 1;
        }
        return -1;
    }

    /**
     * Проверяет массив на корректность. Нулевой элемент String не проверяется, первый элемент должен быть целым положительным
     * числом больше ноля. Второй элемент должен быть целым чисом в диапазоне 1 - 100.
     * @param array Массив для проверки.
     * @return Возвращает -1 если проверка не пройдена и 1 если пройдена.
     */
    public static int controlString(String[] array) {
        if (array.length > 3| array.length < 3) {
            return -1;
        }
        if (array[0].length() ==0 | array[1].length() ==0 | array[2].length() == 0){
            return -1;
        }
        try {
            if (Integer.parseInt(array[1]) < 0){
                return -1;
            }
            if (Integer.parseInt(array[2]) < 1 & Integer.parseInt(array[2]) > 100) {//было if (Integer.parseInt(array[2]) < 0 & Integer.parseInt(array[2]) > 101)
                return -1;
            }
        } catch (NumberFormatException e) {
            return -1;
        }


        return 1;
    }
}