public void Starting_Voltage() {              //get start voltage from text box
  StartV = cp5.get(Textfield.class, "Starting_Voltage").getText();
  iStartV = round(float(StartV));
  iStartV=iStartV+1852;
  StartV = nf(iStartV, 6);   // make StartV have 4 digits. pad with zero if no digits
}
public void End_Voltage() {               // get end voltage from text box
  EndV = cp5.get(Textfield.class, "End_Voltage").getText();
  iEndV = round(float(EndV));
  iEndV=iEndV+1852;
  EndV = nf(iEndV, 6);   // make EndV have 4 digits. pad with zero if no digits
}
public void Scan_Rate() {                 // get scan rate from text box
  ScanR = cp5.get(Textfield.class, "Scan_Rate").getText();
  iScanR = round(float(ScanR));
  ScanR = nf(iScanR, 6);   // make ScanR have 3 digits. pad with zero if no digits
}
public void Delay_Time() {                // get delay time from text box
  DelayT = cp5.get(Textfield.class, "Delay_Time").getText();
  iDelayT = round(float(DelayT));
  DelayT = nf(iDelayT, 6);   // make DelayT have 3 digits. pad with zero if no digits
}

public void InitialV_Time() {                 // get scan rate from text box
  InitVT = cp5.get(Textfield.class, "InitialV_Time").getText();
  iInitVT = round(float(InitVT));
  InitVT = nf(iInitVT, 6);   // make ScanR have 3 digits. pad with zero if no digits
}
public void FinalV_Time() {                // get delay time from text box
  FnlVT = cp5.get(Textfield.class, "FinalV_Time").getText();
  iFnlVT = round(float(FnlVT));
  FnlVT = nf(iFnlVT, 6);   // make DelayT have 3 digits. pad with zero if no digits
}
public void Number_of_Runs() {                // get delay time from text box
  Nor = cp5.get(Textfield.class, "Number_of_Runs").getText();
  iNor = round(float(Nor));
  Nor = nf(iNor, 6);   // make DelayT have 3 digits. pad with zero if no digits
}
public void Run_Intervale() {                // get delay time from text box
  Rint = cp5.get(Textfield.class, "Run_Intervale").getText();
  iRint = round(float(Rint));
  Rint = nf(iRint, 6);   // make DelayT have 3 digits. pad with zero if no digits
}
