package com.example.jabbertest

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.fragment_first.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jivesoftware.smack.AbstractXMPPConnection
import org.jivesoftware.smack.SmackConfiguration
import org.jivesoftware.smack.StanzaListener
import org.jivesoftware.smack.chat2.Chat
import org.jivesoftware.smack.chat2.ChatManager
import org.jivesoftware.smack.filter.AndFilter
import org.jivesoftware.smack.filter.FromMatchesFilter
import org.jivesoftware.smack.filter.StanzaTypeFilter
import org.jivesoftware.smack.packet.Message
import org.jivesoftware.smack.packet.Presence
import org.jivesoftware.smack.packet.Stanza
import org.jivesoftware.smack.roster.Roster
import org.jivesoftware.smack.tcp.XMPPTCPConnection
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration
import org.jivesoftware.smackx.jiveproperties.JivePropertiesManager
import org.jxmpp.jid.EntityBareJid
import org.jxmpp.jid.impl.JidCreate
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

        // Connection builder
        val config = XMPPTCPConnectionConfiguration.builder()
            .setUsernameAndPassword("android", "1234")
            .setXmppDomain("pimux.de")
            .setHost("pimux.de")
            .setPort(5222)
            .setResource("MobileAndroid")
            .build()
        val connection = XMPPTCPConnection(config)
        connection.connect().login()
        Roster.setDefaultSubscriptionMode(Roster.SubscriptionMode.accept_all)

        // Simple Connection
//        val connection: AbstractXMPPConnection =
//            XMPPTCPConnection("android", "1234", "pimux.de")
//        connection.connect().login()

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
        stanzalistener()
        val msg = etMutiInput.text.toString()
        chat.send(msg)
    }

    fun makeListener(chatManager: ChatManager) {
        chatManager.addIncomingListener { from, message, chat ->
            System.out.println("New message from " + from + ": " + message.getBody())
            updateUI(from.toString(), message.body)
            echoIncomingMessage(from, message, chat)
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

    fun echoIncomingMessage(from: EntityBareJid, msg: Message, chat: Chat) {
        // Send back the same text the other user sent us
        chat.send("you told me ${msg.body}")
    }

    fun stanzalistener() {
        Timber.d("Stanza Listener...")
        val filter = AndFilter(
            StanzaTypeFilter.MESSAGE,
            FromMatchesFilter.create(JidCreate.entityBareFrom("myphone@pimux.de"))
        )
        val stanzaCollector = _connection.createStanzaCollector(filter)
        val listen = StanzaListener {
            println("$it")
        }
        _connection.addAsyncStanzaListener(listen, filter)
    }

}