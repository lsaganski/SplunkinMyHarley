//
// HarleyDroid: Harley Davidson J1850 Data Analyser for Android.
//
// Copyright (C) 2010-2012 Stelian Pop <stelian@popies.net>
//
// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program.  If not, see <http://www.gnu.org/licenses/>.
//

package br.com.mobila.splunkinmyharley;

import android.annotation.TargetApi;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.preference.PreferenceActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Switch;

import com.google.gson.Gson;


public class HarleyDroidDashboard extends HarleyDroid {
	private static final boolean D = false;
	private static final String TAG = HarleyDroidDashboard.class.getSimpleName();

	private Toolbar mToolbar;
	public  ImageButton btnPlay;
	public  ImageButton btnPrefs;
	public  ImageButton btnReset;
	public Button btnDevice;
	public ProgressBar progress;

	private HarleyDroidDashboardView mHarleyDroidDashboardView;
	private int mViewMode = HarleyDroidDashboardView.VIEW_GRAPHIC;

	public Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (msg.what == 100) {
				progress.setVisibility(View.VISIBLE);
			} else if (msg.what == 101) {
				progress.setVisibility(View.GONE);
			}
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		if (D) Log.d(TAG, "onCreate()");
		super.onCreate(savedInstanceState);

		Holder.shared().UIHandler = handler;

		mHarleyDroidDashboardView = new HarleyDroidDashboardView(this);
		mHarleyDroidDashboardView.changeView(mViewMode, getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT, mUnitMetric);

		setUpToolbar();
	}

	@Override
	public void onStart() {
		if (D) Log.d(TAG, "onStart()");
		super.onStart();

		mViewMode = mPrefs.getInt("dashboardviewmode", HarleyDroidDashboardView.VIEW_TEXT);
	/*	mHarleyDroidDashboardView.changeView(mViewMode,
				mOrientation == ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED ? getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT
																			: mOrientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT ? true : false,
				mUnitMetric);*/
		mHarleyDroidDashboardView.drawAll(mHD);
	}

	@Override
	public void onStop() {
		if (D) Log.d(TAG, "onStop()");
		super.onStop();

		SharedPreferences.Editor editor = mPrefs.edit();
		editor.putInt("dashboardviewmode", mViewMode);
		editor.commit();
	}

