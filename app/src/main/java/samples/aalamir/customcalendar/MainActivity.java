package samples.aalamir.customcalendar;

import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Gravity;
import android.widget.Button;
import android.widget.Toast;
import android.widget.TimePicker;
import android.widget.PopupWindow;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.view.View;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;


public class MainActivity extends ActionBarActivity
{
	private int activeButton = 0;
	private boolean editMode = false;
	private List<Date> selectedDates;
	LinearLayout myLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		myLayout = (LinearLayout) findViewById(R.layout.activity_main);
		setContentView(myLayout);

		HashSet<Date> events = new HashSet<>();
		events.add(new Date());

		selectedDates = new ArrayList<Date>();

		CalendarView cv = ((CalendarView)findViewById(R.id.calendar_view));
		cv.updateCalendar(events);
		Button editButton = (Button) findViewById(R.id.editButton);
		enterEditMode(editButton);
		/**
		 * Pseudocode for adding buttons based on number of RAs instead of what's hardcoded below
		 * for(RA: List of RAs){
		 * Button button = new Button (this);
		 * button.setText(blah)
         * myLayout.addView(button, parameters necessary);
		 * whenClicked(button); (Actually the method name)
		 * }
		 *
		 */
		Button orange = (Button) findViewById(R.id.button7);
		whenClicked(orange);
		Button blue = (Button) findViewById(R.id.button8);
		whenClicked(blue);

		/**
		 *  Assign event handler to grid cells (make them change color when clicked)
		 */
		cv.getGrid().setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> view, View cell, int position, long id)
			{
				if(activeButton!=0 && editMode) {
					Button temp = (Button) findViewById(activeButton);
					cell.setBackgroundColor(((ColorDrawable) temp.getBackground()).getColor());
					//Will soon implement a better way of adding dates (maybe a set to eliminate duplicates)
					selectedDates.add((Date)view.getItemAtPosition(position));
				}
			}
		});

		cv.setEventHandler(new CalendarView.EventHandler()
		{
			@Override
			public void onDayLongPress(Date date)
			{
				// show returned day
				DateFormat df = SimpleDateFormat.getDateInstance();
				Toast.makeText(MainActivity.this, df.format(date), Toast.LENGTH_SHORT).show();
			}
		});
	}

	private void whenClicked(Button b){
		b.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				activeButton = v.getId();
			}
		});
	}

	private void enterEditMode(Button b){
		b.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Button switchButton = (Button) findViewById(R.id.switchButton);
				editMode = true;
			}
		});
	}

	/**
	 * Once clicked, the popup window prompting a switch/give is initialized
	 * @param b refers to the button clicked
	 */
	/*private void switchAndGiveMode(Button b){
		b.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				PopupWindow popUp = new PopupWindow(myLayout);
				popUp.showAtLocation(v, Gravity.CENTER, 0, 0);
				// popUp.showAtLocation(layout, Gravity.BOTTOM, 10, 10);
				popUp.update(50, 50, 300, 80);
				popUp.setContentView(R.id.popup_window);
				Button okButton = (Button) findViewById(R.id.okButton);
				okButton.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						popUp.dismiss();
					}
				});
				//unnecessary TimePicker here
				TimePicker timePicker = (TimePicker) findViewbyID(R.id.timePicker);
				//obtain all cells that have been clicked on and obtain their information- need to add in scrollview
				//The shift times are likely coming from the database???
			}
		});
	} */


	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings)
		{
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
}
