package GamDev.Screens;

import GamDev.Huds.hudMain;
import GamDev.Misc.WorldLoader;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import GamDev.Sprites.SprMain;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.ContactListener;
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
    private int nChangeX;
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
        if(Spr1.getX() > MapCam.position.x + 200) {
            nChangeX = 2;
        } else if (Spr1.getX() < MapCam.position.x - 200) {
            nChangeX = -2;
        } else { 
            nChangeX = 0;
        }
        worlMain.step(delta, 6, 2);
        //MapCam.position.set(Spr1.getX() + Spr1.getWidth() / 2, Spr1.getY() + Spr1.getHeight() / 2 + 250 - 50, 0);
        MapCam.translate((float) nChangeX, 0);
        MapCam.update();
        MapRender.render();
        MapRender.getBatch().begin();
        Spr1.update(delta, bWolv.getPosition().x - 10, bWolv.getPosition().y - 10);
        System.out.println(bWolv.getPosition().y);
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
        MapCam = new OrthographicCamera();
        Spr1 = new SprMain(this, 300, 200);
        Gdx.input.setInputProcessor(Spr1);
        sprBatch = new SpriteBatch();
        hudMain = new hudMain(sprBatch);
        worlMain = new World(new Vector2(0, -10), true);
        bdWolv = new BodyDef();
        bdWolv.type = BodyDef.BodyType.DynamicBody;
        bdWolv.position.set(Spr1.getX(), Spr1.getY());
        bWolv = worlMain.createBody(bdWolv);
        CircleShape circle = new CircleShape();
        circle.setRadius(10);
        fdWolv = new FixtureDef();
        fdWolv.shape = circle;
        fdWolv.shape.setRadius(10);
        bWolv.createFixture(fdWolv);
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
}