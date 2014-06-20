package com.icesabi.android_hackathon.ui.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.icesabi.android_hackathon.R;

import domain.Buddy;

/**
 * A placeholder fragment containing a simple view.
 */
public class BuddiesFragment extends Fragment {

    private GridView grid;
    private BuddyAdapter adapter;

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
    	 List<Buddy> list = new ArrayList<Buddy>();
    	 for (int i = 0;i < 80 ;i++) {
    		 list.add(new Buddy("Henk", "henk"));
    	 }
    	 adapter.setList(list);
    	 grid.setAdapter(adapter);
    	 
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
			
			Buddy buddy = getItem(position);
			ImageView image = ((ImageView) convertView.findViewById(R.id.buddy_picture));
			image.setImageDrawable(mContext.getResources().getDrawable(android.R.drawable.ic_menu_rotate));
			
			TextView name =  ((TextView) convertView.findViewById(R.id.buddy_name));
			name.setText(buddy.getName());
			return convertView;
		}
    	
    }
}