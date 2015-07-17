/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.base;

import org.newdawn.slick.opengl.Texture;

public class Wall extends GameObject
{
    private Texture t;
    
    public Wall(float x, float y, Texture t)
    {
        super.setX(x);
        super.setY(y);
        
        this.t = t;
    }

    @Override
    public void getInput() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void render()
    {
        t.bind();
        Util.polygon(getX(), getY(), 40, 40, t);
    }
    
}
