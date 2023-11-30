import java.util.Scanner;

class Main {
    final static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        // put your code here
        int u1 = scanner.nextInt();
        int u2 = scanner.nextInt();

        int v1 = scanner.nextInt();
        int v2 = scanner.nextInt();

        int up = (u1 * v1) + (u2 * v2);

        double hypotU = Math.hypot(u1, u2);
        double hypotV = Math.hypot(v1, v2);

        double down = hypotU * hypotV;

        double value = up / down;
        value = Math.toDegrees(Math.acos(value));
        System.out.print(value);
    }
}
