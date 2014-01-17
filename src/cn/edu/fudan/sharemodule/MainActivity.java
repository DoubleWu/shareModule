package cn.edu.fudan.sharemodule;

import java.util.ArrayList;

import cn.edu.fudan.adapter.GridImageAdapter;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

public class MainActivity extends Activity {

	private GridView gridView;
	private ArrayList<String> dataList = new ArrayList<String>();
	private GridImageAdapter gridImageAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();
		initListener();
	}
	
	private void init() {
		gridView = (GridView) findViewById(R.id.myGrid);
		dataList.add("camera_default");
		gridImageAdapter = new GridImageAdapter(this, dataList);
		gridView.setAdapter(gridImageAdapter);
	}
	
	private void initListener() {

		gridView.setOnItemClickListener(new GridView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

					Intent intent = new Intent(MainActivity.this,
							AlbumActivity.class);
					Bundle bundle = new Bundle();
					// intent.putArrayListExtra("dataList", dataList);
					bundle.putStringArrayList("dataList",
							getIntentArrayList(dataList));
					intent.putExtras(bundle);
					startActivityForResult(intent, 0);

//				}

			}

		});
	}
	
	private ArrayList<String> getIntentArrayList(ArrayList<String> dataList) {

		ArrayList<String> tDataList = new ArrayList<String>();

		for (String s : dataList) {
			if (!s.contains("default")) {
				tDataList.add(s);
			}
		}

		return tDataList;

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
