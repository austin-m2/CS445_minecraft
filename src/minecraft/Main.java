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

import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;

public class Main {
    private FPCameraController fp;
    private DisplayMode displayMode;
    private FloatBuffer lightPosition;
    private FloatBuffer whiteLight;

    
    public static void main(String[] args) {        
        Main main = new Main();
        main.start();
    }
    
    /*
    * method: start
    * 
    * purpose: This method create the window and initialize GL and the
    *          camera controller.
    */
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
    
    /*
    * method: createWindow
    * 
    * purpose: This method actually create the window.
    */
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
    
    /*
    * method: initGL
    * 
    * purpose: This method initialize gl.
    */
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
        
        //Initialize lighting stuff
        initLightArrays();
        glLight(GL_LIGHT0, GL_POSITION, lightPosition); //sets our light’s position
        glLight(GL_LIGHT0, GL_SPECULAR, whiteLight);//sets our specular light
        glLight(GL_LIGHT0, GL_DIFFUSE, whiteLight);//sets our diffuse light
        glLight(GL_LIGHT0, GL_AMBIENT, whiteLight);//sets our ambient light
        
        glEnable(GL_LIGHTING);//enables our lighting
        glEnable(GL_LIGHT0);//enables light0
    }
    
    /*
    * method: initLightArrays
    * 
    * purpose: This method initialize the light arrays.
    */
    private void initLightArrays() {
        lightPosition = BufferUtils.createFloatBuffer(4);
        lightPosition.put(0.0f).put(0.0f).put(0.0f).put(1.0f).flip();
        whiteLight = BufferUtils.createFloatBuffer(4);
        whiteLight.put(1.0f).put(1.0f).put(1.0f).put(0.0f).flip();
    }

}
