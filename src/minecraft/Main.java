/***************************************************************
* file: Main.java
* author: Austin Morris, TszWai Yan
* class: CS 445 – Computer Graphics
*
* assignment: final project
* date last modified: 11/5/2017
*
* purpose: It's minecraft 2!
*
****************************************************************/ 
package minecraft;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.util.glu.GLU;

/**
 *
 * @author morri
 */
public class Main {
    private FPCameraController fp;
    private DisplayMode displayMode;
    
    public static void main(String[] args) {        
        Main main = new Main();
        main.start();
    }

    //public Main() {
    //}
    
    public void start() {
        try {
            createWindow();
            initGL();
            fp = new FPCameraController(0f, 0f, 0f);
            fp.gameLoop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
        
    private void createWindow() throws Exception {
        Display.setFullscreen(false);
        DisplayMode d[] = Display.getAvailableDisplayModes();
        for (int i = 0; i < d.length; i++) {
            if (d[i].getWidth() == 640 && d[i].getHeight() == 480 && 
                    d[i].getBitsPerPixel() == 32) {
                displayMode = d[i];
                break;
            } 
        }
        Display.setDisplayMode(displayMode);
        Display.setTitle("Minecraft Too");
        Display.create();
    }
    
    private void initGL() {
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        GLU.gluPerspective(100.0f, 
                (float)displayMode.getWidth() / (float)displayMode.getHeight(), 
                0.1f, 300.0f);
        glMatrixMode(GL_MODELVIEW);
        glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
        glEnableClientState(GL_VERTEX_ARRAY);
        glEnableClientState(GL_COLOR_ARRAY);
        glEnable(GL_DEPTH_TEST);
        
        glEnable(GL_TEXTURE_2D);
        glEnableClientState(GL_TEXTURE_COORD_ARRAY);
    } 
}


