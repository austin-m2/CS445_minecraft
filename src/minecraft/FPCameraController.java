/***************************************************************
* file: FPCameraController.java
* author: Austin Morris, Duy Le, TszWai Yan, Luis Lopez
* class: CS 445 – Computer Graphics
*
* assignment: final project
* date last modified: 11/28/2017
*
* purpose: create a first person camera object for user to control!
* It also renders the world.
*
****************************************************************/ 
package minecraft;

import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.Sys;

public class FPCameraController {
    //3d vector to store the camera's position in
    private Vector3f position   = null;
    private Vector3f lPosition  = null;
    
    //the rotation around the Y axis of the camera
    private float yaw   = 0.0f;
    private float pitch = 0.0f;
    
    private Chunk chunk;
    
    /*
    * method: FPCameraController
    * 
    * purpose: This constructor defines the position of the camera.
    */
    public FPCameraController(float x, float y, float z) {
        //instantiate position Vector3f to the x y z params
        position = new Vector3f(x, y, z);
        lPosition = new Vector3f(x, y, z);
        lPosition.x = 0f;
        lPosition.y = 15f;
        lPosition.z = 0f;
        chunk = new Chunk(-40, -80, -50);
    }
    
    /*
    * method: yaw
    * 
    * purpose: This method increment the camera's current yaw rotation.
    */
    public void yaw(float amount) {
        //increment the yaw by the amount param
        yaw += amount;
    }

    /*
    * method: pitch
    * 
    * purpose: This method increment the camera's current pitch rotation.
    */
    public void pitch(float amount) {
        //increment the pitch by the amount param
        pitch += amount;
        if(pitch < -90) {
            pitch = -90;
        }
        if(pitch > 90) {
            pitch = 90;
        }
    }

    /*
    * method: walkForward
    * 
    * purpose: This method moves the camera forward relative to its current 
    *          rotation (yaw).
    */
    public void walkForward(float distance) {
        float xOffset = distance * (float)Math.sin(Math.toRadians(yaw));
        float zOffset = distance * (float)Math.cos(Math.toRadians(yaw));
        position.x -= xOffset;
        position.z += zOffset;
        //Lighting offset
        FloatBuffer lightPosition = BufferUtils.createFloatBuffer(4);
        lightPosition.put(lPosition.x-=xOffset).put(
        lPosition.y).put(lPosition.z+=zOffset).put(1.0f).flip();
        glLight(GL_LIGHT0, GL_POSITION, lightPosition);
    }

    /*
    * method: walkBackwards
    * 
    * purpose: This method moves the camera backward relative to its current 
    *          rotation (yaw).
    */
    public void walkBackwards(float distance) {
        float xOffset = distance * (float)Math.sin(Math.toRadians(yaw));
        float zOffset = distance * (float)Math.cos(Math.toRadians(yaw));
        position.x += xOffset;
        position.z -= zOffset;
        //Lighting offset
        FloatBuffer lightPosition = BufferUtils.createFloatBuffer(4);
        lightPosition.put(lPosition.x+=xOffset).put(
        lPosition.y).put(lPosition.z-=zOffset).put(1.0f).flip();
        glLight(GL_LIGHT0, GL_POSITION, lightPosition);
    }

    /*
    * method: strafeLeft
    * 
    * purpose: This method strafes the camera left relative to its current 
    *          rotation (yaw).
    */
    public void strafeLeft(float distance) {
        float xOffset = distance * (float)Math.sin(Math.toRadians(yaw-90));
        float zOffset = distance * (float)Math.cos(Math.toRadians(yaw-90));
        position.x -= xOffset;
        position.z += zOffset;
        //Lighting offset
        FloatBuffer lightPosition = BufferUtils.createFloatBuffer(4);
        lightPosition.put(lPosition.x-=xOffset).put(
        lPosition.y).put(lPosition.z+=zOffset).put(1.0f).flip();
        glLight(GL_LIGHT0, GL_POSITION, lightPosition);
    }

