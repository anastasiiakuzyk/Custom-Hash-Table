import java.util.ArrayList;
import java.util.Iterator;

public class HashTable <K, V> {
    private ArrayList<ArrayList<HashNode<K, V>>> bucketArray = new ArrayList<>();
    //Constant for hashing
    private final double A = 0.56;

    private int numBuckets = 100;

    private int size = 0;

    public HashTable() {
        for (int i = 0; i < numBuckets; i++)
            bucketArray.add(new ArrayList<>());
    }

    public int size() {
        return size;
    }
    public boolean isEmpty() {
        return size() == 0;
    }

    private int getBucketIndex(K key) {
        int hashCode = key.hashCode();
        int index = (int)Math.floor((hashCode*A % numBuckets));
        index = index < 0 ? index * -1 : index;
        return index;
    }

    public V remove(K key) {
        int bucketIndex = getBucketIndex(key);

        Iterator<HashNode<K, V>> it = bucketArray.get(bucketIndex).iterator();
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
        int bucketIndex = getBucketIndex(key);


        for (HashNode<K, V> search : bucketArray.get(bucketIndex))
            if (search.key.equals(key))
                return search.value;

        return null;
    }

    public void add(K key, V value) {
        int bucketIndex = getBucketIndex(key);
        ArrayList<HashNode<K, V>> possibleBucket = bucketArray.get(bucketIndex);
        possibleBucket.add(new HashNode<K, V>(key, value));
        size++;


        if ((1.0 * size) / numBuckets >= 0.8) {
            ArrayList<ArrayList<HashNode<K, V>>> tmp = bucketArray;
            bucketArray = new ArrayList<>();
            numBuckets = 2 * numBuckets;
            size = 0;
            for (int i = 0; i < numBuckets; i++)
                bucketArray.add(new ArrayList<>());

            for (ArrayList<HashNode<K, V>> bucket : tmp) {
                for (HashNode<K, V> node : bucket) {
                    add(node.key, node.value);
                }
            }
        }

    }


}