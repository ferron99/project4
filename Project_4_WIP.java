////Nick Ferro
//// Project 4   12-1-15    
//// WIP
Ball[] fer;
Button[] nic;
float[] dist = new float[15]; 
float[] Ycoor = new float[15];
PTable t;


//SETUP: object stuff
void setup() {
  size( 1200, 675 );
  t= new PTable();
  t.left=   75;
  t.right=  width-300;
  t.top=    200;
  t.bottom= height-75;
  t.middle= t.left + (t.right-t.left) / 2;
  t.horizon= (height/4)-20;
  t.wall=true;
  //
  fer= new Ball[16];
  for (int i = 0; i < 1; i++) {
    fer[i] = new Ball(width/4, (t.bottom+t.top)/2, 30, i, fer);
  }
  fer[0].dx = 0;
  fer[0].dy = 0;
  fer[0].r = 255;
  fer[0].g = 255;
  fer[0].b = 255;  
  for (int i = 1; i < fer.length; i++) {
    fer[i] = new Ball(random(t.middle+20,t.right-15), random(t.top+15,t.bottom-15), 30, i, fer);
  }
  
  for (int i=0; i < dist.length; i++) {
    dist[i] = 0;
  }
  for (int i=0; i < Ycoor.length; i++) {
    Ycoor[i] = 0;
  }
  
  nic= new Button[7];
  float buttonX=50;
  for (int i=0; i < nic.length; i++) {
    nic[i]= new Button(buttonX, 5);
    buttonX+=100;
  }
  nic[0].words= "Reset";
  nic[1].words= "Wall";
  nic[2].words= "Bird";
  nic[3].words= "Rat";
  nic[4].words= "Closest";
  nic[5].words= "List";
  nic[6].words= "Sort";
    
  //reset();
}

//RESET function for balls
void reset() { 
  t.wall = true;
  for (Ball b: fer){
    b.reset();
    b.resetCheck();
  }
  resetCue();
}

void resetCue(){
  fer[0].x = width/4;
  fer[0].y = (t.bottom+t.top)/2;
  fer[0].dx = 0;
  fer[0].dy = 0;
}


//main DRAW function
void draw() {
  background( 102,178,205 );
  t.tableDisplay();
  for (Ball b : fer) {
    //b.resetCheck();
    b.collide();
    b.move();
    b.show();  
  }
  buttons();
  for (int i=0; i < dist.length; i++) {
    dist[i] = dist(fer[i+1].x, fer[i+1].y, fer[0].x, fer[0].y);
  }
  for (int i=0; i < Ycoor.length; i++) {
    Ycoor[i] = fer[i+1].y;
  }

  
  //
  fill(0);
  textSize(15);
  show( dist, dist.length, width-260, t.horizon+25);
  closest();
  //srt(Ycoor, Ycoor.length);
  text( smallY(), 50, 100);
  text( whereY()+1, 50, 125);
}

int whereClose( float a[], int m ) {
  int k=0;
  for (int i=1; i<m; i++) {
    if( a[i] < a[k] )  {
      k=  i;
    }
  }
  return k;
}

int whereY() {
  int k=0;
  for (int i=1; i<Ycoor.length; i++) {
    if( Ycoor[i] < Ycoor[k] )  {
      k=  i;
    }
  }
  return k;
}

float smallY() {
  float smY;

  smY=  Ycoor[0];
  for (int i=1; i<Ycoor.length; i++) {
    if( Ycoor[i] < smY )  {
      smY=  Ycoor[i];
    }
  }

  return smY;
}

void srt( float a[], int many ) {
 
  int m=  many;
  int j;
  int k;        // index of closest Y
 
  for (m=many; m>1; m=m-1 ) { 
    k=  whereY();
    j=  m-1;
    swap( Ycoor, j, k );
  }

}
void swap( float a[], int j, int k ) {
  float tmp;
  tmp=  a[k];
  a[k]=  a[j];
  a[j]=  tmp;
}

