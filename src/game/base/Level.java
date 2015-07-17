package game.base;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.logging.Logger;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.opengl.Texture;

public class Level extends GameObject
{
    public static boolean LEVEL_COMPLETE = true;
    
    private GameObject[][] levelMatrix;
    private int[][] levelMap;
    private ArrayList<Element> elem;
    private ArrayList<BrickFinish> dest;
    private Player player;
    private int finishNum = 0;
    public static int moves;
    public static int pushes;
    
    public Level(int levelNumber) throws IOException
    {
        this.levelMatrix = new GameObject[15][20];
        this.levelMap = new int[15][20];
        this.elem = new ArrayList<>();
        this.dest = new ArrayList<>();
    }
    
    public static void incMoves()
    {
        moves++;
    }
    
    public static void incPushes()
    {
        pushes++;
    }
    
    public void setLevelMatrix(ArrayList<Texture> t) throws IOException
    {
        BufferedReader br = new BufferedReader(new FileReader("maps/" + Game.LEVEL_NUMBER + ".txt"));
        
        elem.removeAll(elem);
        dest.removeAll(dest);
        
        String s;
        char c[];
        
        int i = 0;
        for(int y = 560; y >= 0; y -= 40)
        {
            if((s = br.readLine()) == null) break;
            c = s.toCharArray();
            
            int j = 0;
            int k = 0;
            for(int x = 0; x < 800; x += 40)
            {
                if(c[k] == '%')
                {
                    levelMatrix[i][j] = null;//new Wall(x, y, t.get(0));
                    levelMap[i][j] = 1;
                }
                
                if(c[k] == '#')
                {
                    levelMatrix[i][j] = new Wall(x, y, t.get(0));
                    levelMatrix[i][j].render();
                    levelMap[i][j] = 2;
                }
                
                else if(c[k] == ' ')
                {
                    levelMatrix[i][j] = new BrickViable(x, y, "brick2", t.get(1));
                    levelMap[i][j] = 0;
                }
                
                else if(c[k] == 'X')
                {
                    this.dest.add(new BrickFinish(x, y, "brick3", t.get(2)));
                    levelMatrix[i][j] = new BrickViable(x, y, "brick2", t.get(1));
                    levelMatrix[i][j].render();
                    levelMap[i][j] = 0;
                    finishNum++;
                }
                
                else if(c[k] == 'P')
                {
                    this.player = new Player(x, y, i, j);
                    levelMatrix[i][j] = new BrickViable(x, y, "brick2", t.get(1));
                    levelMatrix[i][j].render();
                    levelMap[i][j] = 0;
                }
                else if(c[k] == 'E')
                {
                    this.elem.add(new Element(x, y, i, j, false));
                    levelMatrix[i][j] = new BrickViable(x, y, "brick2", t.get(1));
                    levelMatrix[i][j].render();
                    levelMap[i][j] = 5;
                }
                else if(c[k] == 'A')
                {
                    this.dest.add(new BrickFinish(x, y, "brick3", t.get(2)));
                    this.elem.add(new Element(x, y, i, j, true));
                    finishNum++;
                    levelMatrix[i][j] = new BrickViable(x, y, "brick2", t.get(1));
                    levelMatrix[i][j].render();
                    levelMap[i][j] = 5;
                }
                
                j++;
                k++;
            }
            i++;
        }
    }
    
    @Override
    public void getInput()
    {
        try
        {
            player.move(levelMap, elem);
        }
        catch(IOException ex)
        {
            Logger.getLogger(Level.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update()
    {
        int tmp = 0;
        for(Element e : elem)
        {
            e.isFinish = false;
            for(BrickFinish bf : dest)
            {
                if(bf.getX() == e.getX() && bf.getY() == e.getY())
                {
                    tmp++;
                    e.isFinish = true;
                    
                    if(tmp == finishNum)
                    {
                        //kezelni a stats.txt fájlt
                        /*try
                        {
                            RandomAccessFile raf = new RandomAccessFile("res/maps/stats.txt", "rw");
                            raf.seek(10);
                            raf.write(10);
                        }
                        catch(IOException ex)
                        {
                            Logger.getLogger(Level.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                        }*/
                        Game.IS_LEVEL_COMPLETE = true;
                        
                    }
                    
                    break;
                }
            }
        }
    }

    @Override
    public void render()
    {
        Game.getTexture(5).bind();
        Util.polygon(0, 0, Display.getWidth(), Display.getHeight(), Game.getTexture(5));
        for(int i1 = 0; i1 < 15; i1++)
        {
            for(int i2 = 0; i2 < 20; i2++)
            {
                if(levelMatrix[i1][i2] != null)
                    levelMatrix[i1][i2].render();
            }
        }
        for(BrickFinish bf : dest)
            bf.render();
        for(Element e : elem)
            e.render();
        player.render();
    }
}