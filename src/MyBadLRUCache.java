class MyBadLRUCache {

    private class Node {
        int key;
        int val;
        Node next;

        public Node(Node next) {
            this.next = next;
        }

        public Node(int key, int val, Node next) {
            this.key = key;
            this.val = val;
            this.next = next;
        }
    }

    int capacity;
    Node root, last;

    public MyBadLRUCache(int capacity) {
        this.capacity = capacity;
        root = new Node(null);
    }

    public int get(int key) {
        Node prev = root;
        while (prev.next != null && prev.next.key != key) {
            prev = prev.next;
        }

        if (prev.next == null) {
            return -1;
        } else {
            last.next = prev.next;
            prev.next = prev.next.next;
            last = last.next;
            last.next = null;

            return last.val;
        }
    }

    public void put(int key, int value) {
        Node prev = root;
        while (prev.next != null && prev.next.key != key) {
            prev = prev.next;
        }

        if (prev.next == null) {
            createNewPair(key, value);
        } else {
            prev.next.val = value;

            last.next = prev.next;
            prev.next = prev.next.next;
            last = last.next;
            last.next = null;
        }
    }

    private void createNewPair(int key, int value) {
        Node newNode = new Node(key, value, null);
        if (capacity != 0) {

            if (root.next == null) {
                root.next = newNode;
                last = newNode;
            } else {
                last.next = newNode;
                last = last.next;
            }
            capacity -= 1;

        } else {
            last.next = newNode;
            last = last.next;

            root.next = root.next.next;
        }
    }
}

/**
 * Your MyBadLRUCache object will be instantiated and called as such:
 * MyBadLRUCache obj = new MyBadLRUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */
