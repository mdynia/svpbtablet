package org.svpb.svpbbookingoverview;

import org.svpb.svpbbookingoverview.util.SystemUiHider;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
public class FullscreenActivity extends Activity {
	
	private static Context CONTEXT = null;
	
	
	private final String htmlInit = "<html><body style='text-align:center; padding-top: 150px' ><h1 style='color: #888888'>Vereinsboote<h1><img src='data:image/png;base64,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'/><h2>Bitte warten...</h2></body></html>";

		/**
	 * Whether or not the system UI should be auto-hidden after
	 * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
	 */
	private static final boolean AUTO_HIDE = true;

	/**
	 * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
	 * user interaction before hiding the system UI.
	 */
	private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

	/**
	 * If set, will toggle the system UI visibility upon interaction. Otherwise,
	 * will show the system UI visibility upon interaction.
	 */
	private static final boolean TOGGLE_ON_CLICK = true;

	/**
	 * The flags to pass to {@link SystemUiHider#getInstance}.
	 */
	private static final int HIDER_FLAGS = SystemUiHider.FLAG_HIDE_NAVIGATION;
	
	
	/**
	 * The instance of the {@link SystemUiHider} for this activity.
	 */
	private SystemUiHider mSystemUiHider;
	
	private WebView webView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_fullscreen);
		
		CONTEXT = this;
		
		final View controlsView = findViewById(R.id.fullscreen_content_controls);
		
		webView = (WebView)findViewById(R.id.fullscreen_content);
    
	    
		// connect the button
		Button settings = (Button)findViewById(R.id.dummy_button);
		 settings.setOnClickListener(new OnClickListener() {
			             @Override
			             public void onClick(View view) {
			                 Toast.makeText(FullscreenActivity.this, "Unsupported yet", Toast.LENGTH_SHORT).show();
			             }
			         });

		
		// Set up an instance of SystemUiHider to control the system UI for
		// this activity.
		mSystemUiHider = SystemUiHider.getInstance(this, webView, HIDER_FLAGS);
		mSystemUiHider.setup();
		mSystemUiHider.setOnVisibilityChangeListener(new SystemUiHider.OnVisibilityChangeListener() {
			// Cached values.
			int mControlsHeight;
			int mShortAnimTime;

			@Override
			@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
			public void onVisibilityChange(boolean visible) {
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
					// If the ViewPropertyAnimator API is available
					// (Honeycomb MR2 and later), use it to animate the
					// in-layout UI controls at the bottom of the
					// screen.
					if (mControlsHeight == 0) {
						mControlsHeight = controlsView.getHeight();
					}
					if (mShortAnimTime == 0) {
						mShortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
					}
					controlsView.animate().translationY(visible ? 0 : mControlsHeight).setDuration(mShortAnimTime);
				} else {
					// If the ViewPropertyAnimator APIs aren't
					// available, simply show or hide the in-layout UI
					// controls.
					controlsView.setVisibility(visible ? View.VISIBLE : View.GONE);
				}

				if (visible && AUTO_HIDE) {
					// Schedule a hide().
					delayedHide(AUTO_HIDE_DELAY_MILLIS);
				}
			}
		});

		// Set up the user interaction to manually show or hide the system UI.
		webView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (TOGGLE_ON_CLICK) {
					mSystemUiHider.toggle();
				} else {
					mSystemUiHider.show();
				}
			}
		});

		// Upon interacting with UI controls, delay any scheduled hide()
		// operations to prevent the jarring behavior of controls going away
		// while interacting with the UI.
		findViewById(R.id.dummy_button).setOnTouchListener(mDelayHideTouchListener);
		
		// get the wifilock
		 WifiManager wm = (WifiManager) CONTEXT.getSystemService(Context.WIFI_SERVICE);
	      if (wm != null) {
	        WifiManager.WifiLock wifiLock = wm.createWifiLock(WifiManager.WIFI_MODE_FULL, "sometag");
	        wifiLock.setReferenceCounted(false);
	      } 
	
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);

		// set init page
		String base64 = Base64.encodeToString(htmlInit.getBytes(), Base64.DEFAULT);
	    webView.loadData(base64, "text/html; charset=utf-8", "base64");
	    
		// Trigger the initial hide() shortly after the activity has been
		// created, to briefly hint to the user that UI controls
		// are available.
		delayedHide(100);
	}

	
	
	@Override
	protected void onPause() {
		super.onPause();	
		Log.v("ACTIVITY", "onPause");
		
		WebView webView = (WebView)findViewById(R.id.fullscreen_content);
		
		// set init page
		String base64 = Base64.encodeToString(htmlInit.getBytes(), Base64.DEFAULT);
	    webView.loadData(base64, "text/html; charset=utf-8", "base64");
	}
	
	@Override
	protected void onStop() {
		super.onStop();	
		Log.v("ACTIVITY", "onStop");
		
		WebView webView = (WebView)findViewById(R.id.fullscreen_content);
		
		// set init page
		String base64 = Base64.encodeToString(htmlInit.getBytes(), Base64.DEFAULT);
	    webView.loadData(base64, "text/html; charset=utf-8", "base64");
	}

	
	@Override
	protected void onResume() {
		super.onResume();
		
		Log.v("ACTIVITY", "onResume");
		WebView webView = (WebView)findViewById(R.id.fullscreen_content);
		
		// set init page
		String base64 = Base64.encodeToString(htmlInit.getBytes(), Base64.DEFAULT);
	    webView.loadData(base64, "text/html; charset=utf-8", "base64");	
	    
	    // load real page as a async thread
		MultThread mt = new MultThread(webView, getBaseContext());
		mt.execute();
	}
	
	
	
	/**
	 * Touch listener to use for in-layout UI controls to delay hiding the
	 * system UI. This is to prevent the jarring behavior of controls going away
	 * while interacting with activity UI.
	 */
	View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
		@Override
		public boolean onTouch(View view, MotionEvent motionEvent) {
			if (AUTO_HIDE) {
				delayedHide(AUTO_HIDE_DELAY_MILLIS);
			}
			return false;
		}
	};

	Handler mHideHandler = new Handler();
	Runnable mHideRunnable = new Runnable() {
		@Override
		public void run() {
			mSystemUiHider.hide();
		}
	};

	/**
	 * Schedules a call to hide() in [delay] milliseconds, canceling any
	 * previously scheduled calls.
	 */
	private void delayedHide(int delayMillis) {
		mHideHandler.removeCallbacks(mHideRunnable);
		mHideHandler.postDelayed(mHideRunnable, delayMillis);
	}
}
