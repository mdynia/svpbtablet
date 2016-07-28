package org.svpb.svpbbookingoverview;

import java.net.HttpURLConnection;
import java.net.URL;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.webkit.WebView;

public class MultThread extends AsyncTask<Void, Void, Void> {

	private static final String URL = "http://mein.svpb.de/boote/booking/today/public/";

	private Context context;
	private WebView webView;

	// number of retry operations
	private int retry;

	public MultThread(WebView webView, Context context) {
		this.webView = webView;
		this.context = context;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();

		retry = 20;
		Log.d("My Async Thread", "PRE EXECUTE");
	}

	@Override
	protected Void doInBackground(Void... params) {
		Log.d("My Async Thread", "Checking Internet connection");

		while (!isNetworkAvailable() && retry >= 0) {
			try {
				Log.d("My Async Thread", "No connection. Waiting: " + retry);
				// Log.d("My Async Thread", "PING " +
				// ping("http://www.google.com"));
				retry--;
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		Log.d("My Async Thread", "POST EXECUTE");
		webView.loadUrl(URL);
	}

	private boolean isNetworkAvailable() {
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		boolean isFine = activeNetworkInfo != null && activeNetworkInfo.isConnected();

		boolean responseCodeOk = false;
		try {
			   
			URL url = new URL(URL);
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			responseCodeOk = (200 == urlConnection.getResponseCode());
			Log.d("My Async Thread", "CODE: "+ urlConnection.getResponseCode());
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return isFine && responseCodeOk;
	}

}
