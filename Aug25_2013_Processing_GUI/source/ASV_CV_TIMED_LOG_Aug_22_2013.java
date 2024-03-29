import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import org.gicentre.utils.stat.*; 
import controlP5.*; 
import processing.serial.*; 
import java.io.*; 
import com.shigeodayo.pframe.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class ASV_CV_TIMED_LOG_Aug_22_2013 extends PApplet {


///////////////////////////////////////// Imports/////////////////////////////////
    // For chart classes.
                  // for buttons and txt boxes
          // for serial
                    // for file stuff
      // for extra windows
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
public void setup() {

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

public void draw() {

  background(58, 2, 67);  
  fill(58, 2, 67);
  noStroke();
  rect(0, 80, 780, 580);  
 fill(0xffEADFC9);//#FFFEFC);
 noStroke();    
 rect(200, 80, 580, 580);
 textFont(font4,18); 
 pushMatrix();
 fill(0xffDEC507);
 text("Western",30,height-67);
 text("Carolina",36,height-53);
 popMatrix(); 
 pushMatrix();
 textFont(font2,12);
 fill(0xff080606);
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
        myTextarea2.setColor(0xff036C09);
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
                  lineChart.setMaxX(max(V)+.05f);
                  lineChart.setMaxY(max(Idif)+.05f); 
                  lineChart.setMinX(min(V)-.05f);
                  lineChart.setMinY(min(Idif)-.05f);
                  lineChart.setData(V, Idif);
                                  
                  lineChartIf.setMaxX(max(V)+.05f);
                  lineChartIf.setMaxY(max(I2)+.05f); 
                  lineChartIf.setMinX(min(V)-.05f);
                  lineChartIf.setMinY(min(I2)-.05f);
                  lineChartIf.setData(V, I2);  
                 
                  lineChartIb.setMaxX(max(V)+.05f);
                  lineChartIb.setMaxY(max(I1)+.05f); 
                  lineChartIb.setMinX(min(V)-.05f);
                  lineChartIb.setMinY(min(I1)-.05f);
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




public void Connect() {             // conect to com port bang
   if(Comselected==false){
 try{
  serial = new Serial(this, comList[Ss], 9600);
  println(comList[Ss]);
  myTextarea2.setText("CONNECTED");
  Comselected = true;
 }
 catch (Exception e){
   warning.show();
   warningtxt.setText("Some type of com port error");
   println("Some type of com port error. Restart program");
   myTextarea2.setText("COM ERROR");
 }
   }
   else{
   println("already connected");
   }
}


public void Save_run() {             // set path bang   
  selectInput("Select a file to process:", "fileSelected");
}

public void fileSelected(File selection) {
  if (selection == null) {
    println("Window was closed or the user hit cancel.");
  } 
  else {
    file2 = selection.getAbsolutePath();
    println("User selected " + file2);
   // myTextarea.setText(file2);
      ///////////////////////////////////////
  //  String file2 = "C:/Users/Ben/Documents/Voltammetry Stuff/log/data.txt";
  try{
  saveStream(file2,file1);
      }
      catch(Exception e){}
/////////////////////////////////////////
  }
} 



public void Start_Run() {  // start run bang
  bool = true;
  myTextarea2.setColor(0xffD8070E);
  myTextarea2.setText("RUNNING SCAN");
}

public void Intergrate_Data() {  // intergrate data bang
win.show();
}    //intergrate bag for popup window is in intergrat_window tab//

public void charts_gic_setup(){
  
              ////////////////////////////////gicentre charts///
  lineChart = new XYChart(this);
  lineChart.setData(new float[] {1, 2, 3}, new float[] {1, 2, 3});
  lineChart.showXAxis(true); 
  lineChart.showYAxis(true);
  lineChart.setXAxisLabel("Potential (V)");
  lineChart.setYAxisLabel("Current"); 
  //lineChart.setMinY(0);   
  lineChart.setYFormat("##.##");  
  lineChart.setXFormat("##.##");       
  // Symbol colours
  lineChart.setPointColour(color(234, 28, 28));
  lineChart.setPointSize(5);
  lineChart.setLineWidth(2);

  lineChartIf = new XYChart(this);
  lineChartIf.setData(new float[] {1, 2, 3}, new float[] {1, 2, 3});
  lineChartIf.showXAxis(true); 
  lineChartIf.showYAxis(true);
  lineChartIf.setXAxisLabel("Potential (V)");
  lineChartIf.setYAxisLabel("Current"); 
  //lineChartIf.setMinY(0);   
  lineChartIf.setYFormat("##.##");  
  lineChartIf.setXFormat("##.##");       
  // Symbol colours
  lineChartIf.setPointColour(color(234, 28, 28));
  lineChartIf.setPointSize(5);
  lineChartIf.setLineWidth(2);

  lineChartIb = new XYChart(this);
  lineChartIb.setData(new float[] {1, 2, 3}, new float[] {1, 2, 3});
  lineChartIb.showXAxis(true); 
  lineChartIb.showYAxis(true);
  lineChartIb.setXAxisLabel("Potential (V)");
  lineChartIb.setYAxisLabel("Current"); 
  //lineChartIb.setMinY(0);   
  lineChartIb.setYFormat("##.##");  
  lineChartIb.setXFormat("##.##");       
  // Symbol colours
  lineChartIb.setPointColour(color(234, 28, 28));
  lineChartIb.setPointSize(5);
  lineChartIb.setLineWidth(2);
  
}/////////////////////////////////////////////////end charts_gic_setup///////////////////////////////////////////////
public void cp5_controllers_setup(){
 ////////////////////////////////////////////////Text Fields//////////////////////////////
  cp5 = new ControlP5(this);  //cp5 = new ControlP5(this);
  PFont font = createFont("arial", 20);
  PFont font2 = createFont("arial", 16);
  PFont font3 = createFont("arial",12); 
  
  
  Starting_Voltage = cp5.addTextfield("Starting_Voltage")
    .setColor(0xff030302) 
      //.setColorActive(#AA8A16)
      .setColorBackground(0xffCEC6C6)//(#FFFEFC) 
        //.setColorCaptionLabel(#F01B1B) 
        .setColorForeground(0xffAA8A16) 
          //.setColorValueLabel(#F01B1B)
          .setPosition(20, 100)
            .setSize(100, 40)
              .setFont(font)
                .setFocus(false)
                  //.setLabel("initial voltage (mV)")                
                    .setText("-400");
                      controlP5.Label svl = Starting_Voltage.captionLabel(); 
                        svl.setFont(font2);
                          svl.toUpperCase(false);
                            svl.setText("Initial Voltage (mV)");
  ;

  End_Voltage = cp5.addTextfield("End_Voltage")
    .setColor(0xff030302) 
      //.setColorActive(#AA8A16)
      .setColorBackground(0xffCEC6C6) 
        //.setColorCaptionLabel(int) 
        .setColorForeground(0xffAA8A16) 
          //.setColorValueLabel(int)
          .setPosition(20, 170)
            .setSize(100, 40)
              .setFont(font)
                .setFocus(false)
                  //.setLabel("final voltage (mV)")
                    .setText("400");
                      controlP5.Label evl = End_Voltage.captionLabel(); 
                        evl.setFont(font2);
                          evl.toUpperCase(false);
                            evl.setText("End Voltage (mV)");
  ;

  Scan_Rate = cp5.addTextfield("Scan_Rate")
    .setColor(0xff030302) 
      //.setColorActive(#AA8A16)
      .setColorBackground(0xffCEC6C6) 
        //.setColorCaptionLabel(int) 
        .setColorForeground(0xffAA8A16) 
          //.setColorValueLabel(int)
          .setPosition(20, 240)
            .setSize(100, 40)
              .setFont(font)
                .setFocus(false)
                  //.setLabel("Scan Rate (mV/sec)")
                    .setText("100");
                      controlP5.Label srl = Scan_Rate.captionLabel(); 
                        srl.setFont(font2);
                          srl.toUpperCase(false);
                            srl.setText("Scan Rate (mV/sec)");
  ;

  Delay_Time = cp5.addTextfield("Delay_Time")
    .setColor(0xff030302) 
      //.setColorActive(#AA8A16)
      .setColorBackground(0xffCEC6C6) 
        //.setColorCaptionLabel(int) 
        .setColorForeground(0xffAA8A16) 
          //.setColorValueLabel(int)  
          .setPosition(20, 310)
            .setSize(100, 40)
              .setFont(font)
                .setFocus(false)
                  //.setLabel("concentration time (sec)")
                    .setText("60");
                      controlP5.Label dtl = Delay_Time.captionLabel(); 
                        dtl.setFont(font2);
                          dtl.toUpperCase(false);
                            dtl.setText("Concentration Time (sec)");                    
  ;

  InitialV_Time = cp5.addTextfield("InitialV_Time")
    .setColor(0xff030302) 
      //.setColorActive(#AA8A16)
      .setColorBackground(0xffCEC6C6) 
        //.setColorCaptionLabel(int) 
        .setColorForeground(0xffAA8A16) 
          //.setColorValueLabel(int)
          .setPosition(20, 240)
            .setSize(100, 40)
              .setFont(font)
                .setFocus(false)
                  //.setLabel("Scan Rate (mV/sec)")
                    .setText("100");
                      controlP5.Label invl = InitialV_Time.captionLabel(); 
                        invl.setFont(font2);
                          invl.toUpperCase(false);
                            invl.setText("Initial V Time (msec)");
  ;

  FinalV_Time = cp5.addTextfield("FinalV_Time")  // time based txt field
    .setColor(0xff030302) 
      //.setColorActive(#AA8A16)
      .setColorBackground(0xffCEC6C6) 
        //.setColorCaptionLabel(int) 
        .setColorForeground(0xffAA8A16) 
          //.setColorValueLabel(int)  
          .setPosition(20, 310)
            .setSize(100, 40)
              .setFont(font)
                .setFocus(false)
                  //.setLabel("concentration time (sec)")
                    .setText("60");
                      controlP5.Label fnlvl = FinalV_Time.captionLabel(); 
                        fnlvl.setFont(font2);
                          fnlvl.toUpperCase(false);
                            fnlvl.setText("Final V Time (msec)");                    
  ;
  
    Number_of_Runs = cp5.addTextfield("Number_of_Runs")  // time based txt field
    .setColor(0xff030302) 
      //.setColorActive(#AA8A16)
      .setColorBackground(0xffCEC6C6) 
        //.setColorCaptionLabel(int) 
        .setColorForeground(0xffAA8A16) 
          //.setColorValueLabel(int)  
          .setPosition(20, 380)
            .setSize(100, 40)
              .setFont(font)
                .setFocus(false)
                  //.setLabel("concentration time (sec)")
                    .setText("60");
                      controlP5.Label norl = Number_of_Runs.captionLabel(); 
                        norl.setFont(font2);
                          norl.toUpperCase(false);
                            norl.setText("Number of Runs");                    
  ;
  
    Run_Intervale = cp5.addTextfield("Run_Intervale")  // time based txt field
    .setColor(0xff030302) 
      //.setColorActive(#AA8A16)
      .setColorBackground(0xffCEC6C6) 
        //.setColorCaptionLabel(int) 
        .setColorForeground(0xffAA8A16) 
          //.setColorValueLabel(int)  
          .setPosition(20, 450)
            .setSize(100, 40)
              .setFont(font)
                .setFocus(false)
                  //.setLabel("concentration time (sec)")
                    .setText("60");
                      controlP5.Label ril = Run_Intervale.captionLabel(); 
                        ril.setFont(font2);
                          ril.toUpperCase(false);
                            ril.setText("Run Interval (sec)");                    
  ;

  ///////////////////////////////////////text area//////////////////////////

 /* myTextarea = cp5.addTextarea("txt")  // save path text area
    .setPosition(280, 5)
      .setSize(300, 45)
        .setFont(font3)
          .setLineHeight(20)
            .setColor(#030302)
              .setColorBackground(#CEC6C6)
                .setColorForeground(#AA8A16)//#CEC6C6
                    ;  */

  myTextarea2 = cp5.addTextarea("txt2")  // status and com port text area
    .setPosition(300, 10)
      .setSize(100, 30)
        .setFont(createFont("arial", 12)) //(font)
          .setLineHeight(10)
            .setColor(0xff030302)
              .setColorBackground(0xffCEC6C6)
                .setColorForeground(0xffAA8A16)//#CEC6C6                 
                    ;


  /////////////////////////////Bang's///////////////////////////////////////////////////////////
  cp5.addBang("Start_Run")
    .setColorBackground(0xffFFFEFC)//#FFFEFC 
        .setColorCaptionLabel(0xff030302) //#030302
          .setColorForeground(0xffAA8A16)
          .setColorActive(0xff06CB49)
            .setPosition(20, 520)
              .setSize(150, 40)
                .setTriggerEvent(Bang.RELEASE)
                  .setLabel("Start Run") //
                    .getCaptionLabel().align(ControlP5.CENTER, ControlP5.CENTER)  //.setLabel("Start_Run")
                      ;

 cp5.addBang("Connect")
    .setColorBackground(0xffFFFEFC)//#FFFEFC 
        .setColorCaptionLabel(0xff030302) //#030302
          .setColorForeground(0xffAA8A16)  
          .setPosition(240, 10)
            .setSize(40, 20)
              .setTriggerEvent(Bang.RELEASE)
                .getCaptionLabel().align(ControlP5.CENTER, ControlP5.CENTER)  //.setLabel("Start_Run")
                  ;

  cp5.addBang("Save_run")
    .setColorBackground(0xffFFFEFC)//#FFFEFC 
        .setColorCaptionLabel(0xff030302) //#030302
          .setColorForeground(0xffAA8A16)  
          .setPosition(450, 10)
            .setSize(100, 20)
              .setTriggerEvent(Bang.RELEASE)
                .setLabel("Save Run")
                  .getCaptionLabel().align(ControlP5.CENTER, ControlP5.CENTER)  //.setLabel("Start_Run")
                    ;
                    
   cp5.addBang("Intergrate_Data")                 
      .setColorBackground(0xffFFFEFC)//#FFFEFC 
        .setColorCaptionLabel(0xff030302) //#030302
          .setColorForeground(0xffAA8A16)  
          .setPosition(600, 35)
            .setSize(80, 20)
              .setTriggerEvent(Bang.RELEASE)
                .setLabel("Intergrate Peaks")
                  .getCaptionLabel().align(ControlP5.CENTER, ControlP5.CENTER)  //.setLabel("Start_Run")
                    ;
                    

  //////////////////////////////////////////Dropdownlist////////////////////////////////////////
  ports = cp5.addDropdownList("list-1", 130, 30, 100, 84)
    .setBackgroundColor(color(200))
      .setItemHeight(20)
        .setBarHeight(20)
          .setColorBackground(color(60))
            .setColorActive(color(255, 128))
              .setUpdate(true)
                ;
  ports.captionLabel().set("Select COM port");
  ports.captionLabel().style().marginTop = 3;
  ports.captionLabel().style().marginLeft = 3;
  ports.valueLabel().style().marginTop = 3;
  comList = serial.list(); 
  for (int i=0; i< comList.length; i++)
  {
    ports.addItem(comList[i], i);
  } 

  mode = cp5.addDropdownList("list-2", 10, 30, 100, 84)  //mode = cp5.addDropdownList("list-2", 650, 30, 100, 84) 
    .setBackgroundColor(color(200))
      .setItemHeight(40)//.setItemHeight(20
          .setBarHeight(20)//.setBarHeight(15)
          .setColorBackground(color(60))
            .setColorActive(color(255, 128))
              .setUpdate(true)
                ;
  mode.captionLabel().set("Select Mode");
  mode.captionLabel().style().marginTop = 3;
  mode.captionLabel().style().marginLeft = 3;
  mode.valueLabel().style().marginTop = 3;
  mode.setScrollbarWidth(10);
  mode.addItem("ASV", 0);
  mode.addItem("CV", 1);
  mode.addItem("TIMED",2);
  mode.addItem("LOG-ASV",3);

}//////////////////////////////////end cp5_controllers-setup/////////////////////////////////////////////////
public void controlEvent(ControlEvent theEvent) {
  if (theEvent.isGroup()) 
  {
    if (theEvent.name().equals("list-1")) {//if (theEvent.getGroup().equals("list-1")) {

      float S = theEvent.group().value();
      Ss = PApplet.parseInt(S);
      //Comselected = true;
    }
    
    if (theEvent.name().equals("list-2")) {
      float Mod = theEvent.group().value(); //float Mod = theEvent.group().value();
      Modi = PApplet.parseInt(Mod);
      String [][] Modetype = mode.getListBoxItems(); //  String [] Modetype = theEvent.group().Items();
      Modetorun = Modetype[Modi][0]; //Modetorun = Modetype[Modi][Modi];
      Modesel = true;
      println(Modetorun);
    }
  }
}

public void logData( String fileName, String newData, boolean appendData)  //char//void logData( String fileName, String newData, boolean appendData)
{
  BufferedWriter bw=null;
  try { //try to open the file
    FileWriter fw = new FileWriter(fileName, appendData);
    bw = new BufferedWriter(fw);
    bw.write(newData);// + System.getProperty("line.separator"));
  } 
  catch (IOException e) {
  } 
  finally {
    if (bw != null) { //if file was opened try to close
      try {
        bw.close();
      } 
      catch (IOException e) {
      }
    }
  }
}
public void  read_serialASV() {

  
    for(int l=0;l<strtochar.length;l++){
      cData = strtochar[l];
      if (cData!='\n') { 
       sData3 = sData3+str(cData);
      }
      if (cData =='\n') {
        //int i =0;
        sData = split(sData3, "\t");     
        sData = trim(sData);
       // println("sDat "+sData.length);
        if (sData.length ==3) { //if(sData.length >1){
          float[] fData= PApplet.parseFloat(sData);
          if (i==0) {
            V[i] = fData[0];
            I1[i]=fData[1]; //    g1[i]=fmap[i];
            I2[i]=fData[2]; //    g2[i]=fmap2[i];
          }
          if (i>0) {
            V = append(V, fData[0]);
            I1 = append(I1, fData[1]); //g1 = append(g1,fmap[i]);
            I2 = append(I2, fData[2]); // g2 = append(g2,fmap2[i]);
          }
          //Idif[i] = I2[i]-I1[i];

          i +=1;// ++i;
          //sData3 = "";
        }   
        sData3 = " ";
      }
      
            Idif = newIdif;
       for (int j=0;j<I1.length;j++) {
        if (j==0) {
          Idif[j] = I2[j]-I1[j];
        }
        else {
          Idif = append(Idif, (I2[j]-I1[j]));
        }
      } 
  }
} // end read_serialASV   


public void  read_serialCV() {
 
   
       for(int l=0;l<strtochar.length;l++){
      cData = strtochar[l];
      if (cData!='\n') { 
       sData3 = sData3+str(cData);
      }
      if (cData =='\n') {
        //int i =0;
        sData = split(sData3,"\t");     
        sData = trim(sData);
       // println("sDat "+sData.length);
        if (sData.length ==2) { //if(sData.length >1){
          float[] fData= PApplet.parseFloat(sData);
          boolean fcatch = false;
          /*if(fData[0] ==  || fData[1] == NaN){
          fcatch = true;
          } */
          if (i==0 && fcatch == false) {
            V[i] = fData[0];
            I1[i]=fData[1]; //    g1[i]=fmap[i];
          }
          if (i>0 && fcatch == false) {
            V = append(V, fData[0]);
            I1 = append(I1, fData[1]); //g1 = append(g1,fmap[i]);
          }
          //Idif[i] = I2[i]-I1[i];

          i +=1;// ++i;
         // sData3 = "";
        }   
        sData3 = " ";
      }
  }
   
 } //////////////////////////////end reaad serial CV//////////////////////////////////////////////////////////
 

 
  

public void Starting_Voltage() {              //get start voltage from text box
  StartV = cp5.get(Textfield.class, "Starting_Voltage").getText();
  iStartV = round(PApplet.parseFloat(StartV));
  iStartV=iStartV+1852;
  StartV = nf(iStartV, 6);   // make StartV have 4 digits. pad with zero if no digits
}
public void End_Voltage() {               // get end voltage from text box
  EndV = cp5.get(Textfield.class, "End_Voltage").getText();
  iEndV = round(PApplet.parseFloat(EndV));
  iEndV=iEndV+1852;
  EndV = nf(iEndV, 6);   // make EndV have 4 digits. pad with zero if no digits
}
public void Scan_Rate() {                 // get scan rate from text box
  ScanR = cp5.get(Textfield.class, "Scan_Rate").getText();
  iScanR = round(PApplet.parseFloat(ScanR));
  ScanR = nf(iScanR, 6);   // make ScanR have 3 digits. pad with zero if no digits
}
public void Delay_Time() {                // get delay time from text box
  DelayT = cp5.get(Textfield.class, "Delay_Time").getText();
  iDelayT = round(PApplet.parseFloat(DelayT));
  DelayT = nf(iDelayT, 6);   // make DelayT have 3 digits. pad with zero if no digits
}

public void InitialV_Time() {                 // get scan rate from text box
  InitVT = cp5.get(Textfield.class, "InitialV_Time").getText();
  iInitVT = round(PApplet.parseFloat(InitVT));
  InitVT = nf(iInitVT, 6);   // make ScanR have 3 digits. pad with zero if no digits
}
public void FinalV_Time() {                // get delay time from text box
  FnlVT = cp5.get(Textfield.class, "FinalV_Time").getText();
  iFnlVT = round(PApplet.parseFloat(FnlVT));
  FnlVT = nf(iFnlVT, 6);   // make DelayT have 3 digits. pad with zero if no digits
}
public void Number_of_Runs() {                // get delay time from text box
  Nor = cp5.get(Textfield.class, "Number_of_Runs").getText();
  iNor = round(PApplet.parseFloat(Nor));
  Nor = nf(iNor, 6);   // make DelayT have 3 digits. pad with zero if no digits
}
public void Run_Intervale() {                // get delay time from text box
  Rint = cp5.get(Textfield.class, "Run_Intervale").getText();
  iRint = round(PApplet.parseFloat(Rint));
  Rint = nf(iRint, 6);   // make DelayT have 3 digits. pad with zero if no digits
}

    private class SecondApplet extends PApplet{ 

  public void setup()
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
    .setColorBackground(0xffFFFEFC)//#FFFEFC 
        .setColorCaptionLabel(0xff030302) //#030302
          .setColorForeground(0xffAA8A16)
          .setColorActive(0xff06CB49)
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
  public void draw()
  {
    //super.draw();   // Should be the first line of draw().
    background(200,255,200);
   if(Modetorun != null){
    if(Modetorun.equals("ASV") || Modetorun.equals("LOG-ASV")){
    if(V.length > 3 && Idif.length >3){
      win1Chart.setMaxX(max(V)+.05f);
      win1Chart.setMaxY(max(Idif)+.05f); 
      win1Chart.setMinX(min(V)-.05f);
      win1Chart.setMinY(min(Idif)-.05f);
    win1Chart.setData(V, Idif);
    }
    }
    else {
    if(V.length > 3 && I1.length >3){
      win1Chart.setMaxX(max(V)+.05f);
      win1Chart.setMaxY(max(I1)+.05f); 
      win1Chart.setMinX(min(V)-.05f);
      win1Chart.setMinY(min(I1)-.05f);
    win1Chart.setData(V, I1);
    }
    }
  }  
    pushMatrix();
    translate(40,height/2);
    rotate(1.570796327f);
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

public void Intergrate(){
mousest = true;
println("mousest "+mousest);
}

public void intergdata() { //public void intergdata() {
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
  area = abs(area)*0.5f;
  println("area1 "+area);
  baseline();
  peak_height();
  area = area - areabase;
  println("peak height "+peakheight);
  println("ar2 "+area);
}

public void baseline(){ //public void baseline(){/////////////////////////////////////////////////////////////////
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
areabase = 0.5f*abs(areabase);
println("ar_b "+areabase);
}

public void peak_height(){ ////////////////////////////////////////////////////////////////////////////////////////////////
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

public void start_point() { //public void start_point() {//////////////////////////////////////////////////////////////////
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
public void second_point(){//////////////////////////////////////////////////////////////////////////////////////////////////////
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

public void pvector_creat() {/////////////////////////////////////////////////////////////////////////////////////////////
  float[] Xdat = win1Chart.getXData();//  float[] Xdat = lineChart.getXData();
  float[] Ydat = win1Chart.getYData();//  float[] Ydat = lineChart.getYData();
  for (int i=0; i< Xdat.length; i++) {
    pvector_dat.add(new PVector(Xdat[i], Ydat[i], 0));
  }
}

// A function that returns the closest point from a set
public PVector getClosestPoint(PVector TESTPT, ArrayList PTS) { ////////////////////////////////////////////////////////////////

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




//class WarningSketch extends EmbeddedSketch{
 private class ThirdApplet extends PApplet{ 
  
  public void setup()
  {
    size(250,250);
    PFont font = createFont("SansSerif",16);
    textFont(font, 12);
    smooth();
    textAlign(CENTER,CENTER);
    fill(20,120,20);
   // warning.setResizable(true);
    
  cp5c = new ControlP5(this);  
   cp5c.addBang("OK")
    .setColorBackground(0xffFFFEFC)//#FFFEFC 
        .setColorCaptionLabel(0xff030302) //#030302
          .setColorForeground(0xffAA8A16)
            .setColorActive(0xff06CB49)            
              .setSize(66, 30)
                .setTriggerEvent(Bang.RELEASE)
                  .setLabel("OK") //
                    .getCaptionLabel().align(ControlP5.CENTER, ControlP5.CENTER)  //.setLabel("Start_Run")
                      ;
                      
    warningtxt = cp5c.addTextarea("warning txt")
        .setFont(font)
          .setLineHeight(20)
            .setColor(0xff030302)
              .setColorBackground(0xffCEC6C6)
                .setColorForeground(0xffAA8A16)//#CEC6C6
                    ;
                    
  }
  
  public void draw(){
    
    //super.draw();   // Should be the first line of draw().
    background(0xffACB7B5);//(200,255,200);
    
    if(width >=200 && height >=100){ 
    warningtxt.setSize(width-40, height-60);
    warningtxt.setPosition(20,20);//(width/2-100, height/2);
    cp5c.controller("OK").setPosition(width/2-33, height-35);
    }
  }
  
  
  public void OK(){
    warning.hide();
  }
  
}   
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "ASV_CV_TIMED_LOG_Aug_22_2013" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
