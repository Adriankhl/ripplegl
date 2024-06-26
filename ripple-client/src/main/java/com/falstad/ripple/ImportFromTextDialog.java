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

import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.safehtml.shared.SafeHtml;

public class ImportFromTextDialog extends DialogBox {
	
VerticalPanel vp;
HorizontalPanel hp;
RippleSim sim;
// RichTextArea textBox;
TextArea textArea;
	
	public ImportFromTextDialog( RippleSim asim) {
		super();
		sim=asim;
		Button okButton, cancelButton;
		vp=new VerticalPanel();
		setWidget(vp);
		setText("Import from Text");
		vp.add(new Label("Paste the text file for your layout here..."));
//		vp.add(textBox = new RichTextArea());
		vp.add(textArea = new TextArea());
		textArea.setWidth("800px");
		textArea.setHeight("200px");
		hp = new HorizontalPanel();
		vp.add(hp);
		hp.add(okButton = new Button("OK"));
		okButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				String s;
				sim.pushUndo();
				closeDialog();
//				s=textBox.getHTML();
//				s=s.replace("<br>", "\r");
				s=textArea.getText();
				if (s!=null)
					sim.readImport(s);
			}
		});
		hp.add(cancelButton = new Button("Cancel"));
		cancelButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				closeDialog();
			}
		});
		this.center();
		show();
	}
	
	protected void closeDialog()
	{
		this.hide();
	}

}
