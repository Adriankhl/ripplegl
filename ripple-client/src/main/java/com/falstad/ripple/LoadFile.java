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

import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ContextMenuEvent;



public class LoadFile extends FileUpload implements  ChangeHandler {
	
	static RippleSim sim;
	
	static public final native boolean isSupported() 
		/*-{
			return !!($wnd.File && $wnd.FileReader);
		 }-*/;
	
	static public void doLoadCallback(String s) {
		sim.pushUndo();
		sim.readImport(s);
		sim.createNewLoadFile();
	}
	
	LoadFile(RippleSim s) {
		super();
		sim=s;
		this.setName("Import");
		this.getElement().setId("LoadFileElement");
		this.addChangeHandler(this);
		this.addStyleName("offScreen");
	}
	
	
	
	public void onChange(ChangeEvent e) {
		doLoad();
	}
	
	
	public final native void click() 
	/*-{
		$doc.getElementById("LoadFileElement").click();
	 }-*/;
	
	static public final native void doLoad()
		/*-{
			var oFiles = $doc.getElementById("LoadFileElement").files,
    		nFiles = oFiles.length;
    		if (nFiles>=1 && oFiles[0].size<32000) {
        		var reader = new FileReader();
    			reader.onload = function(e) {
      				var text = reader.result;
      				@com.falstad.ripple.LoadFile::doLoadCallback(Ljava/lang/String;)(text);
        		};

    			reader.readAsText(oFiles[0]);
    		}
		 }-*/;
	
}
