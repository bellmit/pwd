package com.newland.wstdd.common.fileupload;

import java.io.File;
import java.nio.charset.Charset;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.newland.wstdd.common.common.UrlManager;
import com.newland.wstdd.common.fileupload.CustomMultipartEntity.ProgressListener;
import com.newland.wstdd.login.regist.RegistFinishActivity;
import com.newland.wstdd.netutils.MessageUtil;
import com.newland.wstdd.netutils.WBResponse;
/**
 * Multipart上传
 * @author Jason
 *
 */
public class HttpMultipartPost extends AsyncTask<String, Integer, String> {

	private Context context;
	private List<String> filePathList;
	private ProgressDialog pd;
	private long totalSize;

	public HttpMultipartPost(Context context, List<String> filePathList) {
		this.context = context;
		this.filePathList = filePathList;
	}

	@Override
	protected void onPreExecute() {
		pd = new ProgressDialog(context);
		pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		pd.setMessage("Uploading Picture...");
		pd.setCancelable(false);
		pd.show();
	}

	@Override
	protected String doInBackground(String... params) {
		String serverResponse = null;
		HttpClient httpClient = new DefaultHttpClient();
		HttpContext httpContext = new BasicHttpContext();
		HttpPost httpPost = new HttpPost(UrlManager.headURL+"?tag=MLE");

		try {
			CustomMultipartEntity multipartContent = new CustomMultipartEntity(
					new ProgressListener() {
						@Override
						public void transferred(long num) {
							publishProgress((int) ((num / (float) totalSize) * 100));
						}
					});

			// We use FileBody to transfer an image
			//把上传内容添加到MultipartEntity
			for (int i = 0; i < filePathList.size(); i++) {
				multipartContent.addPart("file", new FileBody(new File(
						filePathList.get(i))));
				multipartContent.addPart("data",
	                    new StringBody(filePathList.get(i), Charset
	                                    .forName(org.apache.http.protocol.HTTP.UTF_8)));
			}
			

			
			totalSize = multipartContent.getContentLength();
			System.out.println("totalSize:========="+totalSize);
			// Send it
			httpPost.setEntity(multipartContent);
			HttpResponse response = httpClient.execute(httpPost, httpContext);
			serverResponse = EntityUtils.toString(response.getEntity());
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return serverResponse;
	}

	@Override
	protected void onProgressUpdate(Integer... progress) {
		pd.setProgress((int) (progress[0]));
	}

	@Override
	protected void onPostExecute(String result) {
		System.out.println("result: " + result);
	//	{"code":1,"msg":"è¯·æ±æå","result":"wQqN9lPh1CpOSPdbwgmm2Eie7kEpVl2oGYBZdxQzWPOWVJrbwJzrGKIG/ftl zNQf","timeStamp":"20152616022604"}
//		WBResponse response= MessageUtil.JsonStrToWBResponse(result,cls);
		((RegistFinishActivity) context).handleHeadImg(result);//传完之后的操作
		
		//上传的结果显示在这里
		pd.dismiss();
	}

	@Override
	protected void onCancelled() {
		System.out.println("cancle");
	}
	
}
