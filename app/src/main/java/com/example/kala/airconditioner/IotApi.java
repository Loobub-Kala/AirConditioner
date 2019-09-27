package com.example.kala.airconditioner;

import android.util.Log;

import net.sf.json.JSONObject;


public class IotApi {

    private static final String baseurl = "http://api.nlecloud.com";
    private static final String sensors = "/Sensors/";
    private static final String devices = "/devices/";

    //private int deviceId = 14531;//16890;//15663
    private int deviceId = 16890;
    private String apiId = "nl_wind";
    private String  param = "{\"data\":\"1\"}";

    public WenduKu getSensor(String apiId) throws Exception {
        Log.d("调试","进入getSensor()");
        String result = null;
        //String url = "http://api.nlecloud.com/devices/15663/Sensors/m_temperature";
        String url = baseurl+devices+deviceId+sensors+apiId;
        // System.out.print(url);
        WenduKu wdk=new WenduKu();
        try {
            result = HttpRequest.sendGet(url,param);
            JSONObject json = JSONObject.fromObject(result);
            System.out.println(result);
            JSONObject json2 = JSONObject.fromObject(json.get("ResultObj"));
            System.out.println(json2.getString("ApiTag")+json2.getString("Value"));
            //result=json2.getString("Value");
            wdk.setData(json2.getString("Value"));
            wdk.setTime(json2.getString("RecordTime"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return wdk;
    }

    public void sendCommand(String apiId,String param){
        String result = null;
        //String url=baseurl+"/Cmds"+"?deviceId=15663&apiTag=m_temperature";
        String url="http://api.nlecloud.com/Cmds?deviceId="+deviceId+"&apiTag="+apiId;
        try {
            result = HttpRequest.sendPost(url,param);
            JSONObject json = JSONObject.fromObject(result);

            System.out.println(result);
            if (json.getInt("StatusCode")==0)
            {
                System.out.println(json.getString("Msg"));
                MainActivity.setIsSuccess(true);
            }
            else
                MainActivity.setIsSuccess(false);
           /*JSONObject json2 = JSONObject.fromObject(json.get("ResultObj"));
            System.out.println(json2.getString("ApiTag")+json2.getString("Value"));
            result=json2.getString("Value");*/



        } catch (Exception e) {
            e.printStackTrace();
        }
        //return result;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public void setApiId(String apiId) {
        this.apiId = apiId;
    }

    public void setParam(String param) {
        this.param = param;
    }
}