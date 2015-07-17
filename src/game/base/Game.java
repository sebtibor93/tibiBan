/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.base;

import java.util.ArrayList;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import org.newdawn.slick.opengl.Texture;
import static game.base.Util.loadTexture;
import java.io.IOException;
import java.util.logging.Logger;

public class Game implements Runnable
{
    public static int LEVEL_NUMBER = 1;
    public static boolean IS_LEVEL_COMPLETE = false;
    
    private static ArrayList<Texture> t;
    private static Level l;
     
    private static void getTextures()
    {
        t = new ArrayList<>();
        
        t.add(loadTexture("wall"));
        t.add(loadTexture("brick"));
        t.add(loadTexture("destination2"));
        t.add(loadTexture("element"));
        t.add(loadTexture("player"));
        t.add(loadTexture("background"));
    }
    
    public static Texture getTexture(int n)
    {
        return t.get(n);
    }
    
    public static void initDisplay()
    {
        try
        {
            Display.setDisplayMode(new DisplayMode(800, 600));
            Display.create();
        }
        catch(LWJGLException ex)
        {
            
        }
    }
    
    public static void initGL()
    {
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0,Display.getWidth(),0,Display.getHeight(),-1,1);
        glMatrixMode(GL_MODELVIEW);
        
        glClearColor(0,0,0,1);
        
        glDisable(GL_DEPTH_TEST);
        glEnable(GL_TEXTURE_2D);
    }
    
    private static void gameLoop() throws IOException
    {
        getTextures();
        
        l = new Level(LEVEL_NUMBER);
        l.setLevelMatrix(t);
        
        while(!Display.isCloseRequested())
        {
            l.getInput();
            l.update();
            if(IS_LEVEL_COMPLETE)
            {
                loadNextLevel();
                continue;
            }
            Display.sync(24);
            l.render();
            
            Display.update();
        }
    }
    
    public static void cleanUp()
    {
        Display.destroy();
    }
    
    private static void loadNextLevel() throws IOException
    {
        IS_LEVEL_COMPLETE = false;
        LEVEL_NUMBER++;
        l.setLevelMatrix(t);
    }
    
    @Override
    public void run()
    {
        initDisplay();
        initGL();
        try {
            gameLoop();
        } catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        cleanUp();
    }
}