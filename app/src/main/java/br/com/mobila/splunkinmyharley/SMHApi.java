package br.com.mobila.splunkinmyharley;

/**
 * Created by Leonardo Saganski on 13/12/16.
 */

import android.os.Handler;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SMHApi implements Response.ErrorListener {

    private static SMHApi instance;

    public static SMHApi shared() {
        if (instance == null)
            instance = new SMHApi();

        return instance;
    }

    @Override
    public void onErrorResponse(VolleyError error) {

        Holder.shared().UIHandler.sendEmptyMessage(101);

        if (error != null) {
            if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                Toast.makeText(Holder.shared().context, "Sem conexão à internet : " + error.getMessage(), Toast.LENGTH_LONG).show();  // Timeout error
            } else if (error instanceof AuthFailureError) {
                Toast.makeText(Holder.shared().context, "Erro na autenticação : " + error.getMessage(), Toast.LENGTH_LONG).show();  // Auth error
            } else if (error instanceof ServerError) {
                Toast.makeText(Holder.shared().context, "Erro no servidor : " + error.getMessage(), Toast.LENGTH_LONG).show();   // server error
            } else if (error instanceof NetworkError) {
                Toast.makeText(Holder.shared().context, "Sem internet : " + error.getMessage(), Toast.LENGTH_LONG).show();  // network error
            } else if (error instanceof ParseError) {
                Toast.makeText(Holder.shared().context, "Erro de Conversão : " + error.getMessage(), Toast.LENGTH_LONG).show();  // Parse Error
            }

            if (error.networkResponse != null) {
                if (error.networkResponse.statusCode == 404) {
                    Toast.makeText(Holder.shared().context, "Erro no servidor : " + error.getMessage(), Toast.LENGTH_LONG).show();  // 404 error
                }
            }
        } else {
            Toast.makeText(Holder.shared().context, "Erro desconhecido : " + error.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void SendToSplunk(final String j) {
        try {
            String path = "http://splunk.geoffmartin.com:8088/services/collector";
            int verb = Request.Method.POST;

            StringRequest req = new StringRequest(verb, path,
                    new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response) {
                            try {
                                boolean result = false;

                                Holder.shared().UIHandler.sendEmptyMessage(101);

                                if (response.length() > 1) {
                                    JSONObject obj = new JSONObject(response);
                                    if (!obj.isNull("text")) {
                                        if (obj.getString("text").equals("Success")) {
                                            result = true;
                                        }
                                    }
                                }
                            } catch (JSONException e) {}
                        }
                    }, this){

/*                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String, String> params = new HashMap<String, String>();
                    params.put("username", username);
                    params.put("password", Utils.HashMD5(password));
                    return params;
                }*/

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> params = new HashMap<String, String>();
                    params.put("Authorization", "Splunk 04A89941-C91A-41FE-85D6-C40233A5736C");
                    return params;
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    byte[] result = j.getBytes();
                    return result;
                }
            };

            req.setRetryPolicy(new DefaultRetryPolicy(
                    10000,
                    3,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            // Adding request to request queue
            VolleyHelper.getInstance().addToRequestQueue(req, "tag");

        } catch (Exception e) { }
    }


}
