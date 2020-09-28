import engine.AbstractGame;
import engine.GameContainer;
import engine.gfx.Renderer;
import engine2d.Renderer2D;
import engine2d.Transform2D;
import shapes.Shape;

public class TestShapes extends AbstractGame {

    private Shape shape;

    private Renderer2D renderer2D;

    private float rotation = 0;

    private TestShapes(String title) {
        super(title);
    }

    @Override
    public void initialize(GameContainer gameContainer) {
        shape = new Shape();
        renderer2D = new Renderer2D(gameContainer);
    }

    @Override
    public void update(GameContainer gameContainer, float v) {
        rotation += v;
    }

    @Override
    public void render(GameContainer gameContainer, Renderer renderer) {
        Transform2D transform2D = new Transform2D();
        transform2D.scale(100, 100).translate(-50, -50).rotate(rotation).translate(gameContainer.getInput().getMouseX(), gameContainer.getInput().getMouseY() );
        renderer2D.drawShape(shape, transform2D);
    }

    public static void main(String[] args) {
        GameContainer gc = new GameContainer(new TestShapes("Test shapes"));
        gc.start();
    }

}
