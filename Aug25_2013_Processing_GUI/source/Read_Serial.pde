void  read_serialASV() {

  
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
          float[] fData= float(sData);
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


void  read_serialCV() {
 
   
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
          float[] fData= float(sData);
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
 

 
  

