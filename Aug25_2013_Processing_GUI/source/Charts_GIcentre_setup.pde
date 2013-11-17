void charts_gic_setup(){
  
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
