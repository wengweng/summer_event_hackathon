package com.icesabi.android_hackathon.ui.fragment;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.icesabi.android_hackathon.R;

import domain.Buddy;

@SuppressLint("ValidFragment")
public class BuddiesFragment extends Fragment {
	
	
	public interface IBuddiesFragment {
		public void triggerQuestion();
		public void onBuddySelected(Buddy position, boolean selected);
		public List<Buddy> getCheckedBuddies();
	}

    private GridView grid;
    private BuddyAdapter adapter;
	private IBuddiesFragment mCallback;
	private ArrayList<Buddy> list;

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_buddies, container, false);
        return rootView;
    }
    
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
    	 grid = (GridView) view.findViewById(R.id.grid_buddies);
    	 
    	 adapter = new BuddyAdapter(getActivity());
    	 list = new ArrayList<Buddy>();
    	 String[] names = getResources().getStringArray(R.array.names);
    	 TypedArray pictures = getResources().obtainTypedArray(R.array.pictures);
    	 for (int i = 0;i < names.length ;i++) {
    		 list.add(new Buddy(names[i], pictures.getDrawable(i)));
    	 }
    	 
    	 adapter.setList(list);
    	 setList(mCallback.getCheckedBuddies());
    	 
    	 grid.setAdapter(adapter);
    	 
    	 grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Buddy buddy = adapter.getItem(position);
				buddy.setChecked(! buddy.isChecked());
				mCallback.onBuddySelected(buddy, buddy.isChecked());
				adapter.notifyDataSetChanged();
			}
		});
    	 
    	 grid.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				new MyDialogFragment(adapter.getItem(position).getName(), getActivity()).show(getFragmentManager(), "notification_tag" );
				
				return false;
			}
		});
    	 
    	 
    	 view.findViewById(R.id.notification_container).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mCallback.triggerQuestion();
			}
		});
    }
    
    
    
    private void setList( List<Buddy> buddiesClicked){
    	if (list == null)
    		return;
    	int pos = 0;
    	for (Buddy buddy : buddiesClicked) {
    		for (Buddy myBuddy : list) {
    			if (myBuddy.getName().equalsIgnoreCase(buddy.getName())){
    				break;
    			}
    			pos++;
    		}
    		if (pos <= list.size()) 
    			list.get(pos).setChecked(true);
    	}
    }
    
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (IBuddiesFragment) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }
    
    public class BuddyAdapter extends BaseAdapter {
    	
    	
    	private final Context mContext;
    	private List<Buddy> mList;
    	
    	public BuddyAdapter(Context context) {
    		mContext = context;
    	}

		public List<Buddy> getList() {
			return mList;
		}

		public void setList(List<Buddy> mList) {
			this.mList = mList;
		}

		@Override
		public int getCount() {
			if (mList == null) {
				return 0;
			}
			else {
				return mList.size();
			}
		}

		@Override
		public Buddy getItem(int position) {
			if (mList == null){
				return null;
			}
			else {
				return mList.get(position);
			}
		}

		@Override
		public long getItemId(int position) {
			if (mList == null){
				return -1;
			}
			else {
				return mList.get(position).hashCode();
			}
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(mContext).inflate(R.layout.buddy_item, null);
			}
			
			final Buddy buddy = getItem(position);
			ImageView image = ((ImageView) convertView.findViewById(R.id.buddy_picture));
			image.setImageDrawable(buddy.getPicture());
			
			TextView name =  ((TextView) convertView.findViewById(R.id.buddy_name));
			name.setText(buddy.getName());
			
			if (buddy.isChecked()) {
				convertView.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
				name.setTextColor(getResources().getColor(android.R.color.black));
			}
			else {
				convertView.setBackgroundColor(getResources().getColor(android.R.color.transparent));
				name.setTextColor(getResources().getColor(android.R.color.tertiary_text_dark));
			}
			return convertView;
		}
    	
    }
    
    DialogInterface.OnClickListener mListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
            switch (which) {
			case Dialog.BUTTON_POSITIVE:
				Toast.makeText(getActivity(), "Nope!" , Toast.LENGTH_SHORT).show();
				break;
			case Dialog.BUTTON_NEGATIVE:
				Toast.makeText(getActivity(), "Chicken!" , Toast.LENGTH_SHORT).show();
				break;
			default:
				break;
			}
        }
    };
    
    public class MyDialogFragment extends DialogFragment {
        Context mContext;
        String name;
        
        public MyDialogFragment() {
        	
        }
        public MyDialogFragment(String name, Context context) {
            mContext = context;
            this.name = name;
        }
        
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
            alertDialogBuilder.setTitle("Are you sure?");
            alertDialogBuilder.setMessage("Do you think it is "+ name+ "?");
            //null should be your on click listener
            alertDialogBuilder.setPositiveButton("OK", mListener);
            alertDialogBuilder.setNegativeButton("Cancel", mListener);


            return alertDialogBuilder.create();
        }
    }
}