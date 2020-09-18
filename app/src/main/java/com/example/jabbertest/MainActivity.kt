package com.example.jabbertest

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.fragment_first.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jivesoftware.smack.AbstractXMPPConnection
import org.jivesoftware.smack.StanzaListener
import org.jivesoftware.smack.chat2.Chat
import org.jivesoftware.smack.chat2.ChatManager
import org.jivesoftware.smack.filter.StanzaFilter
import org.jivesoftware.smack.filter.StanzaTypeFilter
import org.jivesoftware.smack.packet.Presence
import org.jivesoftware.smack.roster.Roster
import org.jivesoftware.smack.roster.RosterEntry
import org.jivesoftware.smack.roster.RosterListener
import org.jivesoftware.smack.tcp.XMPPTCPConnection
import org.jivesoftware.smackx.muc.MultiUserChatManager
import org.jxmpp.jid.EntityBareJid
import org.jxmpp.jid.impl.JidCreate
import org.jxmpp.jid.parts.Resourcepart
import org.jxmpp.jid.util.JidUtil
import timber.log.Timber


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Timber.plant(Timber.DebugTree())

    }

    private lateinit var _connection: AbstractXMPPConnection
    private var chatMade: Boolean
    private lateinit var chat: Chat
    private lateinit var _jid: EntityBareJid

    init {
        GlobalScope.launch(Dispatchers.IO) {
            _connection = connectionParamsAndBuilder()
        }
        chatMade = false
    }

    /**
     * Basic information to establish a connection
     */
    suspend fun connectionParamsAndBuilder(): AbstractXMPPConnection {
        Timber.v("Configuring conenction")
        // Create a connection and login to the example.org XMPP service.
        // Create a connection and login to the example.org XMPP service.
        val connection: AbstractXMPPConnection =
            XMPPTCPConnection("android", "1234", "pimux.de")
        connection.connect().login()

        if (!chatMade) {
            val chatManager = ChatManager.getInstanceFor(connection)
            _jid = JidCreate.entityBareFrom("meop@pimux.de")
            chat = chatManager.chatWith(_jid)
            makeListener(chatManager)
            chatMade = true
        }

        return connection
    }

    fun sendClick(v: View) {
        Timber.v("clicked")
        val msg = etMutiInput.text.toString()

        chat.send(msg)
    }

    fun makeListener(chatManager: ChatManager) {
        chatManager.addIncomingListener { from, message, chat ->
            System.out.println("New message from " + from + ": " + message.getBody())
            updateUI(from.toString(), message.body)
        }
        Timber.d("Listener made...")
    }

    fun updateUI(from: String, msg: String) {
        this@MainActivity.runOnUiThread(java.lang.Runnable {
            etFrom.setText(from)
            etGotIt.setText(msg)
        })
    }

    fun setOffline(v: View) {
        Timber.d("Presence set to offline...")
        val presence = Presence(Presence.Type.unavailable)
        presence.status = "Gone Fishing"
        _connection.sendStanza(presence)
    }

    fun setOnline(v: View) {
        Timber.d("Presence set to online...")
        val presence = Presence(Presence.Type.available)
        presence.status = "I am here"
        _connection.sendStanza(presence)
    }

}