/*	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		if (D) Log.d(TAG, "onConfigurationChanged()");
		super.onConfigurationChanged(newConfig);

		if (mOrientation == ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED) {
			mHarleyDroidDashboardView.changeView(mViewMode, newConfig.orientation == Configuration.ORIENTATION_PORTRAIT, mUnitMetric);
			mHarleyDroidDashboardView.drawAll(mHD);
		}
	}*/



	private void Ativar(boolean ativar) {
		if (ativar) {
			startHDS();
			SMHLocation.shared().startLocation();

			Holder.shared().handler.postDelayed(Holder.shared().runnable, 5000);    //setupAlarm();
			btnPlay.setImageDrawable(getResources().getDrawable(R.drawable.icon_stop));
		} else {
			stopHDS();
			SMHLocation.shared().stopService();
			SMHLocation.shared().stopLocation();
			Holder.shared().handler.removeCallbacks(Holder.shared().runnable);     //cancelAlarm();
			btnPlay.setImageDrawable(getResources().getDrawable(R.drawable.icon_play));
			btnReset.setVisibility(View.INVISIBLE);
		}
	}

	private void setUpToolbar() {
		mToolbar = (Toolbar) findViewById(R.id.toolbar);
		if (mToolbar != null) {
			setSupportActionBar(mToolbar);
			getSupportActionBar().setDisplayShowTitleEnabled(false);

			btnPlay = mToolbar.findViewById(R.id.btnPlay);
			btnPrefs = mToolbar.findViewById(R.id.btnPrefs);
			btnReset = mToolbar.findViewById(R.id.btnReset);
			btnDevice = mToolbar.findViewById(R.id.btnDevice);
			progress = mToolbar.findViewById(R.id.progress);

			btnPlay.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					Ativar(mService == null);
				}
			});

			btnPrefs.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {

					Intent settingsActivity = new Intent(getBaseContext(), HarleyDroidSettings.class);
					if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
						settingsActivity.putExtra(PreferenceActivity.EXTRA_SHOW_FRAGMENT, HarleyDroidSettings.Fragment.class.getName());
						settingsActivity.putExtra(PreferenceActivity.EXTRA_NO_HEADERS, true);
					}
					startActivity(settingsActivity);
				}
			});

			btnReset.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					mHD.resetCounters();
				}
			});

			btnDevice.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					Ativar(false);
					Button b = (Button) view;
					if (b.getText().toString().equals("Emulator")) {
						HarleyDroid.EMULATOR = false;
						b.setText("OBDII");
					} else {
						HarleyDroid.EMULATOR = true;
						b.setText("Emulator");
					}

				}
			});
		}
	}

	public void onServiceConnected(ComponentName name, IBinder service) {
		if (D) Log.d(TAG, "onServiceConnected()");
		super.onServiceConnected(name, service);

		if (!mService.isPolling())
			mService.startPoll();

	//	if (!mService.isSending())
//			mService.startSend(types, tas, sas, commands, expects, timeouts, COMMAND_DELAY);

		mHD.addHarleyDataDashboardListener(mHarleyDroidDashboardView);
//		mHD.addHarleyDataDiagnosticsListener(mHarleyDroidDashboardView);
		mHarleyDroidDashboardView.drawAll(mHD);
		btnReset.setVisibility(View.VISIBLE);
	}

	public void onServiceDisconnected(ComponentName name) {
		if (D) Log.d(TAG, "onServiceDisconnected()");

		btnPlay.setImageDrawable(getResources().getDrawable(R.drawable.icon_stop));

		mHD.removeHarleyDataDashboardListener(mHarleyDroidDashboardView);
	//	mHD.removeHarleyDataDiagnosticsListener(mHarleyDroidDashboardView);
		mHarleyDroidDashboardView.drawAll(null);
		super.onServiceDisconnected(name);
		Ativar(false);
	}
/*
	private static final int COMMAND_TIMEOUT = 500;
	private static final int COMMAND_DELAY = 10000;
	private static final int GET_DTC_TIMEOUT = 2000;

	private String[] types = {
			// Get VIN, ECM info etc...
			"0C",
			"0C",
			"0C",
			"0C",
			"0C",
			"0C",
			"0C",
			"0C",
			// Get DTC
			"6C",
			"6C",
			"6C",
	};

	private String[] tas = {
			// Get VIN, ECM info etc...
			"10",
			"10",
			"10",
			"10",
			"10",
			"10",
			"10",
			"10",
			// Get DTC
			"10",
			"40",
			"60",
	};

	private String[] sas = {
			// Get VIN, ECM info etc...
			"F1",
			"F1",
			"F1",
			"F1",
			"F1",
			"F1",
			"F1",
			"F1",
			// Get DTC
			"F1",
			"F1",
			"F1",
	};

	private String[] commands = {
			// Get VIN, ECM info etc...
			"3C01",
			"3C02",
			"3C03",
			"3C04",
			"3C0B",
			"3C0F",
			"3C10",
			"3C11",
			// Get DTC
			"1952FF00",
			"1952FF00",
			"1952FF00",
	};

	private String[] expects = {
			// Get VIN, ECM info etc...
			"0CF1107C01",
			"0CF1107C02",
			"0CF1107C03",
			"0CF1107C04",
			"0CF1107C0B",
			"0CF1107C0F",
			"0CF1107C10",
			"0CF1107C11",
			// Get DTC
			"6CF11059",
			"6CF14059",
			"6CF16059",
	};

	private int[] timeouts = {
			// Get VIN, ECM info etc...
			COMMAND_TIMEOUT,
			COMMAND_TIMEOUT,
			COMMAND_TIMEOUT,
			COMMAND_TIMEOUT,
			COMMAND_TIMEOUT,
			COMMAND_TIMEOUT,
			COMMAND_TIMEOUT,
			COMMAND_TIMEOUT,
			// Get DTC
			GET_DTC_TIMEOUT,
			GET_DTC_TIMEOUT,
			GET_DTC_TIMEOUT,
	};*/
}
