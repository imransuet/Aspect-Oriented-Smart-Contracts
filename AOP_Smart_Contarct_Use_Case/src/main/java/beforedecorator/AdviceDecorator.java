package beforedecorator;

public class AdviceDecorator extends AbstractDecorator {
    public AdviceDecorator(Component component) {
        super(component);
    }

    @Override
    public void operation() {

        super.operation();
        System.out.println("Doing something After...");
    }
}