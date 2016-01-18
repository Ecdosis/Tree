/* Version 3.696.  
   Written by Joseph Felsenstein and Christopher A. Meacham.  Additional code
   written by Hisashi Horino, Sean Lamont, Andrew Keefe, Daniel Fineman, 
   Akiko Fuseki, Doug Buxton, Michal Palczewski, and James McGill.

   Copyright (c) 1986-2014, Joseph Felsenstein
   All rights reserved.

   Redistribution and use in source and binary forms, with or without
   modification, are permitted provided that the following conditions are met:

   1. Redistributions of source code must retain the above copyright notice,
      this list of conditions and the following disclaimer.

   2. Redistributions in binary form must reproduce the above copyright notice,
      this list of conditions and the following disclaimer in the documentation
      and/or other materials provided with the distribution.

   THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" 
   AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE 
   IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE 
   ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE 
   LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR 
   CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF 
   SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS 
   INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN 
   CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) 
   ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE 
   POSSIBILITY OF SUCH DAMAGE.
*/
package tree.drawgram;
public class DrawgramData{
    static String[] fontNames = {"Times-Roman","Times-Italic","Times-Bold",
        "Times-BoldItalic","AvantGarde-Book","AvantGarde-BookOblique",
        "AvantGarde-Demi","AvantGarde-DemiOblique","Bookman-Light",
        "Bookman-LightItalic","Bookman-Demi","Bookman-DemiItalic",
        "Courier","Courier-Italic","Courier-Bold","Courier-BoldItalic",
        "Helvetica","Helvetica-Oblique","Helvetica-Bold","Helvetica-BoldOblique",
        "Helvetica-Narrow","Helvetica-Narrow-Oblique","Helvetica-Narrow-Bold",
        "Helvetica-Narrow-BoldOblique","NewCenturySchlbk-Roman",
        "NewCenturySchlbk-Italic","NewCenturySchlbk-Bold",
        "NewCenturySchlbk-BoldItalic","Palatino-Roman","Palatino-Italic",
        "Palatino-Bold","Palatino-BoldItalic","Symbol",
        "ZapfChancery-MediumItalic","ZapfDingbats"};
    static String[] treeStyles = {"phenogram","cladogram","curvogram",
        "eurogram","swoopogram","circular","phenogram"};
        /** path to newick tree file */
		String  intree;
        /** name of font for rendering (see fontNames above) */
		String  usefont;
        /** name of file for output */
		String  plotfile;
        /** file mode -- supposed to be binary "wb" */
		String  plotfileopt;
        /** "horizontal" or "vertical" */
		String  treegrows;
        /** see treeStyles above */;
		String  treestyle;
        /** if true branch length are proportional to edit distance */
		boolean usebranchlengths;
        /** angle of label to tree: 0.0,90.0,-90.0 */
		Double  labelangle;
        /** seems to regulate scaling of branches globally */
		boolean scalebranchlength;
        /** defaults to "1.0" */
		Double  branchlength;
        /** defaults to "0.53" */
		Double  breadthdepthratio;
        /** stem length tree depth ratio defaults to "0.05" */
		Double  stemltreedratio;
        /** probably governs distance of label to tip defaults to "0.333" */
		Double  chhttipspratio;
        /** one of "weighted","intermediate";,"centered","inner","vshaped",
         * "weighted", defaults to "weighted" I think */
		String  ancnodes;
        /** defaults to user's home directory */
		String  librarypath;
        /** draw plot or preview */
		boolean doplot;
        /** output format. only interesting one: "lw" (all others too bitty) */
		String  finalplottype;
        public DrawgramData()
        {
            this.plotfileopt="wb";
            this.treestyle = "swoopogram";
            this.usebranchlengths = false;
            this.labelangle = 90.0;
            this.scalebranchlength = true;
            this.branchlength = 1.0;
            this.breadthdepthratio = 0.63;
            this.stemltreedratio=0.05;
            this.chhttipspratio=0.333;
            this.ancnodes="weighted";
            this.librarypath=System.getProperty("user.dir");
            this.doplot = true;
            this.finalplottype="lw";
            // user-settable properties
            this.usefont = "Times-Roman";
            this.treegrows = "horizontal";
        }
        public void setIntree(String intree)
        {
            this.intree=intree;
        }
        public void setPlotfile(String plotfile )
        {
            this.plotfile = plotfile;
        }
        public void setFont(String fontName)
        {
            this.usefont=fontName;
        }
        public void setTreeGrows( String treegrows )
        {
            this.treegrows = treegrows;
        }
        public void setTreeStyle( String treestyle )
        {
            this.treestyle = treestyle;
        }
        public void setUseBranchLengths( boolean usebranchlengths )
        {
            this.usebranchlengths = usebranchlengths;
        }
        public void setAncNodes( String value )
        {
            this.ancnodes = value;
        }
	}