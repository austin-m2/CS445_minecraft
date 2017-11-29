/***************************************************************
* file: Chunk.java
* author: Austin Morris, Duy Le, TszWai Yan, Luis Lopez
* class: CS 445 – Computer Graphics
*
* assignment: final project
* date last modified: 11/8/2017
*
* purpose: class to generate chunk of blocks.
*
****************************************************************/
package minecraft;

import java.nio.FloatBuffer;
import java.util.Random;
import org.lwjgl.BufferUtils;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Chunk {
    
    //print a 2D array of noise value
    public static final boolean PRINT_NOISE_VALUES = false;

    static final int CHUNK_SIZE = 30;
    static final int CUBE_LENGTH = 2;

    private Block[][][] blocks;
    private int VBOVertexHandle;
    private int VBOColorHandle;
    private int VBOTextureHandle;
    private Texture texture;
    private int StartX, StartY, StartZ;
    private Random r;
    private SimplexNoise noise;

    /*
    * method: render
    * 
    * purpose: This method render the blocks with gl methods.
    */
    public void render() {
        glPushMatrix();
        
        glBindBuffer(GL_ARRAY_BUFFER, VBOVertexHandle);
        glVertexPointer(3, GL_FLOAT, 0, 0L);
        glBindBuffer(GL_ARRAY_BUFFER, VBOColorHandle);
        glColorPointer(3, GL_FLOAT, 0, 0L);
        glBindBuffer(GL_ARRAY_BUFFER, VBOTextureHandle);
        glBindTexture(GL_TEXTURE_2D, 1);
        glTexCoordPointer(2, GL_FLOAT, 0, 0L);
        glDrawArrays(GL_QUADS, 0, CHUNK_SIZE * CHUNK_SIZE * CHUNK_SIZE * 24);
        
        glPopMatrix();
    }

    /*
    * method: rebuildMesh
    * 
    * purpose: This method rebuild the mesh.
    */
    public void rebuildMesh(float startX, float startY, float startZ) {
        VBOColorHandle = glGenBuffers();
        VBOVertexHandle = glGenBuffers();
        VBOTextureHandle = glGenBuffers();
        FloatBuffer VertexPositionData = BufferUtils.createFloatBuffer(
                (CHUNK_SIZE * CHUNK_SIZE
                * CHUNK_SIZE) * 6 * 12);
        FloatBuffer VertexColorData = BufferUtils.createFloatBuffer(
                (CHUNK_SIZE * CHUNK_SIZE
                * CHUNK_SIZE) * 6 * 12);
        FloatBuffer VertexTextureData = BufferUtils.createFloatBuffer((
                CHUNK_SIZE * CHUNK_SIZE * CHUNK_SIZE) * 6 * 12);
        for (float x = 0; x < CHUNK_SIZE; x += 1) {
            for (float z = 0; z < CHUNK_SIZE; z += 1) {
                for (float y = 0; y < CHUNK_SIZE; y++) {
                    if(blocks[(int)x][(int)y][(int)z].isActive()){
                        VertexPositionData.put(createCube(
                                (float) (startX + x * CUBE_LENGTH),
                                (float) (startY + y * CUBE_LENGTH + 
                                        (int) (CHUNK_SIZE * .8)),
                                (float) (startZ + z * CUBE_LENGTH)));
                        VertexColorData.put(createCubeVertexCol(getCubeColor(
                                blocks[(int) x][(int) y][(int) z])));
                        VertexTextureData.put(createTexCube(0f, 0f, 
                                blocks[(int) x][(int) y][(int) z]));
                    }
                }
            }
        }
        VertexColorData.flip();
        VertexPositionData.flip();
        VertexTextureData.flip();
        glBindBuffer(GL_ARRAY_BUFFER, VBOVertexHandle);
        glBufferData(GL_ARRAY_BUFFER, VertexPositionData, GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindBuffer(GL_ARRAY_BUFFER, VBOColorHandle);
        glBufferData(GL_ARRAY_BUFFER, VertexColorData, GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        
        glBindBuffer(GL_ARRAY_BUFFER, VBOTextureHandle);
        glBufferData(GL_ARRAY_BUFFER, VertexTextureData, GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    /*
    * method: createCubeVertexCol
    * 
    * purpose: This method return the color from a given color array for the 
    *          block.
    */
    private float[] createCubeVertexCol(float[] CubeColorArray) {
        float[] cubeColors = new float[CubeColorArray.length * 4 * 6];
        for (int i = 0; i < cubeColors.length; i++) {
            cubeColors[i] = CubeColorArray[i % CubeColorArray.length];
        }
        return cubeColors;
    }

    /*
    * method: createCube
    * 
    * purpose: This method actually create the block.
    */
    public static float[] createCube(float x, float y, float z) {
        int offset = CUBE_LENGTH / 2;
        return new float[]{
            // TOP QUAD
            x + offset, y + offset, z,
            x - offset, y + offset, z,
            x - offset, y + offset, z - CUBE_LENGTH,
            x + offset, y + offset, z - CUBE_LENGTH,
            // BOTTOM QUAD
            x + offset, y - offset, z - CUBE_LENGTH,
            x - offset, y - offset, z - CUBE_LENGTH,
            x - offset, y - offset, z,
            x + offset, y - offset, z,
            // FRONT QUAD
            x + offset, y + offset, z - CUBE_LENGTH,
            x - offset, y + offset, z - CUBE_LENGTH,
            x - offset, y - offset, z - CUBE_LENGTH,
            x + offset, y - offset, z - CUBE_LENGTH,
            // BACK QUAD
            x + offset, y - offset, z,
            x - offset, y - offset, z,
            x - offset, y + offset, z,
            x + offset, y + offset, z,
            // LEFT QUAD
            x - offset, y + offset, z - CUBE_LENGTH,
            x - offset, y + offset, z,
            x - offset, y - offset, z,
            x - offset, y - offset, z - CUBE_LENGTH,
            // RIGHT QUAD
            x + offset, y + offset, z,
            x + offset, y + offset, z - CUBE_LENGTH,
            x + offset, y - offset, z - CUBE_LENGTH,
            x + offset, y - offset, z};
    }

    /*
    * method: getCubeColor
    * 
    * purpose: This method return a float color array of 1, 1, 1.
    */
    private float[] getCubeColor(Block block) {
        return new float[]{1, 1, 1};
    }

    /*
    * method: Chunk
    * 
    * purpose: This constructor set the texture, and positon of random blocks.
    */
    public Chunk(int startX, int startY, int startZ) {
        try {
            texture = TextureLoader.getTexture("PNG", 
                    ResourceLoader.getResourceAsStream("terrain.png"));
        } catch (Exception e) {
            System.out.print("Error: terrain.png not found.");
        }
        
        r = new Random();
        noise = new SimplexNoise(20, .25d, r.nextInt());
        
        blocks = new Block[CHUNK_SIZE][CHUNK_SIZE][CHUNK_SIZE];
        for (int x = 0; x < CHUNK_SIZE; x++) {
            for(int z = 0; z < CHUNK_SIZE; z++) {
                if(PRINT_NOISE_VALUES) {
                    System.out.print(noise.getNoise(x, z) * 5 + "\t");
                }
                for(int y = 0; y < CHUNK_SIZE; y++) {
                    if(y == 0) {
                        blocks[x][y][z] = new Block(Block.BlockType.BlockType_Bedrock);
                    } else if (y >= 24 + noise.getNoise(x, z) * 5) {
                        int ranNum = r.nextInt(3);
                        switch (ranNum) {
                            case 0:
                                blocks[x][y][z] = new Block(Block.BlockType.BlockType_Grass);
                                break;
                            case 1:
                                blocks[x][y][z] = new Block(Block.BlockType.BlockType_Sand);
                                break;
                            case 2:
                                blocks[x][y][z] = new Block(Block.BlockType.BlockType_Water);
                                break;
                            default:
                                break;
                        }
                    } else if(y < 24 + noise.getNoise(x, z) * 5) {
                        int ranNum = r.nextInt(2);
                        switch(ranNum){
                            case 0:
                                blocks[x][y][z] = new Block(Block.BlockType.BlockType_Dirt);
                                break;
                            default:
                               blocks[x][y][z] = new Block(Block.BlockType.BlockType_Stone);
                               break;
                        }
                    } else {
                        blocks[x][y][z] = new Block(Block.BlockType.BlockType_Bedrock);
                    }
                    
                    if(y >= 25 + noise.getNoise(x, z) * 5) {
                        blocks[x][y][z].setActive(false);
                    }
                }
            }
            if(PRINT_NOISE_VALUES){
                System.out.println();
            }
        }
        VBOColorHandle = glGenBuffers();
        VBOVertexHandle = glGenBuffers();
        VBOTextureHandle = glGenBuffers();
        this.StartX = startX;
        this.StartY = startY;
        this.StartZ = startZ;
        rebuildMesh(startX, startY, startZ);
    }
    
    /*
    * method: createTexCube
    * 
    * purpose: This method create blocks with texture.
    */
    public static float[] createTexCube(float x, float y, Block block) {
        float offset = (1024f/16)/1024f;
        
        switch (block.getID()) {
            case 0:
                return new float[] {
                    //Bottom Quad
                    x + offset * 3, y + offset * 10, 
                    x + offset * 2, y + offset * 10, 
                    x + offset * 2, y + offset * 9, 
                    x + offset * 3, y + offset * 9, 
                    //Top Quad
                    x + offset * 3, y + offset * 1, 
                    x + offset * 2, y + offset * 1, 
                    x + offset * 2, y + offset * 0, 
                    x + offset * 3, y + offset * 0,
                    //Front Quad
                    x + offset * 3, y + offset * 0, 
                    x + offset * 4, y + offset * 0, 
                    x + offset * 4, y + offset * 1, 
                    x + offset * 3, y + offset * 1,
                    //Back Quad
                    x + offset * 4, y + offset * 1, 
                    x + offset * 3, y + offset * 1, 
                    x + offset * 3, y + offset * 0, 
                    x + offset * 4, y + offset * 0,
                    //Left Quad
                    x + offset * 3, y + offset * 0, 
                    x + offset * 4, y + offset * 0, 
                    x + offset * 4, y + offset * 1, 
                    x + offset * 3, y + offset * 1,
                    //Right Quad
                    x + offset * 3, y + offset * 0, 
                    x + offset * 4, y + offset * 0, 
                    x + offset * 4, y + offset * 1, 
                    x + offset * 3, y + offset * 1
                };
            case 1:
                return sameTextureAllSides(x, y, offset, 2, 1);
            case 2:
                return sameTextureAllSides(x, y, offset, 14, 0);
            case 3:
                return sameTextureAllSides(x, y, offset, 2, 0);
            case 4:
                return sameTextureAllSides(x, y, offset, 1, 0);
            case 5:
                return sameTextureAllSides(x, y, offset, 1, 1);
        }
        throw new RuntimeException("No texture mapping for such block ID.");
    }
    
    /*
    * method: sameTextureAllSides
    * 
    * purpose: This method make blocks with same texture on all sides.
    */
    private static float[] sameTextureAllSides(float x, float y, float offset, 
            float left, float top) {
        float right = left + 1;
        float bottom = top + 1;
        return new float[] {
            //Top Quad
            x + offset * right, y + offset * bottom,
            x + offset * left, y + offset * bottom,
            x + offset * left, y + offset * top,
            x + offset * right, y + offset * top,
            //Bottom Quad
            x + offset * right, y + offset * top,
            x + offset * left, y + offset * top,
            x + offset * left, y + offset * bottom,
            x + offset * right, y + offset * bottom,
            //Front Quad
            x + offset * left, y + offset * bottom,
            x + offset * right, y + offset * bottom,
            x + offset * right, y + offset * top,
            x + offset * left, y + offset * top,
            //Back Quad
            x + offset * right, y + offset * top,
            x + offset * left, y + offset * top,
            x + offset * left, y + offset * bottom,
            x + offset * right, y + offset * bottom,
            //Left Quad
            x + offset * left, y + offset * bottom,
            x + offset * right, y + offset * bottom,
            x + offset * right, y + offset * top,
            x + offset * left, y + offset * top,
            //Right Quad
            x + offset * left, y + offset * bottom,
            x + offset * right, y + offset * bottom,
            x + offset * right, y + offset * top,
            x + offset * left, y + offset * top
        };
        
    }
}
