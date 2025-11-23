class FenwickTree {
    private int[] tree;
    private int n;

    public void build(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("Исходный массив не может быть null.");
        }
        if (arr.length == 0) {
            throw new IllegalArgumentException("Исходный массив не может быть пустым.");
        }

        n = arr.length;
        tree = new int[n];

        for (int i = 0; i < n; i++) {
            update(i, arr[i]);
        }
    }

    private void checkBuilt() {
        if (tree == null) {
            throw new IllegalStateException("Дерево ещё не построено. Сначала вызовите build().");
        }
    }

    public void update(int index, int delta) {
        checkBuilt();
        if (index < 0 || index >= n) {
            throw new IndexOutOfBoundsException("Индекс вне диапазона: " + index);
        }

        while (index < n) {
            tree[index] += delta;
            index = index | (index + 1);
        }
    }

    public int prefixSum(int index) {
        checkBuilt();
        if (index < 0 || index >= n) {
            throw new IndexOutOfBoundsException("Индекс вне диапазона: " + index);
        }

        int result = 0;
        while (index >= 0) {
            result += tree[index];
            index = (index & (index + 1)) - 1;
        }
        return result;
    }

    public int rangeSum(int left, int right) {
        checkBuilt();
        if (left < 0 || right < 0 || left >= n || right >= n) {
            throw new IndexOutOfBoundsException(
                    "Границы вне диапазона: left = " + left + ", right = " + right
            );
        }
        if (left > right) {
            throw new IllegalArgumentException("Левая граница больше правой: " + left + " > " + right);
        }

        if (left == 0) {
            return prefixSum(right);
        }
        return prefixSum(right) - prefixSum(left - 1);
    }

    public void printTree() {
        checkBuilt();

        System.out.print("Текущее дерево Fenwick: [");
        for (int i = 0; i < n; i++) {
            System.out.print(tree[i]);
            if (i < n - 1) System.out.print(", ");
        }
        System.out.println("]");
    }

}