package GamDev;
import com.badlogic.gdx.Game;
import GamDev.Screens.ScrGame;


public class GamDev extends Game {
    public static float ppt = 16;
    @Override
    public void create() {
        setScreen(new ScrGame());
    }
    @Override
    public void dispose() {
        super.dispose();
    }
    @Override
    public void render() {
        super.render();
    }
    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }
    @Override
    public void pause() {
        super.pause();
    }
    @Override
    public void resume() {
        super.render();
    }    
}
