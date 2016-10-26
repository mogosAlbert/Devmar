package GamDev.Sprites;

import GamDev.Screens.ScrGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class SprMain extends Sprite implements InputProcessor {

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

    public SprMain(ScrGame scrMain) {
        this.worlMain = scrMain.getWorld();
        vVelocity = new Vector2();
        setSize(35, 70);
        for (int i = 0; i < imgStand.length; i++) {
            imgStand[i] = new Texture(Gdx.files.internal("Wolverine/stand/" + i + ".png"));
        }
        setX(70);
        setY(400);
        bdMain.position.set(getX(), getY());
        bdMain.type = BodyDef.BodyType.DynamicBody;
        worlMain.createBody(bdMain);
        fdMain.shape = new CircleShape();
        fdMain.shape.setRadius(10);
        bodMain.createFixture(fdMain).setUserData(this);
    }

    @Override
    public void draw(Batch batch) {
        update(Gdx.graphics.getDeltaTime());
        super.draw(batch);
    }

    public void update(float fDelta) {
        vVelocity.y -= fGravity * fDelta;
        if (vVelocity.y > fSpeed) {
            vVelocity.y = fSpeed;
        } else if (vVelocity.y < -fSpeed) {
            vVelocity.y = -fSpeed;
        }
        setX(getX() + vVelocity.x * fDelta);
        fIncrement = collisionLayer.getTileWidth();
        fIncrement = getWidth() < fIncrement ? getWidth() / 2 : fIncrement / 2;
        float fOldX = getX(), fOldY = getY();
        boolean isColX = false, isColY = false;
        if (vVelocity.x < 0) {
            isColX = isColLeft();
        } else if (vVelocity.x > 0)
        {
            isColX = isColRight();
        }
        if(isColX) {
            setX(fOldX);
        }
        nOffset++;
        if (nOffset == 5) {
            nOffset = 0;
            nFrame++;
        }
        if (nFrame >= imgStand.length) {
            nFrame = 0;
        }
        imgOut = new TextureRegion(imgStand[nFrame]);
        setRegion(imgOut);

    }

    public TiledMapTileLayer getCollisionLayer() {
        return collisionLayer;
    }

    private boolean isBlocked(float x, float y) {
        Cell cell = collisionLayer.getCell((int) (x / collisionLayer.getTileWidth()), (int) (y / collisionLayer.getTileHeight()));
        return cell != null && cell.getTile() != null && cell.getTile().getProperties().containsKey("blocked");
    }

    private boolean isColLeft() {
        for (float step = 0; step <= getHeight(); step += fIncrement) {
            if (isBlocked(getX(), getY() + step)) {
                return true;
            }
        }
        return false;
    }

    private boolean isColRight() {
        for (float step = 0; step <= getHeight(); step += fIncrement) {
            if (isBlocked(getX() + getWidth(), getY() + step)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean keyDown(int i) {
        if (i == Input.Keys.LEFT || i == Input.Keys.A) {
            vVelocity.x = -fSpeed;
        } else if (i == Input.Keys.RIGHT || i == Input.Keys.D) {
            vVelocity.x = fSpeed;
        }
        return true;
    }

    @Override
    public boolean keyUp(int i) {
        vVelocity.x = 0;
        System.out.println("up");
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}