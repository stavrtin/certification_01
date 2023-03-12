import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;


public class Consol {
    /**
     * Метод передает приветствие и ожидает ввод пользователя в консоль
     * @param prompt Приветствие
     * @return Данные введеные пользователем в консоль
     */
    public String getUserInput(String prompt) {
//        String sUtf8 = new String(bytes, "UTF-8");
        String inputLine = null;
        System.out.print(prompt + "  ");
        try {
            BufferedReader is = new BufferedReader(new InputStreamReader(System.in));
            inputLine = is.readLine();
            if (inputLine.length() == 0 )  return null;
        } catch (IOException e) {
            System.out.println("IOException: " + e);
        }
        return inputLine;
    }

    /**
     * Метод заводит новую игрушку
     * @return Экземпляр игрушки
     */
    public static Toy newToy() {
        Consol helper = new Consol();
        String nameToy = "Игрушка";
        int cont = -1;
        int q = 0;
        int f = 0;
        while (cont < 0) {
            nameToy = helper.getUserInput("Введите название игрушки: ");
            if (nameToy != null) {
                cont = 1;
            } else {
                System.out.println("Ошибка ввода - Имя не может быть пустым.");
            }

        }

        cont = -1;
        while (cont < 0) {
            try {
                String quantityToy = helper.getUserInput("Введите количество игрушек (целое положительное число): ");
                q = Integer.parseInt(quantityToy);
                if (q > 0) {
                    cont = 1;
                } else {
                    System.out.println("Ошибка ввода - должно быть введено положительное число.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Ошибка ввода - введено не целое положительное число.");

            }
        }
        cont = -1;
        while (cont < 0) {
            try {
                String frequencyToy = helper.getUserInput("Введите частоту выпадения игрушек (целое число из дипазона 1 - 100): ");
                f = Integer.parseInt(frequencyToy);
                if (f > 0 & f < 101) {
                    cont = 1;
                } else {
                    System.out.println("Ошибка ввода - должно быть введено целое число из дипазона 1 - 100.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Ошибка ввода - введено не целое число из дипазона 1 - 100.");

            }
        }

        Toy r = new Toy(nameToy, q, f);
        Toy.listToy.add(r);  //Игрушку обязательно добавляем в список игрушек. В нем храняться все экземпляры игрушек.
//        Toy.createListToyWeighted(); //Создаем средневзвешенный генератор игрушек
//        Toy.getСhoice();
        return r;
    }

    /**
     * Стартовое меню
     * @return Выбранный пункт меню
     */
    public static int menu(){
        String menu = """
                
                Розыгрыш игрушек.
                1. Ввести новую игрушку
                2. Просмотреть все игрушки
                3. Редактировать игрушку
                4. Удалить игрушку
                5. Выдать призовую игрушку
                6. Завершить
                
                """;
        System.out.println(menu);
        Consol helper = new Consol();
        String point = helper.getUserInput("Введите номер пункта меню: ");
        try {
            if (Integer.parseInt(point)<1 | Integer.parseInt(point)>6 ){
                System.out.println("Введен некорректный пункт меню.");
                return -1;
            }

        } catch (NumberFormatException e) {
            System.out.println("Введен некорректный пункт меню.");
            return -1;
        }
        return Integer.parseInt(point);
    }

    /**
     * Фиксируем в файле с выданными призами начало работы программы
     */
    public static void startLog() {
        Date date = new Date();
        String START_RECORD = String.valueOf(date);
        FileWorker.write("---------------- " + START_RECORD + " ----------------------");
//        FileWorker.readT();
        if (FileWorker.readT() == 1) {        //Считываем тестовые данные из файла. При считывании создается массив Toy.listToy
            Toy.createListToyWeighted();      //Если считали файл успешно, то создаем генератор средневзвешенного списка игрушек на основании их веса
            Toy.makePrizelist();              //Создаем список призовых игрушек
        }

    }

    /**
     * Работа со стартовым меню.
     */
    public static void startMenu(){
        int w = -1;
        while (w < 0){

            switch (menu()){
                case 1: {
                    Consol.newToy();
                    Toy.createListToyWeighted();      //Создаем генератор средневзвешенного списка игрушек на основании их веса
                    Toy.makePrizelist();
                    break;
                }
                case 2:{
                    Toy.listToys();
                    break;
                }
                case 3:{
                    int e = Toy.getId();
                    if (e > 0){
                        Toy.editToy(e);
                        Toy.createListToyWeighted();      //Создаем новый генератор средневзвешенного списка игрушек на основании их веса на основании измененных данных
                        Toy.makePrizelist();
                    }
                    break;
                }
                case 4:{
                    int d = Toy.getId();
                    if (d > 0){
                        Toy.deleteToy(d);
                        Toy.createListToyWeighted();      //Создаем новый генератор средневзвешенного списка игрушек на основании их веса на основании измененных данных
                        Toy.makePrizelist();
                    }
                    break;
                }
                case 5: {
                    Toy.getPrizeToy();
                    break;
                }
                case 6:{
                    w = 0;
                    break;
                }
            }
        }
    }

}
