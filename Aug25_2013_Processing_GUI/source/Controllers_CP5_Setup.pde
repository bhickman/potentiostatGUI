void cp5_controllers_setup(){
 ////////////////////////////////////////////////Text Fields//////////////////////////////
  cp5 = new ControlP5(this);  //cp5 = new ControlP5(this);
  PFont font = createFont("arial", 20);
  PFont font2 = createFont("arial", 16);
  PFont font3 = createFont("arial",12); 
  
  
  Starting_Voltage = cp5.addTextfield("Starting_Voltage")
    .setColor(#030302) 
      //.setColorActive(#AA8A16)
      .setColorBackground(#CEC6C6)//(#FFFEFC) 
        //.setColorCaptionLabel(#F01B1B) 
        .setColorForeground(#AA8A16) 
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
    .setColor(#030302) 
      //.setColorActive(#AA8A16)
      .setColorBackground(#CEC6C6) 
        //.setColorCaptionLabel(int) 
        .setColorForeground(#AA8A16) 
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
    .setColor(#030302) 
      //.setColorActive(#AA8A16)
      .setColorBackground(#CEC6C6) 
        //.setColorCaptionLabel(int) 
        .setColorForeground(#AA8A16) 
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
    .setColor(#030302) 
      //.setColorActive(#AA8A16)
      .setColorBackground(#CEC6C6) 
        //.setColorCaptionLabel(int) 
        .setColorForeground(#AA8A16) 
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
    .setColor(#030302) 
      //.setColorActive(#AA8A16)
      .setColorBackground(#CEC6C6) 
        //.setColorCaptionLabel(int) 
        .setColorForeground(#AA8A16) 
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
    .setColor(#030302) 
      //.setColorActive(#AA8A16)
      .setColorBackground(#CEC6C6) 
        //.setColorCaptionLabel(int) 
        .setColorForeground(#AA8A16) 
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
    .setColor(#030302) 
      //.setColorActive(#AA8A16)
      .setColorBackground(#CEC6C6) 
        //.setColorCaptionLabel(int) 
        .setColorForeground(#AA8A16) 
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
    .setColor(#030302) 
      //.setColorActive(#AA8A16)
      .setColorBackground(#CEC6C6) 
        //.setColorCaptionLabel(int) 
        .setColorForeground(#AA8A16) 
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
            .setColor(#030302)
              .setColorBackground(#CEC6C6)
                .setColorForeground(#AA8A16)//#CEC6C6                 
                    ;


  /////////////////////////////Bang's///////////////////////////////////////////////////////////
  cp5.addBang("Start_Run")
    .setColorBackground(#FFFEFC)//#FFFEFC 
        .setColorCaptionLabel(#030302) //#030302
          .setColorForeground(#AA8A16)
          .setColorActive(#06CB49)
            .setPosition(20, 520)
              .setSize(150, 40)
                .setTriggerEvent(Bang.RELEASE)
                  .setLabel("Start Run") //
                    .getCaptionLabel().align(ControlP5.CENTER, ControlP5.CENTER)  //.setLabel("Start_Run")
                      ;

 cp5.addBang("Connect")
    .setColorBackground(#FFFEFC)//#FFFEFC 
        .setColorCaptionLabel(#030302) //#030302
          .setColorForeground(#AA8A16)  
          .setPosition(240, 10)
            .setSize(40, 20)
              .setTriggerEvent(Bang.RELEASE)
                .getCaptionLabel().align(ControlP5.CENTER, ControlP5.CENTER)  //.setLabel("Start_Run")
                  ;

  cp5.addBang("Save_run")
    .setColorBackground(#FFFEFC)//#FFFEFC 
        .setColorCaptionLabel(#030302) //#030302
          .setColorForeground(#AA8A16)  
          .setPosition(450, 10)
            .setSize(100, 20)
              .setTriggerEvent(Bang.RELEASE)
                .setLabel("Save Run")
                  .getCaptionLabel().align(ControlP5.CENTER, ControlP5.CENTER)  //.setLabel("Start_Run")
                    ;
                    
   cp5.addBang("Intergrate_Data")                 
      .setColorBackground(#FFFEFC)//#FFFEFC 
        .setColorCaptionLabel(#030302) //#030302
          .setColorForeground(#AA8A16)  
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
