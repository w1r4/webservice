package com.prgguru.android;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
//import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import com.example.webserviceactivity.R;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.*;
import org.ksoap2.transport.*;
import android.text.method.*;

public class MainActivity extends Activity {
	private final String NAMESPACE = "urn:sap-com:document:sap:rfc:functions";
	private final String URL = "http://idesie6.abyor.com:8016/sap/bc/srt/rfc/sap/zws_get_spfli2/800/zws_get_spfli2_service/zws_get_spfli2_binding";
	private final String METHOD_NAME = "ZFM_GET_SPFLI";
	private final String SOAP_ACTION = NAMESPACE + "/" +METHOD_NAME;
	
	private String TAG = "PGGURU";
	private static String fi_input;
//	private static String fi_int2;
	private static String fe_output;
	Button b;
	TextView tv;
	EditText et_fi_input;
//	EditText et_fi_int2;
	private static String msg;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		//fi_input Edit Control
		et_fi_input = (EditText) findViewById(R.id.editText1);
		//et_fi_int2 = (EditText) findViewById(R.id.editText2);
		
		
		//fe_outputheit Text control
		tv = (TextView) findViewById(R.id.tv_result);
		tv.setMovementMethod(new ScrollingMovementMethod());
		//Button to trigger web service invocation
		b = (Button) findViewById(R.id.button1);
		//Button Click Listener
		b.setOnClickListener(new OnClickListener() {
		  public void onClick(View v) {
				
				    
					fi_input = et_fi_input.getText().toString();
//					fi_int2 = et_fi_int2.getText().toString();
					//Create instance for AsyncCallWS
					AsyncCallWS task = new AsyncCallWS();
					//Call execute 
					task.execute();
				
				}
		
	    });
	 }

	public void getResult(String int1) {
		//Create request
		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		
		request.addProperty("FI_CARRID",int1);
	
				
		//Create envelope
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		//envelope.dotNet = true;
		envelope.bodyOut = request;
		//Set output SOAP object
		//envelope.addMapping(NAMESPACE, "FI_INT",new double.class);
		
		envelope.setOutputSoapObject(request);
		//Create HTTP call object
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
		//envelope.bodyOut = androidHttpTransport;

		try {
			//Invole web service
			androidHttpTransport.debug = true;
			androidHttpTransport.call(SOAP_ACTION, envelope);
	
			SoapObject result = (SoapObject) envelope.bodyIn;
			fe_output = androidHttpTransport.responseDump;
			
			//PropertyInfo pi = new
				//PropertyInfo()
			//result.getPropertyInfo("1", pi);
			//Log.d(TAG, androidHttpTransport.requestDump
			//         + androidHttpTransport.responseDump);

		} catch (Exception e) {
			e.printStackTrace();
			msg = e.getMessage();
		}
	}

	private class AsyncCallWS extends AsyncTask<String, Void, Void> {
		@Override
		protected Void doInBackground(String... params) {
			Log.i(TAG, "doInBackground");
			getResult(fi_input);
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			Log.i(TAG, "onPostExecute");
			tv.setText(fe_output);
			//tv.setText(msg);
		}

		@Override
		protected void onPreExecute() {
			Log.i(TAG, "onPreExecute");
			tv.setText("Calculating...");
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			Log.i(TAG, "onProgressUpdate");
		}

	}

}
