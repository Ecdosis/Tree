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
import calliope.core.constants.JSONKeys;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import tree.exception.TreeException;
import tree.constants.Params;
import org.json.simple.JSONObject;

/**
 * Get the title of a docid
 * @author desmond
 */
public class TitleHandler extends GetHandler
{
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
        String docid = request.getParameter(Params.DOCID);
        try
        {
            if ( docid != null )
            {
                JSONObject jObj = doGetMetadata( docid );
                String title = "no title";
                if ( jObj != null && jObj.containsKey(JSONKeys.TITLE) )
                     title = (String)jObj.get(JSONKeys.TITLE);
                response.setContentType("text/plain");
                response.getWriter().write(title);
            }
            else 
                throw new Exception(docid+" not found");
        }
        catch ( Exception e )
        {
            throw new TreeException(e);
        }
    }
}
