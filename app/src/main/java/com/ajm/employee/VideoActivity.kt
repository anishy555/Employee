package com.ajm.employee

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import com.ajm.employee.utils.SnackBarUtils


class VideoActivity : AppCompatActivity() {


    var PARAM_VIDEO = "param_video"


    private var videoUrl: String? = null
    private val videoId: String? = null
    private var snackBarUtils: SnackBarUtils? = null
    private var videoView: VideoView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)

        initUiControls()
    }

    fun initUiControls() {
      //  videoView = findViewById(R.id.videoView)
        snackBarUtils = SnackBarUtils()
        if (intent.extras!!.containsKey(PARAM_VIDEO)) {
            val uriiVideo: Uri = Uri.parse(intent.extras!!.getString(PARAM_VIDEO))
            this.videoUrl = uriiVideo.getPath().toString()
           /* videoView?.setVideoURI(uriiVideo);
            videoView?.start();*/
        }
        val vimeoVideo =
            "<html><body><iframe width=\"420\" height=\"315\" src=\"https://player.vimeo.com/"+videoUrl+"\" frameborder=\"0\" allowfullscreen></iframe></body></html>"

        val webView = findViewById<View>(R.id.myWebView) as WebView
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                webView: WebView,
                request: WebResourceRequest
            ): Boolean {
                webView.loadUrl(request.url.toString())
                return true
            }
        }
        val webSettings = webView.settings
        webSettings.javaScriptEnabled = true
        webView.loadData(vimeoVideo, "text/html", "utf-8")

    }

}
