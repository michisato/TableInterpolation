// data interpolation program.
// do interpolate given table to given resolution.
// input: a part of table from csv file. 
// output: interpolated table as a csv file.

// Need G4P library
import g4p_controls.*;

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


