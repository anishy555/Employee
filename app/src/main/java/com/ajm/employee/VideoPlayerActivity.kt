package com.ajm.employee

import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.webkit.*
import androidx.appcompat.app.AppCompatActivity
import com.ajm.employee.utils.SnackBarUtils
import java.io.File


class VideoPlayerActivity : AppCompatActivity() {

    private val VIMEO_BASE_URL = "http://vimeo.com"
    private val VIMEO_PLAYER_URL = "https://player.vimeo.com/video/"
    var PARAM_VIDEO = "param_video"

    private var videoView: WebView? = null
    private var videoUrl: String? = null
    private val videoId: String? = null
    private var snackBarUtils: SnackBarUtils? = null
    var file: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_player)

        initUiControls()
    }

    fun initUiControls() {
        videoView = findViewById(R.id.videoView)
        snackBarUtils = SnackBarUtils()
        if (intent.extras!!.containsKey(PARAM_VIDEO)) {
            val uriiVideo: Uri = Uri.parse(intent.extras!!.getString(PARAM_VIDEO))
            //videoUrl = uriiVideo.getPath().toString()
            videoUrl = intent.extras!!.getString(PARAM_VIDEO)
             file =   File(uriiVideo.path.toString())
            Log.d("videoUrl", videoUrl.toString())
            val photoUpload = File(uriiVideo.getPath().toString())
            /* val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream((uriimage)))
             image?.setImageBitmap(bitmap)*/
        }


//        if (videoWidth <= 0) {
//            videoWidth = DEFAULT_VIDEO_WIDTH/2;
//        }
//        if (videoHeight <= 0) {
//            videoHeight = DEFAULT_VIDEO_HEIGHT/2;
//        }

//        Log.e("VideoPlayerActivity",">>>> videoSize = " + videoWidth +" x " + videoHeight);
        videoView?.setBackgroundColor(Color.BLACK)
        videoView?.setInitialScale(1)
        videoView?.setWebViewClient(mWebViewClient)
        videoView?.getSettings()?.javaScriptEnabled = true
        videoView?.getSettings()?.loadWithOverviewMode = true
        videoView?.setWebChromeClient(WebChromeClient())
        videoView?.getSettings()?.useWideViewPort = true
        videoView?.getSettings()?.mediaPlaybackRequiresUserGesture = false
        videoView?.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY)
        val displaymetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displaymetrics)

    }

    override fun onPause() {
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        playVideoUsingPath()
    }


    override fun onDestroy() {
        stopLoading()
        super.onDestroy()
    }

    private fun playVideoUsingPath() {
        val cssStyle =
            "<style type=\"text/css\"> html, body { overflow-x: hidden; overflow-y: hidden; width:100%; height: 100%; margin: 0px;padding: 0px; } .container { width: 100%; height: 100%;} .container {  display: flex; align-items: center; justify-content: center; } </style>"
        val VIDEO_URL ="file://$videoUrl" //VIMEO_PLAYER_URL + this.videoId + "?autoplay=1";
        val headTag =
            "<head> <meta charset=\"UTF-8\"><meta name=\"viewport\" content=\"target-densitydpi=high-dpi\" /> <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\"> $cssStyle </head>"
        val iframeTag =
            "style=\"background:black;\" width='100%' height='100%' src=\"$VIDEO_URL\" frameborder=\"0\" mozallowfullscreen webkitallowfullscreen allowfullscreen"
        Log.e(
            VideoPlayerActivity::class.java.name,
            "<html>$headTag<iframe $iframeTag ></iframe></html>"
        )
        videoView!!.loadDataWithBaseURL(
            VIMEO_BASE_URL,
            "<html>$headTag<body><div class=\"container\"><iframe $iframeTag ></iframe></div></body></html>",
            "text/html",
            "UTF-8",
            null
        )
    }

    private fun stopLoading() {
        if (videoView != null) {
            videoView!!.onPause()
            videoView!!.stopLoading()
            videoView!!.loadUrl("")
            videoView!!.reload()
            videoView!!.destroy()
            videoView = null
        }
    }

    private val mWebViewClient: WebViewClient = object : WebViewClient() {
        override fun onReceivedError(
            view: WebView,
            request: WebResourceRequest,
            error: WebResourceError
        ) {
            try {
                videoView?.stopLoading()
            } catch (e: Exception) {
                Log.e("error", e.toString())
            }

            if (videoView!!.canGoBack()) {
                videoView!!.goBack()
            }

            videoView!!.loadUrl("about:blank")
            Log.e("errormessage", error.description.toString())
            Log.e("request", request.url.toString())

            snackBarUtils?.showSnackBar(
                findViewById(R.id.layoutVideoPlayer),
                resources.getString(R.string.msg_video_player_network_error)
            )
            super.onReceivedError(view, request, error)
        }

         override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
             super.onPageStarted(view, url, favicon)
             Log.e("urlmessage", url.toString())
         }


        /* override fun onReceivedSslError(
             view: WebView?,
             handler: SslErrorHandler,
             error: SslError?
         ) {
             handler.proceed()
         }*/

        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            view.loadUrl(url)
            return true
        }
    }
}
