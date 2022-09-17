package com.example.mylibrarysecond;

import static android.provider.ContactsContract.CommonDataKinds.Identity.NAMESPACE;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TestFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TestFrag extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String aadharNo;
    private String transactionID;
    private static final String TAG = "TestFrag";

    private TextView textView;
    public TestFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment test.
     */
    // TODO: Rename and change types and number of parameters
    public static TestFrag newInstance(String param1, String param2) {
        TestFrag fragment = new TestFrag();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test, container, false);
        final Button button = view.findViewById(R.id.sub_button);
        EditText editText = view.findViewById(R.id.aadhar_id);
        textView=view.findViewById(R.id.active_text);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aadharNo = editText.getText().toString();
                transactionID = "1234";
                ekyc();




            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        final String[] message = {""};
        if(requestCode == 100 && resultCode == Activity.RESULT_OK)
        {

            message[0] =data.getStringExtra("response");
            String pid= message[0];
            backendrequest(pid);
        }




    }


    public String generateCaptureRequestBody(){
        String str="<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<PidOptions ver=\"1.0\" env=\"PP\">\n" +
                "   <Opts format=\"0\" pidVer=\"2.0\" otp=\"\" wadh=\"sgydIC09zzy6f8Lb3xaAqzKquKe9lFcNR9uTvYxFp+A=\" />\n" +
                "   <CustOpts>\n" +
                "      <Param name=\"txnId\" value=\""+transactionID+"\"/>"+
                "      <Param name=\"purpose\" value=\"auth\"/>\n" +
                "      <Param name=\"language\" value=\"en\"/>\n" +
                "   </CustOpts>\n" +
                "</PidOptions>";

        return str;
    }

    public void ekyc(){

        Intent captureIntent= new Intent(Constants.CAPTURE_INTENT);
        captureIntent.putExtra(
                "request",
                generateCaptureRequestBody()
        );
        startActivityForResult(captureIntent,100);
    }

    public String generateXml(String pid, String aadharNo, String transactionId){
       // Get Current Date-Time
        Date currentDate = Calendar.getInstance().getTime();

        Date dateTime=new Date();
        android.text.format.DateFormat df = new android.text.format.DateFormat();

        //df.format("yyyy-MM-dd hh:mm:ss a", new java.util.Date());
        //Get Device Info
        String deviceInfo = android.os.Build.MODEL;
        // Return string of XMLRequest

        return "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
                "   <soap:Header xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"></soap:Header>" +
                "   <soapenv:Body>" +
                "      <ns2:eKYCRequest xmlns:ns2=\"uidaiekyc.otp.xsd.hdfcbank.com\">" +
                "         <REQ_TYPE>F</REQ_TYPE>" +
                "         <ResidentConsent>" +
                "            <rc>Y</rc>" +
                "            <mec>Y</mec>" +
                "         </ResidentConsent>" +
                "         <UID_NO></UID_NO>" +
                "         <OTP/>" +
                "         <BIO>" +
                "            <ImageType>FID</ImageType>" +
                "            <BioType/>" +
                "            <BioImage/>" +
                "         </BIO>" +
                "         <Req_Date_Time>"+df.format("yyyyMMddhhmmss", dateTime)+"</Req_Date_Time>" +
                "         <Req_No>"+"UKC:"+ transactionID+ "</Req_No>"+
                "         <Cost_Center_No/>" +
                "         <Meta>" +
                "            <fdc>NC</fdc>" +
                "            <idc>NC</idc>" +
                "            <pip>10.5.229.158</pip>" +
                "            <lot>P</lot>" +
                "            <lov>560103</lov>" +
                "         </Meta>" +
                "         <TransactionInfo>" +
                "         <Pan>6071520+"+aadharNo+"</Pan>" +
                "            <Proc_Code>130000</Proc_Code>" +
                "            <TransmDate>"+df.format("MMddhhmmss a", dateTime) +"</TransmDate>"+
                "            <Stan>007978</Stan>" +
                "            <Local_Trans_Time>"+df.format("hhmmss a", dateTime) +"</Local_Trans_Time>" +
                "            <Local_date>"+df.format("MMdd",dateTime) +"</Local_date>" +
                "            <Mcc>6012</Mcc>" +
                "            <Pos_entry_mode>019</Pos_entry_mode>" +
                "            <Pos_code>05</Pos_code>" +
                "            <AcqId>200030</AcqId>" +
                "            <RRN>031714007978</RRN>" +
                "            <CA_Tid>register</CA_Tid>" +
                "            <CA_ID>HDF10</CA_ID>" +
                "            <CA_TA>HDFC BANK LTD KanjurmarMumbai MHIN</CA_TA>" +
                "         </TransactionInfo>" +
                "         <Cert_Name/>" +
                "         <Value1/>" +
                "         <Value2/>" +
                "         <SOAStandardElements>" +
                "            <service_user/>" +
                "            <consumer_name/>" +
                "            <ADVappId>28</ADVappId>" +
                "            <ADVappName>CSP</ADVappName>" +
                "            <filler1>N</filler1>" +
                "          <filler2><![CDATA["+pid+"]]></filler2>" +
                "          <filler3><![CDATA["+deviceInfo+"]]></filler3>" +
                "                   </SOAStandardElements>" +
                "      </ns2:eKYCRequest>" +
                "   </soapenv:Body>" +
                "</soapenv:Envelope>";
    }

    public String backendrequest(String pid){
        final String[] backendResponse = new String[1];
        try {
            URL url = new URL("https://openapiuat.hdfcbank.com:9443/API/Aadhar_EKYCService_FaceAuth?wsdl");
            String gen = generateXml(pid, aadharNo, transactionID);
            textView.setText(gen);

            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "text/xml;charset=UTF-8");
            con.setDoOutput(true);
            try(OutputStream os = con.getOutputStream()) {
                byte[] input = gen.getBytes("utf-8");
                os.write(input, 0, input.length);
            }catch(Exception e) {
                textView.setText("Unable to Send request");
            }
            try(BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream()))) {
                textView.setText("Reading response");
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
            }
            catch(Exception e) {
                e.printStackTrace();
                //textView.setText("Unable to Read response");
            }
        } catch (Exception e) {
            e.printStackTrace();
            textView.setText("Error In In HTTP");
        }
        return "";
    }
}