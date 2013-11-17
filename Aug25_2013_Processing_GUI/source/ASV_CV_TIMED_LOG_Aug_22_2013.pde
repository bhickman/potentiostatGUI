
///////////////////////////////////////// Imports/////////////////////////////////
import org.gicentre.utils.stat.*;    // For chart classes.
import controlP5.*;                  // for buttons and txt boxes
import processing.serial.*;          // for serial
import java.io.*;                    // for file stuff
import com.shigeodayo.pframe.*;      // for extra windows
///////////////////////////////////////////////////////////////Classes///////////////
XYChart win1Chart;
XYChart intergChart;
XYChart lineChart;
XYChart lineChartIf;
XYChart lineChartIb;
ControlP5 cp5,cp5b,cp5c;
Serial serial;
Textarea myTextarea;   // com port and status window
Textarea myTextarea2;   // save file path window
Textarea warningtxt;  // text for warning window
Textfield Starting_Voltage, End_Voltage, Scan_Rate, Delay_Time, InitialV_Time, FinalV_Time, Number_of_Runs, Run_Intervale;
DropdownList ports, mode;              //Define the variable ports and mode as a Dropdownlist.

SecondApplet secondApplet = null;
PFrame win = null;
ThirdApplet thirdApplet = null;
PFrame warning = null;

//////////////////////////////////variables//////////////////////
int numrun = 0;
String Nor;                    // # of runs variable
int iNor;                        // # of runs int varialbe
String Rint;                   // run interval 
int iRint;                      // int run interval
String sData3 ="";
char[] strtochar;
int updatechart;
String InitVT;
int iInitVT; 
String FnlVT; 
int iFnlVT;
boolean gotparams = false;
float wdth;
float higt;
boolean mousest = false;
boolean nxtpt = false;
int Ss;                          //The dropdown list will return a float value, which we will connvert into an int. we will use this int for that).
String[] comList ;               //A string to hold the ports in.
String[] comList2;               // string to compare comlist to and update
boolean serialSet;               //A value to test if we have setup the Serial port.
boolean Comselected = false;     //A value to test if you have chosen a port in the list.
int Modi; 
boolean Modesel = false;
String Modetorun;
int xacu;
int yacu;
float xspace;
float yspace;
ArrayList qdat;
boolean bool = false;
float p1;
float p2;
String StartV;
int iStartV;
String EndV;
int iEndV;
String ScanR;
int iScanR;
String DelayT;
int iDelayT;
int xPos = 150; 
String ComP;
int serialPortNumber;
String file1 = "logdata.txt";//"C:/Users/Ben/Documents/Voltammetry Stuff/log/data.txt";  //"C:/Users/Public/Documents/Electrochem/logfiletest/TestLog.txt"
String file2;
String file;
String[] sData = new String[3];  //String sData;
String sData2 = " ";
char cData;
int logDelay = 1000;
String Go = "1";
String ASVmod = "1";
String CVmod = "2";
String TIMEDmod = "3";
String LOG_ASVmod = "4";
String star = "*";
int i =0;
float[] V = {0};
float[] I1 = {0};
float[] I2 = {0};
float[] Idif = {0};
float[] newV = {0};
float[] newI1 = {0};
float[] newI2 = {0};
float[] newIdif = {0};
///intergration variables///////////////////////
ArrayList pvector_dat = new ArrayList();
float bx1;
float by1;
float bbtm;
float bwdth;  //
PVector XY1;   // baseline point 1
PVector XY2;  // baseline pooint 2
float area;  // peak area
float areabase;  // baseline area (subtracted from peak area)
float peaktest;  // variable for peak height
float peakheight;  // variable for peak height
//////////////font varialbes///////////////////////////////
  PFont font = createFont("arial", 20);
  PFont font2 = createFont("arial", 16);
  PFont font3 = createFont("arial",12); 
  PFont font4 = createFont("andalus",16);
/////************************////////

///////////////////////////////Setup////////////////////////////////////////////////////
void setup() {

  frameRate(2000);
  size(800, 675); // (800, 700)
  textFont(font2);
  frame.setResizable(true);
  charts_gic_setup();
  cp5_controllers_setup();
  
  secondApplet = new SecondApplet();
  win = new PFrame(secondApplet, 210, 0);
  win.setTitle("Intergration Window");
  win.setSize(550,500);
  win.hide();
  
  thirdApplet = new ThirdApplet();
  warning = new PFrame(thirdApplet, 500, 200);
  warning.setTitle("Warning!!");
  warning.setSize(250,250);
  warning.hide();
}////////////////////End Setup/////////////////////////////
/////////////////////////////////////////////////Draw//////////////////////////////////////////////    

