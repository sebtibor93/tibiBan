/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.base;

public abstract class GameObject
{
    private float x;
    private float y;
    public String type;
    public boolean hasElement;
    
    public abstract void getInput();
    public abstract void update();
    public abstract void render();
    
    public float getX()
    {
        return x;
    }
    
    public void setX(float newX)
    {
        x = newX;
    }
    
    public float getY()
    {
        return y;
    }
    
    public void setY(float newY)
    {
        y = newY;
    }
}