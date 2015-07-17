/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package game.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.lwjgl.opengl.GL11.*;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
//import org.newdawn.slick.opengl.Texture;
//import org.newdawn.slick.opengl.TextureLoader;

public class Util
{ 
    public static void drawCircle(float x, float y, float radius, Texture t)
    {
        glPushMatrix();
        {
            //glTranslatef(x, y, 0);
            
            t.bind();
            glBegin(GL_POLYGON);
            for(float i = 0.0f; i < 360.0f; i+= 2.0f)
            {
                double radian = i * (Math.PI / 180.0f);

                float xcos = (float)Math.cos(radian);
                float ysin = (float)Math.sin(radian);
                float xx = xcos * radius  + x;
                float yy = ysin * radius  + y;
                float tx = xcos * 0.5f + 0.5f;
                float ty = ysin * 0.5f + 0.5f;

                glTexCoord2f(tx, ty);
                glVertex2f(xx, yy);
            }
            glEnd();
        }
        glPopMatrix();
    }
    
    public static void rect(float x, float y, float szelesseg, float magassag)
    {
        rect(x,y,szelesseg,magassag,0);
    }
    
    public static void rect(float x, float y, float szelesseg, float magassag, float forgatas)
    {        
        glPushMatrix();
        {
            glTranslatef(x,y,0);
            glRotatef(forgatas,0,0,1);

            glBegin(GL_QUADS);
            {
                glVertex2f(0,0);
                glVertex2f(0,magassag);
                glVertex2f(szelesseg,magassag);
                glVertex2f(szelesseg,0);
            }
            glEnd();
        }
        glPopMatrix();
    }
    
    public static void polygon(float x, float y, float szelesseg, float magassag, Texture t)
    {        
        glPushMatrix();
        {
            glTranslatef(x,y,0);
            
            //t.bind();
            glBegin(GL_QUADS);
            {
                glTexCoord2f(0,1);
                glVertex2f(0,0);
                glTexCoord2f(0,0);
                glVertex2f(0,magassag);
                glTexCoord2f(1,0);
                glVertex2f(szelesseg,magassag);
                glTexCoord2f(1,1);
                glVertex2f(szelesseg,0);
            }
            glEnd();
        }
        glPopMatrix();
    } 
    
    public static Texture loadTexture(String textureName)
    {
        try
        {
            return TextureLoader.getTexture("png", new FileInputStream(new File("res/" + textureName + ".png")));
        }
        catch(IOException ex)
        {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static void sleep(int ido)
    {
        try
        {
            Thread.sleep(ido);
        }
        catch(InterruptedException ex)
        {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}