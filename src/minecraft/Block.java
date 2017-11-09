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
    private boolean IsActive;
    private BlockType Type;
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

        public int GetID() {
            return BlockID;
        }

        public void SetID(int i) {
            BlockID = i;
        }
    }
    
    public Block(BlockType type) {
        Type = type;
    }

    public void setCoords(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public boolean IsActive() {
        return IsActive;
    }

    public void SetActive(boolean active) {
        IsActive = active;
    }

    public int GetID() {
        return Type.GetID();
    }
}

