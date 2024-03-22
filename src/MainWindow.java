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

public class MainWindow extends JFrame implements ActionListener
{
    JTextField ingredientsAdd;
    JLabel title;
    JButton submitButton;
    ArrayList<JButton> ingredients;
    ArrayList<JButton> recipes;
    ArrayList<String> ingredStrings;
    JPanel pIngred;
    JPanel pRecipes;
    Connector con;
    String queryStm;
    ResultSet rSet;

    public MainWindow(Connector conn)
    {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setSize(1000,800);
        this.setVisible(true);
        this.setResizable(false);
        this.setTitle("FoodFindr");
        con = conn;
        queryStm = "SELECT title FROM full_dataset_recipes WHERE";
        ingredientsAdd = new JTextField();
        submitButton = new JButton("+");
        ingredients = new ArrayList<JButton>();
        recipes = new ArrayList<JButton>();
        ingredStrings = new ArrayList<String>();
        pIngred = new JPanel();
        pRecipes = new JPanel();
        pIngred.setLayout(new FlowLayout());
        pIngred.setBackground(Color.lightGray);
        pRecipes.setBackground(Color.lightGray);
        pRecipes.setLayout(new FlowLayout());
        submitButton.addActionListener(this);
        title = new JLabel("FoodFindr");

        title.setFont(new Font("Consolas", Font.BOLD, 36));
        this.add(ingredientsAdd);
        this.add(pIngred);
        this.add(pRecipes);
        this.add(submitButton);
        this.add(title);

        title.setBounds(400, 50, 450, 50);
        submitButton.setBounds(400, 650, 50, 50);
        ingredientsAdd.setBounds(50,650,350,50);
        pIngred.setBounds(50, 130, 100, 500);
        pRecipes.setBounds(200, 130, 725, 500);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        
        if(e.getSource() == submitButton && !ingredStrings.contains(ingredientsAdd.getText()))
        {
            pRecipes.removeAll();
            pRecipes.revalidate();
            pRecipes.repaint();
            JButton ingredientItem = new JButton(ingredientsAdd.getText());
            pIngred.add(ingredientItem);
            ingredStrings.add(ingredientsAdd.getText());
            ingredients.add(ingredientItem);
            ingredientItem.addActionListener(this);
            
            buildQuery();
            execQuery();
            ingredientItem.revalidate();
        }
        else
        {
            for(JButton j : recipes)
            {
              
                if(e.getSource() == j)
                {
                    RecipeWindow rw = new RecipeWindow(con, j.getText());
                    System.out.println("worked");
                }
            }
            System.out.println("remove");
            for(JButton n : ingredients)
            {
                if(e.getSource() == n)
                {
                    pIngred.remove(n);
                    ingredients.remove(n);
                    String s = n.getText();
                    System.out.println(s);
                    ingredStrings.remove(n.getText());
                    this.repaint();
                    pRecipes.removeAll();
                    buildQuery();
                    execQuery();
                    System.out.println(ingredStrings.size());
                    if(ingredStrings.isEmpty())
                    {
                        System.out.println("EMPTY");
                        pRecipes.removeAll();
                    }
                    pRecipes.revalidate();
                    pRecipes.repaint();
                }
           }
          
        }
    }

    public void buildQuery()
    {
        int i = 1;
        queryStm = "SELECT title FROM full_dataset_recipes WHERE";
        for(String s : ingredStrings)
        {
            queryStm = queryStm + " NER LIKE '%" + s + "%'";
            if(ingredStrings.size() > i)
            {
                queryStm = queryStm + " AND ";
            }
            i++;
        }
    }

    public void execQuery()
    {
        
        recipes.removeAll(recipes);
        rSet = con.exec(queryStm);
        Connection connec = con.getConnec();
            
        System.out.println(queryStm);

        try 
        {
            Statement stmt = connec.createStatement();
            while(rSet.next())
            {
                String s = rSet.getString("title");
                JButton recipeButton = new JButton(s);
                pRecipes.add(recipeButton);
                recipes.add(recipeButton);
                recipeButton.addActionListener(this);
                pRecipes.revalidate();

            }
           
        } 
        catch (Exception e2) 
        {
            System.out.println(e2);
        }
    }
}