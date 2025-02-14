import java.awt.*;

/**
 * Created by chales on 11/6/2017.
 */
public class Bird {



    //allows to set the size of bird and specify other peramiters like if it alive
    public int xpos;                //the x position of the bird
    public int ypos;                //the y position of the bird
    public int dx;                    //the bird speed in the x direction
    public int dy;                    //the bird spee in the y direction
    public int width;
    public int height;
    public boolean isAlive;            //a boolean to show if bird is alive or dead
    public Rectangle rec;
    public boolean iscrash;


    //bird peramiters eg if it alive
    public Bird(int pXpos, int pYpos) {
        xpos = pXpos;
        ypos = pYpos;
        dx = 2;
        dy = 1;
        width = 60;
        height = 60;
        isAlive = true;
        rec=new Rectangle(xpos,ypos,width, height);
        iscrash = false;
    } // constructor

    //The move method.  Everytime this is run (or "called") the hero's x position and y position change by dx and dy
    public void move() {
        xpos = xpos + dx;
        ypos = ypos + dy;
        rec=new Rectangle(xpos,ypos,width, height);

    }


//changes signs of dx and dy when they hit the edges of the screen to make them bounce
    public void bounce() {
        if (xpos > 1000) {
            dx = -dx;

        }
        if (ypos > 690) {
            dy = -dy;

        }

        if (ypos < 0) {
            dy = -dy;
        }


        if (xpos < 2) {
            dx = -dx;
        }
        xpos = xpos + dx;
        ypos = ypos + dy;
        rec=new Rectangle(xpos,ypos,width, height);

    }
    //keeps bird from moving up and down
    public void smallbirdMoves(){
        dx=4;
        dy=0;

    }
//if a bird hits any side of the screen this method makes it so that the bird is replaced at the opposite side so it wraps
    public void wrap() {
        if (xpos > 1000) {
            xpos=1;

        }
        if (ypos > 700) {
            ypos=1;

        }
        if (ypos < 0) {
            ypos=700;

        }
        if (xpos < 1) {
            xpos=700;

        }
        // re adds the rectangle
        rec=new Rectangle(xpos,ypos,width, height);
    }

}