/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.base;

import java.io.IOException;
import java.util.ArrayList;
import org.lwjgl.input.Keyboard;
import org.newdawn.slick.opengl.Texture;

public final class Player extends GameObject
{
    private ArrayList ELOZO_ALLAPOT = new ArrayList();
    private int[][] ELOZO_ALLAPOT2 = new int[15][20];
    
    private int mRow;
    private int mColumn;
    private Texture t[];
    private int direction;
    
    public Player(float x, float y, int row, int column)
    {
        this.setX(x);
        this.setY(y);
        
        this.mRow = row;
        this.mColumn = column;
        
        this.t = new Texture[4];
        t[0] = Util.loadTexture("player3");
        t[1] = Util.loadTexture("player4");
        t[2] = Util.loadTexture("player2");
        t[3] = Util.loadTexture("player");
    }
    
    protected void move(int[][] levelMap, ArrayList<Element> elem) throws IOException
    {
        if(Keyboard.isKeyDown(Keyboard.KEY_UP))
        {
            direction = 0;
            
            if(levelMap[mRow - 1][mColumn] == 0)
            {
                setY(getY() + 40);
                Util.sleep(100);
                Level.incMoves();
                mRow--;
            }
            
            else if(levelMap[mRow - 1][mColumn] == 5 && levelMap[mRow - 2][mColumn] == 0)
            {
                saveState(levelMap, elem, this);
                for(Element e : elem)
                {
                    if(e.getY() == getY() + 40 && e.getX() == getX())
                    {
                        e.setY(e.getY() + 40);
                        break;
                    }
                }
                levelMap[mRow - 1][mColumn] = 0;
                levelMap[mRow - 2][mColumn] = 5;
                setY(getY() + 40);
                Util.sleep(100);
                Level.incMoves();
                Level.incPushes();
                mRow--;
            }
        }
        
        if(Keyboard.isKeyDown(Keyboard.KEY_DOWN))
        {
            direction = 1;
            
            if(levelMap[mRow + 1][mColumn] == 0)
            {
                setY(getY() - 40);
                Util.sleep(100);
                Level.incMoves();
                mRow++;
            }
            
            else if(levelMap[mRow + 1][mColumn] == 5 && levelMap[mRow + 2][mColumn] == 0)
            {
                saveState(levelMap, elem, this);
                for(Element e : elem)
                {
                    if(e.getY() == getY() - 40 && e.getX() == getX())
                    {
                        e.setY(e.getY() - 40);
                        break;
                    }
                }
                levelMap[mRow + 1][mColumn] = 0;
                levelMap[mRow + 2][mColumn] = 5;
                setY(getY() - 40);
                Util.sleep(100);
                Level.incMoves();
                Level.incPushes();
                mRow++;
            }
        }
        
        if(Keyboard.isKeyDown(Keyboard.KEY_LEFT))
        {
            direction = 2;
            
            if(levelMap[mRow][mColumn - 1] == 0)
            {
                //saveState(levelMap, elem, this);
                setX(getX() - 40);
                Util.sleep(100);
                Level.incMoves();
                mColumn--;
            }
            
            else if(levelMap[mRow][mColumn - 1] == 5 && levelMap[mRow][mColumn - 2] == 0)
            {
                saveState(levelMap, elem, this);
                for(Element e : elem)
                {
                    if(e.getX() == getX() - 40 && e.getY() == getY())
                    {
                        e.setX(e.getX() - 40);
                        break;
                    }
                }
                levelMap[mRow][mColumn - 1] = 0;
                levelMap[mRow][mColumn - 2] = 5;
                setX(getX() - 40);
                Util.sleep(100);
                Level.incMoves();
                Level.incPushes();
                mColumn--;
            }
        }
        
        if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT))
        {
            direction = 3;
            
            if(levelMap[mRow][mColumn + 1] == 0)
            {
                setX(getX() + 40);
                Util.sleep(100);
                Level.incMoves();
                mColumn++;
            }
            
            else if(levelMap[mRow][mColumn + 1] == 5 && levelMap[mRow][mColumn + 2] == 0)
            {
                saveState(levelMap, elem, this);
                for(Element e : elem)
                {
                    if(e.getX() == getX() + 40 && e.getY() == getY())
                    {
                        e.setX(e.getX() + 40);
                        break;
                    }
                }
                levelMap[mRow][mColumn + 1] = 0;
                levelMap[mRow][mColumn + 2] = 5;
                setX(getX() + 40);
                Util.sleep(100);
                Level.incMoves();
                Level.incPushes();
                mColumn++;
            }
        }
        
        if(Keyboard.isKeyDown(Keyboard.KEY_U))
        {
            loadState(levelMap, elem, this);
            Util.sleep(100);
        }
        //restart
        if(Keyboard.isKeyDown(Keyboard.KEY_R))
        {
            
        }
    }
    
    private void saveState(int[][] lm, ArrayList<Element> e, Player p)
    {
        ELOZO_ALLAPOT.removeAll(ELOZO_ALLAPOT);
        
        for(int i = 0; i < 15; i++)
        {
            for(int j = 0; j < 20; j++)
            {
                ELOZO_ALLAPOT2[i][j] = lm[i][j];
            }
        }
        
        for(Element tmp : e)
        {
            ELOZO_ALLAPOT.add(tmp.getX());
            ELOZO_ALLAPOT.add(tmp.getY());
        }
        ELOZO_ALLAPOT.add(p.getX());
        ELOZO_ALLAPOT.add(p.getY());
        ELOZO_ALLAPOT.add(mRow);
        ELOZO_ALLAPOT.add(mColumn);
        
        ELOZO_ALLAPOT.add(Level.moves);
        ELOZO_ALLAPOT.add(Level.pushes);
    }
    
    private void loadState(int[][] lm, ArrayList<Element> e, Player p)
    {
        if(ELOZO_ALLAPOT.isEmpty())
            return;
        
        for(int i = 0; i < 15; i++)
        {
            for(int j = 0; j < 20; j++)
            {
                lm[i][j] = ELOZO_ALLAPOT2[i][j];
            }
        }
        
        int i = 0;
        for(Element tmp : e)
        {
            tmp.setX((float)ELOZO_ALLAPOT.get(i)); i++;
            tmp.setY((float)ELOZO_ALLAPOT.get(i)); i++;
        }
        
        p.setX((float)ELOZO_ALLAPOT.get(i)); i++;
        p.setY((float)ELOZO_ALLAPOT.get(i)); i++;
        p.mRow = (int)ELOZO_ALLAPOT.get(i); i++;
        p.mColumn = (int)ELOZO_ALLAPOT.get(i); i++;
        Level.moves = (int)ELOZO_ALLAPOT.get(i); i++;
        Level.pushes = (int)ELOZO_ALLAPOT.get(i); i++;
        
        ELOZO_ALLAPOT.removeAll(ELOZO_ALLAPOT);
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
        switch(direction)
        {
            case 0:
                t[0].bind();
                Util.polygon(getX(), getY(), 40, 40, t[0]);
                break;
            case 1:
                t[1].bind();
                Util.polygon(getX(), getY(), 40, 40, t[1]);
                break;
            case 2:
                t[2].bind();
                Util.polygon(getX(), getY(), 40, 40, t[2]);
                break;
            case 3:
                t[3].bind();
                Util.polygon(getX(), getY(), 40, 40, t[3]);
                break;
        }
    }
}