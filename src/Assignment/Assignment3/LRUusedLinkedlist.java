//package Assignment.Assignment3;
//
//import java.util.*;
//
//public class LRUusedLinkedlist {
//    public static void main(String[] args) {
//        Scanner sc = new Scanner(System.in);
//        int capacity = sc.nextInt(); // 缓存容量
//        int operations = sc.nextInt(); // 操作数
//        LRUCache cache = new LRUCache(capacity);
//
//        while (operations-- > 0) {
//            String op = sc.next();
//            if (op.equals("put")) {
//                int key = sc.nextInt();
//                int value = sc.nextInt();
//                cache.put(key, value);
//            } else if (op.equals("get")) {
//                int key = sc.nextInt();
//                System.out.println(cache.get(key));
//            }
//        }
//    }
//
//    static class LRUCache extends LinkedHashMap<Integer, Integer> {
//        private final int capacity;
//
//        public LRUCache(int capacity) {
//            super(capacity, 0.75f, true); // true 表示按访问顺序
//            this.capacity = capacity;
//        }
//
//        public int get(int key) {
//            return super.getOrDefault(key, -1);
//        }
//
//        public void put(int key, int value) {
//            super.put(key, value);
//        }
//
//        @Override
//        protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest) {
//            return size() > capacity; // 超出容量自动移除最旧的
//        }
//    }
//}
