/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.base;

import org.newdawn.slick.opengl.Texture;

public class Element extends GameObject
{
    private Texture t[];
    public boolean isFinish;
    
    public Element(float x, float y, int row, int column, boolean isFinish)
    {
        super.setX(x);
        super.setY(y);
        
        this.t = new Texture[2];
        t[0] = Util.loadTexture("element5");
        t[1] = Util.loadTexture("element9");
        
        this.isFinish = isFinish;
    }
    
    @Override
    public void getInput()
    {
        
    }

    @Override
    public void update()
    {
        
    }

    @Override
    public void render()
    {
        if(!isFinish)
        {
            t[0].bind();
            Util.polygon(getX(), getY(), 40, 40, t[0]);
        }
        else
        {
            t[1].bind();
            Util.polygon(getX(), getY(), 40, 40, t[1]);
        }
    }
}