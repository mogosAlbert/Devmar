package GamDev.Screens;

import GamDev.Huds.hudMain;
import GamDev.Misc.WorldLoader;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import GamDev.Sprites.SprMain;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class ScrGame implements Screen {

    public SpriteBatch sprBatch;
    private TiledMap Map1;
    private OrthogonalTiledMapRenderer MapRender;
    private OrthographicCamera MapCam;
    private Viewport Vp;
    private float fChangeX = 0;
    private SprMain Spr1;
    private hudMain hudMain;
    public World worlMain;
    private WorldLoader B2World1;
    private Box2DDebugRenderer B2DR;
    private BodyDef bdWolv;
    private Body bWolv;
    private FixtureDef fdWolv;

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        MapRender.setView(MapCam);
        Vp = new FitViewport(1000, 700, MapCam);
        if (Spr1.getX() > MapCam.position.x + 170) {
            fChangeX = Spr1.getX()-(MapCam.position.x + 170) ;
        } else if (Spr1.getX() < MapCam.position.x - 170) {
            fChangeX  = -(MapCam.position.x - 170 - Spr1.getX());
        } else {
            fChangeX = 0;
        }
        Input();
        worlMain.step(delta, 6, 2);
        MapCam.translate(fChangeX, 0);
        MapCam.update();
        MapRender.render();
        MapRender.getBatch().begin();
        Spr1.update(delta);
        Spr1.draw(MapRender.getBatch());
        MapRender.getBatch().end();
        hudMain.draw();
        B2DR.render(worlMain, MapCam.combined);
    }

    @Override
    public void resize(int width, int height) {
        MapCam.viewportHeight = height / 2;
        MapCam.viewportWidth = width / 2;
        MapCam.translate(MapCam.viewportWidth * 0.25f, MapCam.viewportHeight * 0.2f);
        MapCam.update();
    }

    @Override
    public void show() {
        Map1 = new TmxMapLoader().load("Maps/level1.tmx");
        MapRender = new OrthogonalTiledMapRenderer(Map1);
        worlMain = new World(new Vector2(0, -70), true);
        MapCam = new OrthographicCamera();
        Spr1 = new SprMain(this, 300, 200);
        sprBatch = new SpriteBatch();
        hudMain = new hudMain(sprBatch);
        B2DR = new Box2DDebugRenderer();
        B2World1 = new WorldLoader(this);
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        Map1.dispose();
        MapRender.dispose();
    }

    public TiledMap getMap() {
        return Map1;
    }

    public void Input() {
        if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT) && Spr1.bodMain.getLinearVelocity().x > -40f) {
            Spr1.bodMain.applyLinearImpulse(new Vector2(-5f, 0), Spr1.bodMain.getWorldCenter(), true);
        } else if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT) && Spr1.bodMain.getLinearVelocity().x < 40f) {
            Spr1.bodMain.applyLinearImpulse(new Vector2(5f, 0), Spr1.bodMain.getWorldCenter(), true);
        }
    }
}
