package wme.mt.de.infbox_android_g43;

import android.os.AsyncTask;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/*
 Task to download text files from the inf_box server.
 */
public class DownloadTextTask extends AsyncTask<String, Void, String> {
    TextView textView;
    String response;

    public DownloadTextTask(TextView view){
        textView = view;
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            requestText(params[0]);
        } catch (Exception e){
            e.printStackTrace();
        }

        return response;
    }

    @Override
    protected void onPostExecute(String s) {
        textView.setText(response);
    }

    private void requestText(String ustring) throws Exception {
        URL url = new URL(ustring);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();

        connection.setRequestProperty("Accept-Charset", "UTF-8");
        connection.setRequestProperty("Content-Type", "text/plain; charset=utf-8");

        // ISO-8859-1 to display umlaute correctly.
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "ISO-8859-1"));

        String line;
        StringBuilder buffer = new StringBuilder();

        while ((line = in.readLine()) != null){
            buffer.append(line).append("\n");
        }

        in.close();

        response = buffer.toString();
    }
}
