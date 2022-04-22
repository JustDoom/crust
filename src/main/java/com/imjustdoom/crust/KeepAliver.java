package com.imjustdoom.crust;

import com.imjustdoom.crust.net.ConnectionStatus;
import com.imjustdoom.crust.net.PacketHandler;
import com.imjustdoom.crust.packet.out.KeepAlivePacketOut;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class KeepAliver extends Thread {

    @Override
    public void run() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Thread.currentThread().setName("keepalive");
                for (PacketHandler connection : Main.connections) {
                    // Ignore everyone that isn't playing.
                    if (connection.status != ConnectionStatus.PLAYING) continue;
                    KeepAlivePacketOut keepAlivePacketOut = new KeepAlivePacketOut(System.currentTimeMillis());
                    try {
                        keepAlivePacketOut.send(connection.getOut());
                    } catch (IOException e) {
                        // Swallow the exception.
                    }
                }
            }
        }, 1000L, 1000L);
    }
}
