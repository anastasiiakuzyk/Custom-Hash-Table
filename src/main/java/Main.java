// Організувати хеш-таблицю, використовуючи хеш-функцію h (k) за методом множення для формування хеш-адреси, вирішення
// колізій - методом  ланцюгів.
// а) Написати  пошук і вставку за ключем.
// б) Написати метод видалення по ключу.

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.IOException;
import java.util.Date;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException, InvalidFormatException {

        System.out.println("Command options:");
        System.out.println("a: add new birthday");
        System.out.println("b: get all birthdays");
        System.out.println("c: get birthday by the name");
        System.out.println("d: remove birthday by the name");
        Scanner scan = new Scanner(System.in);
        String choice = null;
        do {
            System.out.print("Enter command(to end - q): ");
            choice = scan.nextLine();
            switch (choice) {
                case "a" -> {
                    System.out.print("\nEnter name to add: ");
                    String name = scan.nextLine();
                    System.out.print("\nEnter year of birth: ");
                    int year = Integer.parseInt(scan.nextLine()) - 1900; // year starts from 1990
                    System.out.print("\nEnter month(name) of birth: ");
                    String month = scan.nextLine();
                    int monthNum = month.contains("jan") ? 0 : month.contains("feb") ? 1 : month.contains("mar") ? 2 :
                            month.contains("apr") ? 3 : month.contains("may") ? 4 : month.contains("jun") ? 5 :
                                    month.contains("jul") ? 6 : month.contains("aug") ? 7 : month.contains("sep") ? 8 :
                                            month.contains("oct") ? 9 : month.contains("nov") ? 10 : month.contains("dec") ? 11 : 0;
                    System.out.print("\nEnter day of birth: ");
                    int day = scan.nextInt();
                    Date date = new Date(year, monthNum, day);
                    Table.writeIntoExcel(name, date);
                }
                case "b" -> Table.getTable("all", null);
                case "c" -> {
                    System.out.print("\nEnter name to get birthday: ");
                    String nameToGet = scan.nextLine();
                    Table.getTable("get", nameToGet);
                }
                case "d" -> {
                    System.out.print("\nEnter name to remove birthday: ");
                    String nameToRemove = scan.nextLine();
                    Table.getTable("remove", nameToRemove);
                }
            }
        } while (!choice.equals("q"));
    }
}
