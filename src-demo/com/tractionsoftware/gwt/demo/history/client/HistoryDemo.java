/*
 * Copyright 2013 Traction Software, Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.tractionsoftware.gwt.demo.history.client;

import java.util.Date;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Document;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLTable.RowFormatter;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;

public class HistoryDemo implements EntryPoint {

    private FlexTable location = new FlexTable();
    private FlexTable links = new FlexTable();
    private ListBox events = new ListBox();
    
    private String token = "fun/(&)(&amp;)(%26)(%2526)/(#)(%23)/( )(+)(%2B)(%20)(%252B)(%2520)/%e6%bc%a2";
    
    private static final String CLASS_MATCH = "match";
    private static final String CLASS_NO_MATCH = "no-match";
    
    @Override
    public void onModuleLoad() {
        RootPanel.get("location").add(location);
        RootPanel.get("links").add(links);
        RootPanel.get("events").add(events);
        
        History.addValueChangeHandler(new ValueChangeHandler<String>() {

            @Override
            public void onValueChange(ValueChangeEvent<String> event) {
                showLocationData(event.getValue());
                events.insertItem("["+new Date()+"] ValueChangeEvent: value="+event.getValue(), 0);
            }
            
        });
        showLocationData("");

        String hash = "#"+token;
        String uri = Document.get().getBody().getAttribute("data-file")+hash;        
        
        links.setText(0, 0, "GWT Hyperlink");
        links.setWidget(0, 1, new Hyperlink(SafeHtmlUtils.fromString(token), token));

        links.setText(1, 0, "HTML Hash");
        links.setHTML(1, 1, "<a href=\""+SafeHtmlUtils.htmlEscape(hash)+"\">"+SafeHtmlUtils.htmlEscape(hash)+"</a>");

        links.setText(2, 0, "GWT Anchor");
        links.setWidget(2, 1, new Anchor(SafeHtmlUtils.fromString(uri), uri));

        links.setText(3, 0, "HTML Relative URI");
        links.setHTML(3, 1, "<a href=\""+SafeHtmlUtils.htmlEscape(uri)+"\">"+SafeHtmlUtils.htmlEscape(uri)+"</a>");

        events.setVisibleItemCount(20);
        events.setWidth("800px");        
        
    }
    
    private void showLocationData(String eventToken) {
        String historyToken = History.getToken();
        
        setLocationData(0, "Window.Location.getHref()", Window.Location.getHref());
        setLocationData(1, "Window.Location.getPath()", Window.Location.getPath());
        setLocationData(2, "Window.Location.getHash()", Window.Location.getHash());
        setLocationData(3, "History.getToken()", historyToken);
        setLocationData(4, "ValueChangeEvent.getValue()", eventToken);     
        
        // expect historyToken == eventToken, but style them separately anyway
        styleLocationDataRow(3, historyToken);
        styleLocationDataRow(4, eventToken);
    }
    
    private void styleLocationDataRow(int row, String value) {
        RowFormatter fmt = location.getRowFormatter();
        if (value == null || value.length() == 0) {
            fmt.removeStyleName(row, CLASS_MATCH);
            fmt.removeStyleName(row, CLASS_NO_MATCH);
        }
        else if (value.equals(token)) {
            fmt.addStyleName(row, CLASS_MATCH);            
        }
        else {
            fmt.addStyleName(row, CLASS_NO_MATCH);
        }
    }
    
    private void setLocationData(int row, String name, String value) {
        location.setText(row, 0, name);
        location.setHTML(row, 1, SafeHtmlUtils.fromString(value));
    }

    
    
}
