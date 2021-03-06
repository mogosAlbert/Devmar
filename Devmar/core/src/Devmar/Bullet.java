package Devmar;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.MathUtils;

public class Bullet {
    Vector2 nLoc;
    float fX, fY, fRot, fSpeed, fRotation;
    Texture imgBul = new Texture(Gdx.files.internal("bullet.png"));
    TextureRegion imgOut = new TextureRegion(imgBul);
    public Bullet(float nX, float nY) {
        nLoc = new Vector2(nX, nY);
        fX = Gdx.input.getX();
        fY = (Gdx.input.getY() - 500) * -1;
        fRot = MathUtils.atan2(fY - nLoc.y, fX - nLoc.x) / MathUtils.PI * 180;
        fSpeed = 10;
        fRotation = MathUtils.PI * fRot / 180;
    }
    public void update(){
        nLoc.x += MathUtils.cos(fRot/180*MathUtils.PI)*fSpeed;
        nLoc.y += MathUtils.sin(fRot/180*MathUtils.PI)*fSpeed;
    }
     public boolean isOffScreen () {
         if(nLoc.x + 25 < 0 || nLoc.x > 1000) {
             return true;
         }
         if (nLoc.y + 25 < 0 || nLoc.y > 500) {
             return true;
         }
         return false;
     }      
}
