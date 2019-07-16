
package sierpinski;

import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.lang.Thread;

public class Sierpinski extends JPanel {
    
    static int height = 1000;
    static int width = 1000;
    
    static int maximumLevel = 8;
    static boolean animateAllLevelsUpToMaximum = true;
    static int waitTimeBetweenFrames = 2000;
    static int currLevel = 1;
    
    static boolean labelsOn = true;
    static int label = 1;
    
    
    public void drawSierpinski( Graphics2D g, int level, Point p1, Point p2, Point p3 ) {
       g.setColor( Color.yellow);
       
       //BASE CASE
       if (level == 1) {
            fillTriangle(g, p1, p2, p3);
            label++;
        } 
       
        //RECURSIVE CALLS
        else {
            //CALCULATE THE 3 MIDPOINTS 
            Point mid12 = midpoint(p1, p2);
            Point mid23 = midpoint(p2, p3);
            Point mid31 = midpoint(p3, p1);

            // recurse on 3 triangular areas
            drawSierpinski(g, level - 1,   p1,      mid12,    mid31 );
            drawSierpinski(g, level - 1,   mid12,   p2,       mid23 );
            drawSierpinski(g, level - 1,   mid31,   mid23,    p3 );
        }
    }
    
    public void drawSierpinskiSquare( Graphics2D g, int level, Point p1, Point p2, Point p3, Point p4){
        g.setColor( Color.yellow);
        
        //BASE CASE
        if (level == 1){
            fillSquare(g, p1, p2);
            label ++;
        }
        //RECURSIVE CALLS
        else {
            int thirdLength = (p2.x-p1.x) / 3;
            
            int x1 = p1.x + thirdLength;
            int x2 = p1.x + 2*thirdLength;
            int y1 = p1.y + thirdLength;
            int y2 = p1.y + 2*thirdLength;
            
            Point s1,s2,s3,s4,s5,s6,s7,s8,s9,s10,s11,s12;
            s1 = new Point(x1, p1.y);
            s2 = new Point(x2, p1.y);
            s3 = new Point(p1.x, y1);
            s4 = new Point(x1, y1);
            s5 = new Point(x2, y1);
            s6 = new Point(p2.x, y1);
            s7 = new Point(p1.x, y2);
            s8 = new Point(x1, y2);
            s9 = new Point(x2, y2);
            s10 = new Point(p2.x, y2);
            s11 = new Point(x1, p3.y);
            s12 = new Point (x2, p3.y);
            
            drawSierpinskiSquare( g, level-1, p1, s1, s4, s3);
            drawSierpinskiSquare( g, level-1, s1, s2, s4, s5);
            drawSierpinskiSquare( g, level-1, s2, p2, s6, s5);
            drawSierpinskiSquare( g, level-1, s3, s4, s8, s7);
            drawSierpinskiSquare( g, level-1, s5, s6, s10, s9);
            drawSierpinskiSquare( g, level-1, s7, s8, s11, p3);
            drawSierpinskiSquare( g, level-1, s8, s9, s12, s11);
            drawSierpinskiSquare( g, level-1, s9, s10, p4, s12);
            
            
//            int [] xValues = {p1.x, x1, x2, p2.x};
//            int [] yValues = {p1.x, y1, y2, p4.y};
//            
//            Point [][] allPoints = new Point [4][4];
//            
//            for (int i = 0; i<allPoints.length; i++){
//                for (int j = 0; j<allPoints[i].length; j++){
//                    allPoints[i][j] = new Point (xValues[i], yValues[j]);
//  
//                }
//            }
//            
//             for (int i = 0; i<allPoints.length; i++){
//                for (int j = 0; j<allPoints[i].length; j++){
//                    System.out.print(allPoints[i][j].x + "," + allPoints[i][j].y + "\t");
//                }
//                System.out.println("");
//            }
//             System.out.println("");
//             
//             
//            int num = 0;
//            for (int i = 0; i<allPoints.length-1; i++){
//                for (int j = 0; j<allPoints[i].length-1; j++){
//                    System.out.println("this is " + num);
//                         drawSierpinskiSquare( g, level-1, allPoints[i][j], allPoints[i+1][j], allPoints[i][j+1], allPoints[i+1][j+1]);
//                         num++;
//                    
//                }
//            }
            
        }
    }

     //CALCULATES THE MIDPOINT BETWEEN TWO GIVEN POINTS
    public static Point midpoint(Point p1, Point p2) {
        return new Point((p1.x + p2.x) / 2, (p1.y + p2.y) / 2);
    }
    
    public static void fillSquare( Graphics g, Point p1, Point p2){
        int width = p2.x - p1.x;
        g.fillRect(p1.x, p1.y, width, width);
        
        if (labelsOn && currLevel < 7){
               g.setColor(Color.red);
           
                int xLabel = (p1.x + p2.x)/2;
                int yLabel = (p1.y + p2.y)/2;
        
                g.drawString( Integer.toString(label), xLabel, yLabel );
        }
    }
    public static void fillTriangle( Graphics g, Point p1, Point p2, Point p3 ) {
           int[] xvalues = {p1.x, p2.x, p3.x};
           int[] yvalues = {p1.y, p2.y, p3.y};
           
           Polygon triangle = new Polygon( xvalues, yvalues, 3 );
           g.fillPolygon( triangle );
           
           //IF LABELS OPTION IS CHOSEN, WRITE THE NUMBER OF THE TRIANGLE AS ITS DRAWN
           if ( labelsOn && currLevel < 7 ) {
                g.setColor(Color.red);
           
                int xLabel = (p1.x + p2.x + p3.x)/3;
                int yLabel = (p1.y + p2.y + p3.y)/3;
        
                g.drawString( Integer.toString(label), xLabel, yLabel );
           }
    }
    
    
    public void paint( Graphics g ) {
        
        Graphics2D g2 = (Graphics2D) g;
        
        int padding = 50;
        
        Point startA = new Point( padding, padding );
        Point startB = new Point( width-padding, padding);
        Point startC = new Point( width-padding, height-padding );
        Point startD = new Point( padding, height-padding);
        drawSierpinskiSquare( g2, currLevel, startA, startB, startC, startD  );     
   }
    
    
    public static void main(String[] args) {
        JFrame screen = new JFrame();
        
        screen.add( new Sierpinski() );
        screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        screen.setBackground(Color.black);
        screen.setSize( width, height );
        screen.setVisible(true);
        
        if ( animateAllLevelsUpToMaximum ) {
            
            for ( int i=1; i < maximumLevel; i++ ) {
                sleep( waitTimeBetweenFrames );
                label = 1;
                screen.repaint();
                currLevel++;
            }  
        }
        
        else
            currLevel = maximumLevel;
   }

    
    public static void sleep( int duration ) {
        try {
              Thread.sleep( duration );
            }
        catch (Exception e) {
            }
    }
    

}