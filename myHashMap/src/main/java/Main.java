public class Main {
    public static void main(String[] args) {
        MyHashMap<String, Integer> map = new MyHashMap<>();

        map.put("apple", 10);
        map.put("banana", 20);
        map.put("orange", 30);
        System.out.println("size: " + map.size());
        System.out.println("get apple: " + map.get("apple"));

        map.put("apple", 100);
        System.out.println("new apple: " + map.get("apple"));

        map.remove("banana");
        System.out.println("after remove banana: " + map.get("banana"));
        System.out.println("size: " + map.size());

        map.put(null, 999);
        map.put("special", null);
        System.out.println("get null: " + map.get(null));
        System.out.println("get special: " + map.get("special"));

        map.put(null, 777);
        System.out.println("new null: " + map.get(null));
        map.remove(null);
        System.out.println("after remove null: " + map.get(null));

        MyHashMap<String, String> collMap = new MyHashMap<>(4, 0.75f);
        collMap.put("Aa", "first");
        collMap.put("BB", "second");
        System.out.println("collision Aa: " + collMap.get("Aa"));
        System.out.println("collision BB: " + collMap.get("BB"));

        collMap.put("Aa", "changed");
        System.out.println("changed Aa: " + collMap.get("Aa"));
        collMap.remove("BB");
        System.out.println("after remove BB: " + collMap.get("BB"));

        MyHashMap<Integer, Integer> grow = new MyHashMap<>(2, 0.75f);
        for (int i = 1; i <= 5; i++) grow.put(i, i * 10);
        for (int i = 1; i <= 5; i++)
            System.out.println("get(" + i + ") = " + grow.get(i));
    }
}