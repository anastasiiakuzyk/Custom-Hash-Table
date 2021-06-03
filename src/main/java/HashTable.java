import java.util.ArrayList;
import java.util.Iterator;

public class HashTable<K, V> {
    private ArrayList<ArrayList<HashNode<K, V>>> bucketArray = new ArrayList<>();

    //constant for hashing
    private final double A = 0.56;

    private int numBuckets; //buckets can repeat

    //amount of
    private int size = 0;

    //memory allocation for nodes
    public HashTable(int numBuckets) {
        this.numBuckets = numBuckets;
        for (int i = 0; i < numBuckets; i++) {
            bucketArray.add(new ArrayList<>());
        }
    }
    //memory allocation for nodes
    public HashTable() {
        this(10);
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    private int getHashCode(K key) {
        int hashCode = key.hashCode();
        int index = (int) Math.floor((hashCode * A % 1)) * numBuckets; //closest index
        if (index < 0) {
            index = index * -1;
        }
        return index; //hash code
    }

    public V remove(K key) {
        int hashCode = getHashCode(key);

        Iterator<HashNode<K, V>> it = bucketArray.get(hashCode).iterator();
        while (it.hasNext()) {
            HashNode<K, V> y = it.next();
            if (y.key.equals(key)) {
                it.remove();
                size--;
                return y.value;
            }
        }

        return null;
    }

    public V get(K key) {
        int hashCode = getHashCode(key);

        for (HashNode<K, V> search : bucketArray.get(hashCode)) {
            if (search.key.equals(key)) {
                return search.value;
            }
        }

        return null;
    }

    public void getAll() {
        for (ArrayList<HashNode<K, V>> hashNodes : bucketArray) {
            for (HashNode<K, V> next : hashNodes) {
                if (next != null) {
                    K key = next.key;
                    V value = next.value;
                    System.out.println(key + "`s birthday is " + value);
                }
            }
        }
    }

    public void add(K key, V value) {
        int hashCode = getHashCode(key);
        ArrayList<HashNode<K, V>> nodes = bucketArray.get(hashCode);
        nodes.add(new HashNode<>(key, value));
        size++;

        //expanding table
        if ((double) size / numBuckets >= 0.8) { // table is bigger than 80%
            ArrayList<ArrayList<HashNode<K, V>>> temp = bucketArray;
            bucketArray = new ArrayList<>();
            numBuckets = 2 * numBuckets;
            size = 0;
            for (int i = 0; i < numBuckets; i++) {
                bucketArray.add(new ArrayList<>());
            }

            for (ArrayList<HashNode<K, V>> bucket : temp) {
                for (HashNode<K, V> node : bucket) {
                    add(node.key, node.value);
                }
            }
        }
    }
}