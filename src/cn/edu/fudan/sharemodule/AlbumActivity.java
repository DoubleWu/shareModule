package cn.edu.fudan.sharemodule;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.GridView;
import android.widget.ImageView;

import cn.edu.fudan.adapter.AlbumGridViewAdapter;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;



/**
 * @author 
 */
public class AlbumActivity extends BaseActivity {

	private ArrayList<String> imageUrls;
	private DisplayImageOptions options;
	private AlbumGridViewAdapter imageAdapter;
	
	private ArrayList<String> selectedDataList = new ArrayList<String>();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ac_image_grid);
		
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		selectedDataList = (ArrayList<String>)bundle.getSerializable("dataList");

		final String[] columns = { MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID };
		final String orderBy = MediaStore.Images.Media.DATE_TAKEN;
		@SuppressWarnings("deprecation")
		Cursor imagecursor = managedQuery(
				MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null,
				null, orderBy + " DESC");
		
		this.imageUrls = new ArrayList<String>();
		
		for (int i = 0; i < imagecursor.getCount(); i++) {
			imagecursor.moveToPosition(i);
			int dataColumnIndex = imagecursor.getColumnIndex(MediaStore.Images.Media.DATA);
			imageUrls.add(imagecursor.getString(dataColumnIndex));
			
			System.out.println("=====> Array path => "+imageUrls.get(i));
		}		
		options = new DisplayImageOptions.Builder()
			.showStubImage(R.drawable.stub_image)
			.showImageForEmptyUri(R.drawable.image_for_empty_url)
			.cacheInMemory()
			.cacheOnDisc()
			.build();

		imageAdapter = new AlbumGridViewAdapter(this, imageUrls, options);
		
		GridView gridView = (GridView) findViewById(R.id.gridview);
		gridView.setAdapter(imageAdapter);
		/*gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				startImageGalleryActivity(position);
			}
		});*/
	}

	@Override
	protected void onStop() {
		imageLoader.stop();
		super.onStop();
	}

	public void btnChoosePhotosClick(View v){
		
		ArrayList<String> selectedItems = imageAdapter.getCheckedItems();
		Toast.makeText(AlbumActivity.this, "Total photos selected: "+selectedItems.size(), Toast.LENGTH_SHORT).show();
		Log.d(AlbumActivity.class.getSimpleName(), "Selected Items: " + selectedItems.toString());
	}
}