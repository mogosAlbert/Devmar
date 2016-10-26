package GamDev.Misc;

import GamDev.GamDev;
import GamDev.Screens.ScrGame;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class WorldLoader {
    public WorldLoader(ScrGame scrMain) {
        World wor1 = scrMain.getWorld();
        TiledMap tilmap1 = scrMain.getMap();
        BodyDef bdefMain = new BodyDef();
        PolygonShape psMain = new PolygonShape(); 
        FixtureDef fixdefMain = new FixtureDef();
        Body bodMain;
        for (MapObject mapobMain: tilmap1.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rectObject = ((RectangleMapObject) mapobMain).getRectangle();
            bdefMain.type = BodyDef.BodyType.StaticBody;
            bdefMain.position.set((rectObject.getX() + rectObject.getWidth() / 2) / GamDev.ppt, (rectObject.getY() + rectObject.getHeight() / 2) / GamDev.ppt);
            bodMain = wor1.createBody(bdefMain);
            psMain.setAsBox((rectObject.getWidth() / 2) / GamDev.ppt, (rectObject.getHeight() / 2) / GamDev.ppt);
            fixdefMain.shape = psMain;
            bodMain.createFixture(fixdefMain);
        }
    }
}
