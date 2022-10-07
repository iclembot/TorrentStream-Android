/*
 *
 *  * This file is part of TorrentStreamer-Android.
 *  *
 *  * TorrentStreamer-Android is free software: you can redistribute it and/or modify
 *  * it under the terms of the GNU Lesser General Public License as published by
 *  * the Free Software Foundation, either version 3 of the License, or
 *  * (at your option) any later version.
 *  *
 *  * TorrentStreamer-Android is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  * GNU Lesser General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU Lesser General Public License
 *  * along with TorrentStreamer-Android. If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.github.se_bastiaan.torrentstreamer.sample

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.Button
import android.widget.MediaController
import android.widget.ProgressBar
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.github.se_bastiaan.torrentstream.StreamStatus
import com.github.se_bastiaan.torrentstream.Torrent
import com.github.se_bastiaan.torrentstream.TorrentOptions
import com.github.se_bastiaan.torrentstream.TorrentStream
import com.github.se_bastiaan.torrentstream.listeners.TorrentListener
import java.io.UnsupportedEncodingException
import java.net.URI
import java.net.URLDecoder
import android.content.Context
import android.widget.SearchView

interface AsyncResponse {
    fun processFinish(output: Any?)
}

@SuppressLint("SetTextI18n")
public class MainActivity : AppCompatActivity(), TorrentListener {
    lateinit var button: Button
    lateinit var searchBox: SearchView
    private lateinit var progressBar: ProgressBar
    private lateinit var torrentStream: TorrentStream
    private lateinit var simpleVideoView: VideoView
    private lateinit var mediaControls: MediaController
    private lateinit var parser: ezParser
    var searchText= ""
    private var theDomain= "eztv.re"
    private var queryText= "/search/"
    // private var streamUrl = "magnet:?xt=urn:btih:08ada5a7a6183aae1e09d831df6748d566095a10&dn=Sintel&tr=udp%3A%2F%2Fexplodie.org%3A6969&tr=udp%3A%2F%2Ftracker.coppersurfer.tk%3A6969&tr=udp%3A%2F%2Ftracker.empire-js.us%3A1337&tr=udp%3A%2F%2Ftracker.leechers-paradise.org%3A6969&tr=udp%3A%2F%2Ftracker.opentrackr.org%3A1337&tr=wss%3A%2F%2Ftracker.btorrent.xyz&tr=wss%3A%2F%2Ftracker.fastcast.nz&tr=wss%3A%2F%2Ftracker.openwebtorrent.com&ws=https%3A%2F%2Fwebtorrent.io%2Ftorrents%2F&xs=https%3A%2F%2Fwebtorrent.io%2Ftorrents%2Fsintel.torrent"
    // "magnet:?xt=urn:btih:88594aaacbde40ef3e2510c47374ec0aa396c08e&dn=bbb%5Fsunflower%5F1080p%5F30fps%5Fnormal.mp4&tr=udp%3A%2F%2Ftracker.openbittorrent.com%3A80%2Fannounce&tr=udp%3A%2F%2Ftracker.publicbt.com%3A80%2Fannounce&ws=http%3A%2F%2Fdistribution.bbb3d.renderfarming.net%2Fvideo%2Fmp4%2Fbbb%5Fsunflower%5F1080p%5F30fps%5Fnormal.mp4"
   // private var streamUrl =  "magnet:?xt=urn:btih:09A15869D417200F399DCAB89E6E9F494C7416EC&dn=SHADOWS%20HOUSE%20S02E11%20AAC%20MP4-Mobile&tr=udp%3A%2F%2Ftracker.coppersurfer.tk%3A6969%2Fannounce&tr=udp%3A%2F%2Ftracker.openbittorrent.com%3A6969%2Fannounce&tr=udp%3A%2F%2F9.rarbg.to%3A2710%2Fannounce&tr=udp%3A%2F%2F9.rarbg.me%3A2780%2Fannounce&tr=udp%3A%2F%2F9.rarbg.to%3A2730%2Fannounce&tr=udp%3A%2F%2Ftracker.opentrackr.org%3A1337&tr=http%3A%2F%2Fp4p.arenabg.com%3A1337%2Fannounce&tr=udp%3A%2F%2Ftracker.torrent.eu.org%3A451%2Fannounce&tr=udp%3A%2F%2Ftracker.tiny-vps.com%3A6969%2Fannounce&tr=udp%3A%2F%2Fopen.stealth.si%3A80%2Fannounce"
    private var streamUrl = "magnet:?xt=urn:btih:88594aaacbde40ef3e2510c47374ec0aa396c08e&dn=bbb%5Fsunflower%5F1080p%5F30fps%5Fnormal.mp4&tr=udp%3A%2F%2Ftracker.openbittorrent.com%3A80%2Fannounce&tr=udp%3A%2F%2Ftracker.publicbt.com%3A80%2Fannounce&ws=http%3A%2F%2Fdistribution.bbb3d.renderfarming.net%2Fvideo%2Fmp4%2Fbbb%5Fsunflower%5F1080p%5F30fps%5Fnormal.mp4"
    private var searchUrl = "https://eztv.re/search/the%20sandman%20s01e02"

    var onSearchClickListener = View.OnClickListener {
        if (!torrentStream.isStreaming) {
            try {
                searchText = "the sandman s01e01" // TODO: connect to searchBox ui
                val uri = URI("https", theDomain, queryText, searchText)
                searchUrl = uri.toURL().toString()
            } catch(e: UnsupportedEncodingException) {
                e.printStackTrace()
            }


            parser.parseURL(searchUrl)
        }
    }


    var onStreamClickListener = View.OnClickListener {
        if (searchText == "") {
            try {
                searchText = "lord of the rings s01e07" // searchBox.query.toString() // "the sandman s01e01" // connect to searchbox ui
                val uri = URI("https", theDomain, queryText, searchText)
                streamUrl = uri.toURL().toString()
            } catch (e: UnsupportedEncodingException) {
                e.printStackTrace()
            }
            parser.parseURL(searchUrl)
        }
        progressBar.progress = 0
        if (torrentStream.isStreaming) {
            torrentStream.stopStream()
            button.text = "Start stream"
            return@OnClickListener
        }
        torrentStream.startStream(streamUrl)
        button.text = "Stop stream"
        searchText = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        simpleVideoView = findViewById(R.id.videoView)
        val action = intent.action
        val data = intent.data
        if (action != null && action == Intent.ACTION_VIEW && data != null) {
            try {
                streamUrl = URLDecoder.decode(data.toString(), "utf-8")
            } catch (e: UnsupportedEncodingException) {
                e.printStackTrace()
            }
        }
        parser= ezParser(this)
        val torrentOptions = TorrentOptions.Builder()
            .saveLocation(filesDir)
            .removeFilesAfterStop(true)
            .build()

        torrentStream = TorrentStream.init(torrentOptions)
        torrentStream.addListener(this)
        button = findViewById(R.id.button)
        button.setOnClickListener(onStreamClickListener)
        progressBar = findViewById(R.id.progress)
        progressBar.max = 100
        searchBox = findViewById(R.id.searchBox)
    }

    override fun onStreamPrepared(torrent: Torrent) {
        Log.d(TORRENT, "onStreamPrepared")
        // If you set TorrentOptions#autoDownload(false) then this is probably the place to call
        // torrent.startDownload();
    }

    override fun onStreamStarted(torrent: Torrent) {
        Log.d(TORRENT, "onStreamStarted")
    }

    override fun onStreamError(torrent: Torrent, e: Exception) {
        Log.e(TORRENT, "onStreamError", e)
        button.text = "Start stream"
    }

    override fun onStreamReady(torrent: Torrent) {
        progressBar.progress = 100
        val mediaFile = torrent.videoFile
        Log.d(TORRENT, "onStreamReady: $mediaFile")
        // jc 22
        mediaControls = MediaController(this@MainActivity)
        mediaControls.setAnchorView(simpleVideoView)
        simpleVideoView.setMediaController(mediaControls)

      //  simpleVideoView.setVideoURI(FileProvider.getUriForFile(this@MainActivity, authority, mediaFile))
      //  simpleVideoView.start()
        // Create a sharing intent

        startActivity(Intent().apply {
            action = Intent.ACTION_VIEW
            val authority = "com.github.se_bastiaan.torrentstreamer.sample.provider"
            setDataAndType(FileProvider.getUriForFile(this@MainActivity, authority, mediaFile),MimeTypeMap.getSingleton().getMimeTypeFromExtension(mediaFile.extension))
            flags = Intent.FLAG_GRANT_READ_URI_PERMISSION or
                    Intent.FLAG_ACTIVITY_CLEAR_TOP
        })

    }

    override fun onStreamProgress(torrent: Torrent, status: StreamStatus) {
        if (status.bufferProgress <= 100 && progressBar.progress < 100 && progressBar.progress != status.bufferProgress) {
            Log.d(TORRENT, "Progress: " + status.bufferProgress)
            progressBar.progress = status.bufferProgress
        }
    }

    override fun onStreamStopped() {
        Log.d(TORRENT, "onStreamStopped")
    }

    companion object {
        private const val TORRENT = "Torrent"
    }
}