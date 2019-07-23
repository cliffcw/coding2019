package main.com.chenwan.coding2019.download;

import main.com.chenwan.coding2019.download.api.Connection;

import java.io.RandomAccessFile;
import java.util.concurrent.CyclicBarrier;

/**
 * @program: base
 * @description: 线程下载
 * @author: cliffcw
 * @create: 2019-06-23 12:39
 */
public class DownloadThread extends Thread {

    Connection conn;
    int startPos;
    int endPos;
    CyclicBarrier barrier;
    String localFile;

    DownloadThread(Connection conn, int startPos, int endPos, String localFile, CyclicBarrier barrier) {
        this.conn = conn;
        this.startPos = startPos;
        this.endPos = endPos;
        this.localFile = localFile;
        this.barrier = barrier;
    }

    public void run() {


        try {
            System.out.println("Begin to read [" + startPos + "-" + endPos + "]");

            byte[] data = conn.read(startPos, endPos);

            RandomAccessFile file = new RandomAccessFile(localFile, "rw");

            file.seek(startPos);

            file.write(data);

            conn.close();

            barrier.await(); //等待别的线程完成

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
