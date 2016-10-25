package TiledMapScratch;

import TiledMapScratch.Screens.ScrMain;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

public class GamScratch extends Game {

	
	@Override
	public void create () {
            setScreen(new ScrMain());
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);;
	}
}
