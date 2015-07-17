/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.base;

import org.newdawn.slick.opengl.Texture;

public class BrickFinish extends Brick
{
    private Texture t;
    
    public BrickFinish() {}
    
    public BrickFinish(float x, float y, String type, Texture t)
    {
        super.setX(x);
        super.setY(y);
        
        this.t = t;
        this.type = type;
    }

    @Override
    public int hashCode() {
        int hash = 10;
        return hash;
    }

    @Override
    public boolean equals(Object obj)
    {
        if(obj == null)
            return false;
        
        return getClass() == obj.getClass();
    }
    
    @Override
    public void getInput()
    {}

    @Override
    public void update()
    {}

    @Override
    public void render()
    {
        t.bind();
        Util.polygon(getX(), getY(), 40, 40, t);
    }
    
    public void setHasElement(boolean hasElement)
    {
        this.hasElement = hasElement;
    }
    
    public boolean getHasElement()
    {
        return this.hasElement;
    }
}