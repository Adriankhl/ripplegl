/*
    Copyright (C) 2017 by Paul Falstad

    This file is part of RippleGL.

    RippleGL is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 2 of the License, or
    (at your option) any later version.

    RippleGL is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with RippleGL.  If not, see <http://www.gnu.org/licenses/>.
*/

package com.falstad.ripple;

public class Point {
	public int x;
	public int y;
	
	 public Point(int i, int j) {
		x=i;
		y=j;
	}
	 
	 public Point(Point p) {
		x=p.x;
		y=p.y;
	}
	 
	 public Point() {
		 x=0;
		 y=0;
	 }
	 
	 public void setLocation(Point p) {
		 x=p.x;
		 y=p.y;
	 }
	 
         public String toString() { return "Point(" + x + "," + y + ")"; }

         @Override public boolean equals(Object other) {
             boolean result = false;
             if (other instanceof Point) {
                 Point that = (Point) other;
                 result = (this.x == that.x && this.y == that.y);
             }
             return result;
         }

         @Override public int hashCode() {
             return (41 * (41 + x) + y);
         }
}
