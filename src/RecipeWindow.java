import java.util.ArrayList;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.sql.*;

import javax.swing.*;

public class RecipeWindow extends JFrame
{
    JLabel title;
    JTextArea ingredients;
    JTextArea directions;
    JTextArea link;

    JPanel pan;
    String originRecipe;
    Connector con;
    String queryStm;
    ResultSet rSet;
    
    public RecipeWindow(Connector conn, String recipe)
    {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000,800);
        this.setVisible(true);
        this.setResizable(false);
        this.setTitle("FoodFindr");
        this.setLayout(null);

        originRecipe = recipe;
        con = conn;

        title = new JLabel("Name:");
        ingredients = new JTextArea("Ingredients:");
        directions = new JTextArea("Directions:");
        link = new JTextArea("Link:");
        pan = new JPanel();
        pan.setLayout(new GridLayout(5, 1));
        pan.setBounds(50, 50, 900, 800);
        pan.add(title);
        pan.add(ingredients);
        pan.add(directions);
        pan.add(link);

        this.add(pan);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        ingredients.setLineWrap(true);
        ingredients.setWrapStyleWord(true);
        ingredients.setEditable(false);
        ingredients.setFont(new Font("Consolas", Font.BOLD, 20));
        directions.setLineWrap(true);
        directions.setWrapStyleWord(true);
        directions.setEditable(false);
        directions.setFont(new Font("Consolas", Font.BOLD, 20));
        title.setFont(new Font("Consolas", Font.BOLD, 35));
        link.setFont(new Font("Consolas", Font.BOLD, 15));
        link.setEditable(false);
        execQuery();
    }

    public void execQuery()
    {
        queryStm = "SELECT * FROM full_dataset_recipes WHERE title LIKE '" + originRecipe + "'";
        rSet = con.exec(queryStm);
        Connection connec = con.getConnec();
            
        System.out.println(queryStm);

        try 
        {
            Statement stmt = connec.createStatement();
            while(rSet.next())
            {
                String s = rSet.getString("title");
                title.setText(s);
                s = rSet.getString("ingredients");
                ingredients.setText("Ingredients:" + s);
                s = rSet.getString("directions");
                directions.setText("Directions:" + s);
                s = rSet.getString("link");
                link.setText("Link:" + s);
            }
            this.repaint();
            this.revalidate();
        } 
        catch (Exception e2) 
        {
            System.out.println(e2);
        }
    }
}
