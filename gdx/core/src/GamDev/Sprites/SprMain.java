package GamDev.Sprites;

import GamDev.Screens.ScrGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class SprMain extends Sprite {

    private Texture imgStand[] = new Texture[3];
    private TextureRegion imgOut;
    private Vector2 vVelocity;
    private TiledMapTileLayer collisionLayer;
    private float fSpeed = 60 * 2, fGravity = 60 * 1.8f, fIncrement;
    private int nFrame = 0, nOffset = 0;
    public World worlMain;
    public Body bodMain;
    public BodyDef bdMain;
    public FixtureDef fdMain;

    public SprMain(ScrGame scrMain, float fX, float fY) {
        this.worlMain = scrMain.worlMain;
        vVelocity = new Vector2();
        setSize(20, 25);
        for (int i = 0; i < imgStand.length; i++) {
            imgStand[i] = new Texture(Gdx.files.internal("Wolverine/stand/" + i + ".png"));
        }
        setX(fX);
        setY(fY);
        bdMain = new BodyDef();
        bdMain.position.set(getX(), getY());
        bdMain.type = BodyDef.BodyType.DynamicBody;
        bodMain = worlMain.createBody(bdMain);
        CircleShape circMain = new CircleShape();
        circMain.setRadius(13);
        fdMain = new FixtureDef();
        fdMain.shape = circMain;
        bodMain.createFixture(fdMain);
    }

    @Override
    public void draw(Batch batch) {
        super.draw(batch);
    }

    public void update(float fDelta) {
        nOffset++;
        if (nOffset == 5) {
            nOffset = 0;
            nFrame++;
        }
        if (nFrame >= imgStand.length) {
            nFrame = 0;
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.W) || Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            bodMain.applyLinearImpulse(new Vector2(0, 85f), bodMain.getWorldCenter(), true);
        }
        setX(bodMain.getPosition().x - 13);
        setY(bodMain.getPosition().y - 13);
        imgOut = new TextureRegion(imgStand[nFrame]);
        setRegion(imgOut);
    }
}