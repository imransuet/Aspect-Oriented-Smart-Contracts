package beforedecorator;

public class MainDecorator {
    public static void main(String[] args) {
        Component component = new ConcreteComponent();
        component = new AdviceDecorator(component);
        component.operation();

    }
}

