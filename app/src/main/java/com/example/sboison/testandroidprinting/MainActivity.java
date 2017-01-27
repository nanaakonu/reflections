package com.example.sboison.testandroidprinting;

import android.annotation.TargetApi;
import android.content.Context;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintJob;
import android.print.PrintManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button printButton;
    private WebView mWebView;
 //   private PrintJob mPrintJobs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        printButton = (Button) findViewById(R.id.clickbutton);
        printButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             doWebViewPrint();
            }
        });
    }

    private void doWebViewPrint(){
        WebView webView= new WebView(this);

        // Generate an HTML document on the fly:
        String htmlDocument = "<html><body><h1>Test Content</h1><p>Testing, " +
                "testing, testing...</p></body></html>";
        webView.loadDataWithBaseURL(null, htmlDocument, "text/HTML", "UTF-8", null);

        webView.setWebViewClient(new WebViewClient(){

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String Url) {

                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                Log.i("Page Finished?", "page finished loading " + url);
                createWebPrintJob(view);
                mWebView = null;
            }
        });



        // Keep a reference to WebView object until you pass the PrintDocumentAdapter
        // to the PrintManager
        mWebView = webView;

    }

    @TargetApi(19)
    private void createWebPrintJob(WebView jobWebview){
        // PrintManager Instance
        PrintManager printManager= (PrintManager) this.getSystemService(Context.PRINT_SERVICE);

        // Get a print adapter instance
        PrintDocumentAdapter printAdapter;

            printAdapter = jobWebview.createPrintDocumentAdapter();


        // Create a print job with name and adapter instance
        String jobName = getString(R.string.app_name) + " Document";
        PrintJob printJob = printManager.print(jobName, printAdapter,
                new PrintAttributes.Builder().build());



        // Save the job object for later status checking
      //  mPrintJobs.add(printJob);
    }

}