    /*
    * method: strafeRight
    * 
    * purpose: This method strafes the camera right relative to its current 
    *          rotation (yaw).
    */
    public void strafeRight(float distance) {
        float xOffset = distance * (float)Math.sin(Math.toRadians(yaw+90));
        float zOffset = distance * (float)Math.cos(Math.toRadians(yaw+90));
        position.x -= xOffset;
        position.z += zOffset;
        //Lighting offset
        FloatBuffer lightPosition = BufferUtils.createFloatBuffer(4);
        lightPosition.put(lPosition.x-=xOffset).put(
        lPosition.y).put(lPosition.z+=zOffset).put(1.0f).flip();
        glLight(GL_LIGHT0, GL_POSITION, lightPosition);
    }

    /*
    * method: moveUp
    * 
    * purpose: This method moves the camera up relative to its current 
    *          rotation (yaw).
    */
    public void moveUp(float distance) {
        position.y -= distance;
    }

    /*
    * method: moveDown
    * 
    * purpose: This method moves the camera down relative to its current
    *          rotation (yaw).
    */
    public void moveDown(float distance) {
        position.y += distance;
    }
    
    /*
    * method: lookThrough
    * 
    * purpose: translates and rotate the matrix so that it looks through the 
    *          camera this does basically what gluLookAt() does
    */
    public void lookThrough() {
        //rotate the pitch around the X axis
        glRotatef(pitch, 1.0f, 0.0f, 0.0f);
        //rotate the yaw around the Y axis
        glRotatef(yaw, 0.0f, 1.0f, 0.0f);
        //translate to the position vector's location
        glTranslatef(position.x, position.y, position.z);
        
        FloatBuffer lightPosition = BufferUtils.createFloatBuffer(4);
        lightPosition.put(lPosition.x).put(
        lPosition.y).put(lPosition.z).put(1.0f).flip();
        glLight(GL_LIGHT0, GL_POSITION, lightPosition);
    }
    
    /*
    * method: gameLoop
    * 
    * purpose: This method actually render the blocks.
    */
    public void gameLoop() {
        FPCameraController camera = new FPCameraController(0, 0, 0);
        float dx = 0.0f;
        float dy = 0.0f;
        float dt = 0.0f; //length of frame
        float lastTime = 0.0f; // when the last frame was
        long time = 0;
        float mouseSensitivity = 0.09f;
        float movementSpeed = .35f;
        //hide the mouse
        Mouse.setGrabbed(true);
        
        // keep looping till the display window is closed the ESC key is down
        while (!Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
            try {
                time = Sys.getTime();
                lastTime = time;

                //distance in mouse movement from the last getDX() call.
                dx = Mouse.getDX();
                //distance in mouse movement from the last getDY() call.
                dy = Mouse.getDY();

                //controlcamera yaw from x movement from the mouse
                camera.yaw(dx * mouseSensitivity);
                //control camera pitch from y movement from the mouse
                camera.pitch(dy * mouseSensitivity * -1);

                //when passing in the distance to move
                //we times the movementSpeed with dt this is a time scale
                //so if its a slow frame u move more then a fast frame
                //so on a slow computer you move just as fast as on a fast computer
                if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
                    camera.walkForward(movementSpeed);
                }
                if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
                    camera.walkBackwards(movementSpeed);
                }
                if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
                    camera.strafeLeft(movementSpeed);
                }
                if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
                    camera.strafeRight(movementSpeed);
                }
                if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
                    camera.moveUp(movementSpeed);
                }
                if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
                    camera.moveDown(movementSpeed);
                }
                
                
                //makes sure the user can't go past the edge of the universe
                if (camera.position.x < -35) {
                    camera.position.x = -35;
                }
                else if (camera.position.x > 55) {
                    camera.position.x = 55;
                }
                
                if (camera.position.y < -15) {
                    camera.position.y = -15;
                }
                else if (camera.position.y > 65) {
                    camera.position.y = 65;
                }
                
                if (camera.position.z < -25) {
                    camera.position.z = -25;
                }
                else if (camera.position.z > 70) {
                    camera.position.z = 70;
                }
                

                //set the modelview matrix back to the identity
                glLoadIdentity();
                //look through the camera before you draw anything
                camera.lookThrough();
                glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
                //you would draw your scene here.
                //new Chunk(0, 0, 0).render();
                chunk.render();
                //draw the buffer to the screen
                Display.update();
                Display.sync(60);

            }catch (Exception e) {
                e.printStackTrace();
            }
        } 
        Display.destroy();
    }
}
