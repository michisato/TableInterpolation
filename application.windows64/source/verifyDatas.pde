int verifyDatas() {
  //verify that all numbers are natural number
  //start and end num should be inside of original table size.

  String [] dataverify;
  dataverify = new String [6];
  for (int i=0; i<dataverify.length; i++) {
    dataverify[i]="0";
  }

  int count=0;
  if (int(text_start_row.getText())>0 && int(text_start_row.getText())< originalTable.getRowCount()) {
    dataverify[count++]="1";
  }else{
    dataverify[count++]="0";
  }
  if (int(text_start_column.getText())>0 && int(text_start_column.getText())< originalTable.getColumnCount()) {
    dataverify[count++]="1";
  }else{
    dataverify[count++]="0";
  }
  if (int(text_end_row.getText())>int(text_start_row.getText()) && int(text_end_row.getText())<= originalTable.getRowCount()) {
    dataverify[count++]="1";
  }else{
    dataverify[count++]="0";
  }
  if (int(text_end_column.getText())>int(text_start_column.getText()) && int(text_end_column.getText())<= originalTable.getColumnCount()) {
    dataverify[count++]="1";
  }else{
    dataverify[count++]="0";
  }
  /* currently output row size should be same size to input row size.
   if (int(text_interpolation_row.getText())>0) {
   dataverify[count++]="1";
   }
   */
  if (int(text_interpolation_row.getText())==(int(text_end_row.getText())-int(text_start_row.getText())+1)) {
    dataverify[count++]="1";
  }else{
    dataverify[count++]="0";
  }
  if (int(text_interpolation_column.getText())>0) {
    dataverify[count]="1";
  }

  //println(dataverify);
  String checkDataVerify=join(dataverify, ',');
  println(checkDataVerify);
  if (checkDataVerify.equals("1,1,1,1,1,1")) {
    return 1;
  } else {
    return 0;
  }
}

