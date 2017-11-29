/***************************************************************
* file: Block.java
* author: Austin Morris, Duy Le, TszWai Yan, Luis Lopez
* class: CS 445 – Computer Graphics
*
* assignment: final project
* date last modified: 11/8/2017
*
* purpose: to hold necessary information for of a block.
*
****************************************************************/ 
package minecraft;

public class Block {
    private boolean isActive;
    private BlockType type;
    private float x, y, z;

    public enum BlockType {
        BlockType_Default(-1),
        BlockType_Grass(0),
        BlockType_Sand(1),
        BlockType_Water(2),
        BlockType_Dirt(3),
        BlockType_Stone(4),
        BlockType_Bedrock(5);
        
        private int BlockID;

        BlockType(int i) {
            BlockID = i;
        }

        /*
        * method: getID
        * 
        * purpose: This method get the block's ID.
        */
        public int getID() {
            return BlockID;
        }

        /*
        * method: setID
        * 
        * purpose: This method set the block's ID.
        */
        public void setID(int i) {
            BlockID = i;
        }
    }
    
    /*
    * method: Block
    * 
    * purpose: This constructor set the type of the block & active to true.
    */
    public Block(BlockType type) {
        this.type = type;
        isActive = true;
    }

    /*
    * method: setCoords
    * 
    * purpose: This method set position of the block.
    */
    public void setCoords(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /*
    * method: isActive
    * 
    * purpose: This method return true for active block.
    */
    public boolean isActive() {
        return isActive;
    }

    /*
    * method: setActive
    * 
    * purpose: This method set the isActive boolean for the block.
    */
    public void setActive(boolean active) {
        isActive = active;
    }

    /*
    * method: getID
    * 
    * purpose: This method return the ID of the type.
    */
    public int getID() {
        return type.getID();
    }
}

