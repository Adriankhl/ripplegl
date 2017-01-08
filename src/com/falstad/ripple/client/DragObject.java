package com.falstad.ripple.client;

import java.util.Vector;

public abstract class DragObject implements Editable {
	Vector<DragHandle> handles;
	boolean selected;
	RippleSim sim;
	double rotation;
	double transform[];
	double invTransform[];
	double centerX, centerY;  // set in setTransform()
	int flags;
	
	DragObject() {
		handles = new Vector<DragHandle>(4);
		sim = RippleSim.theSim;
		sim.changedWalls = true;
		setTransform();
	}
	
	DragObject(StringTokenizer st) {
		handles = new Vector<DragHandle>(4);
		sim = RippleSim.theSim;
		sim.changedWalls = true;
		flags = new Integer(st.nextToken()).intValue();
	}

	void prepare() {}
	void select() { selected = true; }
	void deselect() { selected = false; }
	void run() {}
	void setTransform() {
		if (transform == null) {
			transform = new double[6];
			invTransform = new double[6];
		}
		int i;
		double cx = 0, cy = 0;
		for (i = 0; i != handles.size(); i++) {
			DragHandle dh = handles.get(i);
			cx += dh.x;
			cy += dh.y;
		}
		cx /= handles.size();
		cy /= handles.size();
		centerX = cx;
		centerY = cy;
		
		// make translation-rotation-translation matrix
		transform[0] = transform[4] = Math.cos(rotation);
		transform[1] = Math.sin(rotation);
		transform[3] = -transform[1];
		transform[2] = (1-transform[0])*cx - transform[1]*cy;
		transform[5] = -transform[3]*cx + (1-transform[4])*cy;
		invTransform[0] = invTransform[4] = transform[0];
		invTransform[1] = transform[3];
		invTransform[3] = transform[1];
		invTransform[2] = (1-transform[0])*cx + transform[1]*cy;
		invTransform[5] = transform[3]*cx + (1-transform[4])*cy;
	}
	
	boolean drag(int dx, int dy) {
		int i;
		for (i = 0; i != handles.size(); i++) {
			DragHandle dh = handles.get(i);
			dh.x += dx;
			dh.y += dy;
		}
		setTransform();
		return true;
	}
	
	void draw() {
		if (!selected)
			return;
		int i;
		for (i = 0; i != handles.size(); i++) {
			DragHandle dh = handles.get(i);
			int x = (int) (dh.x*transform[0]+dh.y*transform[1]+transform[2]);
			int y = (int) (dh.x*transform[3]+dh.y*transform[4]+transform[5]);
			RippleSim.drawHandle(x, y); // -sim.windowOffsetX, y-sim.windowOffsetY);
		}
		drawSelection();
	}
	
	Point transformPoint(Point p) {
		int x = (int) (p.x*transform[0]+p.y*transform[1]+transform[2]);
		int y = (int) (p.x*transform[3]+p.y*transform[4]+transform[5]);
		return new Point(x, y);
	}

	Point inverseTransformPoint(Point p) {
		int x = (int) (p.x*invTransform[0]+p.y*invTransform[1]+invTransform[2]);
		int y = (int) (p.x*invTransform[3]+p.y*invTransform[4]+invTransform[5]);
		return new Point(x, y);
	}

	void drawSelection() {
	}
	
	void rotate(double ang) {
		rotation += ang;
		setTransform();
		sim.changedWalls = true;
	}

	void rotateTo(int x, int y) {
//		sim.console("rotateto " + x + " " + y + " " + centerX + " " + centerY);
		rotation = Math.atan2(-y+centerY, x-centerX)-Math.PI/2;
		setTransform();
		sim.changedWalls = true;
	}

	boolean canRotate() { return false; }
	
	static double hypotf(double x, double y) {
		return Math.sqrt(x*x+y*y);
	}
	
	static double distanceToLineSegment(double x, double y, double lx1, double ly1, double lx2, double ly2)
	{
	    x   -= lx1;
	    y   -= ly1;
	    lx2 -= lx1;
	    ly2 -= ly1;
	    double lr = hypotf(lx2, ly2);
	    
	    // project along line
	    double proj1 = (x*lx2+y*ly2)/(lr*lr);
	    
	    // if we are off edge of line, return distance to nearest endpoint
	    if (proj1 < 0)
	        return hypotf(x, y);
	    if (proj1 > 1) {
	        x -= lx2;
	        y -= ly2;
	        return hypotf(x, y);
	    }
	    
	    // return distance from line
	    double proj2 = x*ly2-y*lx2;
	    double dist = Math.abs(proj2)/lr;
	    return dist;
	}

	boolean hitTestInside(double x, double y) { return false; }
	
	double hitTest(int x, int y) {
		if (handles.size() == 1) {
			DragHandle dh = handles.get(0);
			return hypotf(dh.x-x, dh.y-y);
		}
		DragHandle dh1 = handles.get(0);
		DragHandle dh2 = handles.get(1);
		double d = distanceToLineSegment(x, y, dh1.x, dh1.y, dh2.x, dh2.y);
		return d;
	}
	
	boolean dragHandle(DragHandle dh, int x, int y) {
		sim.changedWalls = true;
		return true;
	}
	
	void setInitialPosition() {
		if (handles.size() == 1) {
			Rectangle start = sim.findSpace(this, 0, 0);
			DragHandle dh = handles.get(0);
			dh.x = start.x;
			dh.y = start.y;
		}
		if (handles.size() == 2) {
			Rectangle start = sim.findSpace(this, 40, 0);
			DragHandle dh1 = handles.get(0);
			DragHandle dh2 = handles.get(1);
			dh1.x = start.x;
			dh1.y = start.y;
			dh2.x = start.x + start.width;
			dh2.y = start.y;
		}
	}
	
	Rectangle boundingBox() {
		int minx = 10000, miny = 10000, maxx = -10000, maxy = -10000;
		int i;
		for (i = 0; i != handles.size(); i++) {
			DragHandle dh = handles.get(i);
			Point p = transformPoint(new Point(dh.x, dh.y));
            if (p.x < minx)
                minx = p.x;
        if (p.y < miny)
                miny = p.y;
        if (p.x > maxx)
                maxx = p.x;
        if (p.y > maxy)
                maxy = p.y;
		}
		return new Rectangle(minx, miny, maxx-minx, maxy-miny);
	}

	abstract int getDumpType();
	
	String dump() {
		int t = getDumpType();
		String out;
		if (t >= 200)
			out = t + " 0";
		else
			out = (char)t + " 0";
		out += dumpHandles();
		return out;
	}

	String dumpHandles() {
		String out = "";
		int i;
		for (i = 0; i != handles.size(); i++) {
			DragHandle dh = handles.get(i);
			out += " " + dh.x + " " + dh.y;
		}
		return out;
	}

	@Override
	public EditInfo getEditInfo(int n) {
		return null;
	}

	@Override
	public void setEditValue(int n, EditInfo ei) {
	}
	
	void rescale(double scale) {
		int i;
		for (i = 0; i != handles.size(); i++) {
			DragHandle dh = handles.get(i);
			dh.rescale(scale);
		}
		setTransform();
	}
	
	String selectText() {
		if (handles.size() != 2)
			return null;
		DragHandle dh1 = handles.get(0);
		DragHandle dh2 = handles.get(1);
		int len = (int) Math.round(Math.hypot(dh1.x-dh2.x, dh1.y-dh2.y));
		return "length = " + len;
	}
}
