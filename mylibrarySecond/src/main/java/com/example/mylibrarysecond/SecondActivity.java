package com.example.mylibrarysecond;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class SecondActivity extends Activity {

    TextView textView1;
    Button button1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100 && resultCode == Activity.RESULT_OK)
        {
            String message=data.getStringExtra("response");
            textView1.setText(message);
        }else if(requestCode==400 && resultCode == Activity.RESULT_OK){
            String message=data.getStringExtra("response");
            textView1.setText(message);
        }
    }

    public String generateCaptureRequestBody(String transactionID){
        String str="<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<PidOptions ver=\"1.0\" env=\"PP\">\n" +
                "   <Opts format=\"0\" pidVer=\"2.0\" otp=\"\" wadh=\"sgydIC09zzy6f8Lb3xaAqzKquKe9lFcNR9uTvYxFp+A=\" />\n" +
                "   <CustOpts>\n" +
                "      <Param name=\"txnId\" value=\"${transactionID}\"/>\n" +
                "      <Param name=\"purpose\" value=\"auth\"/>\n" +
                "      <Param name=\"language\" value=\"en\"/>\n" +
                "   </CustOpts>\n" +
                "</PidOptions>";

        return str;
    }

    public void ekyc(String transactionID){

        Intent captureIntent= new Intent(Constants.CAPTURE_INTENT);
        captureIntent.putExtra(
                "request",
                generateCaptureRequestBody(transactionID)
        );
        startActivityForResult(captureIntent,100);
    }

}
