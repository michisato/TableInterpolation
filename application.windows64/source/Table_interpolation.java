import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import g4p_controls.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class Table_interpolation extends PApplet {

// data interpolation program.
// do interpolate given table to given resolution.
// input: a part of table from csv file. 
// output: interpolated table as a csv file.

// Need G4P library


Table originalTable;
String absoluteFilePath;
String absoluteFileDirectory;
String inFileName;
String outFileName;

int inputRowSize;
int inputColumnSize;

public void setup() {
  size(480, 500, JAVA2D);
  createGUI();
  customGUI();
  // Place your setup code here
}

public void draw() {
  background(230);
  smooth();
}

// Use this method to add additional statements
// to customise the GUI controls
public void customGUI() {
}



public void fileSelected(File selection) {
  if (selection == null) {
    println("Window was closed or the user hit cancel.");
  } else {

    //get path ======
    absoluteFilePath=selection.getAbsolutePath();
    println("User selected " + absoluteFilePath);
    label_inputPath.setLocalColorScheme(GCScheme.BLUE_SCHEME);
    label_inputPath.setText(absoluteFilePath);
    //label1.setHeightToFit();
    label_inputPath.setTextAlign(GAlign.RIGHT, GAlign.BOTTOM);
    //label_inputPath.setTextBold();
    label3.setLocalColorScheme(GCScheme.BLUE_SCHEME);
    label3.setOpaque(false);

    //path management ========
    String [] splitPath=split(absoluteFilePath, '\\');
    inFileName=splitPath[splitPath.length-1];
    absoluteFileDirectory=join(shorten(splitPath), '\\');
    outFileName=absoluteFileDirectory+"\\LERPED_"+inFileName;

    //load table =========
    originalTable=loadTable(absoluteFilePath);
    inputRowSize=originalTable.getRowCount();
    inputColumnSize=originalTable.getColumnCount();

    println(originalTable.getRowCount() + " "+originalTable.getColumnCount());
    println("The directory is"+absoluteFileDirectory);
    label_tableSize.setText(inFileName+": "+originalTable.getRowCount() + " rows, " + originalTable.getColumnCount() + " columns");
    label_tableSize.setTextBold();
  }
}

/* =========================================================
 * ====                   WARNING                        ===
 * =========================================================
 * The code in this tab has been generated from the GUI form
 * designer and care should be taken when editing this file.
 * Only add/edit code inside the event handlers i.e. only
 * use lines between the matching comment tags. e.g.

 void myBtnEvents(GButton button) { //_CODE_:button1:12356:
     // It is safe to enter your event code here  
 } //_CODE_:button1:12356:
 
 * Do not rename this tab!
 * =========================================================
 */

public void button_interpolate_click(GButton source, GEvent event) { //_CODE_:button_intepolate:616383:
  println("/n button_interpolate_click event occured " + System.currentTimeMillis()%10000000 );
  if (verifyDatas()==0) {
    println("incomplete! failure data.");
    msg_box.setText("incomplete! failure data.");
    msg_box.setTextBold();
  } else {
    println("data verified.");


    //input data triming
    Table dataTable=new Table();
    dataTable=originalTable;

    for (int i=0; i<PApplet.parseInt (text_start_row.getText ())-1; i++) {
      dataTable.removeRow(0);
      println("removed begining row");
    }

    for (int i=0; i<inputRowSize-PApplet.parseInt (text_end_row.getText ()); i++) {
      dataTable.removeRow(dataTable.getRowCount()-1);
      println("removed ending row");
    }

    for (int i=0; i<PApplet.parseInt (text_start_column.getText ())-1; i++) {
      dataTable.removeColumn(0);
      println("removed begining column");
    }

    for (int i=0; i<inputColumnSize-PApplet.parseInt (text_end_column.getText ()); i++) {
      dataTable.removeColumn(dataTable.getColumnCount()-1);
      println("removed ending column");
    }

    println("data triming finished!");
    //show dataTable.
    //println(originalTable.getRowCount()+", "+originalTable.getRowCount());
    for (int i=0; i<dataTable.getRowCount (); i++) {
      for (int j=0; j<dataTable.getColumnCount (); j++) {
        //print(dataTable.getFloat(i, j)+", ");
      }
      //println("");
    }
    
    if (interpolate(dataTable, PApplet.parseInt(text_interpolation_row.getText()), PApplet.parseInt(text_interpolation_column.getText()))==1) {
     println("interpolation success!");
     msg_box.setText("interpolation success!");
     msg_box.setTextBold();
     } else {
     println("incomplete! interpolation did not success.");
     }
     
  }
} //_CODE_:button_intepolate:616383:

public void button_selectFile_click(GButton source, GEvent event) { //_CODE_:button_selectFile:228367:
  println("select file window was opened " + System.currentTimeMillis()%10000000 );
  selectInput("Select a file to process:", "fileSelected");
} //_CODE_:button_selectFile:228367:

public void text_start_row_change(GTextField source, GEvent event) { //_CODE_:text_start_row:840793:
  println("textfield1 - GTextField event occured " + System.currentTimeMillis()%10000000 );
} //_CODE_:text_start_row:840793:

public void text_start_colmn_change1(GTextField source, GEvent event) { //_CODE_:text_start_column:958386:
  println("textfield2 - GTextField event occured " + System.currentTimeMillis()%10000000 );
} //_CODE_:text_start_column:958386:

public void text_end_column_change(GTextField source, GEvent event) { //_CODE_:text_end_column:241393:
  println("textfield3 - GTextField event occured " + System.currentTimeMillis()%10000000 );
} //_CODE_:text_end_column:241393:

public void text_interpolation_row_change(GTextField source, GEvent event) { //_CODE_:text_interpolation_row:821081:
  println("textfield4 - GTextField event occured " + System.currentTimeMillis()%10000000 );
} //_CODE_:text_interpolation_row:821081:

public void text_end_row_change(GTextField source, GEvent event) { //_CODE_:text_end_row:995992:
  println("textfield5 - GTextField event occured " + System.currentTimeMillis()%10000000 );
} //_CODE_:text_end_row:995992:

public void text_interpolation_column_c(GTextField source, GEvent event) { //_CODE_:text_interpolation_column:638372:
  println("textfield1 - GTextField event occured " + System.currentTimeMillis()%10000000 );
} //_CODE_:text_interpolation_column:638372:

public void textarea1_change1(GTextArea source, GEvent event) { //_CODE_:textarea1:817481:
  println("textarea1 - GTextArea event occured " + System.currentTimeMillis()%10000000 );
} //_CODE_:textarea1:817481:



// Create all the GUI controls. 
// autogenerated do not edit
public void createGUI(){
  G4P.messagesEnabled(false);
  G4P.setGlobalColorScheme(GCScheme.CYAN_SCHEME);
  G4P.setCursor(ARROW);
  if(frame != null)
    frame.setTitle("table intepolation");
  button_intepolate = new GButton(this, 330, 270, 120, 30);
  button_intepolate.setText("INTERPOLATE");
  button_intepolate.setTextBold();
  button_intepolate.setLocalColorScheme(GCScheme.PURPLE_SCHEME);
  button_intepolate.addEventHandler(this, "button_interpolate_click");
  button_selectFile = new GButton(this, 370, 20, 80, 30);
  button_selectFile.setText("Select file");
  button_selectFile.setTextBold();
  button_selectFile.addEventHandler(this, "button_selectFile_click");
  text_start_row = new GTextField(this, 170, 100, 90, 30, G4P.SCROLLBARS_NONE);
  text_start_row.setOpaque(true);
  text_start_row.addEventHandler(this, "text_start_row_change");
  text_start_column = new GTextField(this, 360, 100, 90, 30, G4P.SCROLLBARS_NONE);
  text_start_column.setOpaque(true);
  text_start_column.addEventHandler(this, "text_start_colmn_change1");
  text_end_column = new GTextField(this, 360, 160, 90, 30, G4P.SCROLLBARS_NONE);
  text_end_column.setOpaque(true);
  text_end_column.addEventHandler(this, "text_end_column_change");
  text_interpolation_row = new GTextField(this, 170, 240, 90, 30, G4P.SCROLLBARS_NONE);
  text_interpolation_row.setOpaque(true);
  text_interpolation_row.addEventHandler(this, "text_interpolation_row_change");
  text_end_row = new GTextField(this, 170, 160, 90, 30, G4P.SCROLLBARS_NONE);
  text_end_row.setOpaque(true);
  text_end_row.addEventHandler(this, "text_end_row_change");
  label_inputPath = new GLabel(this, 20, 20, 340, 30);
  label_inputPath.setTextAlign(GAlign.RIGHT, GAlign.BOTTOM);
  label_inputPath.setText(" ");
  label_inputPath.setLocalColorScheme(GCScheme.BLUE_SCHEME);
  label_inputPath.setOpaque(false);
  label2 = new GLabel(this, 280, 170, 80, 20);
  label2.setTextAlign(GAlign.RIGHT, GAlign.MIDDLE);
  label2.setText("column");
  label2.setLocalColorScheme(GCScheme.BLUE_SCHEME);
  label2.setOpaque(false);
  label3 = new GLabel(this, 90, 170, 80, 20);
  label3.setTextAlign(GAlign.RIGHT, GAlign.MIDDLE);
  label3.setText("row");
  label3.setLocalColorScheme(GCScheme.BLUE_SCHEME);
  label3.setOpaque(false);
  label4 = new GLabel(this, 20, 150, 150, 20);
  label4.setTextAlign(GAlign.LEFT, GAlign.MIDDLE);
  label4.setText("END cell");
  label4.setTextBold();
  label4.setLocalColorScheme(GCScheme.BLUE_SCHEME);
  label4.setOpaque(false);
  label5 = new GLabel(this, 280, 110, 80, 20);
  label5.setTextAlign(GAlign.RIGHT, GAlign.MIDDLE);
  label5.setText("column");
  label5.setLocalColorScheme(GCScheme.BLUE_SCHEME);
  label5.setOpaque(false);
  label6 = new GLabel(this, 90, 110, 80, 20);
  label6.setTextAlign(GAlign.RIGHT, GAlign.MIDDLE);
  label6.setText("row");
  label6.setLocalColorScheme(GCScheme.BLUE_SCHEME);
  label6.setOpaque(false);
  label7 = new GLabel(this, 20, 90, 150, 20);
  label7.setTextAlign(GAlign.LEFT, GAlign.MIDDLE);
  label7.setText("START cell");
  label7.setTextBold();
  label7.setLocalColorScheme(GCScheme.BLUE_SCHEME);
  label7.setOpaque(false);
  label8 = new GLabel(this, 20, 220, 240, 20);
  label8.setTextAlign(GAlign.LEFT, GAlign.MIDDLE);
  label8.setText("data num of lerp");
  label8.setTextBold();
  label8.setLocalColorScheme(GCScheme.BLUE_SCHEME);
  label8.setOpaque(false);
  label9 = new GLabel(this, 370, 50, 80, 20);
  label9.setTextAlign(GAlign.RIGHT, GAlign.MIDDLE);
  label9.setText("(only .csv)");
  label9.setLocalColorScheme(GCScheme.BLUE_SCHEME);
  label9.setOpaque(false);
  label10 = new GLabel(this, 90, 250, 80, 20);
  label10.setTextAlign(GAlign.RIGHT, GAlign.MIDDLE);
  label10.setText("row");
  label10.setOpaque(false);
  text_interpolation_column = new GTextField(this, 360, 240, 90, 30, G4P.SCROLLBARS_NONE);
  text_interpolation_column.setOpaque(true);
  text_interpolation_column.addEventHandler(this, "text_interpolation_column_c");
  label11 = new GLabel(this, 280, 250, 80, 20);
  label11.setTextAlign(GAlign.RIGHT, GAlign.MIDDLE);
  label11.setText("column");
  label11.setOpaque(false);
  label_tableSize = new GLabel(this, 20, 50, 330, 20);
  label_tableSize.setOpaque(false);
  msg_box = new GLabel(this, 20, 280, 290, 20);
  msg_box.setText(" ");
  msg_box.setTextBold();
  msg_box.setLocalColorScheme(GCScheme.RED_SCHEME);
  msg_box.setOpaque(false);
  textarea1 = new GTextArea(this, 20, 320, 430, 160, G4P.SCROLLBARS_NONE);
  textarea1.setText("Note: This version is quite limited since it is my individual use. Current version allows only linear interpolation (lerp) of COLUMN. So input row size and output row size should be same (ex: when start row is 2 and end row is 10,  the data num of interpolation should be 9.). In addition, each row is presumed as circular data. So the first column data is automatically added into after last column. plz improve someone:) (c)michisato, (CC BY-SA 2.1) . The UI was developed with G4P (http://www.lagers.org.uk/g4p/). Thanks!");
  textarea1.setOpaque(true);
  textarea1.addEventHandler(this, "textarea1_change1");
}

// Variable declarations 
// autogenerated do not edit
GButton button_intepolate; 
GButton button_selectFile; 
GTextField text_start_row; 
GTextField text_start_column; 
GTextField text_end_column; 
GTextField text_interpolation_row; 
GTextField text_end_row; 
GLabel label_inputPath; 
GLabel label2; 
GLabel label3; 
GLabel label4; 
GLabel label5; 
GLabel label6; 
GLabel label7; 
GLabel label8; 
GLabel label9; 
GLabel label10; 
GTextField text_interpolation_column; 
GLabel label11; 
GLabel label_tableSize; 
GLabel msg_box; 
GTextArea textarea1; 

//corrently outputRowSize isnt used.

public int interpolate(Table inputTable, int outputRowSize, int outputColumnSize) {
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

    float j=0.0f;
    int p=0;
    for (int r=0; r<inputColumnSize; r++) {
      //println("1-2-"+i+"-"+r);

      for (; j<step; j+=1.0f) {
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

public int verifyDatas() {
  //verify that all numbers are natural number
  //start and end num should be inside of original table size.

  String [] dataverify;
  dataverify = new String [6];
  for (int i=0; i<dataverify.length; i++) {
    dataverify[i]="0";
  }

  int count=0;
  if (PApplet.parseInt(text_start_row.getText())>0 && PApplet.parseInt(text_start_row.getText())< originalTable.getRowCount()) {
    dataverify[count++]="1";
  }else{
    dataverify[count++]="0";
  }
  if (PApplet.parseInt(text_start_column.getText())>0 && PApplet.parseInt(text_start_column.getText())< originalTable.getColumnCount()) {
    dataverify[count++]="1";
  }else{
    dataverify[count++]="0";
  }
  if (PApplet.parseInt(text_end_row.getText())>PApplet.parseInt(text_start_row.getText()) && PApplet.parseInt(text_end_row.getText())<= originalTable.getRowCount()) {
    dataverify[count++]="1";
  }else{
    dataverify[count++]="0";
  }
  if (PApplet.parseInt(text_end_column.getText())>PApplet.parseInt(text_start_column.getText()) && PApplet.parseInt(text_end_column.getText())<= originalTable.getColumnCount()) {
    dataverify[count++]="1";
  }else{
    dataverify[count++]="0";
  }
  /* currently output row size should be same size to input row size.
   if (int(text_interpolation_row.getText())>0) {
   dataverify[count++]="1";
   }
   */
  if (PApplet.parseInt(text_interpolation_row.getText())==(PApplet.parseInt(text_end_row.getText())-PApplet.parseInt(text_start_row.getText())+1)) {
    dataverify[count++]="1";
  }else{
    dataverify[count++]="0";
  }
  if (PApplet.parseInt(text_interpolation_column.getText())>0) {
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

  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Table_interpolation" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
