package Assignment.Assignment3;

import java.util.HashMap;
import java.util.Scanner;

public class A3 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int capacity = in.nextInt(); // 缓存容量
        int operations = in.nextInt(); // 操作数
        LRUCacheA3 cache = new LRUCacheA3(capacity);
        for (int i = 0; i < operations; i++) {
            String op = in.next();
            if (op.equals("put")) {
                int key = in.nextInt();
                int value = in.nextInt();
                cache.put(key, value);
            } else if (op.equals("get")) {
                int key = in.nextInt();
                System.out.println(cache.get(key));
            }
        }


    }
}

class DoubleNodeA3 {
    int key;
    int value;
    DoubleNodeA3 pre;
    DoubleNodeA3 next;

    public DoubleNodeA3(int key, int value) {
        this.key = key;
        this.value = value;
    }

    public void remove() {
        this.pre.next = this.next;
        this.next.pre = this.pre;
    }

    public void addFirst(DoubleNodeA3 node) {
        node.next = this.next;
        node.pre = this;
        this.next.pre = node;
        this.next = node;
    }

    @Override
    public String toString() {
        return "DoubleNode{" + "key=" + key + ", value=" + value + '}';
    }
}

class LRUCacheA3 {
    final int capacity;
    int size;//当前缓存大小
    DoubleNodeA3 head;
    DoubleNodeA3 tail;
    HashMap<Integer, DoubleNodeA3> map;

    public LRUCacheA3(int capacity) {
        this.capacity = capacity;
        this.size = 0;
        this.head = new DoubleNodeA3(-1, -1);
        this.tail = new DoubleNodeA3(0, 0);
        head.next = tail;
        tail.pre = head;
        map = new HashMap<>();
    }


    public int get(int key) {
        DoubleNodeA3 node = map.get(key);
        int value = -1;
        if (node == null) {
            value = -1;

        } else {
            value = node.value;
            node.remove();
            head.addFirst(node);
        }
        return value;
    }

    public void put(int key, int value) {
        if (map.containsKey(key)) {
            DoubleNodeA3 node = map.get(key);
            node.remove();
            node.value = value;
            head.addFirst(node);
        } else {
            if (size == capacity) {
                map.remove(tail.pre.key);
                tail.pre.remove();
                size--;
            }
            DoubleNodeA3 newNode = new DoubleNodeA3(key, value);
            head.addFirst(newNode);
            map.put(key, newNode);
            size++;


        }

    }
}

