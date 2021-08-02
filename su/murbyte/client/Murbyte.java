package su.murbyte.client;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import javax.swing.JOptionPane;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

public class Murbyte implements Runnable {
    private static final boolean FULLSCREEN_MODE = false;
    private int width;
    private int height;
    private FloatBuffer fogColor = BufferUtils.createFloatBuffer(4);

    public void init() throws LWJGLException, IOException {
        int col = 920330;
        float fr = 0.5F;
        float fg = 0.8F;
        float fb = 1.0F;
        this.fogColor.put(new float[] { (col >> 16 & 0xFF) / 255.0F, (col >> 8 & 0xFF) / 255.0F, (col & 0xFF) / 255.0F, 1.0F });
        this.fogColor.flip();
        Display.setDisplayMode(new DisplayMode(800, 600));
        Display.create();
        Keyboard.create();
        Mouse.create();
        this.width = Display.getDisplayMode().getWidth();
        this.height = Display.getDisplayMode().getHeight();
        GL11.glEnable(3553);
        GL11.glShadeModel(7425);
        GL11.glClearColor(fr, fg, fb, 0.0F);
        GL11.glClearDepth(1.0D);
        GL11.glEnable(2929);
        GL11.glDepthFunc(515);
        GL11.glMatrixMode(5889);
        GL11.glLoadIdentity();
        GL11.glMatrixMode(5888);
        Mouse.setGrabbed(true);
    }
    public void destroy() {
        Mouse.destroy();
        Keyboard.destroy();
        Display.destroy();
    }

    public void run() {
        try {
            init();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString(), "Failed to start Client", 0);
            System.exit(0);
        }
        long lastTime = System.currentTimeMillis();
        int frames = 0;
    }

    public static void main(String[] args) throws LWJGLException {
        (new Thread(new Murbyte())).start();
    }
}
