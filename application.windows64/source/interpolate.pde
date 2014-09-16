//corrently outputRowSize isnt used.

int interpolate(Table inputTable, int outputRowSize, int outputColumnSize) {
  //println("1-1");

  Table outputTable=new Table();
  int inputRowSize=inputTable.getRowCount();
  int inputColumnSize=inputTable.getColumnCount();
  float step=(float)outputColumnSize / (float)inputColumnSize;

  inputTable.addColumn();

  //create new columns.
  for (int i=1; i<=outputColumnSize; i++) {
    outputTable.addColumn("data"+i);
  }

  //println("1-2");

  for (int i=0; i<inputTable.getRowCount (); i++) { //intepolate
    //println("1-2-"+i);

    TableRow inputRow=inputTable.getRow(i);
    inputRow.setFloat(inputTable.getColumnCount()-1, inputRow.getFloat(0));

    TableRow outputRow=outputTable.addRow();

    float j=0.0;
    int p=0;
    for (int r=0; r<inputColumnSize; r++) {
      //println("1-2-"+i+"-"+r);

      for (; j<step; j+=1.0) {
        //println("1-2-"+i+"-"+r+"-"+j);
        
        outputRow.setFloat(p, lerp(inputRow.getFloat(r), inputRow.getFloat(r+1), j/step));

        if (p < outputColumnSize-1) {
          p++;
        }
      }
      j-=step;
    }
  }
  saveTable(outputTable, outFileName);
  return 1;
}

