public class MyHashMap<K, V> {

    private static class Node<K, V> {
        final int hash;
        final K key;
        V value;
        Node<K, V> next;

        Node(int hash, K key, V value, Node<K, V> next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    private static final int DEFAULT_INITIAL_CAPACITY = 16;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;

    private Node<K, V>[] buckets;
    private int size;
    private int threshold;
    private final float loadFactor;

    public MyHashMap() {
        this(DEFAULT_INITIAL_CAPACITY, DEFAULT_LOAD_FACTOR);
    }

    public MyHashMap(int initialCapacity, float loadFactor) {
        if (initialCapacity <= 0)
            throw new IllegalArgumentException("Начальная ёмкость должна быть > 0");
        if (loadFactor <= 0 || Float.isNaN(loadFactor))
            throw new IllegalArgumentException("Коэффициент загрузки должен быть положительным числом");

        this.loadFactor = loadFactor;
        int capacity = 1;
        while (capacity < initialCapacity)
            capacity <<= 1;

        this.buckets = (Node<K, V>[]) new Node[capacity];
        this.threshold = (int) (capacity * loadFactor);
    }


    private int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }

    private int indexFor(int hash, int length) {
        return hash & (length - 1);
    }

    private void resize() {
        Node<K, V>[] oldBuckets = buckets;
        int oldCapacity = oldBuckets.length;
        int newCapacity = oldCapacity << 1;

        if (newCapacity < 0)
            throw new OutOfMemoryError("Ёмкость HashMap превысила максимально допустимую");

        Node<K, V>[] newBuckets = (Node<K, V>[]) new Node[newCapacity];

        for (Node<K, V> head : oldBuckets) {
            while (head != null) {
                Node<K, V> next = head.next;
                int newIdx = indexFor(head.hash, newCapacity);
                head.next = newBuckets[newIdx];
                newBuckets[newIdx] = head;
                head = next;
            }
        }

        buckets = newBuckets;
        threshold = (int) (newCapacity * loadFactor);
    }

    public V put(K key, V value) {
        int hash = hash(key);
        int idx = indexFor(hash, buckets.length);
        Node<K, V> node = buckets[idx];


        while (node != null) {
            if (node.hash == hash && (node.key == key || (key != null && key.equals(node.key)))) {
                V oldValue = node.value;
                node.value = value;
                return oldValue;
            }
            node = node.next;
        }

        Node<K, V> newNode = new Node<>(hash, key, value, buckets[idx]);
        buckets[idx] = newNode;
        size++;

        if (size > threshold) {
            resize();
        }
        return null;
    }

    public V get(K key) {
        int hash = hash(key);
        int idx = indexFor(hash, buckets.length);
        Node<K, V> node = buckets[idx];

        while (node != null) {
            if (node.hash == hash && (node.key == key || (key != null && key.equals(node.key)))) {
                return node.value;
            }
            node = node.next;
        }
        return null;
    }

    public V remove(K key) {
        int hash = hash(key);
        int idx = indexFor(hash, buckets.length);
        Node<K, V> prev = null;
        Node<K, V> curr = buckets[idx];

        while (curr != null) {
            if (curr.hash == hash && (curr.key == key || (key != null && key.equals(curr.key)))) {
                if (prev == null) {
                    buckets[idx] = curr.next;
                } else {
                    prev.next = curr.next;
                }
                size--;
                return curr.value;
            }
            prev = curr;
            curr = curr.next;
        }
        return null;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }
}