void draw() {

  background(58, 2, 67);  
  fill(58, 2, 67);
  noStroke();
  rect(0, 80, 780, 580);  
 fill(#EADFC9);//#FFFEFC);
 noStroke();    
 rect(200, 80, 580, 580);
 textFont(font4,18); 
 pushMatrix();
 fill(#DEC507);
 text("Western",30,height-67);
 text("Carolina",36,height-53);
 popMatrix(); 
 pushMatrix();
 textFont(font2,12);
 fill(#080606);
 text("Dr. Summers research group",20,height-30);
 popMatrix(); 
 textFont(font2);
 //if (bool == false){
    
  comList2 = Serial.list();
  if (comList.length != comList2.length) {          //if(comList.equals(comList2)==false){ // if (comList.length != comList2.length) {
    ports.clear();
    comList = comList2;
    for (int i=0; i< comList.length; i++)
    {
      ports.addItem(comList2[i], i);                 // add available serial ports
    }
  }
  if(comList.length == 0){
  myTextarea2.setText("NOT CONN.");
  ports.clear();                                   // clear ports dropdown list 
  ports.captionLabel().set("Select COM port");
  try{
  serial.stop();                                   // stop the serial port so a new connection can be established
  }
  catch(Exception e){}
  Comselected = false;
  } 

  if (Modetorun=="ASV" || Modetorun=="LOG-ASV") {
    try{
    lineChart.setXAxisLabel("Potential (V)");
    lineChart.setYAxisLabel("Current");
    lineChart.draw(280, 320, 350, 300);
    lineChartIf.draw(220, 80, 250, 200);
    lineChartIb.draw(490, 80, 250, 200);
    }
    catch(Exception e){
      if(V.length >2){
        V = subset(V,1);
        I1= subset(I1,1);
        I2= subset(I2,1);
        Idif = subset(Idif,1);
        println("exception");  
      }  
    }
    fill(120);
    textSize(16);
    text("Forward Current", 280, 100);
    text("Back Current", 550, 100);
    text("Forward-Back Current", 340, 340);

  }
    if (Modetorun=="CV") {
    fill(120);
    textSize(16);
    text("CV Data", 450, 100);
    try{
    lineChart.setXAxisLabel("Potential (V)");
    lineChart.setYAxisLabel("Current");
    lineChart.draw(230, 120, 450, 450);
    }
    catch(Exception e){
      if(V.length >2){
        V = subset(V,1);
        I1= subset(I1,1);
        I2= subset(I2,1);
        Idif = subset(Idif,1);
        println("exception");  
      } 
    }
  } 
  
  if (Modetorun=="TIMED") {
    fill(120);
    textSize(16);
    text("Timed Data", 450, 100);
    try{
    lineChart.setXAxisLabel("Time (msec)");
    lineChart.setYAxisLabel("Current");
    lineChart.draw(230, 120, 450, 450);
    }
    catch(Exception e){
    if(V.length >2){
        V = subset(V,1);
        I1= subset(I1,1);
        I2= subset(I2,1);
        Idif = subset(Idif,1);
        println("exception");  
      } 
    }
  }

//if (bool == false){
  if (Modesel==false) {
    Starting_Voltage.hide();
    End_Voltage.hide();
    Delay_Time.hide();
    Scan_Rate.hide();
    FinalV_Time.hide();
    InitialV_Time.hide();
    Number_of_Runs.hide();
    Run_Intervale.hide();
    cp5.controller("Start_Run").hide();
    cp5.controller("Intergrate_Data").hide();
  }
  if (Modesel==true) {
    Starting_Voltage.show();
    End_Voltage.show();
    
      if (Modetorun=="CV") {
        Delay_Time.hide();
        Scan_Rate.show();
        InitialV_Time.hide();
        FinalV_Time.hide();
        Number_of_Runs.hide();
        Run_Intervale.hide();
      }
      if (Modetorun=="ASV") {
        Delay_Time.show();
        Scan_Rate.show();
        InitialV_Time.hide();
        FinalV_Time.hide();
        Number_of_Runs.hide();
        Run_Intervale.hide();
      }
      if(Modetorun=="TIMED"){
      Delay_Time.hide();
      Scan_Rate.hide();
      Number_of_Runs.hide();
      Run_Intervale.hide();
      InitialV_Time.show();
      FinalV_Time.show();
      }
      if (Modetorun=="LOG-ASV") {
        Delay_Time.show();
        Scan_Rate.show();
        Number_of_Runs.show();
        Run_Intervale.show();
        InitialV_Time.hide();
        FinalV_Time.hide();
      }
    cp5.controller("Start_Run").show();
    cp5.controller("Intergrate_Data").show(); 
  }
 // }  // end if bool == false
  
  
  if (bool == true && Comselected == true) {  
    if (gotparams == false) {    
    V = newV;  //float[]
    I1 = newI1;
    I2 = newI2;
    Idif = newIdif;
println("com "+Comselected);
    if (Modetorun.equals("ASV")) {
      Starting_Voltage();
      End_Voltage();
      Scan_Rate();
      Delay_Time();
      serial.write(ASVmod);  // prg 1 for ASV
      delay(100);
      serial.write(StartV);
      delay(100);
      serial.write(EndV);
      delay(100);
      serial.write(ScanR);
      delay(100);
      serial.write(DelayT);
      delay(100);
      serial.write(Go);
      println(StartV);
      println(EndV);
      println(ScanR);
      println(DelayT);
      println(ASVmod);
      println(Go);
    }

if (Modetorun.equals("CV")) {
      Starting_Voltage();
      End_Voltage();
      Scan_Rate();
      Delay_Time();
      serial.write(CVmod);   // prg 2 for CV
      delay(100);
      serial.write(StartV);
      delay(100);
      serial.write(EndV);
      delay(100);
      serial.write(ScanR);
      delay(100);
      //serial.write(DelayT);
      //delay(100);
      serial.write(Go);
      println(StartV);
      println(EndV);
      println(ScanR);
      println(CVmod);
      println(Go);
    }

if (Modetorun.equals("TIMED")) {
      Starting_Voltage();
      End_Voltage();
      InitialV_Time();
      FinalV_Time();
      serial.write(TIMEDmod);   // prg 3 for TIMED
      delay(100);
      serial.write(StartV);
      delay(100);
      serial.write(EndV);
      delay(100);
      serial.write(InitVT);
      delay(100);
      serial.write(FnlVT);
      delay(100);
      serial.write(Go);
      println(StartV);
      println(EndV);
      println(InitVT);
      println(FnlVT);
      println(TIMEDmod);
      println(Go);
    }
    
      if (Modetorun.equals("LOG-ASV")) {
      Starting_Voltage();
      End_Voltage();
      Scan_Rate();
      Delay_Time();
      Number_of_Runs();
      Run_Intervale();
      serial.write(LOG_ASVmod);  // prg 4 for LOG-ASV
      delay(100);
      serial.write(StartV);
      delay(100);
      serial.write(EndV);
      delay(100);
      serial.write(ScanR);
      delay(100);
      serial.write(DelayT);
      delay(100);
      serial.write(Nor);
      delay(100);
      serial.write(Rint);
      delay(100);
      serial.write(Go);
      println(StartV);
      println(EndV);
      println(ScanR);
      println(DelayT);
      println(LOG_ASVmod);
      println(Nor);
      println(Rint);
      println(Go);
      //read_serialASV();///////////////////////////////*************************///////////////////////
    }
          i=0;                                             // global variable. used to itterate serail read and indicate there is data to draw the chart.
          updatechart = i;                                  // used to limit the the updating of the graphes to once very 2 runs
          numrun = 0;                                  // for LOG-ASV mode. is incremented everytime "run" is passed over the serial port
          logData(file1, "", false);
          while (cData!='&') {                       ////////read paramaters/////////
            //while (serial.available() <= 0) {}
            //serial.bufferUntil('\n');//}
        
            if (serial.available() > 0) { 
              cData =  serial.readChar();//cData =  serial.readChar();
              sData2 = str(cData);
              logData(file1, sData2, true); 
              if (cData == '&') {
                if (Modetorun.equals("ASV")) {
                println("paramaters recieved ASV");
                }
                if (Modetorun.equals("CV")) {
                println("paramaters recieved CV");
                }
                if (Modetorun.equals("TIMED")) {
                println("paramaters recieved TIMED");
                }
                if(Modetorun.equals("LOG-ASV")){
                println("paramaters recieved LOG-ASV");
                }
              }
            }
          }
       cData = 'a';
      }  // lvsr == false
    gotparams = true;
    ///////////////////////////////////////////////////////////////////////////read voltammetry data //////////////////////////////////////////////////////////
    
  if (cData!='@') {   
      
    if (serial.available () <= 0) {  //changed from while  4/27/13
    }
     
    if (serial.available() > 0 && bool == true) { 
      
      
      sData2 = serial.readString(); 
      logData(file1, sData2, true); //logData(file1,getDateTime() + sData,true);logData(file1,sData,true);
      strtochar = sData2.toCharArray();
      
      String[] endsig = match(sData2,"@");  
      if (endsig!=null){ //if (cData == '*') {*
        println("stop");
        bool = false;
        gotparams = false;
        myTextarea2.setColor(#036C09);
        myTextarea2.setText("FINISHED");
      }
      
      String[] runnum = match(sData2,"r");  
      if (runnum!=null){ //if (cData == '*') {*
        println("run-"+(numrun+1));
        myTextarea2.setText("run-"+(numrun+1));
        runnum = null;
        numrun+=1;
      }
      
   ////////////////////////////////////////////////////////////////////////////////////////////////////////// graph datat//////////////////////////////////////////////
if (bool == true && Modetorun.equals("ASV") || Modetorun.equals("LOG-ASV")) {    //if (bool == true && Modetorun=="ASV") {   
  read_serialASV();
}
if (bool == true && Modetorun.equals("CV") || Modetorun.equals("TIMED")) { //if (bool == true && Modetorun=="CV" || Modetorun=="TIMED") {
  read_serialCV();
}  
     ///////////////////////graph//////
     if (V.length>2 && V.length==I1.length) { //if (i!=0) {  (V.length>3)
        
             if (Modetorun.equals("ASV") || Modetorun.equals("LOG-ASV")) {   //if (Modetorun=="ASV") {         
               //if(i==updatechart+3){
                  lineChart.setMaxX(max(V)+.05);
                  lineChart.setMaxY(max(Idif)+.05); 
                  lineChart.setMinX(min(V)-.05);
                  lineChart.setMinY(min(Idif)-.05);
                  lineChart.setData(V, Idif);
                                  
                  lineChartIf.setMaxX(max(V)+.05);
                  lineChartIf.setMaxY(max(I2)+.05); 
                  lineChartIf.setMinX(min(V)-.05);
                  lineChartIf.setMinY(min(I2)-.05);
                  lineChartIf.setData(V, I2);  
                 
                  lineChartIb.setMaxX(max(V)+.05);
                  lineChartIb.setMaxY(max(I1)+.05); 
                  lineChartIb.setMinX(min(V)-.05);
                  lineChartIb.setMinY(min(I1)-.05);
                  lineChartIb.setData(V, I1);
                  
               try{
               lineChart.draw(280, 320, 350, 300); 
               lineChartIf.draw(220, 80, 250, 200);
               lineChartIb.draw(490, 80, 250, 200);
               }
               catch(Exception e){
                  if(V.length >2){
                    V = subset(V,1);
                    I1= subset(I1,1);
                    I2= subset(I2,1);
                    Idif = subset(Idif,1);
                    println("exception");  
                  } 
               }
             }
              
              
             if (Modetorun.equals("CV") || Modetorun.equals("TIMED")) { //if (Modetorun=="CV" || Modetorun == "TIMED") {
                
                if(i>=updatechart+2){
                lineChart.setMaxX(max(V));//+.05
                lineChart.setMaxY(max(I1)); //Idif
                lineChart.setMinX(min(V));
                lineChart.setMinY(min(I1));  //Idif
                lineChart.setData(V, I1);  // Idif
                //delay(1);
                try{  
                lineChart.draw(230, 120, 450, 450);
                }
                catch(Exception e){
                  if(V.length >2){
                    V = subset(V,1);
                    I1= subset(I1,1);
                    I2= subset(I2,1);
                    Idif = subset(Idif,1);
                    println("exception");  
                  } 
                }
                updatechart = i;
                }
             }
            
  
     } // end if(i>0)          
  }  // end serial.availabel
    } // end if(cData!='*')
   //////////////////////////////////////////////////******************************////////////////////////////////////  
  }  // end of run
   
    if (bool == true && comList.length==0) {
    bool = false;  
    warning.show();
    warningtxt.setText("No COM port connection");
    println("comm not connected");
  }
}///////////////////////////End Draw////////////////////////




