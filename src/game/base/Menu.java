/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.base;

import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public final class Menu extends JFrame implements ActionListener
{
    private final JLabel ujJatekLabel;
    private final JLabel folytatasLabel;
    private final JLabel kilepesLabel;
    private final JButton ujJatekBtn;
    private final JButton folytatasBtn;
    private final JButton kilepesBtn;
    
    public static void main(String args[])
    {
        //new Game().run();
        new Menu();
    }
    
    public Menu()
    {
        setSize(450, 650);
        setContentPane(new JLabel(new ImageIcon("res/foMenu.png")));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        
        ujJatekLabel = new JLabel(new ImageIcon("res/ujJatek.png"));
        ujJatekLabel.setBounds(50, 50, 150, 150);
        add(ujJatekLabel);
        
        folytatasLabel = new JLabel(new ImageIcon("res/folytatas.png"));
        folytatasLabel.setBounds(50, 275, 150, 150);
        add(folytatasLabel);
        
        kilepesLabel = new JLabel(new ImageIcon("res/kilepes.png"));
        kilepesLabel.setBounds(getWidth() - 163, getHeight() - 254, 150, 150);
        add(kilepesLabel);
        
        ujJatekBtn = new JButton("Új játék");
        ujJatekBtn.setBounds(225, 100, 100, 50);
        ujJatekBtn.addActionListener(this);
        add(ujJatekBtn);
        
        folytatasBtn = new JButton("Pályaválasztás");
        folytatasBtn.setBounds(225, 325, 150, 50);
        folytatasBtn.addActionListener(this);
        add(folytatasBtn);
        
        kilepesBtn = new JButton("Kilépés");
        kilepesBtn.setBounds(312, 550, 100, 50);
        kilepesBtn.addActionListener(this);
        add(kilepesBtn);
        
        setVisible(true);
    }
    
    void setCenter(JFrame ablak)
    {
        Point center = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
        int x =(int) center.getX() - (ablak.getWidth()/2);
        int y =(int) center.getY() - (ablak.getHeight()/2);
        Point ablakCenter = new Point(x, y);
        ablak.setLocation(ablakCenter);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == ujJatekBtn)
        {
            setVisible(false);
            new Game().run();
            setVisible(true);
        }
        if(e.getSource() == folytatasBtn)
        {
            try
            {
                BufferedReader br = new BufferedReader(new FileReader("res/maps/stats.txt"));
                ArrayList<JButton> l = new ArrayList<>();
                HashMap hm = new HashMap();
                
                JFrame j = new JFrame();
                j.setBounds(0, 0, 1000, 500);
                j.setLayout(null);

                String s;
                int i = 0;
                boolean[] isComplete = new boolean[3];
                int[] numberOfMoves = new int[3];
                int[] numberOfPushes = new int[3];
                while((s = br.readLine()) != null)
                {
                    String s0 = s.substring(0, s.indexOf(":"));
                    if("comp".equals(s0))
                    {
                        String s1 = s.substring(s.indexOf(":") + 1);
                        isComplete[i] = "yes".equals(s1);
                    }
                    else if("move".equals(s0))
                    {
                        String s1 = s.substring(s.indexOf(":") + 1);
                        numberOfMoves[i] = Integer.parseInt(s1);
                    }
                    else if("push".equals(s0))
                    {
                        String s1 = s.substring(s.indexOf(":") + 1);
                        numberOfPushes[i] = Integer.parseInt(s1);
                        i++;
                    }
                }
                
                i = 0;
                while(i < 3)
                {
                    if(isComplete[i])
                    {
                        l.add(new JButton(new ImageIcon("res/maps/" + (i + 1) + ".png")));
                        l.get(i).setBounds(75 + i * 200, 75, 150, 250);
                        l.get(i).addActionListener(new ActionListener()
                        {
                            @Override
                            public void actionPerformed(ActionEvent e)
                            {
                                String s1 = e.getSource().toString();
                                String s2 = s1.substring(s1.indexOf(".png") - 1, s1.indexOf(".png"));
                                Game.LEVEL_NUMBER = Integer.parseInt(s2);
                                Game g = new Game();
                                j.setVisible(false);
                                setVisible(false);
                                g.run();
                                setVisible(true);
                                j.setVisible(true);
                            }
                        });
                        j.add(l.get(i));
                        
                        JLabel tmp1 = new JLabel("Lépések: " + numberOfMoves[i]);
                        tmp1.setBounds(75 + i * 200, 210, 150, 250);
                        j.add(tmp1);
                        
                        JLabel tmp2 = new JLabel("Mozgatások: " + numberOfPushes[i]);
                        tmp2.setBounds(75 + i * 200, 230, 150, 250);
                        j.add(tmp2);
                    }
                    else
                    {
                        l.add(new JButton(new ImageIcon("res/maps/0.png")));
                        l.get(i).setBounds(75 + i * 200, 75, 150, 250);
                        j.add(l.get(i));
                        
                        JLabel tmp1 = new JLabel("Nem elérhető!");
                        tmp1.setBounds(75 + i * 200, 210, 150, 250);
                        j.add(tmp1);
                    }
                    
                    j.setVisible(true);
                    i++;
                }
                br.close();
            }
            catch(FileNotFoundException ex)
            {
                Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
            }
            catch(IOException ex) 
            {
                Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
            }
            catch(StringIndexOutOfBoundsException ex)
            {
                System.out.println(ex.toString());
            }
        }
        if(e.getSource() == kilepesBtn)
        {
            System.exit(0);
        }
    }
}