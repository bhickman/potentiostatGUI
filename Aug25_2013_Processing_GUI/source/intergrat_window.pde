
    private class SecondApplet extends PApplet{ 

  void setup()
  {
    background(0, 0, 0);
    size(550,500);
    PFont font = createFont("SansSerif",16);
    textFont(font, 16);
    smooth();
    textAlign(CENTER,CENTER);
    fill(20,120,20);
    //win.setResizable(true);
    
  cp5b = new ControlP5(this);  
   cp5b.addBang("Intergrate")
    .setColorBackground(#FFFEFC)//#FFFEFC 
        .setColorCaptionLabel(#030302) //#030302
          .setColorForeground(#AA8A16)
          .setColorActive(#06CB49)
            .setPosition(20, 20)
              .setSize(130, 30)
                .setTriggerEvent(Bang.RELEASE)
                  .setLabel("Intergrate") //
                    .getCaptionLabel().align(ControlP5.CENTER, ControlP5.CENTER)  //.setLabel("Start_Run")
                      ;

    
  win1Chart = new XYChart(this);
  win1Chart.setData(new float[] {1, 2, 3}, new float[] {1, 2, 3});
  win1Chart.showXAxis(true); 
  win1Chart.showYAxis(true);
  //win1Chart.setXAxisLabel("Potential (V)");
  //win1Chart.setYAxisLabel("Current"); 
  //lineChart.setMinY(0);   
  win1Chart.setYFormat("##.##");  
  win1Chart.setXFormat("##.##");       
  // Symbol colours
  win1Chart.setPointColour(color(234, 28, 28));
  win1Chart.setPointSize(5);
  win1Chart.setLineWidth(2);
  
  intergChart = new XYChart(this);
  intergChart.setData(new float[] {1, 2, 3}, new float[] {1, 2, 3});
  intergChart.showXAxis(true); //false 
  intergChart.showYAxis(true);
  //intergChart.setXAxisLabel("Potential (V)");
  //intergChart.setYAxisLabel("Current"); 
  //intergChart.setMinY(0);   
  intergChart.setYFormat("##.##");  
  intergChart.setXFormat("##.##");       
  // Symbol colours
  intergChart.setLineColour(color(139,173,206));
  intergChart.setPointColour(color(7, 131, 28));//(234, 28, 28)
  intergChart.setPointSize(5);
  intergChart.setLineWidth(2);
  }

  // ----------------------- Processing draw --------------------------
  
  // Displays some text and animates a change in size.
  void draw()
  {
    //super.draw();   // Should be the first line of draw().
    background(200,255,200);
   if(Modetorun != null){
    if(Modetorun.equals("ASV") || Modetorun.equals("LOG-ASV")){
    if(V.length > 3 && Idif.length >3){
      win1Chart.setMaxX(max(V)+.05);
      win1Chart.setMaxY(max(Idif)+.05); 
      win1Chart.setMinX(min(V)-.05);
      win1Chart.setMinY(min(Idif)-.05);
    win1Chart.setData(V, Idif);
    }
    }
    else {
    if(V.length > 3 && I1.length >3){
      win1Chart.setMaxX(max(V)+.05);
      win1Chart.setMaxY(max(I1)+.05); 
      win1Chart.setMinX(min(V)-.05);
      win1Chart.setMinY(min(I1)-.05);
    win1Chart.setData(V, I1);
    }
    }
  }  
    pushMatrix();
    translate(40,height/2);
    rotate(1.570796327);
    text("Current (relative)", 0, 0); 
    popMatrix(); 
   
  if(Modetorun != null){
    if (Modetorun.equals("ASV") ||Modetorun.equals("CV") || Modetorun.equals("LOG-ASV")) {
    text("Potential (V)", width/2, height-20);
    }
    if (Modetorun.equals("TIMED")) {
    text("Time (msec)", width/2, height-20);
    }
  } 
    win1Chart.draw(100,50,width-200,height-100);
    wdth = width;
    higt = height;
    
     if(mousest == true){
   //win.setVisible(false);
    if(nxtpt == false){
      start_point();
    }
    if(nxtpt == true){
    second_point();    
  }

  }
  if (XY1 != null && XY2 != null) {
     
      intergChart.setMaxX(win1Chart.getMaxX());
      intergChart.setMaxY(win1Chart.getMaxY()); 
      intergChart.setMinX(win1Chart.getMinX());             // added///////////
      intergChart.setMinY(win1Chart.getMinY());

    float btmspc = win1Chart.getBottomSpacing();
    float lftspc = win1Chart.getLeftSpacing();
    intergChart.draw(100,50,width-200,height-100); //88,50,width-188,height-100)
    fill(0);
    text("Peak Area: "+area, width/2,20);
    text("Peak Height: "+peakheight, width/2,40);
  }
 
  }//////////end draw/////////////////////////////////////////////////*********//////////////////////////////////////////////

void Intergrate(){
mousest = true;
println("mousest "+mousest);
}

void intergdata() { //public void intergdata() {
  area = 0;
  int indx1 = pvector_dat.indexOf(XY1);
  int indx2 = pvector_dat.indexOf(XY2);
  println("indx1 "+indx1);
  int bounds = abs(indx1-indx2);
  println("bounds "+bounds);
  if(indx1 < indx2){
  for (int i = indx1+1; i < indx2+1; i++) {
    PVector Xi = (PVector) pvector_dat.get(i);
    PVector Xi2 = (PVector) pvector_dat.get(i-1);
    area += (Xi.x - Xi2.x) * (Xi.y + Xi2.y);
  }
  }
  else{
      for (int i = indx2+1; i < indx1+1; i++) {
    PVector Xi = (PVector) pvector_dat.get(i);
    PVector Xi2 = (PVector) pvector_dat.get(i-1);
    area += (Xi.x - Xi2.x) * (Xi.y + Xi2.y);
  }
  }
  area = abs(area)*0.5;
  println("area1 "+area);
  baseline();
  peak_height();
  area = area - areabase;
  println("peak height "+peakheight);
  println("ar2 "+area);
}

void baseline(){ //public void baseline(){/////////////////////////////////////////////////////////////////
  areabase = 0;
  int indxb1 = pvector_dat.indexOf(XY1);
  int indxb2 = pvector_dat.indexOf(XY2);
  PVector Xbi = (PVector) pvector_dat.get(indxb1);
  PVector Xbi2 = (PVector) pvector_dat.get(indxb2);
if(indxb1 > indxb2){
areabase += (Xbi.x - Xbi2.x) * (Xbi.y + Xbi2.y);
}
else{
areabase += (Xbi2.x - Xbi.x) * (Xbi2.y + Xbi.y);
}
areabase = 0.5*abs(areabase);
println("ar_b "+areabase);
}

void peak_height(){ ////////////////////////////////////////////////////////////////////////////////////////////////
 peaktest = 0;
 peakheight = 0;
 int indx1 = pvector_dat.indexOf(XY1);
 int indx2 = pvector_dat.indexOf(XY2);
   if(indx1 < indx2){
      PVector Xi = (PVector) pvector_dat.get(indx1);
      peakheight = Xi.y;
      for (int i = indx1; i <= indx2; i++) { // for (int i = indx1+1; i < indx2+1; i++) {
        Xi = (PVector) pvector_dat.get(i);
        peaktest = Xi.y;
        if(peakheight < peaktest){
          peakheight = peaktest;
          println("indxx "+i);
        }
      }
   }
  else{
      PVector Xi = (PVector) pvector_dat.get(indx2);
      peakheight = Xi.y;
    for (int i = indx2; i <= indx1; i++) { //for (int i = indx2+1; i < indx1+1; i++) {
        Xi = (PVector) pvector_dat.get(i);
        peaktest = Xi.y;
        if(peakheight < peaktest){
          peakheight = peaktest;
        }
    }
  }
}

void start_point() { //public void start_point() {//////////////////////////////////////////////////////////////////
  delay(20);
  try {
      if(mousePressed==true){
       if (mouseX >= 20 && mouseX <= wdth-20) {  //if (mouseX >= lspc && mouseX <= rspc) {
           if (mouseY >= 52 && mouseY <= higt-20) {//   if (mouseY >= tpspc && mouseY <= btmspc) {
              PVector mouseData = win1Chart.getScreenToData(new PVector(mouseX, mouseY)); // PVector mouseData = lineChart.getScreenToData(new PVector(mouseX, mouseY));
              bx1 = mouseX;
              by1 = mouseY;
              pvector_creat();
              XY1 = getClosestPoint(mouseData, pvector_dat);
              println("initial pt "+XY1);
            }
        }
        nxtpt = true;
      }
  }
  catch(Exception e){
   warning.show();
   warningtxt.setText("Mouse outside of graph bounds");  
  println("Mouse outside of graph bounds");
  }
}
void second_point(){//////////////////////////////////////////////////////////////////////////////////////////////////////
  try {
      if(mousePressed == false && nxtpt == true) {
        if (mouseX >= 20 && mouseX <= wdth-20) {
         if (mouseY >= 50 && mouseY <= higt-20) {
            PVector mouseData = win1Chart.getScreenToData(new PVector(mouseX, mouseY)); // PVector mouseData = lineChart.getScreenToData(new PVector(mouseX, mouseY));
            pvector_creat();
            XY2 = getClosestPoint(mouseData, pvector_dat);
            println("fnl pt "+XY2);
            /////
      
            float[] fitx = {XY1.x, XY2.x};
            float[] fity = {XY1.y, XY2.y};
            println("fit "+XY1.x);
            intergChart.setData(fitx, fity);
            intergChart.showXAxis(true); 
            intergChart.showYAxis(true); 
            
            intergdata();
          }
        }
        nxtpt = false;
      }
  }
  catch (Exception e){
   warning.show();
   warningtxt.setText("Mouse outside of graph bounds");      
   println("Mouse outside of graph bounds");
  }
}

void pvector_creat() {/////////////////////////////////////////////////////////////////////////////////////////////
  float[] Xdat = win1Chart.getXData();//  float[] Xdat = lineChart.getXData();
  float[] Ydat = win1Chart.getYData();//  float[] Ydat = lineChart.getYData();
  for (int i=0; i< Xdat.length; i++) {
    pvector_dat.add(new PVector(Xdat[i], Ydat[i], 0));
  }
}

// A function that returns the closest point from a set
PVector getClosestPoint(PVector TESTPT, ArrayList PTS) { ////////////////////////////////////////////////////////////////

  // some impossibly high distance (ie something is always closer)
  float distClosest = 999999;
  // the closest point
  PVector theClosestPt = null;
  // Loop to find the closest point
  for (int i = 0; i<PTS.size(); ++i) {
    // get a object
    PVector testPos = (PVector) PTS.get(i);
    // get the distance
    float d2 = PVector.dist(TESTPT, testPos);
    // ask the question
    if (d2 < distClosest) {
      // update the closest distance
      distClosest = d2;
      // remember the closest pos
      theClosestPt = testPos.get();
    }
  }

  // return the closest point
  //println("the closest pt  "+theClosestPt);
  return theClosestPt;
}


}///////////////////////////end extra window//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


