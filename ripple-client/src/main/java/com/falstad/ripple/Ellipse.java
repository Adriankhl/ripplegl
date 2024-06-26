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

public class Ellipse extends RectDragObject {
	Ellipse(){}
	Ellipse(StringTokenizer st) { super(st); }
	
	void prepare() {
		RippleSim.drawEllipse(
				(topLeft.x+topRight.x)/2, (topLeft.y+bottomLeft.y)/2,
				(topRight.x-topLeft.x)/2, (bottomLeft.y-topLeft.y)/2);
	}

	boolean hitTestInside(double x, double y) { return false; }

	@Override double hitTest(int x, int y) {
		x -= (topLeft.x+topRight.x)/2;
		y -= (topLeft.y+bottomLeft.y)/2;
		double a = (topRight.x-topLeft.x)/2;
		double b = (bottomLeft.y-topLeft.y)/2;
		double ht = Math.abs(Math.sqrt(x*x/(a*a)+y*y/(b*b))-1)*a;
		return ht;
	}
	
	@Override void drawSelection() {
		prepare();
		double a = (topRight.x-topLeft.x)/2;
		double b = (bottomRight.y-topRight.y)/2;
		int fc = (int)Math.sqrt(Math.abs(a*a-b*b));
		int fd = fc;
		if (a > b)
			fd = 0;
		else
			fc = 0;
		RippleSim.drawFocus((topLeft.x+topRight.x)/2-fc, (topLeft.y+bottomLeft.y)/2-fd);
		RippleSim.drawFocus((topLeft.x+topRight.x)/2+fc, (topLeft.y+bottomLeft.y)/2+fd);
	}
	
	int getDumpType() { return 'e'; }

}
