package su.murbyte.client;

import su.murbyte.client.level.Level;
import su.murbyte.client.phys.phys;
import java.util.List;
import org.lwjgl.input.Keyboard;

public class Player {
    private Level level;

    public float xo;

    public float yo;

    public float zo;

    public float x;

    public float y;

    public float z;

    public float xd;

    public float yd;

    public float zd;

    public float yRot;

    public float xRot;

    public phys physs;

    public boolean onGround = false;

    public Player(Level level) {
        this.level = level;
        resetPosition(); //TODO сделать левел =)
    }

    private void resetPosition() {
        float x = (float)Math.random() * this.level.width;
        float y = (this.level.depth + 10);
        float z = (float)Math.random() * this.level.height;
        setPosition(x, y, z);
    }

    private void setPosition(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
        float w = 0.3F;
        float h = 0.9F;
        this.physs = new phys(x - w, y - h, z - w, x + w, y + h, z + w);
    }
    public void turn(float xo, float yo) {
        this.yRot = (float)(this.yRot + xo * 0.15D);
        this.xRot = (float)(this.xRot - yo * 0.15D);
        if (this.xRot < -90.0F)
            this.xRot = -90.0F;
        if (this.xRot > 90.0F)
            this.xRot = 90.0F;
    }

    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        float xa = 0.0F;
        float ya = 0.0F;
        if (Keyboard.isKeyDown(19))
            resetPosition();
        if (Keyboard.isKeyDown(200) || Keyboard.isKeyDown(17))
            ya--;
        if (Keyboard.isKeyDown(208) || Keyboard.isKeyDown(31))
            ya++;
        if (Keyboard.isKeyDown(203) || Keyboard.isKeyDown(30))
            xa--;
        if (Keyboard.isKeyDown(205) || Keyboard.isKeyDown(32))
            xa++;
        if (Keyboard.isKeyDown(57) || Keyboard.isKeyDown(219))
            if (this.onGround)
                this.yd = 0.12F;
        moveRelative(xa, ya, this.onGround ? 0.02F : 0.005F);
        this.yd = (float)(this.yd - 0.005D);
        move(this.xd, this.yd, this.zd);
        this.xd *= 0.91F;
        this.yd *= 0.98F;
        this.zd *= 0.91F;
        if (this.onGround) {
            this.xd *= 0.8F;
            this.zd *= 0.8F;
        }
    }

    public void move(float xa, float ya, float za) {
        float xaOrg = xa;
        float yaOrg = ya;
        float zaOrg = za;
        List<phys> aABBs = this.level.getCubes(this.physs.expand(xa, ya, za)); //TODO допилить эта чиста в левель
        int i;
        for (i = 0; i < aABBs.size(); i++)
            ya = ((phys)aABBs.get(i)).clipYCollide(this.physs, ya);
        this.physs.move(0.0F, ya, 0.0F);
        for (i = 0; i < aABBs.size(); i++)
            xa = ((phys)aABBs.get(i)).clipXCollide(this.physs, xa);
        this.physs.move(xa, 0.0F, 0.0F);
        for (i = 0; i < aABBs.size(); i++)
            za = ((phys)aABBs.get(i)).clipZCollide(this.physs, za);
        this.physs.move(0.0F, 0.0F, za);
        this.onGround = (yaOrg != ya && yaOrg < 0.0F);
        if (xaOrg != xa)
            this.xd = 0.0F;
        if (yaOrg != ya)
            this.yd = 0.0F;
        if (zaOrg != za)
            this.zd = 0.0F;
        this.x = (this.physs.x0 + this.physs.x1) / 2.0F;
        this.y = this.physs.y0 + 1.62F;
        this.z = (this.physs.z0 + this.physs.z1) / 2.0F;
    }

    public void moveRelative(float xa, float za, float speed) {
        float dist = xa * xa + za * za;
        if (dist < 0.01F)
            return;
        dist = speed / (float)Math.sqrt(dist);
        xa *= dist;
        za *= dist;
        float sin = (float)Math.sin(this.yRot * Math.PI / 180.0D);
        float cos = (float)Math.cos(this.yRot * Math.PI / 180.0D);
        this.xd += xa * cos - za * sin;
        this.zd += za * cos + xa * sin;
    }
}