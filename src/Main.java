// Організувати хеш-таблицю, використовуючи хеш-функцію h (k) за методом множення для формування хеш-адреси, вирішення
// колізій - методом  ланцюгів.
// а) Написати  пошук і вставку за ключем.
// б) Написати метод видалення по ключу.

public class Main {

    public static void main(String[] args) {
        HashTable<String, Integer> map = new HashTable<>();
        map.add("this", 1);
        map.add("coder", 2);
        map.add("this", 4);
        map.add("hi", 5);
        System.out.println(map.get("this"));
        System.out.println(map.remove("coder"));
        System.out.println(map.remove("this"));
        System.out.println(map.remove("this"));
    }
}
