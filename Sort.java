int[] num = {2 ,500, 600, 200, 300, 400, 100, 55, 698, 254, 211, 4};

void setup(){
  size(600,600);
}

void draw() {
  background(150);
  show();
  //num[0] +=1;
}

void show(){
  float y = 100;
  for (int i=0; i<num.length; i++){
    text( i+1, 50, y);
    y+=15;
  }
  y = 100;
  for (int i=0; i<num.length; i++){
    text(num[i], 100, y);
    y += 15;
  }
}

void srt(){
  for( int j= 0; j<num.length; j++) {
  for (int i=0; i<num.length-1; i++){
    if(num[i]>num[i+1]){
      swap( num, i, i+1);
    }
  }
  }
}

void swap( int a[], int j, int k ) {
  int tmp;
  tmp=  a[k];
  a[k]=  a[j];
  a[j]=  tmp;
}

void keyPressed() {
 if (key == 's') srt();
} 
