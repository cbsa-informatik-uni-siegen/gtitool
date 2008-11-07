package de.unisiegen.gtitool.ui.latex;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;



/**
 * TODO
 *
 */
public class LatexExporter
{

  
  /**
   * TODO
   *
   */
  public LatexExporter ()
  {
    
  }
  
  public void buildLatexFile(String latex, File file) {
    String s = "\\documentclass[a4paper,11pt]{article}\n";
    s += "\\usepackage{german}\n\\usepackage[utf8]{inputenc}\n";
    s+="\\usepackage{tree-dvips}\n";
    s+="\\begin{document}\n";
    s+=latex;
    s+="\n\\end{document}";
    FileWriter fw;
    try
    {
      fw = new FileWriter(file);
      fw.write ( s );
      fw.close ();
    }
    catch ( IOException exc )
    {
      exc.printStackTrace();
    }
  }
  
}
