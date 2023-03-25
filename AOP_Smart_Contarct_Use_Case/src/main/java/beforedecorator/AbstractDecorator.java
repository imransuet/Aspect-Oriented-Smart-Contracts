package beforedecorator;

public abstract class AbstractDecorator implements Component {
    private final Component component;

    public AbstractDecorator(Component component) {
        this.component = component;
    }

    @Override
    public void operation() {
        component.operation();
    }
}