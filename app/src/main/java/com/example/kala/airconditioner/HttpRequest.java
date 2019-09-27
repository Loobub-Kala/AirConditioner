package com.example.kala.airconditioner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

public class HttpRequest {


    /**
     * 向指定URL发送GET方法的请求
     * @param url   发送请求的URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        String accessToken = "E41D737B6F7FC7C32F2240BB51BC72CB6B1EA3741D91D9C53E82BAF4A4DE9CEB005D0B0FB2A600D18BD1BC015D5FB49177F997C7529661EE9DDEC688EA965BC98503494185FC569AC763B7CF127C3A83C55F816CE8732F39F97930512A1F16F64CA8D7DC3AF8093EF1AC9595506518E3C6D8484574124046A2FA9617177D86F1CE58146F7353DA2E09489A5FD025B5B1A72298B09EB4AF1F203AAE9ECEA57BAF8F1778622A1EEB6873928AD1767976362FC6FCF5B9A981B4E994273F6B7CE486BE4E3A3E5FA70D9692643C605F1CEEECF87F5A4440074F04DF61E876A76888B5E14FA48A1684EE407FFC025B8FA04C73";
        //String accessToken = "ECC9E64D2A49A619B9EAB8AE6F838A423828C3FF6C406E4F2F9C2B8BDCDA1500F4451AFC30533530DD596ECC3D00322B81529F961701F90858049F2EDD0B16AE0236D77A377B991BF7516B17ADC006ACC3BDC10AEBB63E9A69AD1CBCC00F003BB6488D32B459CD3D484D9D0EBF21D294FCFBED097553012BA4517BE0AC33904046C9632A3EFA3F765D9CE9788646F9B1DB08697690BC2341213800CA06AD17A657D09958697B2F136D8CBC65FD79DBCDBFDBF344F5AB575217A40A811D4EB2C214847501DF58481C207B0F623D6F211B23D579A98E786D9FD358D5E0530E5BE09012FC8446CDFE0EA16FA014B3C3A3A7";
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("AccessToken", accessToken);

            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     * @param url   发送请求的 URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        String accessToken = "E41D737B6F7FC7C32F2240BB51BC72CB6B1EA3741D91D9C53E82BAF4A4DE9CEB005D0B0FB2A600D18BD1BC015D5FB49177F997C7529661EE9DDEC688EA965BC98503494185FC569AC763B7CF127C3A83C55F816CE8732F39F97930512A1F16F64CA8D7DC3AF8093EF1AC9595506518E3C6D8484574124046A2FA9617177D86F1CE58146F7353DA2E09489A5FD025B5B1A72298B09EB4AF1F203AAE9ECEA57BAF8F1778622A1EEB6873928AD1767976362FC6FCF5B9A981B4E994273F6B7CE486BE4E3A3E5FA70D9692643C605F1CEEECF87F5A4440074F04DF61E876A76888B5E14FA48A1684EE407FFC025B8FA04C73";
        //String accessToken = "ECC9E64D2A49A619B9EAB8AE6F838A423828C3FF6C406E4F2F9C2B8BDCDA1500F4451AFC30533530DD596ECC3D00322B81529F961701F90858049F2EDD0B16AE0236D77A377B991BF7516B17ADC006ACC3BDC10AEBB63E9A69AD1CBCC00F003BB6488D32B459CD3D484D9D0EBF21D294FCFBED097553012BA4517BE0AC33904046C9632A3EFA3F765D9CE9788646F9B1DB08697690BC2341213800CA06AD17A657D09958697B2F136D8CBC65FD79DBCDBFDBF344F5AB575217A40A811D4EB2C214847501DF58481C207B0F623D6F211B23D579A98E786D9FD358D5E0530E5BE09012FC8446CDFE0EA16FA014B3C3A3A7";
        try {
            URL realUrl = new URL(url);
            //System.out.println("111");
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("AccessToken", accessToken);
            //3E1F4E2FE08CE18D0439D288CC67E3152724E5E562B0B28A1877F250CA10F0351483D4D0082A5FE46FBAFDCCB1F6AA801417A42C014E903859DCBBF15A039022F25A0A98E70C85B5E1D1264DA14DA2F458BD9576C538A61134327A9BBB8CE6E3C3890DC13DF6AD16C2D2CEF9C1173989C965DC246140743267A9B1477E12D9D788F313AADAFCD03806CD97EB1FE484C63B6C9D3F39A9ADC511A31F1A6C9203DD28B1A740844A85A7C32216B179CFD9A226C2D2B623C51E5A9C3D1C00607F816E207C8666FCC0C55C5F515AD5915D9886E3490B11217064C92C14113A6CD8B83183B56BEFE08EF57CF4485657ED27078F
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            //1.获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            //2.中文有乱码的需要将PrintWriter改为如下
            //out=new OutputStreamWriter(conn.getOutputStream(),"UTF-8")
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        System.out.println("post推送结果：" + result);
        return result;
    }


}

