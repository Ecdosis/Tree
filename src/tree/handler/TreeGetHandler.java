/*
 * This file is part of Tree.
 *
 *  Tree is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  Tree is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Tree.  If not, see <http://www.gnu.org/licenses/>.
 *  (c) copyright Desmond Schmidt 2016
 */


package tree.handler;

import calliope.core.handler.GetHandler;
import calliope.core.Utils;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import javax.servlet.ServletOutputStream;
import calliope.core.constants.Database;
import calliope.core.handler.EcdosisMVD;
import edu.luc.nmerge.fastme.FastME;
import tree.exception.TreeException;
import tree.constants.Service;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import tree.constants.Params;
import tree.drawgram.DrawgramData;
import tree.drawgram.DrawgramInterface;
import java.util.List;
import java.io.FileNotFoundException;
import java.io.File;
import java.io.PrintStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.awt.Image;
import org.ghost4j.document.PSDocument;
import org.ghost4j.renderer.SimpleRenderer;
import java.io.ByteArrayOutputStream;

/**
 * Get a file in the misc collection. No versions, but maybe links.
 * @author desmond
 */
public class TreeGetHandler extends GetHandler
{
    String font;
    String treeGrows;
    String treeStyle;
    boolean useBranchLengths; 
    String ancNodes;
    String docid;
    void fixPages( File plotfile )
    {
        try
        {
            FileInputStream fis = new FileInputStream(plotfile);
            byte[] data = new byte[(int)plotfile.length()];
            fis.read(data);
            fis.close();
            String content = new String(data);
            content = content.replace("%%Pages: 1 1","%%Pages: 1");
            data = content.getBytes();
            FileOutputStream fos = new FileOutputStream(plotfile);
            fos.write(data );
            fos.close();
        }
        catch ( Exception e )
        {
        }
    }
    void loadMissingFile( ByteArrayOutputStream bos ) throws Exception
    {
        File missing = new File("webapps/tree/WEB-INF/missing.jpg");
        if ( !missing.exists() )
        {
            // try local directory
            missing = new File("missing.jpg");
            if ( !missing.exists() )
                throw new FileNotFoundException("missing.jpg not found in "
                    +System.getProperty("user.dir"));
        }
        BufferedImage notFound = ImageIO.read(missing);
        ImageIO.write(notFound, "jpg",bos);
    }
    /**
     * Get a JPG representing a tree of an MVD
     * @param request the servlet request
     * @param response the servlet response
     * @param urn the docID, stripped of its prefix
     * @throws TreeException 
     */
    public void handle( HttpServletRequest request, 
        HttpServletResponse response, String urn ) throws TreeException
    {
        if ( urn != null && urn.equals(Service.TITLE) )
            new TitleHandler().handle(request,response,Utils.pop(urn));
        else
        {
            treeStyle = request.getParameter(Params.TREESTYLE);
            docid = request.getParameter(Params.DOCID);
            //font = request.getParameter(Params.FONT);
            treeGrows = request.getParameter(Params.TREEGROWS);
            String ubl = request.getParameter(Params.USEBRANCHLENGTHS);
            useBranchLengths = (ubl==null)?false:(ubl.toLowerCase().equals("yes"))?true:false;
            ancNodes = request.getParameter(Params.ANCNODES);
//            System.out.println("docid="+docid);
//            //System.out.println("font="+font);
//            System.out.println("treegrows="+treeGrows);
//            System.out.println("treestyle="+treeStyle);
//            System.out.println("usebranchlengths="+useBranchLengths);
//            System.out.println("ancnodes="+ancNodes);
            if ( docid != null )
            {
                DrawgramData dgd = new DrawgramData();
//                if ( font != null )
//                    dgd.setFont(font);
                if ( treeGrows != null )
                    dgd.setTreeGrows(treeGrows);
                if ( treeStyle != null )
                    dgd.setTreeStyle(treeStyle);
                System.out.println("treeStyle = "+treeStyle);
                if ( useBranchLengths != false )
                    dgd.setUseBranchLengths(useBranchLengths);
                if ( ancNodes != null )
                    dgd.setAncNodes(ancNodes);
                // create temporary files
                try
                {
                    File infile = File.createTempFile("TREE",".tre");
                    File plotfile = File.createTempFile("PLOT",".ps");
                    EcdosisMVD eMvd = doGetMVD( Database.CORTEX, docid );
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    if ( eMvd != null && eMvd.mvd != null )
                    {
                        double[][] d = eMvd.mvd.computeDiffMatrix();
                        FastME fastME = new FastME();
                        int numVersions = eMvd.mvd.numVersions();
                        String[] shortNames = new String[numVersions];
                        for ( int i=0;i<numVersions;i++ )
                        {
                            String vid = eMvd.mvd.getVersionId((short)(i+1) );
                            if ( vid.startsWith("/") )
                                vid = vid.substring(1);
                            shortNames[i] = vid;
                        }
                        fastME.buildTree( d, shortNames );
                        fastME.refineTree();
                        PrintStream out = new PrintStream(infile);
                        fastME.T.NewickPrintTree( out );
                        out.close();
                        // actually draw it!
                        DrawgramInterface dgi = new DrawgramInterface();
                        dgd.setIntree(infile.getAbsolutePath());
                        dgd.setPlotfile(plotfile.getAbsolutePath());
                        if ( plotfile.exists() )
                            plotfile.delete();
                        try
                        {
                            dgi.drawgramRun(dgd);
                            fixPages(plotfile);
                            // convert plotfile to JPG  
                            //System.out.println(System.getProperty("java.library.path"));
                            PSDocument document = new PSDocument();
                            document.load(plotfile);
                            SimpleRenderer renderer = new SimpleRenderer();
                            if ( plotfile.exists() )
                                plotfile.delete();
                            renderer.setResolution(300);
                            List<Image> images = renderer.render(document);
                            ImageIO.write((RenderedImage) images.get(0), "jpg",bos);
                        }
                        catch ( Exception e )
                        {
                            loadMissingFile(bos);
                        }
                    }
                    else
                    {
                        loadMissingFile(bos);
                    }
                    // write to output
                    response.setContentType("image/jpeg");
                    ServletOutputStream output = response.getOutputStream();
                    output.write(bos.toByteArray());
                }
                catch ( Exception e )
                {
                    throw new TreeException(e);
                }

            }
            else
                throw new TreeException("Missing docid for tree");
        }
    }
}