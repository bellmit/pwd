package com.newland.wstdd.mine.minesetting.feedbackandhelp.selectphoto;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap.Config;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import com.newland.wstdd.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

public class AlbumItemAdapter extends BaseAdapter {
	private PhotoUpImageItem photoUpImageItem;
	private List<PhotoUpImageItem> list;
	private LayoutInflater layoutInflater;
	private ImageLoader imageLoader;
	private DisplayImageOptions options;
	private Context context;
	private ArrayList<PhotoUpImageItem> selectImages;//选中相片时候保存的集合列表
	public AlbumItemAdapter(List<PhotoUpImageItem> list,Context context){
		this.list = list;
		this.context=context;
		layoutInflater = LayoutInflater.from(context);
		imageLoader = ImageLoader.getInstance();
		selectImages = new ArrayList<PhotoUpImageItem>();
		// 使用DisplayImageOption.Builder()创建DisplayImageOptions
		options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.album_default_loading_pic) // 设置图片下载期间显示的图片
				.showImageForEmptyUri(R.drawable.album_default_loading_pic) // 设置图片Uri为空或是错误的时候显示的图片
				.showImageOnFail(R.drawable.album_default_loading_pic) // 设置图片加载或解码过程中发生错误显示的图片
				.cacheInMemory(true) // 设置下载的图片是否缓存在内存中
				.cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
				// .displayer(new RoundedBitmapDisplayer(20)) // 设置成圆角图片
				.bitmapConfig(Config.ARGB_8888)
				.imageScaleType(ImageScaleType.IN_SAMPLE_INT)
				.build(); // 创建配置过的DisplayImageOption对象
	}
	
	@Override
	public int getCount() {
		return list == null ? 0:list.size();
	}

	@Override
	public Object getItem(int position) {
		if (list != null && list.size() != 0) {
			return list.get(position);
			}
			return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final Holder holder;
		if (convertView == null) {
			convertView = layoutInflater.inflate(R.layout.album_item_images_item_view, parent, false);
			holder = new Holder();
			holder.imageView = (ImageView) convertView.findViewById(R.id.image_item);
			holder.checkBox = (CheckBox) convertView.findViewById(R.id.check);
			convertView.setTag(holder);
		}else {
			holder = (Holder) convertView.getTag();
		}
		//图片加载器的使用代码，就这一句代码即可实现图片的加载。请注意
		//这里的uri地址，因为我们现在实现的是获取本地图片，所以使
		//用"file://"+图片的存储地址。如果要获取网络图片，
		//这里的uri就是图片的网络地址。
		imageLoader.displayImage("file://"+list.get(position).getImagePath(), holder.imageView, options);
		holder.checkBox.setChecked(list.get(position).isSelected());

		convertView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//这里需要理解好，那就是checkBox本身为false的时候就是没有选中的意思   初始化的时候holder.checkBox.isChecked()===false也就是没有打钩的
				if(holder.checkBox.isChecked()){
					list.get(position).setSelected(false);
				}else{
					list.get(position).setSelected(true);
				}
				if(selectImages!=null&&selectImages.size()>0){
					boolean isExist=false;
					PhotoUpImageItem tempImageItem0=null;
					if (list.get(position).isSelected()) {
						for (int i = 0; i < selectImages.size(); i++) {
							if(list.get(position).getImageId().equals(selectImages.get(i).getImageId())){
								//判断下是否是相等的，相等的话就不用添加了
								
								isExist=true;
								break;
							}else{
								isExist=false;
							}
						}
						if(!isExist){
						list.get(position).setSelected(true);
						if((3-AlbumsActivity.selectNum)>selectImages.size()){
						selectImages.add(list.get(
								position));
						holder.checkBox.setChecked(list.get(position).isSelected());
						}else{
							Toast.makeText(context, "选择图片不能超过"+(3-AlbumsActivity.selectNum)+"张", 1).show();
							
						}
						
						}
					} else {
						boolean isExist1=false;
						PhotoUpImageItem tempImageItem=null;
						for (int i = 0; i < selectImages.size(); i++) {
							if(list.get(position).getImageId().equals(selectImages.get(i).getImageId())){
								isExist1=true;
								tempImageItem=selectImages.get(i);
								break;
							}else{
								isExist1=false;
							}
						}
						if(isExist1){
							list.get(position).setSelected(false);
							selectImages.remove(tempImageItem);
						}
						holder.checkBox.setChecked(list.get(position).isSelected());
					}
					
				}else if(selectImages.size()==0){
					selectImages.add(list.get(
							position));
					holder.checkBox.setChecked(list.get(position).isSelected());
				}
				
			}
		});
		
		
		
		
		return convertView;
	}
	
	class Holder{
		ImageView imageView;
		CheckBox checkBox;
	}

	public List<PhotoUpImageItem> getList() {
		return list;
	}

	public void setList(List<PhotoUpImageItem> list) {
		this.list = list;
	}

	public ArrayList<PhotoUpImageItem> getSelectImages() {
		return selectImages;
	}

	public void setSelectImages(ArrayList<PhotoUpImageItem> selectImages) {
		this.selectImages = selectImages;
	}
	
	
}
