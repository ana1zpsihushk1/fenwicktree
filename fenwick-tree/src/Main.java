import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        FenwickTree fenwick = new FenwickTree();

        int n = 0;

        while (true) {
            System.out.print("Введите размер массива (> 0): ");
            try {
                n = scanner.nextInt();
                if (n <= 0) {
                    System.out.println("Ошибка: размер должен быть положительным.");
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("Ошибка: ожидается целое число.");
                scanner.nextLine();
            }
        }

        scanner.nextLine();

        int[] arr = new int[n];

        while (true) {
            System.out.println("Введите массив чисел длиной " + n + " в формате [1, 2, 3]:");

            String line = scanner.nextLine().trim();

            if (!line.startsWith("[") || !line.endsWith("]")) {
                System.out.println("Ошибка: массив должен начинаться с '[' и заканчиваться ']'.");
                continue;
            }

            String inside = line.substring(1, line.length() - 1).trim();

            if (inside.isEmpty()) {
                System.out.println("Массив построен: " + formatArray(arr));
                break;
            }

            String[] parts = inside.split(",");
            int count = Math.min(parts.length, n);

            boolean valid = true;

            for (int i = 0; i < count; i++) {
                String p = parts[i].trim();
                try {
                    arr[i] = Integer.parseInt(p);
                } catch (NumberFormatException e) {
                    System.out.println("Ошибка: '" + p + "' не является целым числом.");
                    valid = false;
                    break;
                }
            }

            if (!valid) continue;

            System.out.println("Массив построен: " + formatArray(arr));
            break;
        }

        try {
            fenwick.build(arr);
            System.out.println("Дерево Фенвика успешно построено.");
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка при построении дерева: " + e.getMessage());
            scanner.close();
            return;
        }

        while (true) {
            System.out.println();
            System.out.println("Меню:");
            System.out.println("1 - обновить элемент (arr[index] += delta)");
            System.out.println("2 - префиксная сумма [0..index]");
            System.out.println("3 - сумма на отрезке [left..right]");
            System.out.println("4 - вывести текущее дерево Fenwick");
            System.out.println("0 - выход");
            System.out.print("Ваш выбор: ");

            int command;
            try {
                command = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Ошибка: команда должна быть целым числом.");
                scanner.nextLine();
                continue;
            }

            if (command == 0) {
                System.out.println("Завершение работы.");
                break;
            }

            try {
                switch (command) {
                    case 1: {
                        System.out.print("Введите индекс (0.." + (n - 1) + "): ");
                        int index = scanner.nextInt();
                        System.out.print("Введите delta: ");
                        int delta = scanner.nextInt();
                        fenwick.update(index, delta);
                        System.out.println("Элемент обновлён.");
                        break;
                    }
                    case 2: {
                        System.out.print("Введите индекс (0.." + (n - 1) + "): ");
                        int index = scanner.nextInt();
                        int sum = fenwick.prefixSum(index);
                        System.out.println("Сумма на префиксе [0.." + index + "] = " + sum);
                        break;
                    }
                    case 3: {
                        System.out.print("Введите left (0.." + (n - 1) + "): ");
                        int left = scanner.nextInt();
                        System.out.print("Введите right (0.." + (n - 1) + "): ");
                        int right = scanner.nextInt();
                        int sum = fenwick.rangeSum(left, right);
                        System.out.println("Сумма на отрезке [" + left + ".." + right + "] = " + sum);
                        break;
                    }
                    case 4: {
                        fenwick.printTree();
                        break;
                    }
                    default:
                        System.out.println("Неизвестная команда.");
                }
            } catch (Exception e) {
                System.out.println("Ошибка: " + e.getMessage());
                scanner.nextLine();
            }
        }

        scanner.close();
    }

    private static String formatArray(int[] arr) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < arr.length; i++) {
            sb.append(arr[i]);
            if (i < arr.length - 1) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }
}
