/***************************************************************
* file: Main.java
* author: Austin Morris
* class: CS 445 – Computer Graphics
*
* assignment: final project
* date last modified: 11/18/2017
*
* purpose: A vector class for use with the FPCameraController class.
*
****************************************************************/ 
package minecraft;

public class Vector3Float {
    public float x, y, z;
    
    /*
    * method: Vector3Float
    * 
    * purpose: This constructor defines the x, y, z instances.
    */
    public Vector3Float(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
}
