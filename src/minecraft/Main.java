/***************************************************************
* file: Main.java
* author: Austin Morris
* class: CS 445 – Computer Graphics
*
* assignment: final project
* date last modified: 11/5/2017
*
* purpose: It's minecraft 2!
*
****************************************************************/ 
package minecraft;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import static org.lwjgl.opengl.GL11.*;

/**
 *
 * @author morri
 */
public class Main {

    public static void main(String[] args) throws FileNotFoundException {        
        Main main = new Main();
        main.start();
    }

    public Main() {
    }
    
    public void start() {
        try {
            createWindow();
            initGL();
            render();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
        
    private void createWindow() throws Exception {
        Display.setFullscreen(false);
        Display.setDisplayMode(new DisplayMode(640, 480));
        Display.setTitle("Oh Yeah!");
        Display.create();
    }
    
    private void initGL() {
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(-320, 320, -240, 240, 1, -1);
        glMatrixMode(GL_MODELVIEW);
        glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
    }
    
    private void render() {
        while (!Display.isCloseRequested()) {
            try {
                if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
                    System.exit(0);
                }

                glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
                glLoadIdentity();
                
                glColor3f(1.0f, 1.0f, 0.0f);
                glPointSize(1);
                
                
                Display.update();
                Display.sync(60);
            } catch (Exception e) {}
        }
        Display.destroy();
    }
    
}


