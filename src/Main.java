public class Main {

    public static void main(String[] args)
    {
        HashTable<String, Integer> map = new HashTable<>();
        map.add("this", 1);
        map.add("coder", 2);
        map.add("this", 4);
        map.add("hi", 5);
        System.out.println(map.get("this"));
        System.out.println(map.remove("coder"));
        System.out.println(map.remove("this"));
    }
}
