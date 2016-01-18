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

package tree.exception;
import calliope.core.exception.CalliopeException;

/**
 * Specific exception classes for various parts of TILT
 * @author desmond
 */
public class TreeException extends CalliopeException
{
    /**
     * Create a general MMLException from scratch
     * @param message the message it is to bear
     */
    public TreeException( String message )
    {
        super( message );
    }
    /**
     * Wrapper for another exception
     * @param e the other exception
     */
    public TreeException( Exception e )
    {
        super( e );
    }
}
