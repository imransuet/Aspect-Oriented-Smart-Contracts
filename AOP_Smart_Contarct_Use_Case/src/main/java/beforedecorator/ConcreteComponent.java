package beforedecorator;

public class ConcreteComponent implements Component {
    @Override
    public void operation() {
        System.out.println("Doing something...");
    }
}