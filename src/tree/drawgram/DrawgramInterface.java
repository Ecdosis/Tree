package tree.drawgram;

import tree.exception.TreeException;

import tree.util.TestFileNames;

import com.sun.jna.Library;
import com.sun.jna.Native;

public class DrawgramInterface 
{
    public interface Drawgram extends Library {
        public void  drawgram(
            String  intree,
            String  usefont,
            String  plotfile,
            String  plotfileopt,
            String  treegrows,  // useful option
            String  treestyle,  // useful option
            boolean usebranchlengths,   // useful option
            double  labelangle,
            boolean scalebranchlength,
            double  branchlength,
            double  breadthdepthratio,
            double  stemltreedratio,
            double  chhttipspratio,
//            double xpageratio,
//            double ypageratio,
            String  ancnodes,
            boolean doplot,
            String  finalplotkind); // useful option
    }

    public boolean drawgramRun(DrawgramData inVals) throws TreeException
    {
        TestFileNames test = new TestFileNames();

        if (!test.FileAvailable(inVals.intree, "Intree"))
        {
            return false;
        }

        if (inVals.doplot) // only check if final plot
        { 
            String opt = test.FileAlreadyExists(inVals.plotfile, "Plotfile");
            if (opt == "q")
            {
                return false;
            }
            else
            {
                if (opt == "a")
                {
                    inVals.plotfileopt = "ab";
                }
                else
                {
                    inVals.plotfileopt = "wb";					
                }
            }
        }

        // at this point we hook into the C code			
        try
        {
            Drawgram Drawgram = (Drawgram) Native.loadLibrary("drawgram", 
                Drawgram.class);
            Drawgram.drawgram(
                    inVals.intree,
                    inVals.usefont,
                    inVals.plotfile,
                    inVals.plotfileopt,
                    inVals.treegrows,
                    inVals.treestyle,
                    inVals.usebranchlengths,
                    inVals.labelangle,
                    inVals.scalebranchlength,
                    inVals.branchlength,
                    inVals.breadthdepthratio,
                    inVals.stemltreedratio,
                    inVals.chhttipspratio,
                    inVals.ancnodes,
                    inVals.doplot,
                    inVals.finalplottype);
            return true;
        }
        catch (UnsatisfiedLinkError e)
        {
            e.printStackTrace(System.out);
        }
        return false;
    }
}

	