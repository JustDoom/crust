package com.imjustdoom.crust;

import com.imjustdoom.crust.network.ConnectionStatus;
import com.imjustdoom.crust.network.PacketHandler;
import com.imjustdoom.crust.network.packet.out.KeepAlivePacketOut;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class KeepAlive extends Thread{

    @Override
    public void run() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Thread.currentThread().setName("keepalive");
                for (PacketHandler connection : Main.getServer().getConnections()) {
                    // Ignore everyone that isn't playing.
                    if (connection.getStatus() != ConnectionStatus.PLAYING) continue;
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
