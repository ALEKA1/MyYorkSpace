package edu.ycp.cs320.myYorkSpace.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

import edu.ycp.cs320.myYorkSpace.shared.Account;
import edu.ycp.cs320.myYorkSpace.shared.Event;

import com.google.gwt.user.client.ui.ListBox;


public class EventView extends Composite implements View {

	private ArrayList<Event> events;
	private ListBox listBox;
	private Event displayedEvent;
	private Label lblEvent;
	private Label label;
	private Label lblName;
	private Label lblDescription;

	public EventView(){
		events = new ArrayList<Event>() ;
		
		LayoutPanel panel = new LayoutPanel();
		initWidget(panel);
		panel.setSize("576px", "578px");
		
		lblEvent = new Label("");
		panel.add(lblEvent);
		panel.setWidgetLeftWidth(lblEvent, 203.0, Unit.PX, 462.0, Unit.PX);
		panel.setWidgetTopHeight(lblEvent, 249.0, Unit.PX, 28.0, Unit.PX);
		
		listBox = new ListBox();
		panel.add(listBox);
		panel.setWidgetLeftWidth(listBox, 13.0, Unit.PX, 174.0, Unit.PX);
		panel.setWidgetTopHeight(listBox, 76.0, Unit.PX, 313.0, Unit.PX);
		
		GetEvents(Session.getInstance().getAccount());
		
		label = new Label("");
		panel.add(label);
		panel.setWidgetLeftWidth(label, 193.0, Unit.PX, 235.0, Unit.PX);
		panel.setWidgetTopHeight(label, 159.0, Unit.PX, 28.0, Unit.PX);
		
		lblName = new Label("Event name:");
		panel.add(lblName);
		panel.setWidgetLeftWidth(lblName, 193.0, Unit.PX, 235.0, Unit.PX);
		panel.setWidgetTopHeight(lblName, 125.0, Unit.PX, 28.0, Unit.PX);
		
		lblDescription = new Label("Description");
		panel.add(lblDescription);
		panel.setWidgetLeftWidth(lblDescription, 193.0, Unit.PX, 235.0, Unit.PX);
		panel.setWidgetTopHeight(lblDescription, 214.0, Unit.PX, 28.0, Unit.PX);
		
	}
	
	public void activate() {

		listBox.setVisibleItemCount(events.size());
		for(int i = 0; i < events.size(); i++)
		{
			listBox.addItem(events.get(i).getEventName());
		}
		listBox.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				displayedEvent =  events.get(listBox.getSelectedIndex());
				lblEvent.setText(displayedEvent.getEventDesc());
			}
		});
	}

	
	protected void GetEvents(Account host) {
		RPC.EventService.getEvents(host.getEmail(), new AsyncCallback<ArrayList<Event>>() {
			@Override
			public void onSuccess(ArrayList<Event> returnedList) {
				if (returnedList == null) {
					GWT.log("Host Account no longer exists");
				} else {
					// Successful
					events = returnedList;
					activate();
				}
			}
			@Override
			public void onFailure(Throwable caught) {
				// TODO: display error msg
				GWT.log("Login RPC call failed", caught);
			}
		});
	}
}
 