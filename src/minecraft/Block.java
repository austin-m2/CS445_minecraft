/***************************************************************
* file: Block.java
* author: Duy Le
* class: CS 445 – Computer Graphics
*
* assignment: final project
* date last modified: 11/8/2017
*
* purpose: to hold necessary information for of a block.
*
****************************************************************/ 
package minecraft;

/**
 *
 * @author Duy
 */
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

        public int getID() {
            return BlockID;
        }

        public void setID(int i) {
            BlockID = i;
        }
    }
    
    public Block(BlockType type) {
        this.type = type;
        isActive = true;
    }

    public void setCoords(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public int getID() {
        return type.getID();
    }
}

