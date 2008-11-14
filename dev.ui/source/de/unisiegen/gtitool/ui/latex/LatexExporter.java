package de.unisiegen.gtitool.ui.latex;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;



/**
 * Exporter for Latex content
 * 
 * @author Simon Meurer
 * @version
 */
public final class LatexExporter
{
  /**
   * Creates the suroundings of the LatexFile
   *
   * @param latex The contents of the latexFile
   * @param file The latexFile
   */
  public static void buildLatexFile(String latex, File file) {
    String s = "\\documentclass[a4paper,11pt]{article}\n"; //$NON-NLS-1$
    s += "\\usepackage{german}\n\\usepackage[utf8]{inputenc}\n"; //$NON-NLS-1$
    s+="\\usepackage{tree-dvips}\n"; //$NON-NLS-1$
    s+="\\begin{document}\n"; //$NON-NLS-1$
    s+=latex;
    s+="\n\\end{document}"; //$NON-NLS-1$
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
