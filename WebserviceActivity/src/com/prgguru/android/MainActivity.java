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
	//private final String URL = "http://idesie6.abyor.com:8016/sap/bc/srt/rfc/sap/zws_simple_add/800/simple_add_service/simple_add_binding";
	//private final String METHOD_NAME = "ZFM_SIMPLE_ADD";
	private final String URL = "http://idesie6.abyor.com:8016/sap/bc/srt/rfc/sap/zws_get_spfli2/800/zws_get_spfli2_service/zws_get_spfli2_binding";
	private final String METHOD_NAME = "ZFM_GET_SPFLI";
	private final String SOAP_ACTION = NAMESPACE + "/" +METHOD_NAME;
	
	private String TAG = "PGGURU";
	private static String fi_int1;
	private static String fi_int2;
	private static String fe_int;
	Button b;
	TextView tv;
	EditText et_fi_int1;
	EditText et_fi_int2;
	private static String msg;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		//fi_int1 Edit Control
		et_fi_int1 = (EditText) findViewById(R.id.editText1);
		et_fi_int2 = (EditText) findViewById(R.id.editText2);
		
		
		//fe_intheit Text control
		tv = (TextView) findViewById(R.id.tv_result);
		tv.setMovementMethod(new ScrollingMovementMethod());
		//Button to trigger web service invocation
		b = (Button) findViewById(R.id.button1);
		//Button Click Listener
		b.setOnClickListener(new OnClickListener() {
		  public void onClick(View v) {
				
				    
					fi_int1 = et_fi_int1.getText().toString();
					fi_int2 = et_fi_int2.getText().toString();
					//Create instance for AsyncCallWS
					AsyncCallWS task = new AsyncCallWS();
					//Call execute 
					task.execute();
				
				}
		
	    });
	 }

	public void getResult(String int1, String int2) {
		//Create request
		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		
		//Property which holds input parameters
		//PropertyInfo fi_int1 = new PropertyInfo();
		//Set Name
		//fi_int1.setName("FI_INT");
		//Set Value
		//fi_int1.setValue(int1);
		//Set dataType
		//fi_int1.setType(double.class);
		//fi_int1.setNamespace(NAMESPACE);
		
		//Add the property to request object
		//request.addProperty(fi_int1);
		//request.addProperty("FI_INT1",int1);
		//request.addProperty("FI_INT2",int2);
		request.addProperty("FI_CARRID",int1);
	
		//request.addProperty("FI_INT","23");
	
	
		
		
		//PropertyInfo fi_int2 = new PropertyInfo();
		//Set Name
		//fi_int2.setName("FI_INT2");
		//Set Value
		//fi_int2.setValue(int2);
		//Set dataType
		//fi_int2.setType(double.class);
		//Add the property to request object
		//request.addProperty(fi_int2);
		
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
	
			
			//Get the response
			//SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
			
			//SoapObject response = (SoapObject) envelope.getResponse();
			SoapObject result = (SoapObject) envelope.bodyIn;
			//Assign it to fe_int static variable
			
			//fe_int = response.getProperty("FE_INT").toString();
			//fe_int = result.getProperty("FE_INT").toString();
			fe_int = androidHttpTransport.responseDump;
			
			//PropertyInfo pi = new
				//PropertyInfo();
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
			getResult(fi_int1,fi_int2);
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			Log.i(TAG, "onPostExecute");
			tv.setText(fe_int);
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
