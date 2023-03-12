import java.util.ArrayList;
import java.util.List;

public class Toy {
    public static List<Toy> prizeList = new ArrayList<>(); //Призовой список экземпляров игрушек
    public static List<Toy> listToy = new ArrayList<>(); //Все заведенные экземпляры игрушек

    static RandomCollection<Object> rc = new RandomCollection<>(); //Экземпляр генератора средневзвешенного списка
    private String name;
    private int toyID = 1;
    private static int countID;
    {
        countID += 1;
    }
    private int quantity;
    private int frequency;
    private int remain;
    public Toy(String name, int quantity, int frequency){
        this.name = name;
        this.frequency = frequency;
        this.quantity = quantity;
        this.remain = quantity;
        this.toyID = countID;
    }


    public String getInfo() {
        return "ID: " + this.toyID + ". Наименование: " + this.name + ", количество: " + this.quantity + ", частота: " + this.frequency+".";
    }

    public void setToyID(int toyID) {
        this.toyID = toyID;
    }

    public int getRemain() {
        return remain;
    }

    public void setRemain(int remain) {
        this.remain = remain;
    }

    public String getName() {
        return name;
    }

    public int gettoyID() {
        return toyID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getFrequency() {
        return frequency;
    }


    /**
     * Расчет количества игрушек во всех экземплярах игрушек
     * @return Количество игрушек
     */
    static public int ctnToys(){
        int fullNumber = 0;
        for (int i = 0; i < listToy.size(); i++) {
            fullNumber += listToy.get(i).getQuantity();
        }
        return fullNumber;
    }



    /**
     * Создает средневзвешенный список экземпляров призовых игрушек.
     *
     */
    static public void makePrizelist() {
        prizeList.clear();
        int k = Toy.ctnToys();
        Toy t;
        for (int i = 0; i < Toy.listToy.size() ; i++) {
            Toy.listToy.get(i).setRemain(Toy.listToy.get(i).getQuantity());
        }
        while (k >= 1){                     //Пока не распределим все игрушки
            t = (Toy) rc.next();
            if (t.getRemain() > 0){         //Если остались не распределенные игрушки в экземпляре.
                prizeList.add(t);
                t.setRemain(t.getRemain() - 1);
                k -=1;
            }
        }
    }

    /**
     * Выдать призовую игрушку из массива призовых игрушек
     */
    static public void getPrizeToy(){
        if (prizeList.size() > 0){
            Toy t = prizeList.get(0);
            if (t.getQuantity() > 0){
                t.setQuantity(t.getQuantity()-1);
                FileWorker.write(t.getName());
                System.out.println("Игрушка: \""+ t.getName() + "\" успешно сохранена в файл");
                prizeList.remove(0);
            }

        } else {
            System.out.println("Призовые игрушки закончилсь");
        }

    }

    /**
     * Создание генератора средневзвешенного списка игрушек на основании их веса
     */
    public static void createListToyWeighted(){
        rc = null;
        rc = new RandomCollection<>();
        for (int i = 0; i < Toy.listToy.size(); i++) {
            Toy.rc.add(Toy.listToy.get(i).getFrequency(), Toy.listToy.get(i));
        }

    }

    /**
     * Запрос ID игрушки. Если ID не найдено или ввод не корректный возвращается -1
     * @return ID игрушки или ошибка.
     */
    public static int getId(){
        Consol helper = new Consol();
//        int cont = -1;
        int q = 0;

//        while (cont < 0) {
        try {
            String idToy = helper.getUserInput("Введите ID игрушки: ");
            q = Integer.parseInt(idToy);
            int f = -1;
            if (q > 0) {
                for (int i = 0; i < Toy.listToy.size(); i++) {
                    if (Toy.listToy.get(i).gettoyID() == q) {
                        f = 1;
                    }
                }
                if (f == 1) {
                    return q;
                } else {
                    System.out.println("Игрушки с ID: \"" + q + "\" не найдено. ");
                    return -1;
                }
            }else {
                System.out.println("Ошибка ввода - должно быть введено положительное число больше нуля.");
                return -1;
            }
        } catch (NumberFormatException e) {
            System.out.println("Ошибка ввода - введено не целое положительное число.");
            return -1;
        }
//        }
    }

    /**
     * Удаление игрушки по ID.
     * @param id ID игрушки.
     */
    public static void deleteToy(int id){
        for (int i = 0; i < Toy.listToy.size(); i++) {
            if (Toy.listToy.get(i).gettoyID() == id){
                Toy deletedToy = Toy.listToy.get(i);
                Toy.listToy.remove(i);
                System.out.println("Успешно удалена игрушка: " + deletedToy.getInfo());
                deletedToy = null;
            }

        }
    }

    /**
     * Редактирование игрушки по ID.
     * Экземпляр игрушки с введеным ID  удаляется, заводится новый экземпляр и ему присваивается ID старой игрушки
     * @param id ID игрушки.
     */
    public static void editToy(int id){
        for (int i = 0; i < Toy.listToy.size(); i++) {
            if (Toy.listToy.get(i).gettoyID() == id){
                Toy deletedToy = Toy.listToy.get(i);
                Toy.listToy.remove(i);
                deletedToy = null;
                Consol.newToy().setToyID(id);
                break;
            }

        }
    }

    /**
     * Просмотр списка игрушек
     */
    public static void listToys(){
        for (int i = 0; i < Toy.listToy.size(); i++) {
            System.out.println(Toy.listToy.get(i).getInfo());
        }
        if (Toy.listToy.size() == 0){
            System.out.println("Список игрушек пуст");
        }
    }

}