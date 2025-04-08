package pl.iashchenko.hello;

public class App {
    public static void main(String[] args) {
        var name = "Kostia";

        int a = 2;
        int b = 3;

        var result = a + b;

        if (result != 5){
            throw new IllegalStateException("assetion error");
        }
    }
}
