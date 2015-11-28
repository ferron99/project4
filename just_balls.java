////Nick Ferro
//// Project 4   11-27-15    
//// version with just balls for the purpose
//// of getting the clean reset working
Ball[] fer;
PTable t;


//SETUP: object stuff
void setup() {
  size( 960, 540 );
  t= new PTable();
  t.left=   75;
  t.right=  width-75;
  t.top=    175;
  t.bottom= height-75;
  t.middle= t.left + (t.right-t.left) / 2;
  t.horizon= (height/4)-20;
  t.wall=true;
  //
  fer= new Ball[16];
  for (int i = 0; i < fer.length; i++) {
    fer[i] = new Ball(random(t.middle+20,t.right-15), random(t.top+15,t.bottom-15), 30, i, fer);
  }
  
  
  reset();
}

//RESET function for balls
void reset() { 
  t.wall = true;
  for (Ball b: fer){
    b.reset();
    b.resetCheck();
  }
}


//main DRAW function
void draw() {
  background( 102,178,205 );
  t.tableDisplay();
  //drawGrass();
  //drawClouds();
  for (Ball b : fer) {
    //b.resetCheck();
    b.collide();
    b.move();
    b.show();  
  }
  //buttons();
  //birds();
  //rats();
  //frame +=1;
  //showScore();
}

// KEY PRESSED stuff: exit, reset, other buttons
void keyPressed() {
  if (key == 'q') exit();
  if (key == 'r') reset();
  if (key == 'w') t.wall = false;
}

//BALL CLASS
class Ball {
  // PROPERTIES
  float x,y;
  float diameter;
  float dx, dy;
  float r,g,b;
  int id;
  Ball[] others;

  Ball(float xin, float yin, float din, int idin, Ball[] oin) {
    x = xin;
    y = yin;
    diameter = din;
    id = idin;
    others = oin;
    dx=random(-2,2);
    dy=random(-2,2);
    r=random(0,255);
    g=random(0,255);
    b=random(0,255);
  }
  // METHODS
  void collide(){
    for (int i = id+1; i < fer.length; i++) {
      float distance = dist(x,y,others[i].x,others[i].y);
      if (distance < diameter) {
        float tmp;
        tmp = dx; dx = others[i].dx; others[i].dx = tmp;
        tmp = dy; dy = others[i].dy; others[i].dy = tmp;
      }
    }
  }
  void show() {
    stroke(0);
    strokeWeight(1);
    fill(r,g,b);
    ellipse( x,y, diameter, diameter );
    fill(0);
    text(id+1,x-5,y);
  }
  
  void move() {
    if (t.wall) {
      if (x>t.right-4 || x<t.middle+20) {  dx=  -dx; }    //where the balls bounce off of depending on
    }else{                                                //whether the wall is engaged or not
      if (x>t.right-4 || x<t.left+4) {  dx=  -dx; }
    }
    if (y>t.bottom-4 || y<t.top+4) {  dy=  -dy; }
    x=  x+dx;
    y=  y+dy;
  }
  void reset(){
    x=random(t.middle+20,t.right-15); 
    y=random(t.top+15,t.bottom-15);
    dx=random(-2,2);
    dy=random(-2,2);
  }
  
  void resetCheck(){
     for(int i = id+1; i < fer.length; i++){
     float distance = dist(x,y,others[i].x,others[i].y);
     if (distance < diameter) {
       reset();
     }
   }
  }
}

//PTABLE CLASS
class PTable {
  //PROPERTIES
  float left, right, top, bottom, middle;
  float horizon;
  boolean wall;
  
  //METHODS
  void tableDisplay(){
    noStroke();
    fill( 222,184,135 );
    rect( 0,horizon, width,(height*3/4)+25 );
    fill( 100, 250, 100 );    
    strokeWeight(20);
    stroke( 127, 0, 0 );      // walls of pool talbe
    rectMode( CORNERS );
    rect( left-20, top-20, right+20, bottom+20 );
    stroke(0);
    strokeWeight(1);
    
    if (wall) {
      stroke( 0, 127, 0 );
      strokeWeight(10);
      line( middle,top-4, middle,bottom+4 );
    }
  }
} 