void closest(){
  if (nic[4].ring == true){
    fill(0);
    strokeWeight(4);
    stroke(255);
    rect((width/2)-10, 100, 175, 50);
    int close;
    close = whereClose(dist, dist.length)+1;
    fill(fer[close].r,fer[close].g,fer[close].b);
    text("The closest ball is "+close, width/2, 125);
    noFill();
    stroke(255);
    ellipse(fer[close].x, fer[close].y, 32,32);
  }
}




void buttons(){
  for (int i=0; i<nic.length; i++) {
    nic[i].buttonDisplay();
  }
}

void show(float a[], int m, float x, float y ) {
  if (nic[5].list == true){
    float ytmp;
    ytmp = y;
    for (int i=0; i<m; i++) {
      text( i+1, x, ytmp );
      ytmp += 15;
    }
    x += 25;
    ytmp = y;
    for (int i=0; i<m; i++) {
      int rx = round(fer[i+1].x);
      text( rx, x, ytmp );
      ytmp += 15;
    }
    x += 35;
    ytmp = y;
    for (int i=0; i<m; i++) {
      int ry = round(fer[i+1].y);
      text( ry, x, ytmp );
      ytmp += 15;
    }
    x += 35;
    ytmp = y;
    for (int i=0; i<m; i++) {
      int ry = round(dist[i]);
      text( ry, x, ytmp );
      ytmp += 15;
    }
    x += 50;
    ytmp = y;
    for (int i=0; i<m; i++) {
      int ry = round(Ycoor[i]);
      text( ry, x, ytmp );
      ytmp += 15;
    }
  }
}  

// KEY PRESSED stuff: exit, reset, other buttons
void keyPressed() {
  if (key == 'q') exit();
  if (key == 'r') reset();
  if (key == 'w') t.wall = false;
}

void mousePressed() {
  nic[0].buttonReset();
  nic[1].buttonWall();
  
  
  nic[4].buttonClose();
  nic[5].buttonList();
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
    text(id,x-5,y);
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
    rect( 0, horizon, width, height );
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

class Button {
  //PROPERTIES
  float x,y;
  String words;
  boolean counter;
  boolean ring;
  boolean list;
  int buffer;
  //CONSTRUCTOR
  Button(float tempX, float tempY) {
    x = tempX;
    y = tempY;
    counter = false;
    ring = false;
    list = false;
    buffer = 0;
  }
  //METHODS
  void buttonDisplay(){
    fill(0);
    strokeWeight(4);
    stroke(255);
    rectMode( CORNER );
    rect(x, y, 80, 40);
    fill(255);
    text( words, x+15, y+20);
  }
  //resets balls and wall
  void buttonReset(){
    if (mouseX >x && mouseX<x+80 && mouseY>y && mouseY<y+40){
    reset();
  }
 }
 //disables wall
 void buttonWall(){
  if (mouseX >x && mouseX<x+80 && mouseY>y && mouseY<y+40){
    t.wall = false;
  }
 }
 void buttonClose(){
  if (mouseX >x && mouseX<x+80 && mouseY>y && mouseY<y+40){
    ring = true;
  }
 }
 void buttonList(){
  if (mouseX >x && mouseX<x+80 && mouseY>y && mouseY<y+40){
    list = true;
  }
 }
 //multifunctional button. Sends the bird flying on the first click
 //and drops a bomb on the second click, then does nothing untill the
 //bird completes its flight.
 //void buttonBird(){
 //  if (mouseX >x && mouseX<x+80 && mouseY>y && mouseY<y+40){
 //    brd.fly = true;
 //    counter = true;
 //    if (buffer > 1){
 //      brd.drop = true;
 //    }
 //  }
 //}
 //Buffer counter for the bird button. Button will only enable drop if at least
 //two frames have passed since fly was enabled.
 //void buttonBirdBuffer(){
 //  if (counter == true) {
 //    buffer +=1;
 //      }   
 //    }
 //void buttonRat(){
 //  if (mouseX >x && mouseX<x+80 && mouseY>y && mouseY<y+40){
 //    rt.crawl = true;
 //  }
 //}
}
