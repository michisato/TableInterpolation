
void fileSelected(File selection) {
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

