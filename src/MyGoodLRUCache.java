import java.util.HashMap;

class MyGoodLRUCache {

    private class Node {
        int key;
        int value;

        Node prev;
        Node next;

        public Node() {
        }

        public Node(int key, int value, Node prev, Node next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }

    private static HashMap<Integer, Node> map;
    private static int capacity;
    private static Node HEAD, TAIL;

    public MyGoodLRUCache(int capacity) {
        this.capacity = capacity;
        HEAD = new Node();
        TAIL = new Node();
        HEAD.next = TAIL;
        TAIL.prev = HEAD;
        map = new HashMap<>();
    }

    public int get(int key) {
        if (map.containsKey(key)) {
            // получаем ноду из мапы
            Node temp = map.get(key);

            // вытаскиваем ноду из листа
            popNode(temp);

            // вставляем её в конец
            insertToTail(temp);

            return temp.value;
        } else {
            return -1;
        }
    }

    public void put(int key, int value) {
        Node newNode;

        if (map.containsKey(key)) {
            // берем уже существующую по ключу ноду
            newNode = map.get(key);
            newNode.value = value;

            // вытаскиваем ноду из листа
            popNode(newNode);

            // вставляем её в конец
            insertToTail(newNode);

        } else {
            // ноды с таким ключом не было, создаем новую
            newNode = new Node();
            newNode.key = key;
            newNode.value = value;

            // вставляем её в конец
            insertToTail(newNode);
            // добавляем новую в лист
            map.put(key, newNode);

            // если вместимость закончилась
            if (capacity == 0) {
                // удаляем ноду из мапы
                map.remove(HEAD.next.key);
                // удаляем самую старую ноду из листа
                HEAD.next = HEAD.next.next;
                HEAD.next.prev = HEAD;
            } else {
                capacity--;
            }
        }
    }

    // вставка ноды в конец (как последняя использованная)
    private static void insertToTail(Node newNode) {
        Node temp = TAIL.prev;

        temp.next = newNode;
        newNode.prev = temp;
        newNode.next = TAIL;
        TAIL.prev = newNode;
    }

    // вытаскиваем ноду из листа
    private static void popNode(Node temp) {
        Node prev = temp.prev;
        Node next = temp.next;
        prev.next = next;
        next.prev = prev;
    }
}

/**
 * Your MyGoodLRUCache object will be instantiated and called as such:
 * MyGoodLRUCache obj = new MyGoodLRUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */